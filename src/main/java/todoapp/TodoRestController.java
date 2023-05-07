package todoapp;


import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import todoapp.core.todos.application.TodoEditor;
import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.domain.Todo;

@RestController
public class TodoRestController {

  private final TodoFinder finder;
  private final TodoEditor editor;

  public TodoRestController(TodoFinder finder, TodoEditor editor) {
    this.finder = finder;
    this.editor = editor;
  }

  @GetMapping("/api/todos")
  public List<Todo> list(){
    return finder.getAll();
  }

  @PostMapping("/api/todos")
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@RequestBody CreateTodoCommand command){
    // JsonNode Map<String, Object>
    editor.create(command.getTitle());
  }
  static class CreateTodoCommand{
    private String title;

    public String getTitle() {
      return title;
    }

    @Override
    public String toString() {
      return "CreateTodoCommand{" +
          "title='" + title + '\'' +
          '}';
    }

    public void setTitle(String title) {
      this.title = title;
    }
  }
}
