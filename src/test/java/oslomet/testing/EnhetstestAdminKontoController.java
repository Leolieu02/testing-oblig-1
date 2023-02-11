package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

    @InjectMocks
    // Denne skal testes
    private AdminKontoController adminKontoController;

    @Mock
    // Denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // Denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKonti_loggetInn(){
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("123456789000", "123456543212", 300,
                "Brukskonto", "NOK", null);
        Konto konto2 = new Konto("123456789000", "789098765432", 1000,
                "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("12345679000");

        when(repository.hentAlleKonti()).thenReturn(konti);

        //act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        //assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentAlleKonti_IkkeloggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        //assert
        assertNull(resultat);
    }

    @Test
    public void registrerKonto_loggetInn(){
        //arrange
        Konto konto = new Konto("123456789000", "123456543212", 300,
                "Brukskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("123456789000");

        when(repository.registrerKonto(any())).thenReturn("OK");

        //act
        String resultat = adminKontoController.registrerKonto(konto);

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerKonto_IkkeloggetInn(){
        //arrange
        Konto konto = new Konto("123456789000", "123456543212", 300,
                "Brukskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKontoController.registrerKonto(konto);

        //assert
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void endreKonto_loggetInn(){
        //assert
        Konto konto = new Konto("123456789000", "123456543212", 300,
                "Brukskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("123456789000");

        when(repository.endreKonto(any())).thenReturn("OK");

        //act
        String resultat = adminKontoController.endreKonto(konto);

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKonto_IkkeloggetInn(){
        //assert
        Konto konto = new Konto("123456789000", "123456543212", 300,
                "Brukskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKontoController.endreKonto(konto);

        //assert
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void slettKonto_loggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn("123456789000");

        when(repository.slettKonto(anyString())).thenReturn("OK");

        //act
        String resultat = adminKontoController.slettKonto("112233445566");

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void slettKonto_IkkeloggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKontoController.slettKonto("112233445566");

        //assert
        assertEquals("Ikke innlogget", resultat);
    }
}
