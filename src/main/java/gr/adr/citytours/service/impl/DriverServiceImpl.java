package gr.adr.citytours.service.impl;

import gr.adr.citytours.domain.Driver;
import gr.adr.citytours.repository.DriverRepository;
import gr.adr.citytours.service.DriverService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.citytours.domain.Driver}.
 */
@Service
@Transactional
public class DriverServiceImpl implements DriverService {

    private final Logger log = LoggerFactory.getLogger(DriverServiceImpl.class);

    private final DriverRepository driverRepository;

    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Driver save(Driver driver) {
        log.debug("Request to save Driver : {}", driver);
        return driverRepository.save(driver);
    }

    @Override
    public Driver update(Driver driver) {
        log.debug("Request to update Driver : {}", driver);
        return driverRepository.save(driver);
    }

    @Override
    public Optional<Driver> partialUpdate(Driver driver) {
        log.debug("Request to partially update Driver : {}", driver);

        return driverRepository
            .findById(driver.getId())
            .map(existingDriver -> {
                if (driver.getName() != null) {
                    existingDriver.setName(driver.getName());
                }
                if (driver.getHiredAt() != null) {
                    existingDriver.setHiredAt(driver.getHiredAt());
                }
                if (driver.getAge() != null) {
                    existingDriver.setAge(driver.getAge());
                }
                if (driver.getEmail() != null) {
                    existingDriver.setEmail(driver.getEmail());
                }
                if (driver.getMobile() != null) {
                    existingDriver.setMobile(driver.getMobile());
                }

                return existingDriver;
            })
            .map(driverRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Driver> findAll(Pageable pageable) {
        log.debug("Request to get all Drivers");
        return driverRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Driver> findOne(Long id) {
        log.debug("Request to get Driver : {}", id);
        return driverRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Driver : {}", id);
        driverRepository.deleteById(id);
    }
}
