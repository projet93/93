package fr.district.codemax.service.impl;

import fr.district.codemax.service.LogoClubService;
import fr.district.codemax.domain.LogoClub;
import fr.district.codemax.repository.LogoClubRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link LogoClub}.
 */
@Service
@Transactional
public class LogoClubServiceImpl implements LogoClubService {

    private final Logger log = LoggerFactory.getLogger(LogoClubServiceImpl.class);

    private final LogoClubRepository logoClubRepository;

    public LogoClubServiceImpl(LogoClubRepository logoClubRepository) {
        this.logoClubRepository = logoClubRepository;
    }

    /**
     * Save a logoClub.
     *
     * @param logoClub the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LogoClub save(LogoClub logoClub) {
        log.debug("Request to save LogoClub : {}", logoClub);
        return logoClubRepository.save(logoClub);
    }

    /**
     * Get all the logoClubs.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LogoClub> findAll() {
        log.debug("Request to get all LogoClubs");
        return logoClubRepository.findAll();
    }


    /**
     *  Get all the logoClubs where Club is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<LogoClub> findAllWhereClubIsNull() {
        log.debug("Request to get all logoClubs where Club is null");
        return StreamSupport
            .stream(logoClubRepository.findAll().spliterator(), false)
            .filter(logoClub -> logoClub.getClub() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one logoClub by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LogoClub> findOne(Long id) {
        log.debug("Request to get LogoClub : {}", id);
        return logoClubRepository.findById(id);
    }

    /**
     * Delete the logoClub by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LogoClub : {}", id);
        logoClubRepository.deleteById(id);
    }
}
