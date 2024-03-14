package gr.adr.citytours.service.impl;

import gr.adr.citytours.domain.ImageFile;
import gr.adr.citytours.repository.ImageFileRepository;
import gr.adr.citytours.service.ImageFileService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.citytours.domain.ImageFile}.
 */
@Service
@Transactional
public class ImageFileServiceImpl implements ImageFileService {

    private final Logger log = LoggerFactory.getLogger(ImageFileServiceImpl.class);

    private final ImageFileRepository imageFileRepository;

    public ImageFileServiceImpl(ImageFileRepository imageFileRepository) {
        this.imageFileRepository = imageFileRepository;
    }

    @Override
    public ImageFile save(ImageFile imageFile) {
        log.debug("Request to save ImageFile : {}", imageFile);
        return imageFileRepository.save(imageFile);
    }

    @Override
    public ImageFile update(ImageFile imageFile) {
        log.debug("Request to update ImageFile : {}", imageFile);
        return imageFileRepository.save(imageFile);
    }

    @Override
    public Optional<ImageFile> partialUpdate(ImageFile imageFile) {
        log.debug("Request to partially update ImageFile : {}", imageFile);

        return imageFileRepository
            .findById(imageFile.getId())
            .map(existingImageFile -> {
                if (imageFile.getFilename() != null) {
                    existingImageFile.setFilename(imageFile.getFilename());
                }
                if (imageFile.getData() != null) {
                    existingImageFile.setData(imageFile.getData());
                }
                if (imageFile.getDataContentType() != null) {
                    existingImageFile.setDataContentType(imageFile.getDataContentType());
                }

                return existingImageFile;
            })
            .map(imageFileRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImageFile> findAll(Pageable pageable) {
        log.debug("Request to get all ImageFiles");
        return imageFileRepository.findAll(pageable);
    }

    public Page<ImageFile> findAllWithEagerRelationships(Pageable pageable) {
        return imageFileRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ImageFile> findOne(Long id) {
        log.debug("Request to get ImageFile : {}", id);
        return imageFileRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ImageFile : {}", id);
        imageFileRepository.deleteById(id);
    }
}
