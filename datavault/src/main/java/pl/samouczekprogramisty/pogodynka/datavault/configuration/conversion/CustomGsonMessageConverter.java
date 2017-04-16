package pl.samouczekprogramisty.pogodynka.datavault.configuration.conversion;

import com.google.gson.Gson;
import org.springframework.http.converter.json.GsonHttpMessageConverter;


public class CustomGsonMessageConverter extends GsonHttpMessageConverter {

    public CustomGsonMessageConverter(Gson gson) {
        setGson(gson);
    }
}
