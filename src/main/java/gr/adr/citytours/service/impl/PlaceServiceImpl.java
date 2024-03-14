package gr.adr.citytours.service.impl;

import gr.adr.citytours.domain.Place;
import gr.adr.citytours.repository.PlaceRepository;
import gr.adr.citytours.service.PlaceService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.citytours.domain.Place}.
 */
@Service
@Transactional
public class PlaceServiceImpl implements PlaceService {

    private final Logger log = LoggerFactory.getLogger(PlaceServiceImpl.class);

    private final PlaceRepository placeRepository;

    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public Place save(Place place) {
        log.debug("Request to save Place : {}", place);
        return placeRepository.save(place);
    }

    @Override
    public Place update(Place place) {
        log.debug("Request to update Place : {}", place);
        return placeRepository.save(place);
    }

    @Override
    public Optional<Place> partialUpdate(Place place) {
        log.debug("Request to partially update Place : {}", place);

        return placeRepository
            .findById(place.getId())
            .map(existingPlace -> {
                if (place.getCode() != null) {
                    existingPlace.setCode(place.getCode());
                }
                if (place.getName() != null) {
                    existingPlace.setName(place.getName());
                }
                if (place.getDescription() != null) {
                    existingPlace.setDescription(place.getDescription());
                }
                if (place.getFullDescription() != null) {
                    existingPlace.setFullDescription(place.getFullDescription());
                }
                if (place.getLongitude() != null) {
                    existingPlace.setLongitude(place.getLongitude());
                }
                if (place.getLatitude() != null) {
                    existingPlace.setLatitude(place.getLatitude());
                }

                return existingPlace;
            })
            .map(placeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Place> findAll(Pageable pageable) {
        log.debug("Request to get all Places");
        return placeRepository.findAll(pageable);
    }

    public Page<Place> findAllWithEagerRelationships(Pageable pageable) {
        return placeRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Place> findOne(Long id) {
        log.debug("Request to get Place : {}", id);
        return placeRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Place : {}", id);
        placeRepository.deleteById(id);
    }
}
