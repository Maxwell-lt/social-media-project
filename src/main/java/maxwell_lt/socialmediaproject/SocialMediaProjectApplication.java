package maxwell_lt.socialmediaproject;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("maxwell_lt.socialmediaproject.repository")
@PropertySource("classpath:application-local.properties")
@PropertySource("classpath:application.properties")
public class SocialMediaProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialMediaProjectApplication.class, args);
    }

    @Bean
    public PrettyTime prettyTime() {
        return new PrettyTime();
    }

}
