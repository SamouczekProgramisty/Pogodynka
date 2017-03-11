package pl.samouczekprogramisty.pogodynka.thermometer;


import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.samouczekprogramisty.pogodynka.thermometer.exceptions.IllegalResponseCode;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

public class TemperatureWriter implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(TemperatureWriter.class);

    private final CloseableHttpClient httpClient;

    private final URI dataSink;

    public TemperatureWriter(CloseableHttpClient httpClient, URI dataSink) {
        this.httpClient = httpClient;
        this.dataSink = dataSink;
    }

    public void addTemperature(TemperaturePoint temperaturePoint) {
        String measurement = temperaturePoint.toJson();
        try {
            sendMeasurement(measurement);
        } catch (IOException exception) {
            LOG.error(exception.getLocalizedMessage());
            LOG.error(Arrays.toString(exception.getStackTrace()));
            throw new RuntimeException(exception);
        }
    }

    private void sendMeasurement(String jsonMeasurement) throws IOException {
        HttpPost request = new HttpPost(dataSink);
        request.setEntity(new StringEntity(jsonMeasurement, ContentType.APPLICATION_JSON));
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new IllegalResponseCode(statusCode);
            }
        }
    }

    public static void main(String... args) {
        Arguments arguments = new Arguments(args);
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(arguments.getUsername(), arguments.getPassword()));

        LOG.info("Logging as {}", arguments.getUsername());

        // TODO: replace that with proper implementation of thermometer
        Thermometer thermometer = new DummyThermometer();

        HttpClientBuilder httpClientBuilder = HttpClients
                .custom()
                .setDefaultCredentialsProvider(credentialsProvider);

        try (TemperatureWriter temperatureWriter = new TemperatureWriter(httpClientBuilder.build(), arguments.getDataSink())) {
            TemperaturePoint currentTemperature = thermometer.measure();
            LOG.info("Current temperature {}", currentTemperature);
            temperatureWriter.addTemperature(currentTemperature);
        } catch (IOException exception) {
            LOG.error(exception.getLocalizedMessage());
            LOG.error(Arrays.toString(exception.getStackTrace()));
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void close() throws IOException {
        httpClient.close();
    }
}