package com.garabanda.KontehProject.web.controller;


import com.garabanda.KontehProject.exception.BadRequestException;
import com.garabanda.KontehProject.model.Trip;
import com.garabanda.KontehProject.service.TripService;
import com.garabanda.KontehProject.web.dto.TripDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;


@RestController
public class TripController {

    @Autowired
    TripService tripService;

    @RequestMapping(
            value = "/api/trip",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Create a trip resource.",
            notes = "All fields except comment must be filled. Dates must be provided in dd.MM.yyyy. format",
            httpMethod = "POST",
            consumes = "application/json"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created new trip", response = Trip.class),
            @ApiResponse(code = 400, message = "Inappropriate object sent in request body")
    })
    public ResponseEntity setTrip(@RequestBody  @Valid  TripDTO tripDTO,
                                  BindingResult errors) throws InvalidPropertiesFormatException {
        if(errors.hasErrors())
            throw new BadRequestException("Some properties in the DTO object are empty!");

        tripService.save(tripDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/trip_by_date",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Gets all trips that are in between current date and the date passed as a parameter, order by" +
                    "number of days spent on the trip.",
            notes = "Date must be provided in dd.MM.yyyy. format",
            httpMethod = "GET",
            produces = "application/json",
            consumes = "text/plain"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All the trips are received", response = List.class),
            @ApiResponse(code = 400, message = "Inappropriate date format"),
    })
    public ResponseEntity getAllTripsUntilStartDate(@RequestParam String date)
    {
        List<Trip> trips = this.tripService.getAllTripsUntilStartDate(date);
        ArrayList<TripDTO> dtos = new ArrayList<>();

        trips.forEach(trip -> dtos.add(new TripDTO(trip)));
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/trip_by_destination",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Gets all trips by destination.",
            httpMethod = "GET",
            produces = "application/json",
            consumes = "text/plain"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All the trips are received", response = List.class),
    })
    public ResponseEntity getAllTripsByDestination(@RequestParam String destination)
    {
        List<Trip> trips = this.tripService.getAllByDestination(destination);
        ArrayList<TripDTO> dtos = new ArrayList<>();

        trips.forEach(trip -> dtos.add(new TripDTO(trip)));

        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }
}
