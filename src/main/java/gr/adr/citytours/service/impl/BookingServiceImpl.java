package gr.adr.citytours.service.impl;

import gr.adr.citytours.domain.Booking;
import gr.adr.citytours.repository.BookingRepository;
import gr.adr.citytours.service.BookingService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.citytours.domain.Booking}.
 */
@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking save(Booking booking) {
        log.debug("Request to save Booking : {}", booking);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking update(Booking booking) {
        log.debug("Request to update Booking : {}", booking);
        return bookingRepository.save(booking);
    }

    @Override
    public Optional<Booking> partialUpdate(Booking booking) {
        log.debug("Request to partially update Booking : {}", booking);

        return bookingRepository
            .findById(booking.getId())
            .map(existingBooking -> {
                if (booking.getBookDatetime() != null) {
                    existingBooking.setBookDatetime(booking.getBookDatetime());
                }
                if (booking.getNoPersons() != null) {
                    existingBooking.setNoPersons(booking.getNoPersons());
                }
                if (booking.getNoKids() != null) {
                    existingBooking.setNoKids(booking.getNoKids());
                }
                if (booking.getNoPets() != null) {
                    existingBooking.setNoPets(booking.getNoPets());
                }
                if (booking.getCost() != null) {
                    existingBooking.setCost(booking.getCost());
                }
                if (booking.getPaymentType() != null) {
                    existingBooking.setPaymentType(booking.getPaymentType());
                }
                if (booking.getValid() != null) {
                    existingBooking.setValid(booking.getValid());
                }
                if (booking.getCancelledAt() != null) {
                    existingBooking.setCancelledAt(booking.getCancelledAt());
                }
                if (booking.getRemoteData() != null) {
                    existingBooking.setRemoteData(booking.getRemoteData());
                }
                if (booking.getRemoteId() != null) {
                    existingBooking.setRemoteId(booking.getRemoteId());
                }

                return existingBooking;
            })
            .map(bookingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Booking> findAll(Pageable pageable) {
        log.debug("Request to get all Bookings");
        return bookingRepository.findAll(pageable);
    }

    public Page<Booking> findAllWithEagerRelationships(Pageable pageable) {
        return bookingRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> findOne(Long id) {
        log.debug("Request to get Booking : {}", id);
        return bookingRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Booking : {}", id);
        bookingRepository.deleteById(id);
    }
}
