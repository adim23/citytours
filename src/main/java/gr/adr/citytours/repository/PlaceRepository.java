package gr.adr.citytours.repository;

import gr.adr.citytours.domain.Place;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Place entity.
 */
@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    default Optional<Place> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Place> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Place> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(value = "select place from Place place left join fetch place.city", countQuery = "select count(place) from Place place")
    Page<Place> findAllWithToOneRelationships(Pageable pageable);

    @Query("select place from Place place left join fetch place.city")
    List<Place> findAllWithToOneRelationships();

    @Query("select place from Place place left join fetch place.city where place.id =:id")
    Optional<Place> findOneWithToOneRelationships(@Param("id") Long id);
}
