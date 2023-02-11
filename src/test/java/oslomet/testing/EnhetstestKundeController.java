package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestKundeController {
    @InjectMocks
    // tester kundecontrolleren
    private AdminKundeController kundeController;

    @Mock
    // mocker Adminrepository
    private AdminRepository repository;

    @Mock
    // mocker sikkerhet, for å sjekke logg inn statusen
    private Sikkerhet sjekk;

    @Test
    public void hentAlle_OK(){
        // arrange
        List <Kunde> kunder = new ArrayList<>(); // lager listen som skal returneres
        Kunde kunde1 = new Kunde("12345678910", "Per", "Hansen", "Osloveien 2",
                "0123", "Oslo", "10203040", "Hei123"); // lager en kunde som skal være "logget inn"
        Kunde kunde2 = new Kunde("10987654321", "Olav", "Hansen", "Osloveien 10", "0123"
                , "Oslo", "40302010", "Hei123"); // lager en kunde som skal være "logget inn"
        kunder.add(kunde1);
        kunder.add(kunde2);

        when(sjekk.loggetInn()).thenReturn("12345678910");

        when(repository.hentAlleKunder()).thenReturn(kunder);

        // act
        List <Kunde> kundeResultat = kundeController.hentAlle();

        //assert
        assertEquals(kunder, kundeResultat);
    }

    @Test
    public void hentAlle_ikkeLoggetInn(){
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List <Kunde> kundeResultat = kundeController.hentAlle();

        // assert
        assertNull(kundeResultat);
    }

    @Test
    public void lagreKunde_loggetInn(){
        // arrange
        Kunde kunde1 = new Kunde("12345678910", "Per", "Hansen", "Osloveien 2",
                "0123", "Oslo", "10203040", "Hei123"); // lager en kunde som skal lagres
        String riktig = "OK"; // strengen som returneres om det er riktig

        when(sjekk.loggetInn()).thenReturn("12345678900");

        when(repository.registrerKunde(any())).thenReturn(riktig);

        // act
        String resultat = kundeController.lagreKunde(kunde1);

        // assert
        assertEquals(riktig, resultat);
    }

    @Test
    public void lagreKunde_ikkeLoggetInn(){
        // arrange
        Kunde kunde1 = new Kunde("12345678910", "Per", "Hansen", "Osloveien 2",
                "0123", "Oslo", "10203040", "Hei123"); // lager en kunde som skal lagres
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = kundeController.lagreKunde(kunde1);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void endreKunde_loggetInn(){
        // arrange
        String riktig = "OK";
        Kunde kunde1 = new Kunde("12345678910", "Per", "Hansen", "Osloveien 2",
                "0123", "Oslo", "10203040", "Hei123"); // lager en kunde som skal lagres

        when(sjekk.loggetInn()).thenReturn("12345678900");

        when(repository.endreKundeInfo(any())).thenReturn(riktig);

        // act
        String resultat = kundeController.endre(kunde1);

        // assert
        assertEquals(riktig, resultat);
    }

    @Test
    public void endreKunde_ikkeLoggetInn(){
        // arrange
        Kunde kunde1 = new Kunde("12345678910", "Per", "Hansen", "Osloveien 2",
                "0123", "Oslo", "10203040", "Hei123"); // lager en kunde som skal lagres
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = kundeController.endre(kunde1);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }


    @Test
    public void slett_loggetInn(){
        // arrange
        String riktig = "OK";
        String kontonummer = "1020304050"; // kontonummer som skal slettes

        when(sjekk.loggetInn()).thenReturn("12345678910"); // sånn at den er logger inn

        when(repository.slettKunde(anyString())).thenReturn(riktig);

        // act
        String resultat = kundeController.slett(kontonummer);

        // assert
        assertEquals(riktig, resultat);
    }


    @Test
    public void slett_ikkeLoggetInn(){
        // arrange
        String kontonummer = "10203040";
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = kundeController.slett(kontonummer);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

}
