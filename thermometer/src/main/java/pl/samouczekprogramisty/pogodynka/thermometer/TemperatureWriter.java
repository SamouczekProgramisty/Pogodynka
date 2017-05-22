package pl.samouczekprogramisty.pogodynka.thermometer;


import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.samouczekprogramisty.pogodynka.thermometer.exceptions.IllegalResponseCode;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;

public class TemperatureWriter implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(TemperatureWriter.class);

    private static final String HEADER_NAME = "UserAuthorisation";

    private final CloseableHttpClient httpClient;

    private final URI dataSink;

    private final String authorisationHeader;

    public TemperatureWriter(CloseableHttpClient httpClient, URI dataSink, String authorisationHeader) {
        this.httpClient = httpClient;
        this.dataSink = dataSink;
        this.authorisationHeader = authorisationHeader;
    }

    public void addTemperature(TemperaturePoint temperaturePoint) throws IOException {
        String measurement = temperaturePoint.toJson();
        sendMeasurement(measurement);
    }

    private void sendMeasurement(String jsonMeasurement) throws IOException {
        HttpPost request = new HttpPost(dataSink);
        request.setHeader(HEADER_NAME, authorisationHeader);
        request.setEntity(new StringEntity(jsonMeasurement, ContentType.APPLICATION_JSON));
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new IllegalResponseCode(
                        statusCode,
                        IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8"))
                );
            }
        }
    }

    public static void main(String... args) {
        Arguments arguments = new Arguments(args);
        try {
            TemperaturePoint currentTemperature = getCurrentTemperature(arguments);
            sendCurrentTemperature(arguments, currentTemperature);
        } catch (IOException exception) {
            LOG.error("Oups, there was a problem during reading/sending temperature!", exception);
            System.exit(1);
        }
    }

    private static void sendCurrentTemperature(Arguments arguments, TemperaturePoint currentTemperature) throws IOException {
        HttpClientBuilder httpClientBuilder = HttpClients
                .custom();

        try (TemperatureWriter temperatureWriter = new TemperatureWriter(httpClientBuilder.build(), arguments.getDataSink(), arguments.getAuthorisationHeader())) {
            LOG.info("Current temperature {}", currentTemperature);
            temperatureWriter.addTemperature(currentTemperature);
        }
    }

    private static TemperaturePoint getCurrentTemperature(Arguments arguments) throws IOException {
        try (InputStream inputFile = new FileInputStream(arguments.getInputFilePath())) {
            Thermometer thermometer = new FromFileThermometer(inputFile);
            return thermometer.measure();
        }
    }

    @Override
    public void close() throws IOException {
        httpClient.close();
    }
}