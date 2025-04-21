package acc.library.reservation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDTO {
    @JsonProperty("userId")
    @NotNull(message = "User Id is required")
    private UUID userId;

    @JsonProperty("bookId")
    @NotNull(message = "Book Id is required")
    private UUID bookId;
}
