package todoapp.security.web.servlet;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.security.RolesAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import todoapp.security.AccessDeniedException;
import todoapp.security.UnauthorizedAccessException;
import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;
import todoapp.security.support.RolesAllowedSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Role(역할) 기반으로 사용자가 사용 권한을 확인하는 인터셉터 구현체
 *
 * @author springrunner.kr@gmail.com
 */
public class RolesVerifyHandlerInterceptor implements HandlerInterceptor, RolesAllowedSupport {


    private final UserSessionRepository userSessionRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    public RolesVerifyHandlerInterceptor(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            RolesAllowed rolesAllowed = ((HandlerMethod) handler).getMethodAnnotation(RolesAllowed.class);
            if(Objects.isNull(rolesAllowed)){
                rolesAllowed  = AnnotatedElementUtils.findMergedAnnotation(((HandlerMethod) handler).getBeanType(), RolesAllowed.class);
            }
            if(Objects.nonNull(rolesAllowed)){
                // 1/ 로그인이 되어있나요 ?
                UserSession userSession = userSessionRepository.get();
                // 로그인이 안되어 있으면 예외 발생
                if(Objects.isNull(userSession)){
                    throw new UnauthorizedAccessException();
                }
                // 2 역할은 적절한가요?
                Set<String> matchedRoles = Stream.of(rolesAllowed.value())
                    .filter(role -> userSession.hasRole(role))
                    .collect(Collectors.toSet());
                // 역할이 적합하지 않으면 , 예외를 발생시킴
                if(matchedRoles.isEmpty()){
                    throw new AccessDeniedException();
                }
            }

        }
        // throw new UnsupportedOperationException("unimplemented feature for RolesVerifyHandlerInterceptor");
        // 로그인이 되어 있으면 , 핸들러 실행


        return true;
    }

}
