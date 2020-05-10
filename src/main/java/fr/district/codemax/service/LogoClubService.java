package fr.district.codemax.service;

import fr.district.codemax.domain.LogoClub;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LogoClub}.
 */
public interface LogoClubService {

    /**
     * Save a logoClub.
     *
     * @param logoClub the entity to save.
     * @return the persisted entity.
     */
    LogoClub save(LogoClub logoClub);

    /**
     * Get all the logoClubs.
     *
     * @return the list of entities.
     */
    List<LogoClub> findAll();
    /**
     * Get all the LogoClubDTO where Club is {@code null}.
     *
     * @return the list of entities.
     */
    List<LogoClub> findAllWhereClubIsNull();

    /**
     * Get the "id" logoClub.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LogoClub> findOne(Long id);

    /**
     * Delete the "id" logoClub.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
