package fr.district.codemax.web.rest;

import fr.district.codemax.Application;
import fr.district.codemax.domain.DocumentPlateau;
import fr.district.codemax.repository.DocumentPlateauRepository;
import fr.district.codemax.service.DocumentPlateauService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DocumentPlateauResource} REST controller.
 */
@SpringBootTest(classes = Application.class)

@AutoConfigureMockMvc
@WithMockUser
public class DocumentPlateauResourceIT {

    private static final byte[] DEFAULT_PROGRAMME = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PROGRAMME = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PROGRAMME_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PROGRAMME_CONTENT_TYPE = "image/png";

    @Autowired
    private DocumentPlateauRepository documentPlateauRepository;

    @Autowired
    private DocumentPlateauService documentPlateauService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentPlateauMockMvc;

    private DocumentPlateau documentPlateau;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentPlateau createEntity(EntityManager em) {
        DocumentPlateau documentPlateau = new DocumentPlateau()
            .programme(DEFAULT_PROGRAMME)
            .programmeContentType(DEFAULT_PROGRAMME_CONTENT_TYPE);
        return documentPlateau;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentPlateau createUpdatedEntity(EntityManager em) {
        DocumentPlateau documentPlateau = new DocumentPlateau()
            .programme(UPDATED_PROGRAMME)
            .programmeContentType(UPDATED_PROGRAMME_CONTENT_TYPE);
        return documentPlateau;
    }

    @BeforeEach
    public void initTest() {
        documentPlateau = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentPlateau() throws Exception {
        int databaseSizeBeforeCreate = documentPlateauRepository.findAll().size();

        // Create the DocumentPlateau
        restDocumentPlateauMockMvc.perform(post("/api/document-plateaus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentPlateau)))
            .andExpect(status().isCreated());

        // Validate the DocumentPlateau in the database
        List<DocumentPlateau> documentPlateauList = documentPlateauRepository.findAll();
        assertThat(documentPlateauList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentPlateau testDocumentPlateau = documentPlateauList.get(documentPlateauList.size() - 1);
        assertThat(testDocumentPlateau.getProgramme()).isEqualTo(DEFAULT_PROGRAMME);
        assertThat(testDocumentPlateau.getProgrammeContentType()).isEqualTo(DEFAULT_PROGRAMME_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createDocumentPlateauWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentPlateauRepository.findAll().size();

        // Create the DocumentPlateau with an existing ID
        documentPlateau.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentPlateauMockMvc.perform(post("/api/document-plateaus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentPlateau)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentPlateau in the database
        List<DocumentPlateau> documentPlateauList = documentPlateauRepository.findAll();
        assertThat(documentPlateauList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDocumentPlateaus() throws Exception {
        // Initialize the database
        documentPlateauRepository.saveAndFlush(documentPlateau);

        // Get all the documentPlateauList
        restDocumentPlateauMockMvc.perform(get("/api/document-plateaus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentPlateau.getId().intValue())))
            .andExpect(jsonPath("$.[*].programmeContentType").value(hasItem(DEFAULT_PROGRAMME_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].programme").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROGRAMME))));
    }
    
    @Test
    @Transactional
    public void getDocumentPlateau() throws Exception {
        // Initialize the database
        documentPlateauRepository.saveAndFlush(documentPlateau);

        // Get the documentPlateau
        restDocumentPlateauMockMvc.perform(get("/api/document-plateaus/{id}", documentPlateau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentPlateau.getId().intValue()))
            .andExpect(jsonPath("$.programmeContentType").value(DEFAULT_PROGRAMME_CONTENT_TYPE))
            .andExpect(jsonPath("$.programme").value(Base64Utils.encodeToString(DEFAULT_PROGRAMME)));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentPlateau() throws Exception {
        // Get the documentPlateau
        restDocumentPlateauMockMvc.perform(get("/api/document-plateaus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentPlateau() throws Exception {
        // Initialize the database
        documentPlateauService.save(documentPlateau);

        int databaseSizeBeforeUpdate = documentPlateauRepository.findAll().size();

        // Update the documentPlateau
        DocumentPlateau updatedDocumentPlateau = documentPlateauRepository.findById(documentPlateau.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentPlateau are not directly saved in db
        em.detach(updatedDocumentPlateau);
        updatedDocumentPlateau
            .programme(UPDATED_PROGRAMME)
            .programmeContentType(UPDATED_PROGRAMME_CONTENT_TYPE);

        restDocumentPlateauMockMvc.perform(put("/api/document-plateaus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumentPlateau)))
            .andExpect(status().isOk());

        // Validate the DocumentPlateau in the database
        List<DocumentPlateau> documentPlateauList = documentPlateauRepository.findAll();
        assertThat(documentPlateauList).hasSize(databaseSizeBeforeUpdate);
        DocumentPlateau testDocumentPlateau = documentPlateauList.get(documentPlateauList.size() - 1);
        assertThat(testDocumentPlateau.getProgramme()).isEqualTo(UPDATED_PROGRAMME);
        assertThat(testDocumentPlateau.getProgrammeContentType()).isEqualTo(UPDATED_PROGRAMME_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentPlateau() throws Exception {
        int databaseSizeBeforeUpdate = documentPlateauRepository.findAll().size();

        // Create the DocumentPlateau

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentPlateauMockMvc.perform(put("/api/document-plateaus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentPlateau)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentPlateau in the database
        List<DocumentPlateau> documentPlateauList = documentPlateauRepository.findAll();
        assertThat(documentPlateauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentPlateau() throws Exception {
        // Initialize the database
        documentPlateauService.save(documentPlateau);

        int databaseSizeBeforeDelete = documentPlateauRepository.findAll().size();

        // Delete the documentPlateau
        restDocumentPlateauMockMvc.perform(delete("/api/document-plateaus/{id}", documentPlateau.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentPlateau> documentPlateauList = documentPlateauRepository.findAll();
        assertThat(documentPlateauList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
