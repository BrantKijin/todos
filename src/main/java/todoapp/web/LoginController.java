package todoapp.web;



import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import todoapp.core.user.application.UserPasswordVerifier;
import todoapp.core.user.application.UserRegistration;
import todoapp.core.user.domain.UserEntityNotFoundException;
import todoapp.core.user.domain.UserPasswordNotMatchedException;
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
  public String loginProcess(@Valid LoginCommand command, BindingResult bindingResult, Model model){
    // 0 입력 값 검증에 실패한 경우 : 로그인 페이지로 돌려보내기

    // 1 사용자 저장소에 사용자가 있을 경우 : 비밀번호 확인후 로그인 처리
    // 2 사용자가 없는 경우: 회원가입 처리 후로그인 처리
    if(command.getUsername().length() < 4){
      // 입력값이 올바르지 않습니다.
      model.addAttribute("bingingResult", bindingResult);
      model.addAttribute("message", "입력값이 없거나 올바르지 않아요.");
      return "login";
    }
    try{
      userPasswordVerifier.verify(command.username, command.password);
    }catch (UserEntityNotFoundException error){
      userRegistration.join(command.username, command.password);
    }catch (UserPasswordNotMatchedException error){
      //3 비밀 번호가 틀린 경우: 로그인 페이지로 돌려보내기
      model.addAttribute("message", error.getMessage());
      return "login";
    }
    return "redirect:/todos";

  }

  @ExceptionHandler(BindException.class)
  public String handleBindException(BindException error, Model model){
    model.addAttribute("bindingResult", error.getBindingResult());
    model.addAttribute("message", "입력 값이 없거나 올바르지 않아요.");
    return "login";
  }
  @ExceptionHandler(UserPasswordNotMatchedException.class)
  public String handleUSerPasswordNotMatchedException(UserPasswordNotMatchedException error, Model model){
    model.addAttribute("message", error.getMessage());
    return "login";
  }


  static class LoginCommand {
    @Size(min = 4, max = 20)
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
