package pl.samouczekprogramisty.pogodynka.thermometer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class Arguments {

    private static final Logger LOG = LoggerFactory.getLogger(Arguments.class);

    private URI dataSink;

    private String username;

    private String password;

    public Arguments(String... args) {
        if (args.length != 4) {
            LOG.error("There is something iffy with arguments ({})!", args.length);
            throw new IllegalArgumentException("You need to provide <username> <password> <data sink url> arguments!");
        }
        username = args[1];
        password = args[2];
        try {
            dataSink = new URI(args[3]);
        } catch (URISyntaxException exception) {
            LOG.error("There is something iffy with URL ({})!", args[3]);
            throw new RuntimeException(exception);
        }
    }

    public URI getDataSink() {
        return dataSink;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
