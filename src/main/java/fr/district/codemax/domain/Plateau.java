package fr.district.codemax.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fr.district.codemax.domain.enumeration.Statut;

/**
 * A Plateau.
 */
@Entity
@Table(name = "plateau")
public class Plateau implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private Instant dateDebut;

    @Column(name = "date_fin")
    private Instant dateFin;

    @Column(name = "nombre_equipe_max")
    private Integer nombreEquipeMax;

    @Column(name = "nombre_equipe")
    private Integer nombreEquipe;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private Statut statut;

    @Column(name = "valid")
    private Boolean valid;

    @Version
    @Column(name = "version")
    private long version;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private DocumentPlateau documentPlateau;

    @OneToMany(mappedBy = "plateau",fetch = FetchType.EAGER)
    private Set<Inscription> inscriptions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("plateaus")
    private Referent referent;

    @ManyToOne
    @JsonIgnoreProperties("plateaus")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("plateaus")
    private Stade stade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("plateaus")
    private Categorie categorie;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public Plateau dateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Instant getDateFin() {
        return dateFin;
    }

    public Plateau dateFin(Instant dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public Integer getNombreEquipeMax() {
        return nombreEquipeMax;
    }

    public Plateau nombreEquipeMax(Integer nombreEquipeMax) {
        this.nombreEquipeMax = nombreEquipeMax;
        return this;
    }

    public void setNombreEquipeMax(Integer nombreEquipeMax) {
        this.nombreEquipeMax = nombreEquipeMax;
    }

    public Integer getNombreEquipe() {
        return nombreEquipe;
    }

    public Plateau nombreEquipe(Integer nombreEquipe) {
        this.nombreEquipe = nombreEquipe;
        return this;
    }

    public void setNombreEquipe(Integer nombreEquipe) {
        this.nombreEquipe = nombreEquipe;
    }

    public Statut getStatut() {
        return statut;
    }

    public Plateau statut(Statut statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Boolean isValid() {
        return valid;
    }

    public Plateau valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public long getVersion() {
        return version;
    }

    public Plateau version(long version) {
        this.version = version;
        return this;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public DocumentPlateau getDocumentPlateau() {
        return documentPlateau;
    }

    public Plateau documentPlateau(DocumentPlateau documentPlateau) {
        this.documentPlateau = documentPlateau;
        return this;
    }

    public void setDocumentPlateau(DocumentPlateau documentPlateau) {
        this.documentPlateau = documentPlateau;
    }

    public Set<Inscription> getInscriptions() {
        return inscriptions;
    }

    public Plateau inscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
        return this;
    }

    public Plateau addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
        inscription.setPlateau(this);
        return this;
    }

    public Plateau removeInscription(Inscription inscription) {
        this.inscriptions.remove(inscription);
        inscription.setPlateau(null);
        return this;
    }

    public void setInscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public Referent getReferent() {
        return referent;
    }

    public Plateau referent(Referent referent) {
        this.referent = referent;
        return this;
    }

    public void setReferent(Referent referent) {
        this.referent = referent;
    }

    public User getUser() {
        return user;
    }

    public Plateau user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stade getStade() {
        return stade;
    }

    public Plateau stade(Stade stade) {
        this.stade = stade;
        return this;
    }

    public void setStade(Stade stade) {
        this.stade = stade;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public Plateau categorie(Categorie categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plateau)) {
            return false;
        }
        return id != null && id.equals(((Plateau) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Plateau{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", nombreEquipeMax=" + getNombreEquipeMax() +
            ", nombreEquipe=" + getNombreEquipe() +
            ", statut='" + getStatut() + "'" +
            ", valid='" + isValid() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
