package pl.samouczekprogramisty.pogodynka.datavault.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("pl.samouczekprogramisty.pogodynka.datavault")
public class WebAppConfiguration {

    @Bean
    public ObjectMapper customObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JodaModule());
        return om;
    }
}
