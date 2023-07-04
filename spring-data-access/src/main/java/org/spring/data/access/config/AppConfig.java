package org.spring.data.access.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
@ComponentScan("org.spring.data.access")
@PropertySource("classpath:application.properties")
@EnableWebMvc
@Import({DataSourceConfig.class})
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public HandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }

}
