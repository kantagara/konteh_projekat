package com.garabanda.KontehProject.model;

//Creating the trip with Destination, StartDate, EndDate and Comment (optional field)

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)

    private Date StartDate;

    @Column(nullable = false)
    private Date EndDate;

    private String comment;

    public Trip() {
    }

    public Trip(String destination, Date startDate, Date endDate, String comment) {
        this.destination = destination;
        StartDate = startDate;
        EndDate = endDate;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
