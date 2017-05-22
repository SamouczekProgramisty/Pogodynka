package pl.samouczekprogramisty.pogodynka.thermometer;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ArgumentsTest {

    private Arguments validAruments;

    @Before
    public void setUp() {
        validAruments = new Arguments("token", "http://www.samouczekprogramisty.pl", "/some/path");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNotEnoughArgumengs() {
        new Arguments();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionOnTooManyArguments() {
        new Arguments("1", "2", "3", "4");
    }

    @Test
    public void shouldParseAuthorisationToken() {
        assertThat(validAruments.getAuthorisationHeader(), is("token"));
    }

    @Test
    public void shouldParseDataSink() throws URISyntaxException {
        assertThat(validAruments.getDataSink(), is(new URI("http://www.samouczekprogramisty.pl")));
    }

    @Test(expected = RuntimeException.class)
    public void shouldTrowExcpeptionOnInvalidURL() {
        new Arguments("token", "http://some_illegal_uri.pl?s=^", "/some/path");
    }
}