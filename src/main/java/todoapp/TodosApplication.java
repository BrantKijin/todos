package todoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import todoapp.commons.web.error.ReadableErrorAttributes;


@SpringBootApplication
@ConfigurationPropertiesScan
public class TodosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodosApplication.class, args);
	}

//	@Bean
//	public SiteProperties siteProperties(){
//		return new SiteProperties();
//	}

	@Bean
	public ReadableErrorAttributes errorAttributes(){
		return new ReadableErrorAttributes();
	}



}
