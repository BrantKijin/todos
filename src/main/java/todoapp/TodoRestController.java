package todoapp;


import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import todoapp.core.todos.application.TodoEditor;
import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.domain.Todo;
import todoapp.security.UserSessionRepository;

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

  @PutMapping("/api/todos/{id}")
  public void update(@PathVariable("id") Long id, @RequestBody WriteTodoCommand command){

    editor.update(id, command.getTitle(), command.isCompleted());
  }


  @PostMapping("/api/todos")
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@RequestBody @Valid CreateTodoCommand command){
    // JsonNode Map<String, Object>
    editor.create(command.getTitle());
  }

  @DeleteMapping("/api/todos/{id}")
  public void delete(@PathVariable("id") Long id){
    editor.delete(id);
  }



  static class CreateTodoCommand{
    @NotBlank
    @Size(min =4, max=140)
    private String title;
    private Boolean completed;

    public Boolean getCompleted() {
      return completed;
    }

    public void setCompleted(Boolean completed) {
      this.completed = completed;
    }

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

  static class WriteTodoCommand{
    @NotBlank
    @Size(min =4, max=140)
    private String title;
    private boolean completed;

    public boolean isCompleted() {
      return completed;
    }

    public void setCompleted(boolean completed) {
      this.completed = completed;
    }

    @Override
    public String toString() {
      return "WriteTodoCommand{" +
          "title='" + title + '\'' +
          '}';
    }

    public String getTitle() {

      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }
  }
}
