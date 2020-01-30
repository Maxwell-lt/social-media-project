package maxwell_lt.socialmediaproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@EnableJpaRepositories("maxwell_lt.socialmediaproject.repository")
public class SocialMediaProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialMediaProjectApplication.class, args);
    }

}
