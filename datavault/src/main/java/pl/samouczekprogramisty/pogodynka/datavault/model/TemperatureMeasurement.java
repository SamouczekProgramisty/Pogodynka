package pl.samouczekprogramisty.pogodynka.datavault.model;


import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "temperature_measurements",
        indexes = @Index(name = "idx_temperature_measurements_when_meaasured",
                columnList = "when_measured"))

public class TemperatureMeasurement {

    @Id
    @SequenceGenerator(name = "measurements_sequence", allocationSize = 5, sequenceName = "temperature_measurements_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "measurements_sequence")
    private Long id;

    private BigDecimal temperature;

    @Column(name = "when_measured")
    private DateTime whenMeasured;

    public TemperatureMeasurement() {
    }

    public TemperatureMeasurement(BigDecimal temperature, DateTime whenMeasured) {
        this.temperature = temperature;
        this.whenMeasured = whenMeasured;
    }

    @NotNull
    public BigDecimal getTemperature() {
        return temperature;
    }

    @NotNull
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
