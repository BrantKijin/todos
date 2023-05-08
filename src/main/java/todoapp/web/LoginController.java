package todoapp.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

  @PostMapping("/login")
  public void loginProcess(LoginCommand loginCommand){

  }

  static class LoginCommand {
    String username;
    String password;

    @Override
    public String toString() {
      return "LoginCommand{" +
          "username='" + username + '\'' +
          ", password='" + password + '\'' +
          '}';
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }

}
