package todoapp.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import todoapp.core.user.application.UserPasswordVerifier;
import todoapp.core.user.application.UserRegistration;
import todoapp.core.user.domain.UserEntityNotFoundException;
import todoapp.web.model.SiteProperties;

@Controller
public class LoginController {


  private final UserPasswordVerifier userPasswordVerifier;
  private final UserRegistration userRegistration;


  private final SiteProperties siteProperties;

  public LoginController(UserPasswordVerifier userPasswordVerifier, UserRegistration userRegistration, SiteProperties siteProperties) {
    this.userPasswordVerifier = userPasswordVerifier;
    this.userRegistration = userRegistration;
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
  public String loginProcess(LoginCommand command){
    // 1 사용자 저장소에 사용자가 있을 경우 : 비밀번호 확인후 로그인 처리
    // 2 사용자가 없는 경우: 회원가입 처리 후로그인 처리
    try{
      userPasswordVerifier.verify(command.username, command.password);
    }catch (UserEntityNotFoundException error){
      userRegistration.join(command.username, command.password);
    }
    return "redirect:/todos";

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
