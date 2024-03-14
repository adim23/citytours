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
 * A Driver.
 */
@Entity
@Table(name = "driver")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "hired_at")
    private Instant hiredAt;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "driver")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "bookings", "tour", "vehicle", "driver" }, allowSetters = true)
    private Set<TourSchedule> tourSchedules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Driver id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Driver firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Driver lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Instant getHiredAt() {
        return this.hiredAt;
    }

    public Driver hiredAt(Instant hiredAt) {
        this.setHiredAt(hiredAt);
        return this;
    }

    public void setHiredAt(Instant hiredAt) {
        this.hiredAt = hiredAt;
    }

    public Integer getAge() {
        return this.age;
    }

    public Driver age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return this.email;
    }

    public Driver email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return this.mobile;
    }

    public Driver mobile(String mobile) {
        this.setMobile(mobile);
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Set<TourSchedule> getTourSchedules() {
        return this.tourSchedules;
    }

    public void setTourSchedules(Set<TourSchedule> tourSchedules) {
        if (this.tourSchedules != null) {
            this.tourSchedules.forEach(i -> i.setDriver(null));
        }
        if (tourSchedules != null) {
            tourSchedules.forEach(i -> i.setDriver(this));
        }
        this.tourSchedules = tourSchedules;
    }

    public Driver tourSchedules(Set<TourSchedule> tourSchedules) {
        this.setTourSchedules(tourSchedules);
        return this;
    }

    public Driver addTourSchedule(TourSchedule tourSchedule) {
        this.tourSchedules.add(tourSchedule);
        tourSchedule.setDriver(this);
        return this;
    }

    public Driver removeTourSchedule(TourSchedule tourSchedule) {
        this.tourSchedules.remove(tourSchedule);
        tourSchedule.setDriver(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Driver)) {
            return false;
        }
        return getId() != null && getId().equals(((Driver) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Driver{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", hiredAt='" + getHiredAt() + "'" +
            ", age=" + getAge() +
            ", email='" + getEmail() + "'" +
            ", mobile='" + getMobile() + "'" +
            "}";
    }
}
