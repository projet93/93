package fr.district.codemax.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Club.
 */
@Entity
@Table(name = "club")
public class Club implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "telephone")
    private String telephone;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @OneToOne
    @JoinColumn(unique = true)
    private LogoClub logoClub;

    @OneToMany(mappedBy = "club")
    private Set<Stade> stades = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("clubs")
    private User user;

    @ManyToMany
    @JoinTable(name = "club_categorie",
               joinColumns = @JoinColumn(name = "club_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "categorie_id", referencedColumnName = "id"))
    private Set<Categorie> categories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Club nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Club adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public Club telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public Club email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LogoClub getLogoClub() {
        return logoClub;
    }

    public Club logoClub(LogoClub logoClub) {
        this.logoClub = logoClub;
        return this;
    }

    public void setLogoClub(LogoClub logoClub) {
        this.logoClub = logoClub;
    }

    public Set<Stade> getStades() {
        return stades;
    }

    public Club stades(Set<Stade> stades) {
        this.stades = stades;
        return this;
    }

    public Club addStade(Stade stade) {
        this.stades.add(stade);
        stade.setClub(this);
        return this;
    }

    public Club removeStade(Stade stade) {
        this.stades.remove(stade);
        stade.setClub(null);
        return this;
    }

    public void setStades(Set<Stade> stades) {
        this.stades = stades;
    }

    public User getUser() {
        return user;
    }

    public Club user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Categorie> getCategories() {
        return categories;
    }

    public Club categories(Set<Categorie> categories) {
        this.categories = categories;
        return this;
    }

    public Club addCategorie(Categorie categorie) {
        this.categories.add(categorie);
        categorie.getClubs().add(this);
        return this;
    }

    public Club removeCategorie(Categorie categorie) {
        this.categories.remove(categorie);
        categorie.getClubs().remove(this);
        return this;
    }

    public void setCategories(Set<Categorie> categories) {
        this.categories = categories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Club)) {
            return false;
        }
        return id != null && id.equals(((Club) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Club{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
