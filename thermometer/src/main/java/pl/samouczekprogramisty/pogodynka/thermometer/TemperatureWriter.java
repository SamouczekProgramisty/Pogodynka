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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class TemperatureWriter implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(TemperatureWriter.class);

    private final CloseableHttpClient httpClient;

    private final URI dataSink;

    public TemperatureWriter(CloseableHttpClient httpClient, URI dataSink) {
        this.httpClient = httpClient;
        this.dataSink = dataSink;
    }

    public void addTemperature(TemperaturePoint temperaturePoint) throws IOException {
        String measurement = temperaturePoint.toJson();
        sendMeasurement(measurement);
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

        try {
            TemperaturePoint currentTemperature = getCurrentTemperature(arguments);
            sendCurrentTemperature(arguments, credentialsProvider, currentTemperature);
        } catch (IOException exception) {
            LOG.error("Oups, there was a problem during reading/sending temperature!", exception);
            System.exit(1);
        }
    }

    private static void sendCurrentTemperature(Arguments arguments, CredentialsProvider credentialsProvider, TemperaturePoint currentTemperature) throws IOException {
        HttpClientBuilder httpClientBuilder = HttpClients
                .custom()
                .setDefaultCredentialsProvider(credentialsProvider);

        try (TemperatureWriter temperatureWriter = new TemperatureWriter(httpClientBuilder.build(), arguments.getDataSink())) {
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