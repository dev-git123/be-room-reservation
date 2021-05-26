package com.reservation.repostiories;

import com.reservation.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    public static final String FIND_BOOKING = "SELECT * FROM booking b " +
            "join employee e " +
            "on b.employee_id = e.id " +
            "where e.employee_no = :employeeNo";
    @Query(value = FIND_BOOKING, nativeQuery = true)
    List<Booking> findAllByEmployeeNo(@Param("employeeNo") String employeeId);
}
