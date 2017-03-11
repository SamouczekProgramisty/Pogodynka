package pl.samouczekprogramisty.pogodynka.thermometer;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ArgumentsTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNotEnoughArgumengs() {
        new Arguments();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionOnTooManyArguments() {
        new Arguments("1", "2", "3", "4");
    }

    @Test
    public void shouldParseUsername() {
        Arguments arguments = new Arguments("username", "password", "http://www.samouczekprogramisty.pl");
        assertThat(arguments.getUsername(), is("username"));
    }

    @Test
    public void shouldParsePassword() {
        Arguments arguments = new Arguments("username", "password", "http://www.samouczekprogramisty.pl");
        assertThat(arguments.getPassword(), is("password"));
    }

    @Test
    public void shouldParseDataSink() throws URISyntaxException {
        Arguments arguments = new Arguments("username", "password", "http://www.samouczekprogramisty.pl");
        assertThat(arguments.getDataSink(), is(new URI("http://www.samouczekprogramisty.pl")));
    }

    @Test(expected = RuntimeException.class)
    public void shouldTrowExcpeptionOnInvalidURL() throws URISyntaxException {
        new Arguments("username", "password", "http://some_illegal_uri.pl?s=^");
    }
}