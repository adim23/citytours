package gr.adr.citytours.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gr.adr.citytours.IntegrationTest;
import gr.adr.citytours.domain.ImageFile;
import gr.adr.citytours.repository.ImageFileRepository;
import gr.adr.citytours.service.ImageFileService;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ImageFileResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ImageFileResourceIT {

    private static final String DEFAULT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_FILENAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/image-files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ImageFileRepository imageFileRepository;

    @Mock
    private ImageFileRepository imageFileRepositoryMock;

    @Mock
    private ImageFileService imageFileServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImageFileMockMvc;

    private ImageFile imageFile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImageFile createEntity(EntityManager em) {
        ImageFile imageFile = new ImageFile().filename(DEFAULT_FILENAME).data(DEFAULT_DATA).dataContentType(DEFAULT_DATA_CONTENT_TYPE);
        return imageFile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImageFile createUpdatedEntity(EntityManager em) {
        ImageFile imageFile = new ImageFile().filename(UPDATED_FILENAME).data(UPDATED_DATA).dataContentType(UPDATED_DATA_CONTENT_TYPE);
        return imageFile;
    }

    @BeforeEach
    public void initTest() {
        imageFile = createEntity(em);
    }

    @Test
    @Transactional
    void createImageFile() throws Exception {
        int databaseSizeBeforeCreate = imageFileRepository.findAll().size();
        // Create the ImageFile
        restImageFileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imageFile)))
            .andExpect(status().isCreated());

        // Validate the ImageFile in the database
        List<ImageFile> imageFileList = imageFileRepository.findAll();
        assertThat(imageFileList).hasSize(databaseSizeBeforeCreate + 1);
        ImageFile testImageFile = imageFileList.get(imageFileList.size() - 1);
        assertThat(testImageFile.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testImageFile.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testImageFile.getDataContentType()).isEqualTo(DEFAULT_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createImageFileWithExistingId() throws Exception {
        // Create the ImageFile with an existing ID
        imageFile.setId(1L);

        int databaseSizeBeforeCreate = imageFileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restImageFileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imageFile)))
            .andExpect(status().isBadRequest());

        // Validate the ImageFile in the database
        List<ImageFile> imageFileList = imageFileRepository.findAll();
        assertThat(imageFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllImageFiles() throws Exception {
        // Initialize the database
        imageFileRepository.saveAndFlush(imageFile);

        // Get all the imageFileList
        restImageFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imageFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME)))
            .andExpect(jsonPath("$.[*].dataContentType").value(hasItem(DEFAULT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_DATA))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllImageFilesWithEagerRelationshipsIsEnabled() throws Exception {
        when(imageFileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restImageFileMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(imageFileServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllImageFilesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(imageFileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restImageFileMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(imageFileRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getImageFile() throws Exception {
        // Initialize the database
        imageFileRepository.saveAndFlush(imageFile);

        // Get the imageFile
        restImageFileMockMvc
            .perform(get(ENTITY_API_URL_ID, imageFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(imageFile.getId().intValue()))
            .andExpect(jsonPath("$.filename").value(DEFAULT_FILENAME))
            .andExpect(jsonPath("$.dataContentType").value(DEFAULT_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.data").value(Base64.getEncoder().encodeToString(DEFAULT_DATA)));
    }

    @Test
    @Transactional
    void getNonExistingImageFile() throws Exception {
        // Get the imageFile
        restImageFileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingImageFile() throws Exception {
        // Initialize the database
        imageFileRepository.saveAndFlush(imageFile);

        int databaseSizeBeforeUpdate = imageFileRepository.findAll().size();

        // Update the imageFile
        ImageFile updatedImageFile = imageFileRepository.findById(imageFile.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedImageFile are not directly saved in db
        em.detach(updatedImageFile);
        updatedImageFile.filename(UPDATED_FILENAME).data(UPDATED_DATA).dataContentType(UPDATED_DATA_CONTENT_TYPE);

        restImageFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedImageFile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedImageFile))
            )
            .andExpect(status().isOk());

        // Validate the ImageFile in the database
        List<ImageFile> imageFileList = imageFileRepository.findAll();
        assertThat(imageFileList).hasSize(databaseSizeBeforeUpdate);
        ImageFile testImageFile = imageFileList.get(imageFileList.size() - 1);
        assertThat(testImageFile.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testImageFile.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testImageFile.getDataContentType()).isEqualTo(UPDATED_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingImageFile() throws Exception {
        int databaseSizeBeforeUpdate = imageFileRepository.findAll().size();
        imageFile.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImageFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, imageFile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageFile))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImageFile in the database
        List<ImageFile> imageFileList = imageFileRepository.findAll();
        assertThat(imageFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchImageFile() throws Exception {
        int databaseSizeBeforeUpdate = imageFileRepository.findAll().size();
        imageFile.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImageFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageFile))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImageFile in the database
        List<ImageFile> imageFileList = imageFileRepository.findAll();
        assertThat(imageFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamImageFile() throws Exception {
        int databaseSizeBeforeUpdate = imageFileRepository.findAll().size();
        imageFile.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImageFileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imageFile)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ImageFile in the database
        List<ImageFile> imageFileList = imageFileRepository.findAll();
        assertThat(imageFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateImageFileWithPatch() throws Exception {
        // Initialize the database
        imageFileRepository.saveAndFlush(imageFile);

        int databaseSizeBeforeUpdate = imageFileRepository.findAll().size();

        // Update the imageFile using partial update
        ImageFile partialUpdatedImageFile = new ImageFile();
        partialUpdatedImageFile.setId(imageFile.getId());

        restImageFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImageFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImageFile))
            )
            .andExpect(status().isOk());

        // Validate the ImageFile in the database
        List<ImageFile> imageFileList = imageFileRepository.findAll();
        assertThat(imageFileList).hasSize(databaseSizeBeforeUpdate);
        ImageFile testImageFile = imageFileList.get(imageFileList.size() - 1);
        assertThat(testImageFile.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testImageFile.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testImageFile.getDataContentType()).isEqualTo(DEFAULT_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateImageFileWithPatch() throws Exception {
        // Initialize the database
        imageFileRepository.saveAndFlush(imageFile);

        int databaseSizeBeforeUpdate = imageFileRepository.findAll().size();

        // Update the imageFile using partial update
        ImageFile partialUpdatedImageFile = new ImageFile();
        partialUpdatedImageFile.setId(imageFile.getId());

        partialUpdatedImageFile.filename(UPDATED_FILENAME).data(UPDATED_DATA).dataContentType(UPDATED_DATA_CONTENT_TYPE);

        restImageFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImageFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImageFile))
            )
            .andExpect(status().isOk());

        // Validate the ImageFile in the database
        List<ImageFile> imageFileList = imageFileRepository.findAll();
        assertThat(imageFileList).hasSize(databaseSizeBeforeUpdate);
        ImageFile testImageFile = imageFileList.get(imageFileList.size() - 1);
        assertThat(testImageFile.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testImageFile.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testImageFile.getDataContentType()).isEqualTo(UPDATED_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingImageFile() throws Exception {
        int databaseSizeBeforeUpdate = imageFileRepository.findAll().size();
        imageFile.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImageFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, imageFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(imageFile))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImageFile in the database
        List<ImageFile> imageFileList = imageFileRepository.findAll();
        assertThat(imageFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchImageFile() throws Exception {
        int databaseSizeBeforeUpdate = imageFileRepository.findAll().size();
        imageFile.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImageFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(imageFile))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImageFile in the database
        List<ImageFile> imageFileList = imageFileRepository.findAll();
        assertThat(imageFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamImageFile() throws Exception {
        int databaseSizeBeforeUpdate = imageFileRepository.findAll().size();
        imageFile.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImageFileMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(imageFile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ImageFile in the database
        List<ImageFile> imageFileList = imageFileRepository.findAll();
        assertThat(imageFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteImageFile() throws Exception {
        // Initialize the database
        imageFileRepository.saveAndFlush(imageFile);

        int databaseSizeBeforeDelete = imageFileRepository.findAll().size();

        // Delete the imageFile
        restImageFileMockMvc
            .perform(delete(ENTITY_API_URL_ID, imageFile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ImageFile> imageFileList = imageFileRepository.findAll();
        assertThat(imageFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
