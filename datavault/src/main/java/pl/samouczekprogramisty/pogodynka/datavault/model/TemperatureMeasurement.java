package pl.samouczekprogramisty.pogodynka.datavault.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.Gson;
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
    @JsonSerialize(using = JodaDateTimeSerializer.class)
    @JsonDeserialize(using = JodaDateTimeDeserializer.class)
    public DateTime getWhenMeasured() {
        return whenMeasured;
    }

    public static TemperatureMeasurement parse(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, TemperatureMeasurement.class);
    }

    @Override
    public String toString() {
        return "TemperatureMeasurement{" +
                "temperature=" + temperature +
                ", whenMeasured=" + whenMeasured +
                '}';
    }
}
