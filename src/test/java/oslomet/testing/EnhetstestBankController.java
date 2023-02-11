package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;


    @Mock
    // denne skal Mock'es
    private BankRepository repository;


    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;


    @Test
    public void test_hentTransaksjonerOK(){
        // arrange
        List ArrayList = new ArrayList<>();
        Konto konto1 = new Konto("11144477889", "11111111111", 250.0, "Visa", "NOK",ArrayList);
        String personnummer = "100299 89333"; // random laget personnummer

        when(sjekk.loggetInn()).thenReturn(personnummer);

        when(repository.hentTransaksjoner(konto1.getKontonummer(),"12.11", "14.11")).thenReturn(konto1);

        // act
        Konto resultat = bankController.hentTransaksjoner("11111111111","12.11","14.11");

        // assert
        assertEquals(konto1, resultat);
    }

    @Test
    public void test_hentTransaksjonerNULL() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        Konto resultat = bankController.hentTransaksjoner("11111111111", "12.11", "14.11");

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentSaldiOK(){
        // arrange
        List <Transaksjon> transaksjoner = new ArrayList<>();
        Konto konto1 = new Konto("11144477889", "11111111111", 250.0, "Visa", "NOK", transaksjoner);
        List <Konto> kontoer = new ArrayList<>();
        kontoer.add(konto1);
        String personnummer = "100299 89333"; // random laget personnummer

        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(repository.hentSaldi(personnummer)).thenReturn(kontoer);

        // act
        List <Konto> resultat1 = bankController.hentSaldi();

        // assert
        assertEquals(kontoer, resultat1);
    }

    @Test
    public void hentSaldiFEIL(){
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat1 = bankController.hentSaldi();

        // assert
        assertNull(resultat1);
    }

    @Test
    public void reigstrerBetaling_OK(){
        // arrange
        Transaksjon transaksjon = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "105010123456", "1010");
        String personnummer = "100299 89333"; // random laget personnummer

        when(sjekk.loggetInn()).thenReturn(personnummer);

        when(repository.registrerBetaling(transaksjon)).thenReturn("OK");

        // act
        String resultat = bankController.registrerBetaling(transaksjon);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void reigstrerBetaling_Feil(){
        // arrange
        Transaksjon transaksjon = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "105010123456", "1010");
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = bankController.registrerBetaling(transaksjon);

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentbetalingerOK(){
        // arrange
        List <Transaksjon> transaksjon1  = new ArrayList<>();
        String personnummer = "100299 89333"; // random laget personnummer

        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(repository.hentBetalinger(personnummer)).thenReturn(transaksjon1);

        // act
        List <Transaksjon> resultat2 = bankController.hentBetalinger();

        // assert
        assertEquals(transaksjon1, resultat2);

    }

    @Test
    public void hentbetalingerFEIL() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List <Transaksjon> resultat2 = bankController.hentBetalinger();

        // assert
        assertNull(resultat2);

    }

    @Test
    public void utforbetalingOK(){
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();
        String personnummer = "100299 89333"; // random laget personnummer

        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(repository.utforBetaling(1)).thenReturn("OK");
        when(repository.hentBetalinger(personnummer)).thenReturn(transaksjoner);

        // act
        List <Transaksjon> resultat3 = bankController.utforBetaling(1);

        // assert
        assertEquals(transaksjoner, resultat3);
    }

    @Test
    public void utforbetalingIKKELoggetInn(){
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat4 = bankController.utforBetaling(0);

        // assert
        assertNull(resultat4);

    }

    @Test
    public void hentKundeInfo_loggetInn() {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void endreKunde_OK() {
        // arrange
        Kunde kunde1 = new Kunde("12345678910", "Per", "Hansen", "Osloveien 2",
                "0123", "Oslo", "10203040", "Hei123");

        when(sjekk.loggetInn()).thenReturn("1020304050");

        when(repository.endreKundeInfo(any())).thenReturn("OK");

        // act
        String resultat = bankController.endre(kunde1);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKunde_Feil() {
        // arrange
        Kunde kunde1 = new Kunde("12345678910", "Per", "Hansen", "Osloveien 2",
                "0123", "Oslo", "10203040", "Hei123");
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = bankController.endre(kunde1);

        // assert
        assertNull(resultat);
    }

}

