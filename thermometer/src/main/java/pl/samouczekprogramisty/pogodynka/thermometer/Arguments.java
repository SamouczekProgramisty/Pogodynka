package pl.samouczekprogramisty.pogodynka.thermometer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class Arguments {

    private static final Logger LOG = LoggerFactory.getLogger(Arguments.class);

    private URI dataSink;

    private String authorisationHeader;

    private String inputFilePath;

    public Arguments(String... args) {
        if (args.length != 3) {
            LOG.error("There is something iffy with arguments ({})!", args.length);
            throw new IllegalArgumentException("You need to provide <username> <password> <data sink url> arguments!");
        }
        authorisationHeader = args[0];
        try {
            dataSink = new URI(args[1]);
        } catch (URISyntaxException exception) {
            LOG.error("There is something iffy with URL ({})!", args[1]);
            throw new RuntimeException(exception);
        }
        inputFilePath = args[2];
    }

    public URI getDataSink() {
        return dataSink;
    }

    public String getAuthorisationHeader() {
        return authorisationHeader;
    }

    public String getInputFilePath() {
        return inputFilePath;
    }
}
