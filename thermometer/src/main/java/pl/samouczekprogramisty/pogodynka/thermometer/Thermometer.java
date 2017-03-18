package pl.samouczekprogramisty.pogodynka.thermometer;

import java.io.IOException;

public interface Thermometer {

    /**
     * Just measures temperature.
     *
     * @return current temperature
     */
    TemperaturePoint measure() throws IOException;
}
