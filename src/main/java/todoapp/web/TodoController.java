package todoapp.web;


import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractView;
import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.domain.Todo;
import todoapp.web.convert.TodoToSpreadsheetConverter;
import todoapp.web.model.SiteProperties;

@Controller
public class TodoController {

  private final SiteProperties siteProperties;
  private TodoFinder finder;

  public TodoController(SiteProperties siteProperties, TodoFinder finder) {
    this.siteProperties = siteProperties;
    this.finder = finder;
  }

  @RequestMapping("/todos")
  public void todos(Model model){
    model.addAttribute("site", siteProperties);
  }

  @RequestMapping(path = "/todos", produces = "text/csv")
  public void downloadTodos(Model model){
    model.addAttribute("todos",
    new TodoToSpreadsheetConverter().convert(finder.getAll())
        );
  }

  public static class TodoCsvViewResolver implements ViewResolver {

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
      if("todos".equals(viewName)){
        return new TodoCsvView();
      }
      return null;
    }
  }
  public static class TodoCsvView extends AbstractView implements View {

    @Override
    protected boolean generatesDownloadContent() {
      return true;
    }

    public TodoCsvView(){
      setContentType("text/csv");
    }
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
      List<Todo> todos = (List<Todo>) model.getOrDefault("todos", Collections.emptyList());


      response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"todos.csv\"");
      response.getWriter().println("id,title,complated");
      for(Todo todo: todos){
        String line = String.format("%d,%s,%s", todo.getId(),todo.getTitle(),todo.isCompleted());
        response.getWriter().println(line);
      }

      response.flushBuffer();
    }
  }



}
