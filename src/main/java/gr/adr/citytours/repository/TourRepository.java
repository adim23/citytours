package gr.adr.citytours.repository;

import gr.adr.citytours.domain.Tour;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tour entity.
 */
@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    default Optional<Tour> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Tour> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Tour> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(value = "select tour from Tour tour left join fetch tour.city", countQuery = "select count(tour) from Tour tour")
    Page<Tour> findAllWithToOneRelationships(Pageable pageable);

    @Query("select tour from Tour tour left join fetch tour.city")
    List<Tour> findAllWithToOneRelationships();

    @Query("select tour from Tour tour left join fetch tour.city where tour.id =:id")
    Optional<Tour> findOneWithToOneRelationships(@Param("id") Long id);
}
