package pl.samouczekprogramisty.pogodynka.datavault;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.samouczekprogramisty.pogodynka.datavault.model.TemperatureMeasurement;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/temperatures")
public class TemperatureController {

    private static final Logger LOG = LoggerFactory.getLogger(TemperatureController.class);

    private final TemperatureService temperatureService;

    private final MessageSource messageSource;

    private final String authorisationToken = System.getenv("POGODYNKA_AUTHORISATION_TOKEN");

    @Autowired
    public TemperatureController(TemperatureService temperatureService, MessageSource messageSource) {
        this.messageSource = messageSource;
        this.temperatureService = temperatureService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity addTemperature(@Valid @RequestBody TemperatureMeasurement temperature, Errors errors, @RequestHeader("UserAuthorisation") String authorisationToken) {
        if (!this.authorisationToken.equals(authorisationToken)) {
            return new ResponseEntity<>(Collections.singletonMap("errors", "Missing or invalid UserAuthorisation header!"), HttpStatus.FORBIDDEN);
        }

        if (errors.hasErrors()) {
            List<String> errorMessages = errors.getAllErrors().stream()
                    .map(e -> messageSource.getMessage(e.getCode(), e.getArguments(), null))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(Collections.singletonMap("errors", errorMessages), HttpStatus.BAD_REQUEST);
        }

        temperatureService.addTemperature(temperature);

        return new ResponseEntity<>(Collections.singletonMap("result", "Temperature added"), HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @CrossOrigin
    public Map<String, List<TemperatureMeasurement>> listTemperatures() {
        LOG.debug("Listing all temperatures");
        List<TemperatureMeasurement> temperatures = temperatureService.getTemperatures();
        Map<String, List<TemperatureMeasurement>> responseMap = new HashMap<>();
        responseMap.put("temperatures", temperatures);
        return responseMap;
    }
}
