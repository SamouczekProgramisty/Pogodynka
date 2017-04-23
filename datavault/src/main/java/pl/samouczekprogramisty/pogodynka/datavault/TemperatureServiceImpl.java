package pl.samouczekprogramisty.pogodynka.datavault;


import org.springframework.stereotype.Service;
import pl.samouczekprogramisty.pogodynka.datavault.model.TemperatureMeasurement;
import pl.samouczekprogramisty.pogodynka.datavault.model.TemperatureMeasurementDAO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TemperatureServiceImpl implements TemperatureService {

    private final TemperatureMeasurementDAO temperatureDAO;

    public TemperatureServiceImpl(TemperatureMeasurementDAO temperatureDAO) {
        this.temperatureDAO = temperatureDAO;
    }

    @Override
    public void addTemperature(TemperatureMeasurement temperatureMeasurement) {
        temperatureDAO.save(temperatureMeasurement);
    }

    @Override
    public List<TemperatureMeasurement> getTemperatures() {
        return StreamSupport
                .stream(temperatureDAO.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
