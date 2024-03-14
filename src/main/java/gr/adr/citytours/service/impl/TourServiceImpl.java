package gr.adr.citytours.service.impl;

import gr.adr.citytours.domain.Tour;
import gr.adr.citytours.repository.TourRepository;
import gr.adr.citytours.service.TourService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.citytours.domain.Tour}.
 */
@Service
@Transactional
public class TourServiceImpl implements TourService {

    private final Logger log = LoggerFactory.getLogger(TourServiceImpl.class);

    private final TourRepository tourRepository;

    public TourServiceImpl(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Override
    public Tour save(Tour tour) {
        log.debug("Request to save Tour : {}", tour);
        return tourRepository.save(tour);
    }

    @Override
    public Tour update(Tour tour) {
        log.debug("Request to update Tour : {}", tour);
        return tourRepository.save(tour);
    }

    @Override
    public Optional<Tour> partialUpdate(Tour tour) {
        log.debug("Request to partially update Tour : {}", tour);

        return tourRepository
            .findById(tour.getId())
            .map(existingTour -> {
                if (tour.getCode() != null) {
                    existingTour.setCode(tour.getCode());
                }
                if (tour.getTitle() != null) {
                    existingTour.setTitle(tour.getTitle());
                }
                if (tour.getDuration() != null) {
                    existingTour.setDuration(tour.getDuration());
                }
                if (tour.getPetFriendly() != null) {
                    existingTour.setPetFriendly(tour.getPetFriendly());
                }
                if (tour.getKidsAllowed() != null) {
                    existingTour.setKidsAllowed(tour.getKidsAllowed());
                }
                if (tour.getAvailableFromDate() != null) {
                    existingTour.setAvailableFromDate(tour.getAvailableFromDate());
                }
                if (tour.getAvailableToDate() != null) {
                    existingTour.setAvailableToDate(tour.getAvailableToDate());
                }
                if (tour.getEnabled() != null) {
                    existingTour.setEnabled(tour.getEnabled());
                }

                return existingTour;
            })
            .map(tourRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Tour> findAll(Pageable pageable) {
        log.debug("Request to get all Tours");
        return tourRepository.findAll(pageable);
    }

    public Page<Tour> findAllWithEagerRelationships(Pageable pageable) {
        return tourRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tour> findOne(Long id) {
        log.debug("Request to get Tour : {}", id);
        return tourRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tour : {}", id);
        tourRepository.deleteById(id);
    }
}
