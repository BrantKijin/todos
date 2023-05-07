package todoapp.web;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
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

}
