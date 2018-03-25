package com.garabanda.KontehProject.repository;

import com.garabanda.KontehProject.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

    //List<Trip> getAllTripsUntilStartDate(Date startDate);

    List<Trip> getAllByDestination(String destination);

    @Query(value = "SELECT * FROM TRIP T WHERE T.START_DATE > NOW() AND T.START_DATE <= :searchDate" +
            "  ORDER BY DATEDIFF('DAY', T.END_DATE, T.START_DATE) ASC", nativeQuery = true)
    List<Trip> getAllTripsUntilStartDate(@Param("searchDate") Date date);
}
