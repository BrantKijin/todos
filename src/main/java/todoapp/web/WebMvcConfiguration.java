package todoapp.web;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import todoapp.commons.web.view.CommaSeparatedValuesView;

/**
 * Spring Web MVC 설정
 *
 * @author springrunner.kr@gmail.com
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {



    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
       // registry.viewResolver(new TodoController.TodoCsvViewResolver());
        // registry.enableContentNegotiation(new CommaSeparatedValuesView());
        // 위와 같이 직접 설정하면, 스프링부트가 구성한 ContentNegotiatingViewResolver 전략이 무시된다.


    }

//    @Bean(name="todos")
//    public CommaSeparatedValuesView todoCsvVIew(){
//        return new CommaSeparatedValuesView();
//    }


    /**
     * 스프링부트가 생성한 ContentNegotiatingViewResolver를 조작할 목적으로 작성된 컴포넌트
     */

    @Configuration
    public static class ContentNegotiationCustomizer {

        @Autowired
        public void configurer(ContentNegotiatingViewResolver viewResolver) {

            List<View> defaultViews = new ArrayList<>(viewResolver.getDefaultViews());
            defaultViews.add(new CommaSeparatedValuesView());
            defaultViews.add(new MappingJackson2JsonView());
            viewResolver.setDefaultViews(defaultViews);
        }

    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       // 리소스 핸들러를 등록, 정적 자원을 처리할수 있다
        // 서블릿 컨텍스트 경로에서 정적 자원 제공
        //registry.addResourceHandler("/assets/**").addResourceLocations("assets/");

        // 파일 경로에서 정적 자원 제공
//        registry.addResourceHandler("/assets/**")
//            .addResourceLocations("file:/Users/gwaggijin/study/todos/files/assets/");

        //classpath 경로에서 자원제공
//        registry
//            .addResourceHandler("/assets/**")
//            .addResourceLocations(
//                "assets/",
//                "file:/Users/gwaggijin/study/todos/files/assets/",
//                "classpath:assets/");
    }
}
