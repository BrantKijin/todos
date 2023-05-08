package todoapp.web;


import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;
import todoapp.web.model.UserProfile;

@RestController
public class UserRestController {

  private final UserSessionRepository userSessionRepository;

  public UserRestController(UserSessionRepository userSessionRepository) {
    this.userSessionRepository = userSessionRepository;
  }

  @GetMapping("/api/user/profile")
  public ResponseEntity<UserProfile> userProfile(){
    UserSession userSession = userSessionRepository.get();
    // User user = (User) session.getAttribute("user");
    if (Objects.nonNull(userSession)) {
      return ResponseEntity.ok(new UserProfile(userSession.getUser()));
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

}
