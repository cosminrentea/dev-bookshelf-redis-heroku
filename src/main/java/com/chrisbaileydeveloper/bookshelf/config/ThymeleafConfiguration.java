package com.chrisbaileydeveloper.bookshelf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import uk.co.gcwilliams.jodatime.thymeleaf.JodaTimeDialect;

@Configuration
public class ThymeleafConfiguration {

    /**
     * Joda Time to Thymeleaf converter<br>
     * Spring Boot, in the ThymeleafAutoConfiguration class,
     * will automatically add any Beans that implement the IDialect interface.
     */
    @Bean
    public JodaTimeDialect jodaTimeDialect() {
        return new JodaTimeDialect();
    }

    /**
     * Thymeleaf - Spring Security Integration
     */
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}
