package pl.samouczekprogramisty.pogodynka.datavault;


import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import pl.samouczekprogramisty.pogodynka.datavault.model.TemperatureMeasurement;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class TemperatureServiceImpl implements TemperatureService {

    @Override
    public void addTemperature(BigDecimal temperature, DateTime whenMeasured) {

    }

    @Override
    public List<TemperatureMeasurement> getTemperatures() {
        return Arrays.asList(new TemperatureMeasurement(new BigDecimal(123), DateTime.now()));
    }

    @Override
    public void addTemperature(TemperatureMeasurement temperature) {

    }
}
