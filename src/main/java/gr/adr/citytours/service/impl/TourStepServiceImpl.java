package gr.adr.citytours.service.impl;

import gr.adr.citytours.domain.TourStep;
import gr.adr.citytours.repository.TourStepRepository;
import gr.adr.citytours.service.TourStepService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.citytours.domain.TourStep}.
 */
@Service
@Transactional
public class TourStepServiceImpl implements TourStepService {

    private final Logger log = LoggerFactory.getLogger(TourStepServiceImpl.class);

    private final TourStepRepository tourStepRepository;

    public TourStepServiceImpl(TourStepRepository tourStepRepository) {
        this.tourStepRepository = tourStepRepository;
    }

    @Override
    public TourStep save(TourStep tourStep) {
        log.debug("Request to save TourStep : {}", tourStep);
        return tourStepRepository.save(tourStep);
    }

    @Override
    public TourStep update(TourStep tourStep) {
        log.debug("Request to update TourStep : {}", tourStep);
        return tourStepRepository.save(tourStep);
    }

    @Override
    public Optional<TourStep> partialUpdate(TourStep tourStep) {
        log.debug("Request to partially update TourStep : {}", tourStep);

        return tourStepRepository
            .findById(tourStep.getId())
            .map(existingTourStep -> {
                if (tourStep.getCode() != null) {
                    existingTourStep.setCode(tourStep.getCode());
                }
                if (tourStep.getStepOrder() != null) {
                    existingTourStep.setStepOrder(tourStep.getStepOrder());
                }
                if (tourStep.getWaitTime() != null) {
                    existingTourStep.setWaitTime(tourStep.getWaitTime());
                }
                if (tourStep.getDriveTime() != null) {
                    existingTourStep.setDriveTime(tourStep.getDriveTime());
                }

                return existingTourStep;
            })
            .map(tourStepRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TourStep> findAll(Pageable pageable) {
        log.debug("Request to get all TourSteps");
        return tourStepRepository.findAll(pageable);
    }

    public Page<TourStep> findAllWithEagerRelationships(Pageable pageable) {
        return tourStepRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TourStep> findOne(Long id) {
        log.debug("Request to get TourStep : {}", id);
        return tourStepRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TourStep : {}", id);
        tourStepRepository.deleteById(id);
    }
}
