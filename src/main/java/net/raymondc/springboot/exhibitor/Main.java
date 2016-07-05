package net.raymondc.springboot.exhibitor;

import com.netflix.exhibitor.servlet.ExhibitorServletContextListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Servlet;

/**
 * Created by raymondcoetzee on 04/07/2016.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class Main {

    @Bean
    @ConditionalOnMissingBean(ExhibitorServletContextListener.class)
    public ExhibitorServletContextListener exhibitorServletContextListener(){
        return new ExhibitorServletContextListener();
    }

    @Bean
    public FilterRegistrationBean filterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new com.netflix.exhibitor.servlet.ExhibitorServletFilter());
        registration.addUrlPatterns("/");
        registration.setName("default-redirect");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(new com.sun.jersey.spi.container.servlet.ServletContainer());
        registrationBean.setName("Jersey REST Service");
        registrationBean.setLoadOnStartup(1);
        registrationBean.addInitParameter("com.sun.jersey.config.property.resourceConfigClass","com.netflix.exhibitor.servlet.ExhibitorResourceConfig");
        registrationBean.addUrlMappings("/*");
        return registrationBean;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }

}
