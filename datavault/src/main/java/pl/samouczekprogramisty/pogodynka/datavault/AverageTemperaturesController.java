package pl.samouczekprogramisty.pogodynka.datavault;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.samouczekprogramisty.pogodynka.datavault.model.TemperatureAverage;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/temperatures/average")
public class AverageTemperaturesController {

    private static final Logger LOG = LoggerFactory.getLogger(TemperatureController.class);

    private final TemperatureService temperatureService;

    private final MessageSource messageSource;

    private final String authorisationToken = System.getenv("POGODYNKA_AUTHORISATION_TOKEN");

    @Autowired
    public AverageTemperaturesController(TemperatureService temperatureService, MessageSource messageSource) {
        this.messageSource = messageSource;
        this.temperatureService = temperatureService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @CrossOrigin
    public Map<String, List<TemperatureAverage>> listAverages() {
        LOG.debug("Listing average daily temperatures");
        return Collections.singletonMap("temperatureAverages", temperatureService.getAverageTemperatures());
    }
}
