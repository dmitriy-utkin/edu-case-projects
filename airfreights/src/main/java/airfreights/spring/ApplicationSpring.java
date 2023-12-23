package airfreights.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationSpring {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationSpring.class, args);
    }
}

//TODO: N+1 issue with locking for the freight number when i try to get an Airport.
