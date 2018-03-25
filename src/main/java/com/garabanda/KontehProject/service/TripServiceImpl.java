package com.garabanda.KontehProject.service;

import com.garabanda.KontehProject.exception.BadRequestException;
import com.garabanda.KontehProject.model.Trip;
import com.garabanda.KontehProject.repository.TripRepository;
import com.garabanda.KontehProject.web.dto.TripDTO;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Override
    public void save(TripDTO tripDTO) throws BadRequestException{

        checkIfLocationExists(tripDTO.getDestination());

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
        Trip trip = new Trip();

        Date startDate;
        Date endDate;
        Date currentDate = new Date();

        try {
            startDate = sdf.parse(tripDTO.getStartDate());
            endDate = sdf.parse(tripDTO.getEndDate());
            }
            catch (ParseException e){
            throw new BadRequestException("Date format is incorrect!");
            }

        if(startDate.before(currentDate))
            throw new BadRequestException("Start date can't be in the past!");
        else if(startDate.after(endDate))
            throw new BadRequestException("End date can't be before start date!");

        trip.setDestination(tripDTO.getDestination());
        trip.setStartDate(startDate);
        trip.setEndDate(endDate);
        trip.setComment(tripDTO.getComment());

        tripRepository.save(trip);
    }


    @Override
    public List<Trip> getAllTripsUntilStartDate(String startDate) {

        Date date;

        try {
            date = new SimpleDateFormat("dd.MM.yyyy.").parse(startDate);
        } catch (ParseException e) {
            throw new BadRequestException("Date format is invalid");
        }

        return tripRepository.getAllTripsUntilStartDate(date);
    }

    @Override
    public List<Trip> getAllByDestination(String destination)
    {
        return tripRepository.getAllByDestination(destination);
    }

    public void checkIfLocationExists(String destination) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyCIXkW7Em3tHjYDeLQH6G3y8HqtNKs_Jgs")
                .build();
        GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context,
                    destination).await();
            if(results.length == 0)
                throw new BadRequestException("No destination found!");
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }


}
