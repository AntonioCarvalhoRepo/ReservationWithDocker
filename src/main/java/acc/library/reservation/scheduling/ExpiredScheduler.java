package acc.library.reservation.scheduling;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import acc.library.reservation.entity.ReservationsRepository;

import java.util.concurrent.TimeUnit;

@Component
public class ExpiredScheduler {
    @Autowired
    ReservationsRepository reservationsRepository;

    @Scheduled(fixedRate = 7, timeUnit = TimeUnit.DAYS)
    @Transactional
    public void execute() {
        reservationsRepository.updateAllReservationStatus();

        System.out.println("Expiring All Reservations that are opened for more than 7 days");
    }
}