package acc.library.reservation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import acc.library.reservation.type.ReservationStatus;

import java.util.Date;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationInfoDTO {
    @JsonProperty("userId")
    private UUID userId;

    @JsonProperty("book")
    private BookInfoDTO book;

    @JsonProperty("created")
    public Date created;

    @JsonProperty("status")
    private ReservationStatus status;
}
