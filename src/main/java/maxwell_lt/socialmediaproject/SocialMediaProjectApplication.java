package maxwell_lt.socialmediaproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        ManagementWebSecurityAutoConfiguration.class})
@EnableJpaRepositories("maxwell_lt.socialmediaproject.repository")
public class SocialMediaProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialMediaProjectApplication.class, args);
    }

}
