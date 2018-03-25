package com.garabanda.KontehProject.service;

import com.garabanda.KontehProject.model.Trip;
import com.garabanda.KontehProject.web.dto.TripDTO;

import java.util.List;

public interface TripService {

    void save(TripDTO trip);

    List<Trip> getAllTripsUntilStartDate(String startDate);

    List<Trip> getAllByDestination(String destination);

    void checkIfLocationExists(String destination);
}
