package com.garabanda.KontehProject.repository.unit;

import com.garabanda.KontehProject.model.Trip;
import com.garabanda.KontehProject.repository.TripRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TripRepositoryUnitTest {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private EntityManager entityManager;

    @Before
    public void SetUp() {

        Calendar start = Calendar.getInstance();
        start.set(Calendar.MONTH, 4);
        start.set(Calendar.DAY_OF_MONTH, 3);

        Calendar end = Calendar.getInstance();
        end.set(Calendar.MONTH, 4);
        end.set(Calendar.DAY_OF_MONTH, 13);

        Trip trip1 = new Trip("New York", start.getTime(), end.getTime(), null);
        entityManager.persist(trip1);

        start.set(Calendar.MONTH, 5);
        end.set(Calendar.MONTH, 7);

        Trip trip2 = new Trip("Belgrade", start.getTime(), end.getTime(), null);
        entityManager.persist(trip2);

        start.set(Calendar.MONTH, 4);
        end.set(Calendar.MONTH, 4);
        end.set(Calendar.DAY_OF_MONTH, 7);

        Trip trip3 = new Trip("New York", start.getTime(), end.getTime(), null);
        entityManager.persist(trip3);

    }

    @Test
    public void testGetAllTripsUntilStartDate(){
        /*
        Test proverava ispravnost rada metode getAllTripsUntilStartDate repozitorijuma TripRepository.
        Posto se ovde radi sa pravim repozitorijumom, entitet sa najduzom razlikom u danima izmedju startDate i endDate
        ce bii prikazan prvi
        */
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 8);
        List<Trip> trips = tripRepository.getAllTripsUntilStartDate(calendar.getTime());

        assertEquals(3, trips.size());
        assertEquals("Belgrade", trips.get(0).getDestination());
    }

    @Test
    public void testGetAllTripsByDestination(){
        /*
        Test proverava ispravnost rada metode getAllTripsByDestination repozitorijuma TripRepository.
        */

        List<Trip> trips = tripRepository.getAllByDestination("New York");
        assertEquals(2, trips.size());
    }

    //Testiranje metode save je bespotrebno, jer je to ugradjena metoda u JpaRepository.


}
