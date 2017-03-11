package pl.samouczekprogramisty.pogodynka.thermometer;

public interface Thermometer {

    /**
     * Just measures temperature.
     *
     * @return current temperature
     */
    TemperaturePoint measure();
}
