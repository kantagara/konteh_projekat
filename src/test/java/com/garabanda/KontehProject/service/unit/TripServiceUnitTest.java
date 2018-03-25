package com.garabanda.KontehProject.service.unit;

import com.garabanda.KontehProject.exception.BadRequestException;
import com.garabanda.KontehProject.model.Trip;
import com.garabanda.KontehProject.repository.TripRepository;
import com.garabanda.KontehProject.service.TripService;
import com.garabanda.KontehProject.web.dto.TripDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TripServiceUnitTest {

    @Autowired
    private TripService tripService;

    @MockBean
    private TripRepository tripRepository;


    @Before
    public void SetUp(){
        List<Trip> trips1 = new ArrayList<>();
        List<Trip> trips2 = new ArrayList<>();

        Calendar start = Calendar.getInstance();
        start.set(Calendar.MONTH, 4);
        start.set(Calendar.DAY_OF_MONTH, 3);

        Calendar end = Calendar.getInstance();
        end.set(Calendar.MONTH, 4);
        end.set(Calendar.DAY_OF_MONTH, 13);

        Trip trip1 = new Trip("New York",start.getTime(), end.getTime(), null);
        trips1.add(trip1);
        trips2.add(trip1);

        start.set(Calendar.MONTH, 5);
        end.set(Calendar.MONTH, 7);

        trips1.add(new Trip("New York", start.getTime(), end.getTime(), null));
        trips2.add(new Trip("Belgrade", start.getTime(), end.getTime(), null));

        start.set(Calendar.MONTH, 4);
        end.set(Calendar.MONTH, 4);
        end.set(Calendar.DAY_OF_MONTH, 7);

        Trip trip3 = new Trip("New York", start.getTime(), end.getTime(), null);
        trips1.add(trip3);
        trips2.add(trip3);

        given(
                this.tripRepository.getAllByDestination("New York")
        ).willReturn(
            trips1
        );

        //Posto u testu koristim samo dan mesec i godinu, sve ostale parametre vremena (sat, minut, sekund i milisekundu)
        //sam morao da setujem na 0 kako bih bio siguran da ce mi test proci.
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 6);
        cal.set(Calendar.DAY_OF_MONTH, 11);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);


        given(
                this.tripRepository.getAllTripsUntilStartDate(cal.getTime())
        ).willReturn(
                trips2
        );
    }

    @Test(expected = BadRequestException.class)
    public void testGetAllTripsUntilStartDateBadRequest(){
        /*
        Test proverava rad Trip servisa i njegove metode getAllTripsUntilStartDate kada je kao parametar prosledjen
        datum nevalidnog formata
        */
        List<Trip> trips = this.tripService.getAllTripsUntilStartDate("11/07.2018.");
    }

    @Test
    public void testGetAllTripsUntilStartDate(){
         /*
        Test proverava rad Trip servisa i njegove metode getAllTripsUntilStartDate.
        Zbog toga sto se poziva mokovani repozitorijum, putovanja nisu sortirana po broju dana provedenih na njemu
        (to je odradjeno u unit testu za servis).
        */
        List<Trip> trips = this.tripService.getAllTripsUntilStartDate("11.07.2018.");
        assertEquals(3, trips.size());
    }

    @Test
    public void testSave()
    {
        /*
        Test proverava rad Trip servisa i njegove metode save, kada su svi parametri validni
         */
        TripDTO tripDTO = new TripDTO("New York", "11.06.2018.", "18.08.2018.", null);
        this.tripService.save(tripDTO);
    }

    @Test(expected = BadRequestException.class)
    public void testSaveInvalidDate()
    {
        /*
        Test proverava rad Trip servisa i njegove metode save, kada parametar startDate nije validan
         */
        TripDTO tripDTO = new TripDTO("New York", "11/06.2018.", "18.08.2018.", null);
        this.tripService.save(tripDTO);
    }


    @Test(expected = BadRequestException.class)
    public void testSaveInvalidDestination()
    {
        /*
        Test proverava rad Trip servisa i njegove metode save, kada parametar destination ne postoji na google mapi.
         */
        TripDTO tripDTO = new TripDTO("FASOFJASIOFJAIOAIJOF", "11.06.2018.", "18.08.2018.", null);
        this.tripService.save(tripDTO);
    }

    @Test(expected = BadRequestException.class)
    public void testSaveStartDateBeforeNow()
    {
        /*
        Test proverava rad Trip servisa i njegove metode save, kada parametar startDate pocinje pre danasnjeg datuma.
         */
        TripDTO tripDTO = new TripDTO("New York", "11.06.2017.", "18.08.2018.", null);
        this.tripService.save(tripDTO);
    }

    @Test(expected = BadRequestException.class)
    public void testSaveStartDateAfterEndDate()
    {
        /*
        Test proverava rad Trip servisa i njegove metode save, kada parametar startDate pocinje posle parametra endDate.
         */
        TripDTO tripDTO = new TripDTO("New York", "11.06.2019.", "18.08.2018.", null);
        this.tripService.save(tripDTO);
    }

    @Test
    public void getAllByDestination()
    {
        /*
        Test proverava rad Trip servisa i njegove metode getAllByDestination
         */
        List<Trip> trips = this.tripService.getAllByDestination("New York");
        assertEquals(3, trips.size());
    }


}
