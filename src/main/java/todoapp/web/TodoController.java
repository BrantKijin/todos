package todoapp.web;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import todoapp.web.model.SiteProperties;

@Controller
public class TodoController {

  private SiteProperties siteAuthor;

  public TodoController(SiteProperties siteAuthor) {
    this.siteAuthor = siteAuthor;
  }

  @RequestMapping("/todos")
  public ModelAndView todos(){

    SiteProperties site = new SiteProperties();
    site.setAuthor(siteAuthor.getAuthor());
    site.setDescription(siteAuthor.getDescription());
    ModelAndView mav = new ModelAndView();
    mav.addObject("site",site);
    mav.setViewName("todos");

    return mav;
  }

}
