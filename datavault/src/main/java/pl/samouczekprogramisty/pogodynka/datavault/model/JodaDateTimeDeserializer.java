package pl.samouczekprogramisty.pogodynka.datavault.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class JodaDateTimeDeserializer extends JsonDeserializer {

    private static final Logger LOG = LoggerFactory.getLogger(JodaDateTimeDeserializer.class);

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        return null;
    }
}
