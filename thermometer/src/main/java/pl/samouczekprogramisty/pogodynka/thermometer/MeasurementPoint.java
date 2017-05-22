package pl.samouczekprogramisty.pogodynka.thermometer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

public abstract class MeasurementPoint<T> {

    protected final DateTime whenMeasured;

    protected final T temperature;

    protected static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(
                DateTime.class,
                (JsonSerializer<DateTime>) (dateTime, type, context) -> new JsonPrimitive(ISODateTimeFormat.dateTime().print(dateTime)))
        .create();

    public MeasurementPoint(T measurment) {
        this(measurment, DateTime.now(DateTimeZone.UTC));
    }

    public MeasurementPoint(T measurment, DateTime whenMeasured) {
        this.temperature = measurment;
        this.whenMeasured = whenMeasured;
    }

    public DateTime getWhenMeasured() {
        return whenMeasured;
    }

    public T getTemperature() {
        return temperature;
    }

    public String toJson() {
        return GSON.toJson(this);
    }

    @Override
    public String toString() {
        return "MeasurementPoint{" +
                "whenMeasured=" + whenMeasured +
                ", temperature=" + temperature +
                '}';
    }
}
