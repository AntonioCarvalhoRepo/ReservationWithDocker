package acc.library.reservation.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import acc.library.reservation.dto.ReservationDTO;
import acc.library.reservation.dto.ReservationInfoDTO;
import acc.library.reservation.entity.Books;
import acc.library.reservation.entity.Reservations;
import acc.library.reservation.type.ReservationStatus;

@Component
public class ReservationMapper {
    @Autowired
    BookMapper bookMapper;

    public Reservations mapReservationRequestDTOtoEntity(ReservationDTO reservationDTO) {
        Reservations entity = new Reservations();
        entity.setUserId(reservationDTO.getUserId());
        entity.setBookId(reservationDTO.getBookId());
        entity.setStatus(ReservationStatus.ACTIVE);
        return entity;
    }

    public ReservationInfoDTO mapReservationInfoEntityToDTO(Reservations reservation, Books book) {
        ReservationInfoDTO dto = new ReservationInfoDTO();
        dto.setUserId(reservation.getUserId());
        dto.setBook(bookMapper.mapBookEntityToDTO(book));
        dto.setCreated(reservation.getCreated());
        dto.setStatus(reservation.getStatus());
        return dto;
    }


}
