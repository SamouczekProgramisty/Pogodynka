package pl.samouczekprogramisty.pogodynka.datavault.model;


import org.joda.time.DateTime;

import java.math.BigDecimal;

public class TemperatureMeasurement {
    private final BigDecimal temperature;
    private final DateTime whenMeasured;

    public TemperatureMeasurement(BigDecimal temperature, DateTime whenMeasured) {
        this.temperature = temperature;
        this.whenMeasured = whenMeasured;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public DateTime getWhenMeasured() {
        return whenMeasured;
    }

    @Override
    public String toString() {
        return "TemperatureMeasurement{" +
                "temperature=" + temperature +
                ", whenMeasured=" + whenMeasured +
                '}';
    }
}
