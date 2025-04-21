package acc.library.reservation.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import acc.library.reservation.dto.ReservationDTO;
import acc.library.reservation.dto.ReservationInfoDTO;
import acc.library.reservation.entity.Books;
import acc.library.reservation.entity.BooksRepository;
import acc.library.reservation.entity.Reservations;
import acc.library.reservation.entity.ReservationsRepository;
import acc.library.reservation.mapper.ReservationMapper;
import acc.library.reservation.type.ReservationStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of the ReservationService interface responsible for handling
 * reservation-related business logic such as creating reservations, retrieving
 * reservations, and cancelling reservations. This class interacts with the BooksRepository
 * and ReservationsRepository for persistence operations and uses ReservationMapper
 * to map between entity and DTO representations.
 * This class is marked as a Spring component and is transactional for methods
 * that modify database state. Exceptions are thrown for various scenarios such
 * as missing resources, invalid operations, or exceeding constraints.
 */
@Component
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    BooksRepository booksRepository;

    @Autowired
    ReservationsRepository reservationsRepository;

    @Autowired
    ReservationMapper reservationMapper;

    /**
     * Creates a new reservation for a book by a user. Ensures that the book exists,
     * has available copies, and that the user does not exceed the reservation limit.
     * If the operation is successful, the number of available book copies is decremented,
     * and the reservation is saved.
     *
     * @param reservation the data transfer object containing the user ID and book ID
     *                    for the reservation request
     * @return the UUID of the newly created reservation
     * @throws ResponseStatusException if the book does not exist, no copies are available,
     *                                 or the user has reached the maximum number of active reservations
     */
    @Override
    @Transactional
    public UUID create(ReservationDTO reservation) throws ResponseStatusException {
        //find if book exists
        if (booksRepository.findById(reservation.getBookId()).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book Not Found");
        }

        //find if book has enough copies to be reserved
        if (booksRepository.findById(reservation.getBookId()).get().getCopies() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No copies of the book left to be reserved.");
        }
        //find if user can make more reservations
        if (reservationsRepository.numberOfActiveReservations(reservation.getUserId()) >= 3) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User " + reservation.getUserId() + " is not allow to make more book reservations.");
        }

        //Remove one unit
        booksRepository.decrementBookCopies(reservation.getBookId());

        return reservationsRepository.save(reservationMapper.mapReservationRequestDTOtoEntity(reservation)).getId();
    }

    /**
     * Fetches reservation details based on the provided reservation ID.
     * If the reservation is not found, a {@link ResponseStatusException} with
     * {@code HttpStatus.NOT_FOUND} is thrown.
     *
     * @param reservationId the unique identifier of the reservation to fetch
     * @return a {@link ReservationInfoDTO} containing details of the reservation
     * @throws ResponseStatusException if the reservation is not found in the repository
     */
    @Override
    public ReservationInfoDTO getReservationById(UUID reservationId) throws ResponseStatusException {
        if (reservationsRepository.findById(reservationId).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Reservation Not Found");
        }

        return getReservationInfoDTO(reservationId);
    }


    /**
     * Retrieves all reservations associated with a specific user.
     * If no reservations are found for the given user ID, a {@link ResponseStatusException}
     * with {@code HttpStatus.NOT_FOUND} is thrown.
     *
     * @param userId the unique identifier of the user whose reservations are to be retrieved
     * @return a list of {@link ReservationInfoDTO} objects containing details of each reservation
     * @throws ResponseStatusException if no reservations are found for the provided userId
     */
    @Override
    public List<ReservationInfoDTO> getAllReservationsByUser(UUID userId) {
        if (reservationsRepository.findByUserId(userId).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Reservations Not Found");
        }

        List<Reservations> allReservations = reservationsRepository.findByUserId(userId);

        List<ReservationInfoDTO> clientReservations = new ArrayList<>();

        allReservations.forEach(reservations -> {
            clientReservations.add(getReservationInfoDTO(reservations.getId()));
        });

        return clientReservations;
    }

    /**
     * Cancels the status of a reservation by updating it to CANCELED.
     * If the reservation ID does not exist, a {@link ResponseStatusException} with
     * {@code HttpStatus.NOT_FOUND} is thrown. If the provided status is not
     * {@code ReservationStatus.CANCELED}, a {@link ResponseStatusException} with
     * {@code HttpStatus.BAD_REQUEST} is thrown. This method will also
     * increment the book copies associated with the canceled reservation.
     *
     * @param reservationId the unique identifier of the reservation to cancel
     * @param status        the status to update the reservation to, expected to be {@code ReservationStatus.CANCELED}
     * @throws ResponseStatusException if the reservation ID does not exist or the status is invalid
     */
    @Override
    @Transactional
    public void cancelReservationStatus(UUID reservationId, ReservationStatus status) {
        if (reservationsRepository.findById(reservationId).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Reservation Not Found");
        }

        if (status != ReservationStatus.CANCELED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Status Change, only CANCELED is allowed.");
        }

        reservationsRepository.updateReservationStatus(status, reservationId);

        booksRepository.incrementBookCopies(reservationsRepository.findById(reservationId).get().getBookId());
    }

    private ReservationInfoDTO getReservationInfoDTO(UUID reservationId) {
        Reservations reservation = reservationsRepository.findById(reservationId).get();

        Books book = booksRepository.findById(reservation.getBookId()).get();

        return reservationMapper.mapReservationInfoEntityToDTO(reservation, book);
    }
}
