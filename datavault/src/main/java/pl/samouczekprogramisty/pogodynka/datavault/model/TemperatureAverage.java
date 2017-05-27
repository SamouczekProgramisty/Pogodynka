package pl.samouczekprogramisty.pogodynka.datavault.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;

public class TemperatureAverage {

    private BigDecimal averageTemperature;

    private DateTime day;

    public TemperatureAverage(BigDecimal averageTemperature, DateTime day) {
        this.averageTemperature = averageTemperature;
        this.day = day;
    }

    public BigDecimal getAverageTemperature() {
        return averageTemperature;
    }

    public DateTime getDay() {
        return day;
    }
}
