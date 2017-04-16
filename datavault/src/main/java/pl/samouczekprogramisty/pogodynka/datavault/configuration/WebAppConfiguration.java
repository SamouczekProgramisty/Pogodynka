package pl.samouczekprogramisty.pogodynka.datavault.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pl.samouczekprogramisty.pogodynka.datavault.configuration.conversion.CustomDateTimeAdapter;
import pl.samouczekprogramisty.pogodynka.datavault.configuration.conversion.CustomGsonMessageConverter;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan("pl.samouczekprogramisty.pogodynka.datavault")
public class WebAppConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new CustomGsonMessageConverter(getGson()));
    }

    @Bean
    public Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(DateTime.class, new CustomDateTimeAdapter()).create();
    }
}
