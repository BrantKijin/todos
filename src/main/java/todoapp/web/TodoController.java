package todoapp.web;


import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractView;
import todoapp.web.model.SiteProperties;

@Controller
public class TodoController {

  private final SiteProperties siteProperties;

  public TodoController(SiteProperties siteAuthor) {
    this.siteProperties = siteAuthor;
  }

  @RequestMapping("/todos")
  public void todos(Model model){
    model.addAttribute("site", siteProperties);
  }

  @RequestMapping(path = "/todos", produces = "text/csv")
  public void downloadTodos(){

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

    public TodoCsvView(){
      setContentType("text/csv");
    }
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

    }
  }


}
