package gr.adr.citytours.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TourStep.
 */
@Entity
@Table(name = "tour_step")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TourStep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "step_order", nullable = false)
    private Integer stepOrder;

    @Column(name = "wait_time")
    private Long waitTime;

    @Column(name = "drive_time")
    private Integer driveTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tourSchedules", "steps", "city" }, allowSetters = true)
    private Tour tour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "steps", "images", "city" }, allowSetters = true)
    private Place place;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TourStep id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public TourStep code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStepOrder() {
        return this.stepOrder;
    }

    public TourStep stepOrder(Integer stepOrder) {
        this.setStepOrder(stepOrder);
        return this;
    }

    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }

    public Long getWaitTime() {
        return this.waitTime;
    }

    public TourStep waitTime(Long waitTime) {
        this.setWaitTime(waitTime);
        return this;
    }

    public void setWaitTime(Long waitTime) {
        this.waitTime = waitTime;
    }

    public Integer getDriveTime() {
        return this.driveTime;
    }

    public TourStep driveTime(Integer driveTime) {
        this.setDriveTime(driveTime);
        return this;
    }

    public void setDriveTime(Integer driveTime) {
        this.driveTime = driveTime;
    }

    public Tour getTour() {
        return this.tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public TourStep tour(Tour tour) {
        this.setTour(tour);
        return this;
    }

    public Place getPlace() {
        return this.place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public TourStep place(Place place) {
        this.setPlace(place);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TourStep)) {
            return false;
        }
        return getId() != null && getId().equals(((TourStep) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TourStep{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", stepOrder=" + getStepOrder() +
            ", waitTime=" + getWaitTime() +
            ", driveTime=" + getDriveTime() +
            "}";
    }
}
