package pl.samouczekprogramisty.pogodynka.thermometer;

import org.joda.time.DateTime;

import java.math.BigDecimal;

public class TemperaturePoint extends MeasurementPoint<BigDecimal> {
    public TemperaturePoint(BigDecimal temperature) {
        super(temperature);
    }

    public TemperaturePoint(BigDecimal temperature, DateTime measurementTime) {
        super(temperature, measurementTime);
    }
}
