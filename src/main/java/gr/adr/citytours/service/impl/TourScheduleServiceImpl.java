package gr.adr.citytours.service.impl;

import gr.adr.citytours.domain.TourSchedule;
import gr.adr.citytours.repository.TourScheduleRepository;
import gr.adr.citytours.service.TourScheduleService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.citytours.domain.TourSchedule}.
 */
@Service
@Transactional
public class TourScheduleServiceImpl implements TourScheduleService {

    private final Logger log = LoggerFactory.getLogger(TourScheduleServiceImpl.class);

    private final TourScheduleRepository tourScheduleRepository;

    public TourScheduleServiceImpl(TourScheduleRepository tourScheduleRepository) {
        this.tourScheduleRepository = tourScheduleRepository;
    }

    @Override
    public TourSchedule save(TourSchedule tourSchedule) {
        log.debug("Request to save TourSchedule : {}", tourSchedule);
        return tourScheduleRepository.save(tourSchedule);
    }

    @Override
    public TourSchedule update(TourSchedule tourSchedule) {
        log.debug("Request to update TourSchedule : {}", tourSchedule);
        return tourScheduleRepository.save(tourSchedule);
    }

    @Override
    public Optional<TourSchedule> partialUpdate(TourSchedule tourSchedule) {
        log.debug("Request to partially update TourSchedule : {}", tourSchedule);

        return tourScheduleRepository
            .findById(tourSchedule.getId())
            .map(existingTourSchedule -> {
                if (tourSchedule.getStartDatetime() != null) {
                    existingTourSchedule.setStartDatetime(tourSchedule.getStartDatetime());
                }
                if (tourSchedule.getNoPassengers() != null) {
                    existingTourSchedule.setNoPassengers(tourSchedule.getNoPassengers());
                }
                if (tourSchedule.getNoKids() != null) {
                    existingTourSchedule.setNoKids(tourSchedule.getNoKids());
                }
                if (tourSchedule.getNoPets() != null) {
                    existingTourSchedule.setNoPets(tourSchedule.getNoPets());
                }
                if (tourSchedule.getStartPlace() != null) {
                    existingTourSchedule.setStartPlace(tourSchedule.getStartPlace());
                }
                if (tourSchedule.getEndPlace() != null) {
                    existingTourSchedule.setEndPlace(tourSchedule.getEndPlace());
                }

                return existingTourSchedule;
            })
            .map(tourScheduleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TourSchedule> findAll(Pageable pageable) {
        log.debug("Request to get all TourSchedules");
        return tourScheduleRepository.findAll(pageable);
    }

    public Page<TourSchedule> findAllWithEagerRelationships(Pageable pageable) {
        return tourScheduleRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TourSchedule> findOne(Long id) {
        log.debug("Request to get TourSchedule : {}", id);
        return tourScheduleRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TourSchedule : {}", id);
        tourScheduleRepository.deleteById(id);
    }
}
