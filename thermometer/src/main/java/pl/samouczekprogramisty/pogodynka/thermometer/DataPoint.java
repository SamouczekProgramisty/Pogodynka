package pl.samouczekprogramisty.pogodynka.thermometer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

public abstract class DataPoint<T> {
    protected final DateTime pointTime;
    protected final T measurment;

    protected static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(
                DateTime.class,
                (JsonSerializer<DateTime>) (dateTime, type, context) -> new JsonPrimitive(ISODateTimeFormat.dateTime().print(dateTime)))
        .create();

    public DataPoint(T measurment) {
        this(measurment, DateTime.now(DateTimeZone.UTC));
    }

    public DataPoint(T measurment, DateTime pointTime) {
        this.measurment = measurment;
        this.pointTime = pointTime;
    }

    public DateTime getPointTime() {
        return pointTime;
    }

    public T getMeasurment() {
        return measurment;
    }

    public String toJson() {
        return GSON.toJson(this);
    }
}
