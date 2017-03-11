package pl.samouczekprogramisty.pogodynka.thermometer;


import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Random;

public class DummyThermometer implements Thermometer {

    private static final Random randomizer = new Random();

    /**
     * Just a dummy implementation that returns some measurement.
     *
     * @return
     */
    @Override
    public TemperaturePoint measure() {
        double fractionPart = randomizer.nextInt(100) / 100.;
        BigDecimal temperature = new BigDecimal(DateTime.now().getHourOfDay() + fractionPart);
        return new TemperaturePoint(temperature);
    }
}
