package gr.adr.citytours.service.impl;

import gr.adr.citytours.domain.Vehicle;
import gr.adr.citytours.repository.VehicleRepository;
import gr.adr.citytours.service.VehicleService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.citytours.domain.Vehicle}.
 */
@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final Logger log = LoggerFactory.getLogger(VehicleServiceImpl.class);

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        log.debug("Request to save Vehicle : {}", vehicle);
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        log.debug("Request to update Vehicle : {}", vehicle);
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Optional<Vehicle> partialUpdate(Vehicle vehicle) {
        log.debug("Request to partially update Vehicle : {}", vehicle);

        return vehicleRepository
            .findById(vehicle.getId())
            .map(existingVehicle -> {
                if (vehicle.getPlate() != null) {
                    existingVehicle.setPlate(vehicle.getPlate());
                }
                if (vehicle.getType() != null) {
                    existingVehicle.setType(vehicle.getType());
                }
                if (vehicle.getCapacity() != null) {
                    existingVehicle.setCapacity(vehicle.getCapacity());
                }
                if (vehicle.getColor() != null) {
                    existingVehicle.setColor(vehicle.getColor());
                }

                return existingVehicle;
            })
            .map(vehicleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Vehicle> findAll(Pageable pageable) {
        log.debug("Request to get all Vehicles");
        return vehicleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vehicle> findOne(Long id) {
        log.debug("Request to get Vehicle : {}", id);
        return vehicleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vehicle : {}", id);
        vehicleRepository.deleteById(id);
    }
}
