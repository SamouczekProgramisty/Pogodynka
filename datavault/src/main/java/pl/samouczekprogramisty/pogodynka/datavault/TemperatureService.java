package pl.samouczekprogramisty.pogodynka.datavault;


import pl.samouczekprogramisty.pogodynka.datavault.model.TemperatureAverage;
import pl.samouczekprogramisty.pogodynka.datavault.model.TemperatureMeasurement;

import java.util.List;

public interface TemperatureService {

    List<TemperatureMeasurement> getTemperatures();

    void addTemperature(TemperatureMeasurement temperature);

    List<TemperatureAverage> getAverageTemperatures();
}
