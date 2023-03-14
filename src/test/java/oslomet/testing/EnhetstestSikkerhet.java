package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhet {
    @InjectMocks
    // Denne skal testes
    private Sikkerhet sjekk;

    @Mock
    // Denne skal Mock'es
    private BankRepository repository;

    @Mock
    // Denne skal Mock'es
    private HttpSession session;

    @Test
    public void loggInn() {
        // arrange
        when(repository.sjekkLoggInn("12345678901", "HeiHei")).thenReturn("OK");
        session.setAttribute("12345678901", "Innlogget");

        // act
        String resultat = sjekk.sjekkLoggInn("12345678901", "HeiHei");

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void loggInnFeil() {
        // arrange
        when(repository.sjekkLoggInn("12345678901", "HeiHei")).thenReturn("Feil");

        // act
        String resultat = sjekk.sjekkLoggInn("12345678901", "HeiHei");

        // assert
        assertEquals("Feil i personnummer eller passord", resultat);
    }

    @Test
    public void LoggUt() {
        // arrange
        session.setAttribute("Innlogget", "12345678901");

        // act
        sjekk.loggUt();
        Object resultat = session.getAttribute("Innlogget");

        // assert
        assertNull(resultat);
    }

    @Test
    public void loggInnAdmin() {
        // arrange
        session.setAttribute("Innlogget", "Admin");

        // act
        String resultat = sjekk.loggInnAdmin("Admin", "Admin");

        // assert
        assertEquals("Logget inn", resultat);
    }

    @Test
    public void loggInnAdminFeil() {
        // act
        String resultat = sjekk.loggInnAdmin("Ad", "Ad");

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void loggetInn() {
        // arrange
        //when(sjekk.sjekkLoggInn("12345678901", "HeiHei")).thenReturn("OK");
        session.setAttribute("Innlogget", "12345678901");
        //when(sjekk.loggetInn()).thenReturn("12345678901");

        // act
        String resultat = sjekk.loggetInn();

        // assert
        assertEquals("12345678901", resultat);
    }

    @Test
    public void loggetInnFeil() {
        // act
        String resultat = sjekk.loggetInn();

        // assert
        assertNull(resultat);
    }
}
