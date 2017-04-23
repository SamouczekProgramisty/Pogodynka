package pl.samouczekprogramisty.pogodynka.datavault.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureMeasurementDAO extends CrudRepository<TemperatureMeasurement, Long> {

}
