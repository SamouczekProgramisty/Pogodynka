package pl.samouczekprogramisty.pogodynka.thermometer;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.math.BigDecimal;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TemperatureWriterTest {

    @Mock
    private CloseableHttpClient httpClient;

    @Test
    public void shouldSendHttpRequest() throws IOException {
        TemperatureWriter temperatureWriter = new TemperatureWriter(httpClient, null);
        CloseableHttpResponse mockResponse = getMockResponse(200);
        when(httpClient.execute(any())).thenReturn(mockResponse);

        temperatureWriter.addTemperature(new TemperaturePoint(BigDecimal.TEN));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionOnWrongStatusCode() throws IOException {
        TemperatureWriter temperatureWriter = new TemperatureWriter(httpClient, null);
        CloseableHttpResponse mockResponse = getMockResponse(404);
        when(httpClient.execute(any())).thenReturn(mockResponse);

        temperatureWriter.addTemperature(new TemperaturePoint(BigDecimal.TEN));
    }

    private CloseableHttpResponse getMockResponse(int statusCode) {
        CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);
        StatusLine mockStatusLine = mock(StatusLine.class);
        when(mockStatusLine.getStatusCode()).thenReturn(statusCode);
        when(mockResponse.getStatusLine()).thenReturn(mockStatusLine);
        return mockResponse;
    }
}