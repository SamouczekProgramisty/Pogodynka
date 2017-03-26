package pl.samouczekprogramisty.pogodynka.datavault.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("pl.samouczekprogramisty.pogodynka.datavault")
public class WebAppConfiguration {
}
