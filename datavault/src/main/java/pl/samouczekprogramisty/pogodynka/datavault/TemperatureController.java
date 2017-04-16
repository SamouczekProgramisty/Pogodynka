package pl.samouczekprogramisty.pogodynka.datavault;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.samouczekprogramisty.pogodynka.datavault.model.TemperatureMeasurement;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/temperatures")
public class TemperatureController {

    private static final Logger LOG = LoggerFactory.getLogger(TemperatureController.class);

    private final TemperatureService temperatureService;

    @Autowired
    public TemperatureController(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    protected static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(
                    DateTime.class,
                    (JsonSerializer<DateTime>) (dateTime, type, context) -> new JsonPrimitive(ISODateTimeFormat.dateTime().print(dateTime)))
            .create();

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> addTemperature(@RequestBody TemperatureMeasurement temperature, Errors errors) {
        LOG.debug("Adding new temperature.");
        LOG.debug(errors.toString());

        temperatureService.addTemperature(temperature);

        return new ResponseEntity<>("Temperature added", HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Map<String, List<TemperatureMeasurement>> listTemperatures() {
        LOG.debug("Listing all temperatures");
        List<TemperatureMeasurement> temperatures = temperatureService.getTemperatures();
        Map<String, List<TemperatureMeasurement>> responseMap = new HashMap<>();
        responseMap.put("temperatures", temperatures);
        return responseMap;
    }
}
