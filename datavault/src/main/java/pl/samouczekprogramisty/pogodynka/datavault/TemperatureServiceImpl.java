package pl.samouczekprogramisty.pogodynka.datavault;


import org.hibernate.query.NativeQuery;
import org.hibernate.transform.ResultTransformer;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import pl.samouczekprogramisty.pogodynka.datavault.model.TemperatureAverage;
import pl.samouczekprogramisty.pogodynka.datavault.model.TemperatureMeasurement;
import pl.samouczekprogramisty.pogodynka.datavault.model.TemperatureMeasurementDAO;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TemperatureServiceImpl implements TemperatureService {

    /**
     * Querying for day and average daily temperature. Average is computed based on (roughly) 4 measurements from
     * a day. Exact measurements count depends on database content.
     */
    private static final String AVERAGE_TEMPERATURES_QUERY =
            "SELECT day, " +
                    "SUM(temperature) / COUNT(temperature) AS daily_average " +
                    "FROM (SELECT DATE(when_measured) AS day, " +
                    "EXTRACT('hour' FROM when_measured) AS hour, " +
                    "temperature " +
                    "FROM temperature_measurements " +
                    "WHERE EXTRACT('hour' FROM when_measured) IN (1, 7, 13, 19) " +
                    "AND EXTRACT('minute' FROM when_measured) < 2) AS daily_temps " +
                    "GROUP BY day " +
                    "ORDER BY day";

    private final TemperatureMeasurementDAO temperatureDAO;

    private final EntityManager entityManager;

    public TemperatureServiceImpl(TemperatureMeasurementDAO temperatureDAO, EntityManager entityManager) {
        this.temperatureDAO = temperatureDAO;
        this.entityManager = entityManager;
    }

    @Override
    public void addTemperature(TemperatureMeasurement temperatureMeasurement) {
        temperatureDAO.save(temperatureMeasurement);
    }

    @Override
    public List<TemperatureAverage> getAverageTemperatures() {

        NativeQuery query = entityManager.createNativeQuery(AVERAGE_TEMPERATURES_QUERY).unwrap(NativeQuery.class);
        // Although it was deprecated with hibernate 5.2 I didn't find any replacement mechanism.
        query.setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] tuple, String[] aliases) {
                DateTime day = null;
                BigDecimal dailyAverage = null;
                for (int index = 0; index < aliases.length; index++) {
                    String column = aliases[index];
                    Object columnValue = tuple[index];
                    switch (column) {
                        case "day":
                            day = new DateTime(columnValue);
                            break;
                        case "daily_average":
                            dailyAverage = ((BigDecimal) columnValue).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                            break;
                        default:
                            throw new IllegalStateException("Unknown column name: " + column + "!");
                    }
                }
                return new TemperatureAverage(dailyAverage, day);
            }

            @Override
            public List transformList(List collection) {
                return collection;
            }
        });
        return query.getResultList();
    }

    @Override
    public List<TemperatureMeasurement> getTemperatures() {
        return StreamSupport
                .stream(temperatureDAO.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
