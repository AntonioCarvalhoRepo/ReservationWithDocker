package acc.library.reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import acc.library.reservation.type.ReservationStatus;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservations")
public class Reservations {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", unique = true, nullable = false)
    public UUID id;

    @Column(name = "userId")
    private UUID userId;

    @Column(name = "bookId")
    private UUID bookId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    @CreationTimestamp
    public Date created;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
