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
import pl.samouczekprogramisty.pogodynka.thermometer.exceptions.IllegalResponseCode;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
public class TemperatureWriter implements Closeable{

    private final CloseableHttpClient httpClient;

    public TemperatureWriter(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void addTemperature(TemperaturePoint temperaturePoint) throws IOException {
        String measurement = temperaturePoint.toJson();
        sendMeasurement("http://requestb.in/1hl319c1", measurement);
    }

    private void sendMeasurement(String endpoint, String jsonMeasurement) throws IOException {
        HttpPost request = new HttpPost(endpoint);
        request.setEntity(new StringEntity(jsonMeasurement, ContentType.APPLICATION_JSON));
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new IllegalResponseCode(statusCode);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials("user", "passwd"));

        HttpClientBuilder httpClientBuilder = HttpClients
                .custom()
                .setDefaultCredentialsProvider(credentialsProvider);

        try (TemperatureWriter temperatureWriter = new TemperatureWriter(httpClientBuilder.build())) {
            temperatureWriter.addTemperature(new TemperaturePoint(BigDecimal.TEN));
        }
    }

    @Override
    public void close() throws IOException {
        httpClient.close();
    }
}