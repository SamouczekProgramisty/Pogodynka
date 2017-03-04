package pl.samouczekprogramisty.pogodynka.thermometer;

import org.joda.time.DateTime;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class TemperaturePointTest {
    @Test
    public void shouldCreateTemperatureMeasurment() {
        TemperaturePoint measurment = new TemperaturePoint(BigDecimal.ONE);

        assertThat(measurment.getMeasurment(), is(BigDecimal.ONE));
    }

    @Test
    public void shouldCreateTemperatureMeasurmentWithValidDate() {
        DateTime beforeMeasurment = DateTime.now().minusMillis(1);
        TemperaturePoint measurment = new TemperaturePoint(BigDecimal.ONE);
        DateTime afterMeasurment = DateTime.now().plusMillis(1);

        DateTime measurmentTime = measurment.getPointTime();
        assertThat(measurmentTime, greaterThan(beforeMeasurment));
        assertThat(measurmentTime, lessThan(afterMeasurment));
    }

    @Test
    public void shouldBeAbleToJsonifyMeasurment() {
        DateTime measurementTime = new DateTime(2017, 3, 4, 11, 11);
        TemperaturePoint measurment = new TemperaturePoint(BigDecimal.ONE, measurementTime);

        assertThat(measurment.toJson(), is("{\"pointTime\":\"2017-03-04T11:11:00.000+01:00\",\"measurment\":1}"));
    }
}