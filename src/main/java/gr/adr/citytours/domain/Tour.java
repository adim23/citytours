package gr.adr.citytours.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tour.
 */
@Entity
@Table(name = "tour")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tour implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @NotNull
    @Column(name = "pet_friendly", nullable = false)
    private Boolean petFriendly;

    @NotNull
    @Column(name = "kids_allowed", nullable = false)
    private Boolean kidsAllowed;

    @Column(name = "available_from_date")
    private LocalDate availableFromDate;

    @Column(name = "available_to_date")
    private LocalDate availableToDate;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tour")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "bookings", "tour", "vehicle", "driver" }, allowSetters = true)
    private Set<TourSchedule> tourSchedules = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tour")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tour", "place" }, allowSetters = true)
    private Set<TourStep> steps = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tours", "places" }, allowSetters = true)
    private City city;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tour id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Tour code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public Tour title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Tour duration(Integer duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getPetFriendly() {
        return this.petFriendly;
    }

    public Tour petFriendly(Boolean petFriendly) {
        this.setPetFriendly(petFriendly);
        return this;
    }

    public void setPetFriendly(Boolean petFriendly) {
        this.petFriendly = petFriendly;
    }

    public Boolean getKidsAllowed() {
        return this.kidsAllowed;
    }

    public Tour kidsAllowed(Boolean kidsAllowed) {
        this.setKidsAllowed(kidsAllowed);
        return this;
    }

    public void setKidsAllowed(Boolean kidsAllowed) {
        this.kidsAllowed = kidsAllowed;
    }

    public LocalDate getAvailableFromDate() {
        return this.availableFromDate;
    }

    public Tour availableFromDate(LocalDate availableFromDate) {
        this.setAvailableFromDate(availableFromDate);
        return this;
    }

    public void setAvailableFromDate(LocalDate availableFromDate) {
        this.availableFromDate = availableFromDate;
    }

    public LocalDate getAvailableToDate() {
        return this.availableToDate;
    }

    public Tour availableToDate(LocalDate availableToDate) {
        this.setAvailableToDate(availableToDate);
        return this;
    }

    public void setAvailableToDate(LocalDate availableToDate) {
        this.availableToDate = availableToDate;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public Tour enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<TourSchedule> getTourSchedules() {
        return this.tourSchedules;
    }

    public void setTourSchedules(Set<TourSchedule> tourSchedules) {
        if (this.tourSchedules != null) {
            this.tourSchedules.forEach(i -> i.setTour(null));
        }
        if (tourSchedules != null) {
            tourSchedules.forEach(i -> i.setTour(this));
        }
        this.tourSchedules = tourSchedules;
    }

    public Tour tourSchedules(Set<TourSchedule> tourSchedules) {
        this.setTourSchedules(tourSchedules);
        return this;
    }

    public Tour addTourSchedule(TourSchedule tourSchedule) {
        this.tourSchedules.add(tourSchedule);
        tourSchedule.setTour(this);
        return this;
    }

    public Tour removeTourSchedule(TourSchedule tourSchedule) {
        this.tourSchedules.remove(tourSchedule);
        tourSchedule.setTour(null);
        return this;
    }

    public Set<TourStep> getSteps() {
        return this.steps;
    }

    public void setSteps(Set<TourStep> tourSteps) {
        if (this.steps != null) {
            this.steps.forEach(i -> i.setTour(null));
        }
        if (tourSteps != null) {
            tourSteps.forEach(i -> i.setTour(this));
        }
        this.steps = tourSteps;
    }

    public Tour steps(Set<TourStep> tourSteps) {
        this.setSteps(tourSteps);
        return this;
    }

    public Tour addSteps(TourStep tourStep) {
        this.steps.add(tourStep);
        tourStep.setTour(this);
        return this;
    }

    public Tour removeSteps(TourStep tourStep) {
        this.steps.remove(tourStep);
        tourStep.setTour(null);
        return this;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Tour city(City city) {
        this.setCity(city);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tour)) {
            return false;
        }
        return getId() != null && getId().equals(((Tour) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tour{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", title='" + getTitle() + "'" +
            ", duration=" + getDuration() +
            ", petFriendly='" + getPetFriendly() + "'" +
            ", kidsAllowed='" + getKidsAllowed() + "'" +
            ", availableFromDate='" + getAvailableFromDate() + "'" +
            ", availableToDate='" + getAvailableToDate() + "'" +
            ", enabled='" + getEnabled() + "'" +
            "}";
    }
}
