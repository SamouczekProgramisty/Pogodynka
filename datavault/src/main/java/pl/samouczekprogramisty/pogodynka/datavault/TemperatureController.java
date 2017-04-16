package pl.samouczekprogramisty.pogodynka.datavault;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.samouczekprogramisty.pogodynka.datavault.model.TemperatureMeasurement;

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> addTemperature(@RequestBody TemperatureMeasurement temperature, Errors errors) {
        LOG.debug("Adding new temperature.");

        temperatureService.addTemperature(temperature);

        return new ResponseEntity<String>("Temperature added", HttpStatus.CREATED);
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
