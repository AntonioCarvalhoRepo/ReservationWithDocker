package acc.library.reservation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import acc.library.reservation.dto.ReservationDTO;
import acc.library.reservation.dto.ReservationInfoDTO;
import acc.library.reservation.service.ReservationService;
import acc.library.reservation.type.ReservationStatus;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationController {
    @Autowired
    ReservationService reservationService;

    @Operation(summary = "Reserve a book.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Reservation created.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Book not Found",
                    content = @Content)})
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createReservation(@Valid @RequestBody ReservationDTO reservation) throws ResponseStatusException {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.create(reservation));
    }

    @Operation(summary = "Retrieve a existing reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Existing Reservation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationInfoDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content)})
    @GetMapping(path = "/{reservationId}")
    public ReservationInfoDTO getReservationById(@PathVariable UUID reservationId) throws ResponseStatusException {
        return reservationService.getReservationById(reservationId);
    }

    @Operation(summary = "Retrieve all reservations for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Existing Reservation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content)})
    @GetMapping(path = "users/{userId}")
    public List<ReservationInfoDTO> getAllReservationsByUser(@PathVariable UUID userId) throws ResponseStatusException {
        return reservationService.getAllReservationsByUser(userId);
    }

    @Operation(summary = "Cancel a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Canceled Reservation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Reservation not found",
                    content = @Content)})
    @PatchMapping(path = "{reservationId}/{status}")
    public void cancelReservationStatus(@PathVariable UUID reservationId, @PathVariable ReservationStatus status) throws ResponseStatusException {
        reservationService.cancelReservationStatus(reservationId, status);
    }
}
