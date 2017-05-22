package pl.samouczekprogramisty.pogodynka.thermometer;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class FromFileThermometerTest {

    @Test
    public void shouldBeAbleToParseFileContent() throws IOException {
        InputStream inputFile = getClass().getResourceAsStream(File.separator + "temperature_file");
        Thermometer thermometer = new FromFileThermometer(inputFile);
        TemperaturePoint temperaturePoint = thermometer.measure();

        assertThat(temperaturePoint.getTemperature(), is(new BigDecimal("22.125")));
    }
}