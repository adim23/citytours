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
 * A Vehicle.
 */
@Entity
@Table(name = "vehicle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "plate", nullable = false)
    private String plate;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "color")
    private String color;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "bookings", "tour", "vehicle", "driver" }, allowSetters = true)
    private Set<TourSchedule> tourSchedules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vehicle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlate() {
        return this.plate;
    }

    public Vehicle plate(String plate) {
        this.setPlate(plate);
        return this;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getType() {
        return this.type;
    }

    public Vehicle type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public Vehicle capacity(Integer capacity) {
        this.setCapacity(capacity);
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getColor() {
        return this.color;
    }

    public Vehicle color(String color) {
        this.setColor(color);
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Set<TourSchedule> getTourSchedules() {
        return this.tourSchedules;
    }

    public void setTourSchedules(Set<TourSchedule> tourSchedules) {
        if (this.tourSchedules != null) {
            this.tourSchedules.forEach(i -> i.setVehicle(null));
        }
        if (tourSchedules != null) {
            tourSchedules.forEach(i -> i.setVehicle(this));
        }
        this.tourSchedules = tourSchedules;
    }

    public Vehicle tourSchedules(Set<TourSchedule> tourSchedules) {
        this.setTourSchedules(tourSchedules);
        return this;
    }

    public Vehicle addTourSchedule(TourSchedule tourSchedule) {
        this.tourSchedules.add(tourSchedule);
        tourSchedule.setVehicle(this);
        return this;
    }

    public Vehicle removeTourSchedule(TourSchedule tourSchedule) {
        this.tourSchedules.remove(tourSchedule);
        tourSchedule.setVehicle(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicle)) {
            return false;
        }
        return getId() != null && getId().equals(((Vehicle) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", plate='" + getPlate() + "'" +
            ", type='" + getType() + "'" +
            ", capacity=" + getCapacity() +
            ", color='" + getColor() + "'" +
            "}";
    }
}
