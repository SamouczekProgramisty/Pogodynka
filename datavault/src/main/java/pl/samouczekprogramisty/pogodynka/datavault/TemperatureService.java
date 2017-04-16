package pl.samouczekprogramisty.pogodynka.datavault;


import org.joda.time.DateTime;
import pl.samouczekprogramisty.pogodynka.datavault.model.TemperatureMeasurement;

import java.math.BigDecimal;
import java.util.List;

public interface TemperatureService {

    void addTemperature(BigDecimal temperature, DateTime whenMeasured);

    List<TemperatureMeasurement> getTemperatures();

    void addTemperature(TemperatureMeasurement temperature);
}
