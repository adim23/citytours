package gr.adr.citytours.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "book_datetime", nullable = false)
    private Instant bookDatetime;

    @Column(name = "no_persons")
    private Integer noPersons;

    @Column(name = "no_kids")
    private Integer noKids;

    @Column(name = "no_pets")
    private Integer noPets;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "valid")
    private Boolean valid;

    @Column(name = "cancelled_at")
    private Instant cancelledAt;

    @Lob
    @Column(name = "remote_data")
    private String remoteData;

    @Column(name = "remote_id")
    private String remoteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "bookings", "tour", "vehicle", "driver" }, allowSetters = true)
    private TourSchedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "bookings" }, allowSetters = true)
    private Passenger passenger;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Booking id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getBookDatetime() {
        return this.bookDatetime;
    }

    public Booking bookDatetime(Instant bookDatetime) {
        this.setBookDatetime(bookDatetime);
        return this;
    }

    public void setBookDatetime(Instant bookDatetime) {
        this.bookDatetime = bookDatetime;
    }

    public Integer getNoPersons() {
        return this.noPersons;
    }

    public Booking noPersons(Integer noPersons) {
        this.setNoPersons(noPersons);
        return this;
    }

    public void setNoPersons(Integer noPersons) {
        this.noPersons = noPersons;
    }

    public Integer getNoKids() {
        return this.noKids;
    }

    public Booking noKids(Integer noKids) {
        this.setNoKids(noKids);
        return this;
    }

    public void setNoKids(Integer noKids) {
        this.noKids = noKids;
    }

    public Integer getNoPets() {
        return this.noPets;
    }

    public Booking noPets(Integer noPets) {
        this.setNoPets(noPets);
        return this;
    }

    public void setNoPets(Integer noPets) {
        this.noPets = noPets;
    }

    public Double getCost() {
        return this.cost;
    }

    public Booking cost(Double cost) {
        this.setCost(cost);
        return this;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

    public Booking paymentType(String paymentType) {
        this.setPaymentType(paymentType);
        return this;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Boolean getValid() {
        return this.valid;
    }

    public Booking valid(Boolean valid) {
        this.setValid(valid);
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Instant getCancelledAt() {
        return this.cancelledAt;
    }

    public Booking cancelledAt(Instant cancelledAt) {
        this.setCancelledAt(cancelledAt);
        return this;
    }

    public void setCancelledAt(Instant cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public String getRemoteData() {
        return this.remoteData;
    }

    public Booking remoteData(String remoteData) {
        this.setRemoteData(remoteData);
        return this;
    }

    public void setRemoteData(String remoteData) {
        this.remoteData = remoteData;
    }

    public String getRemoteId() {
        return this.remoteId;
    }

    public Booking remoteId(String remoteId) {
        this.setRemoteId(remoteId);
        return this;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public TourSchedule getSchedule() {
        return this.schedule;
    }

    public void setSchedule(TourSchedule tourSchedule) {
        this.schedule = tourSchedule;
    }

    public Booking schedule(TourSchedule tourSchedule) {
        this.setSchedule(tourSchedule);
        return this;
    }

    public Passenger getPassenger() {
        return this.passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Booking passenger(Passenger passenger) {
        this.setPassenger(passenger);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Booking)) {
            return false;
        }
        return getId() != null && getId().equals(((Booking) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", bookDatetime='" + getBookDatetime() + "'" +
            ", noPersons=" + getNoPersons() +
            ", noKids=" + getNoKids() +
            ", noPets=" + getNoPets() +
            ", cost=" + getCost() +
            ", paymentType='" + getPaymentType() + "'" +
            ", valid='" + getValid() + "'" +
            ", cancelledAt='" + getCancelledAt() + "'" +
            ", remoteData='" + getRemoteData() + "'" +
            ", remoteId='" + getRemoteId() + "'" +
            "}";
    }
}
