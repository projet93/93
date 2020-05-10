package fr.district.codemax.repository;

import fr.district.codemax.domain.Plateau;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Plateau entity.
 */
@Repository
public interface PlateauRepository extends JpaRepository<Plateau, Long>, JpaSpecificationExecutor<Plateau> {

    @Query("select plateau from Plateau plateau where plateau.user.login = ?#{principal.username}")
    List<Plateau> findByUserIsCurrentUser();

//    @Query(value = "select distinct plateau from Plateau plateau",
//        countQuery = "select count(distinct plateau) from Plateau plateau")
//    Page<Plateau> findAll(Pageable pageable);

    @Query("select distinct plateau from Plateau plateau left join fetch plateau.user")
    List<Plateau> findAllWithLazyRelationships();

    @Query("select plateau from Plateau plateau left join fetch plateau.stade join fetch plateau.referent join fetch plateau.categorie where plateau.id =:id")
    Optional<Plateau> findOneWithEagerRelationships(@Param("id") Long id);
    
    @Query("select plateau from Plateau plateau where plateau.id =:id")
    Optional<Plateau> findOneWithLazyRelationships(@Param("id") Long id);

    @Query("select plateau from Plateau plateau where plateau.valid = true or plateau.user.login = ?#{principal.username}")
	Page<Plateau> findByUserIsCurrentUser(Pageable pageable);
}
