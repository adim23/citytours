package gr.adr.citytours.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Place.
 */
@Entity
@Table(name = "place")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Lob
    @Column(name = "full_description", nullable = false)
    private String fullDescription;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tour", "place" }, allowSetters = true)
    private Set<TourStep> steps = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "place" }, allowSetters = true)
    private Set<ImageFile> images = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tours", "places" }, allowSetters = true)
    private City city;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Place id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Place code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Place name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Place description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullDescription() {
        return this.fullDescription;
    }

    public Place fullDescription(String fullDescription) {
        this.setFullDescription(fullDescription);
        return this;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public Place longitude(String longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public Place latitude(String latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Set<TourStep> getSteps() {
        return this.steps;
    }

    public void setSteps(Set<TourStep> tourSteps) {
        if (this.steps != null) {
            this.steps.forEach(i -> i.setPlace(null));
        }
        if (tourSteps != null) {
            tourSteps.forEach(i -> i.setPlace(this));
        }
        this.steps = tourSteps;
    }

    public Place steps(Set<TourStep> tourSteps) {
        this.setSteps(tourSteps);
        return this;
    }

    public Place addSteps(TourStep tourStep) {
        this.steps.add(tourStep);
        tourStep.setPlace(this);
        return this;
    }

    public Place removeSteps(TourStep tourStep) {
        this.steps.remove(tourStep);
        tourStep.setPlace(null);
        return this;
    }

    public Set<ImageFile> getImages() {
        return this.images;
    }

    public void setImages(Set<ImageFile> imageFiles) {
        if (this.images != null) {
            this.images.forEach(i -> i.setPlace(null));
        }
        if (imageFiles != null) {
            imageFiles.forEach(i -> i.setPlace(this));
        }
        this.images = imageFiles;
    }

    public Place images(Set<ImageFile> imageFiles) {
        this.setImages(imageFiles);
        return this;
    }

    public Place addImages(ImageFile imageFile) {
        this.images.add(imageFile);
        imageFile.setPlace(this);
        return this;
    }

    public Place removeImages(ImageFile imageFile) {
        this.images.remove(imageFile);
        imageFile.setPlace(null);
        return this;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Place city(City city) {
        this.setCity(city);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Place)) {
            return false;
        }
        return getId() != null && getId().equals(((Place) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Place{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", fullDescription='" + getFullDescription() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            "}";
    }
}
