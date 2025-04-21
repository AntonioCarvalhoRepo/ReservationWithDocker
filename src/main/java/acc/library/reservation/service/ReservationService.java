package acc.library.reservation.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import acc.library.reservation.dto.ReservationDTO;
import acc.library.reservation.dto.ReservationInfoDTO;
import acc.library.reservation.type.ReservationStatus;

import java.util.List;
import java.util.UUID;

@Service
public interface ReservationService {
    UUID create(@Valid ReservationDTO reservation) throws ResponseStatusException;

    ReservationInfoDTO getReservationById(UUID reservationId) throws ResponseStatusException;

    List<ReservationInfoDTO> getAllReservationsByUser(UUID userId);

    void cancelReservationStatus(UUID reservationId, ReservationStatus status);
}
