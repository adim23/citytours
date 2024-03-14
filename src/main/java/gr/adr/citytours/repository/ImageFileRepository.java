package gr.adr.citytours.repository;

import gr.adr.citytours.domain.ImageFile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ImageFile entity.
 */
@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
    default Optional<ImageFile> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ImageFile> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ImageFile> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select imageFile from ImageFile imageFile left join fetch imageFile.place",
        countQuery = "select count(imageFile) from ImageFile imageFile"
    )
    Page<ImageFile> findAllWithToOneRelationships(Pageable pageable);

    @Query("select imageFile from ImageFile imageFile left join fetch imageFile.place")
    List<ImageFile> findAllWithToOneRelationships();

    @Query("select imageFile from ImageFile imageFile left join fetch imageFile.place where imageFile.id =:id")
    Optional<ImageFile> findOneWithToOneRelationships(@Param("id") Long id);
}
