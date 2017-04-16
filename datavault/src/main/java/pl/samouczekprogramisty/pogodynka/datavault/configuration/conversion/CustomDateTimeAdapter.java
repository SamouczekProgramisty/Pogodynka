package pl.samouczekprogramisty.pogodynka.datavault.configuration.conversion;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

public class CustomDateTimeAdapter extends TypeAdapter<DateTime> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = ISODateTimeFormat.dateTime();

    @Override
    public void write(JsonWriter out, DateTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(DATE_TIME_FORMATTER.print(value));
        }
    }

    @Override
    public DateTime read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        return DATE_TIME_FORMATTER.parseDateTime(in.nextString());
    }
}
