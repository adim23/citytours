package gr.adr.citytours.service.impl;

import gr.adr.citytours.domain.Passenger;
import gr.adr.citytours.repository.PassengerRepository;
import gr.adr.citytours.service.PassengerService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.citytours.domain.Passenger}.
 */
@Service
@Transactional
public class PassengerServiceImpl implements PassengerService {

    private final Logger log = LoggerFactory.getLogger(PassengerServiceImpl.class);

    private final PassengerRepository passengerRepository;

    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Passenger save(Passenger passenger) {
        log.debug("Request to save Passenger : {}", passenger);
        return passengerRepository.save(passenger);
    }

    @Override
    public Passenger update(Passenger passenger) {
        log.debug("Request to update Passenger : {}", passenger);
        return passengerRepository.save(passenger);
    }

    @Override
    public Optional<Passenger> partialUpdate(Passenger passenger) {
        log.debug("Request to partially update Passenger : {}", passenger);

        return passengerRepository
            .findById(passenger.getId())
            .map(existingPassenger -> {
                if (passenger.getName() != null) {
                    existingPassenger.setName(passenger.getName());
                }
                if (passenger.getEmail() != null) {
                    existingPassenger.setEmail(passenger.getEmail());
                }
                if (passenger.getMobile() != null) {
                    existingPassenger.setMobile(passenger.getMobile());
                }
                if (passenger.getAge() != null) {
                    existingPassenger.setAge(passenger.getAge());
                }
                if (passenger.getGender() != null) {
                    existingPassenger.setGender(passenger.getGender());
                }
                if (passenger.getNationality() != null) {
                    existingPassenger.setNationality(passenger.getNationality());
                }

                return existingPassenger;
            })
            .map(passengerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Passenger> findAll(Pageable pageable) {
        log.debug("Request to get all Passengers");
        return passengerRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Passenger> findOne(Long id) {
        log.debug("Request to get Passenger : {}", id);
        return passengerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Passenger : {}", id);
        passengerRepository.deleteById(id);
    }
}
