package gr.adr.citytours.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TourSchedule.
 */
@Entity
@Table(name = "tour_schedule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TourSchedule implements Serializable {

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
    @Column(name = "start_datetime", nullable = false)
    private Instant startDatetime;

    @Column(name = "no_passengers")
    private Integer noPassengers;

    @Column(name = "no_kids")
    private Integer noKids;

    @Column(name = "no_pets")
    private Integer noPets;

    @Column(name = "start_place")
    private String startPlace;

    @Column(name = "end_place")
    private String endPlace;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "schedule", "passenger" }, allowSetters = true)
    private Set<Booking> bookings = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tourSchedules", "steps", "city" }, allowSetters = true)
    private Tour tour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tourSchedules" }, allowSetters = true)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tourSchedules" }, allowSetters = true)
    private Driver driver;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TourSchedule id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public TourSchedule code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getStartDatetime() {
        return this.startDatetime;
    }

    public TourSchedule startDatetime(Instant startDatetime) {
        this.setStartDatetime(startDatetime);
        return this;
    }

    public void setStartDatetime(Instant startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Integer getNoPassengers() {
        return this.noPassengers;
    }

    public TourSchedule noPassengers(Integer noPassengers) {
        this.setNoPassengers(noPassengers);
        return this;
    }

    public void setNoPassengers(Integer noPassengers) {
        this.noPassengers = noPassengers;
    }

    public Integer getNoKids() {
        return this.noKids;
    }

    public TourSchedule noKids(Integer noKids) {
        this.setNoKids(noKids);
        return this;
    }

    public void setNoKids(Integer noKids) {
        this.noKids = noKids;
    }

    public Integer getNoPets() {
        return this.noPets;
    }

    public TourSchedule noPets(Integer noPets) {
        this.setNoPets(noPets);
        return this;
    }

    public void setNoPets(Integer noPets) {
        this.noPets = noPets;
    }

    public String getStartPlace() {
        return this.startPlace;
    }

    public TourSchedule startPlace(String startPlace) {
        this.setStartPlace(startPlace);
        return this;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getEndPlace() {
        return this.endPlace;
    }

    public TourSchedule endPlace(String endPlace) {
        this.setEndPlace(endPlace);
        return this;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public Set<Booking> getBookings() {
        return this.bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        if (this.bookings != null) {
            this.bookings.forEach(i -> i.setSchedule(null));
        }
        if (bookings != null) {
            bookings.forEach(i -> i.setSchedule(this));
        }
        this.bookings = bookings;
    }

    public TourSchedule bookings(Set<Booking> bookings) {
        this.setBookings(bookings);
        return this;
    }

    public TourSchedule addBookings(Booking booking) {
        this.bookings.add(booking);
        booking.setSchedule(this);
        return this;
    }

    public TourSchedule removeBookings(Booking booking) {
        this.bookings.remove(booking);
        booking.setSchedule(null);
        return this;
    }

    public Tour getTour() {
        return this.tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public TourSchedule tour(Tour tour) {
        this.setTour(tour);
        return this;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public TourSchedule vehicle(Vehicle vehicle) {
        this.setVehicle(vehicle);
        return this;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public TourSchedule driver(Driver driver) {
        this.setDriver(driver);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TourSchedule)) {
            return false;
        }
        return getId() != null && getId().equals(((TourSchedule) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TourSchedule{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", startDatetime='" + getStartDatetime() + "'" +
            ", noPassengers=" + getNoPassengers() +
            ", noKids=" + getNoKids() +
            ", noPets=" + getNoPets() +
            ", startPlace='" + getStartPlace() + "'" +
            ", endPlace='" + getEndPlace() + "'" +
            "}";
    }
}
