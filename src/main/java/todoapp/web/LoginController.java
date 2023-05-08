package todoapp.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import todoapp.web.model.SiteProperties;

@Controller
public class LoginController {

  private final SiteProperties siteProperties;

  public LoginController(SiteProperties siteProperties) {
    this.siteProperties = siteProperties;
  }

//  @ModelAttribute("site")
//  public SiteProperties siteProperties(){
//    return siteProperties;
//  }

  @GetMapping("/login")
  public ModelAndView loginForm(Model model){

    return new ModelAndView("/login");
  }

}
