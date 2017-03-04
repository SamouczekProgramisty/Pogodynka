package pl.samouczekprogramisty.pogodynka.thermometer;


import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.Closeable;
import java.io.IOException;

public class TemperatureWriter implements Closeable{

    private final CloseableHttpClient httpClient;

    public TemperatureWriter(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void addTemperature(TemperaturePoint temperaturePoint) {
        String measurment = temperaturePoint.toJson();
    }

    public static void main(String[] args) throws IOException {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials("user", "passwd"));

        HttpClientBuilder httpClientBuilder = HttpClients
                .custom()
                .setDefaultCredentialsProvider(credentialsProvider);

        try (CloseableHttpClient client = httpClientBuilder.build()) {
            HttpPost request = new HttpPost();
            try (CloseableHttpResponse response = client.execute(request)) {
                System.out.println(response.getStatusLine().getStatusCode());
                response.getEntity().getContent();
            }
        }
    }

    @Override
    public void close() throws IOException {
        httpClient.close();
    }
}