package pl.samouczekprogramisty.pogodynka.thermometer;


import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;

public class FromFileThermometer implements Thermometer {

    private static final Logger LOG = LoggerFactory.getLogger(FromFileThermometer.class);

    public static final int TEMPERATURE_IS_STORED_WITHOUT_DECIMAL = 1000;

    private final InputStream inputFile;

    public FromFileThermometer(InputStream inputFile) {
        this.inputFile = inputFile;
    }

    @Override
    public TemperaturePoint measure() throws IOException {
        List<String> fileContent = IOUtils.readLines(inputFile, (Charset) null);
        BigDecimal temperature = parseFileContent(fileContent);
        LOG.debug("I've read following temperature {}.", temperature.toString());
        return new TemperaturePoint(temperature);
    }

    private BigDecimal parseFileContent(List<String> fileContent) {
        if (fileContent.size() != 2) {
            throw new IllegalArgumentException("File doesn't contain 2 lines! It contains " + fileContent.size());
        }
        String lineWithTemperature = fileContent.get(1);
        String[] lineParts = lineWithTemperature.split("=");
        if (lineParts.length != 2) {
            throw new IllegalArgumentException("Line with temperature has illegal format! " + lineWithTemperature);
        }
        String temperature = lineParts[1];
        return new BigDecimal(temperature).divide(new BigDecimal(TEMPERATURE_IS_STORED_WITHOUT_DECIMAL));
    }
}
