package fr.district.codemax.repository;

import fr.district.codemax.domain.LogoClub;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LogoClub entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogoClubRepository extends JpaRepository<LogoClub, Long> {
}
