package pl.samouczekprogramisty.pogodynka.datavault.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemperatureMeasurementDAO extends CrudRepository<TemperatureMeasurement, Long> {

    List<TemperatureMeasurement> findAllByOrderByWhenMeasuredAsc();

}
