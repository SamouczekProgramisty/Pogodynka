package pl.samouczekprogramisty.pogodynka.thermometer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

public abstract class MeasurementPoint<T> {

    protected final DateTime measurementTime;

    protected final T measurement;

    protected static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(
                DateTime.class,
                (JsonSerializer<DateTime>) (dateTime, type, context) -> new JsonPrimitive(ISODateTimeFormat.dateTime().print(dateTime)))
        .create();

    public MeasurementPoint(T measurment) {
        this(measurment, DateTime.now(DateTimeZone.UTC));
    }

    public MeasurementPoint(T measurment, DateTime measurementTime) {
        this.measurement = measurment;
        this.measurementTime = measurementTime;
    }

    public DateTime getMeasurementTime() {
        return measurementTime;
    }

    public T getMeasurement() {
        return measurement;
    }

    public String toJson() {
        return GSON.toJson(this);
    }

    @Override
    public String toString() {
        return "MeasurementPoint{" +
                "measurementTime=" + measurementTime +
                ", measurement=" + measurement +
                '}';
    }
}
