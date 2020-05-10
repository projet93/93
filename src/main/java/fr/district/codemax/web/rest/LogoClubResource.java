package fr.district.codemax.web.rest;

import fr.district.codemax.domain.LogoClub;
import fr.district.codemax.service.LogoClubService;
import fr.district.codemax.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link fr.district.codemax.domain.LogoClub}.
 */
@RestController
@RequestMapping("/api")
public class LogoClubResource {

    private final Logger log = LoggerFactory.getLogger(LogoClubResource.class);

    private static final String ENTITY_NAME = "logoClub";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LogoClubService logoClubService;

    public LogoClubResource(LogoClubService logoClubService) {
        this.logoClubService = logoClubService;
    }

    /**
     * {@code POST  /logo-clubs} : Create a new logoClub.
     *
     * @param logoClub the logoClub to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new logoClub, or with status {@code 400 (Bad Request)} if the logoClub has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/logo-clubs")
    public ResponseEntity<LogoClub> createLogoClub(@RequestBody LogoClub logoClub) throws URISyntaxException {
        log.debug("REST request to save LogoClub : {}", logoClub);
        if (logoClub.getId() != null) {
            throw new BadRequestAlertException("A new logoClub cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogoClub result = logoClubService.save(logoClub);
        return ResponseEntity.created(new URI("/api/logo-clubs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /logo-clubs} : Updates an existing logoClub.
     *
     * @param logoClub the logoClub to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logoClub,
     * or with status {@code 400 (Bad Request)} if the logoClub is not valid,
     * or with status {@code 500 (Internal Server Error)} if the logoClub couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/logo-clubs")
    public ResponseEntity<LogoClub> updateLogoClub(@RequestBody LogoClub logoClub) throws URISyntaxException {
        log.debug("REST request to update LogoClub : {}", logoClub);
        if (logoClub.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LogoClub result = logoClubService.save(logoClub);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, logoClub.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /logo-clubs} : get all the logoClubs.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of logoClubs in body.
     */
    @GetMapping("/logo-clubs")
    public List<LogoClub> getAllLogoClubs(@RequestParam(required = false) String filter) {
        if ("club-is-null".equals(filter)) {
            log.debug("REST request to get all LogoClubs where club is null");
            return logoClubService.findAllWhereClubIsNull();
        }
        log.debug("REST request to get all LogoClubs");
        return logoClubService.findAll();
    }

    /**
     * {@code GET  /logo-clubs/:id} : get the "id" logoClub.
     *
     * @param id the id of the logoClub to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the logoClub, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/logo-clubs/{id}")
    public ResponseEntity<LogoClub> getLogoClub(@PathVariable Long id) {
        log.debug("REST request to get LogoClub : {}", id);
        Optional<LogoClub> logoClub = logoClubService.findOne(id);
        return ResponseUtil.wrapOrNotFound(logoClub);
    }

    /**
     * {@code DELETE  /logo-clubs/:id} : delete the "id" logoClub.
     *
     * @param id the id of the logoClub to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/logo-clubs/{id}")
    public ResponseEntity<Void> deleteLogoClub(@PathVariable Long id) {
        log.debug("REST request to delete LogoClub : {}", id);
        logoClubService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
