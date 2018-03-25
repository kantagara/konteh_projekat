package com.garabanda.KontehProject.web.dto;

import com.garabanda.KontehProject.model.Trip;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;

public class TripDTO {

    @NotNull
    @NotEmpty
    String destination;
    @NotNull
    String startDate;
    @NotNull
    String endDate;

    String comment;

    public TripDTO() {
    }

    public TripDTO(String destination, String startDate, String endDate, String comment) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.comment = comment;
    }

    public TripDTO(Trip trip){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
        this.destination = trip.getDestination();
        this.comment = trip.getComment();
        this.startDate = sdf.format(trip.getStartDate());
        this.endDate = sdf.format(trip.getEndDate());
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
