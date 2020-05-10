package fr.district.codemax.web.rest;

import fr.district.codemax.Application;
import fr.district.codemax.domain.LogoClub;
import fr.district.codemax.repository.LogoClubRepository;
import fr.district.codemax.service.LogoClubService;

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
 * Integration tests for the {@link LogoClubResource} REST controller.
 */
@SpringBootTest(classes = Application.class)

@AutoConfigureMockMvc
@WithMockUser
public class LogoClubResourceIT {

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    @Autowired
    private LogoClubRepository logoClubRepository;

    @Autowired
    private LogoClubService logoClubService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLogoClubMockMvc;

    private LogoClub logoClub;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogoClub createEntity(EntityManager em) {
        LogoClub logoClub = new LogoClub()
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE);
        return logoClub;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogoClub createUpdatedEntity(EntityManager em) {
        LogoClub logoClub = new LogoClub()
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE);
        return logoClub;
    }

    @BeforeEach
    public void initTest() {
        logoClub = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogoClub() throws Exception {
        int databaseSizeBeforeCreate = logoClubRepository.findAll().size();

        // Create the LogoClub
        restLogoClubMockMvc.perform(post("/api/logo-clubs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logoClub)))
            .andExpect(status().isCreated());

        // Validate the LogoClub in the database
        List<LogoClub> logoClubList = logoClubRepository.findAll();
        assertThat(logoClubList).hasSize(databaseSizeBeforeCreate + 1);
        LogoClub testLogoClub = logoClubList.get(logoClubList.size() - 1);
        assertThat(testLogoClub.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testLogoClub.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createLogoClubWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logoClubRepository.findAll().size();

        // Create the LogoClub with an existing ID
        logoClub.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogoClubMockMvc.perform(post("/api/logo-clubs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logoClub)))
            .andExpect(status().isBadRequest());

        // Validate the LogoClub in the database
        List<LogoClub> logoClubList = logoClubRepository.findAll();
        assertThat(logoClubList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLogoClubs() throws Exception {
        // Initialize the database
        logoClubRepository.saveAndFlush(logoClub);

        // Get all the logoClubList
        restLogoClubMockMvc.perform(get("/api/logo-clubs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logoClub.getId().intValue())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))));
    }
    
    @Test
    @Transactional
    public void getLogoClub() throws Exception {
        // Initialize the database
        logoClubRepository.saveAndFlush(logoClub);

        // Get the logoClub
        restLogoClubMockMvc.perform(get("/api/logo-clubs/{id}", logoClub.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(logoClub.getId().intValue()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)));
    }

    @Test
    @Transactional
    public void getNonExistingLogoClub() throws Exception {
        // Get the logoClub
        restLogoClubMockMvc.perform(get("/api/logo-clubs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogoClub() throws Exception {
        // Initialize the database
        logoClubService.save(logoClub);

        int databaseSizeBeforeUpdate = logoClubRepository.findAll().size();

        // Update the logoClub
        LogoClub updatedLogoClub = logoClubRepository.findById(logoClub.getId()).get();
        // Disconnect from session so that the updates on updatedLogoClub are not directly saved in db
        em.detach(updatedLogoClub);
        updatedLogoClub
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE);

        restLogoClubMockMvc.perform(put("/api/logo-clubs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLogoClub)))
            .andExpect(status().isOk());

        // Validate the LogoClub in the database
        List<LogoClub> logoClubList = logoClubRepository.findAll();
        assertThat(logoClubList).hasSize(databaseSizeBeforeUpdate);
        LogoClub testLogoClub = logoClubList.get(logoClubList.size() - 1);
        assertThat(testLogoClub.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testLogoClub.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingLogoClub() throws Exception {
        int databaseSizeBeforeUpdate = logoClubRepository.findAll().size();

        // Create the LogoClub

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogoClubMockMvc.perform(put("/api/logo-clubs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logoClub)))
            .andExpect(status().isBadRequest());

        // Validate the LogoClub in the database
        List<LogoClub> logoClubList = logoClubRepository.findAll();
        assertThat(logoClubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLogoClub() throws Exception {
        // Initialize the database
        logoClubService.save(logoClub);

        int databaseSizeBeforeDelete = logoClubRepository.findAll().size();

        // Delete the logoClub
        restLogoClubMockMvc.perform(delete("/api/logo-clubs/{id}", logoClub.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LogoClub> logoClubList = logoClubRepository.findAll();
        assertThat(logoClubList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
