package acc.library.reservation.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for managing {@link Books} entities. Extends {@link JpaRepository}
 * to provide basic CRUD operations. It includes custom methods to modify the number of
 * available copies of a book in the inventory.
 * This repository is used by services to handle database interactions related to books,
 * particularly during operations like reservations and cancellations.
 */
@Repository
public interface BooksRepository extends JpaRepository<Books, UUID> {
    /**
     * Decrements the number of available copies of a book in the inventory by 1.
     * This method is typically used when a book is reserved, to reflect that one less copy
     * is available for other reservations.
     *
     * @param bookId the unique identifier of the book whose available copies are to be decremented
     */
    @Modifying
    @Query("UPDATE Books books set books.copies = books.copies - 1 where books.id = ?1")
    void decrementBookCopies(UUID bookId);

    /**
     * Increments the number of available copies of a book in the inventory by 1.
     * This method is typically used when a reservation is canceled, to reflect that
     * one more copy is available for other users.
     *
     * @param bookId the unique identifier of the book whose available copies are to be incremented
     */
    @Modifying
    @Query("UPDATE Books books set books.copies = books.copies - 1 where books.id = ?1")
    void incrementBookCopies(UUID bookId);
}

