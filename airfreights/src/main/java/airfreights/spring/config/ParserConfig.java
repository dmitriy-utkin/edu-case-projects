package airfreights.spring.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "parser-settings")
public class ParserConfig {
    private String jsonFilePath;
    private boolean responseWithMessage;
}
