package acc.library.reservation.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import acc.library.reservation.type.ReservationStatus;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing {@link Reservations} entities. It extends {@link JpaRepository}
 * to provide basic CRUD operations and includes custom methods for handling reservation-specific
 * queries and modifications. This repository is a key component for interacting with the
 * reservations table in the database.
 */
@Repository
public interface ReservationsRepository extends JpaRepository<Reservations, UUID> {
    /**
     * Retrieves the count of active reservations made by a specific user.
     *
     * @param userId the unique identifier of the user whose active reservations are to be counted
     * @return the total number of active reservations for the specified user
     */
    @Query(value = "SELECT COUNT(id) from reservations WHERE user_id= ?1 AND reservations.status = 'ACTIVE'", nativeQuery = true)
    Long numberOfActiveReservations(UUID userId);

    /**
     * Updates the status of a reservation in the database.
     *
     * @param status        the new status to update the reservation to; must be a valid {@link ReservationStatus} value
     * @param reservationId the unique identifier of the reservation to update
     */
    @Modifying
    @Query("UPDATE Reservations reservations set reservations.status = ?1 where reservations.id = ?2")
    void updateReservationStatus(ReservationStatus status, UUID reservationId);

    List<Reservations> findByUserId(UUID userId);

    /**
     * Updates the status of all reservations in the database to "EXPIRED".
     * This is a bulk update operation that modifies all rows in the reservations
     * table by setting their status to {@link ReservationStatus#EXPIRED}.
     * Typically used for expiring reservations that exceed a given threshold
     * duration. It requires that the calling method is executed within a
     * transactional context.
     * Note: Ensure that this operation is used cautiously as it affects all
     * records in the reservations table.
     */
    @Modifying
    @Query("UPDATE Reservations reservations set reservations.status = 'EXPIRED'")
    void updateAllReservationStatus();
}

