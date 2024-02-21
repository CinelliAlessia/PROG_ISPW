import engineering.exceptions.*;
import org.junit.Assert;
import org.junit.Test;
import engineering.dao.ClientDAOJSON;
import model.Login;

import java.util.Arrays;


/**
 * Studente: Andrea Tozzi
 * Matricola: 0350270
 */


public class TestForRegistration {

    /**
     * Testo che l'inserimento di un utente avvenga con successo se tutti i valori sono validi (email e username generati random)
    */
    @Test
    public void testRegistrationWithValidData() {
        ClientDAOJSON clientDAO = new ClientDAOJSON();
        int res = -1;

        String randomUsername = generateRandomUsername();
        String randomEmail = generateRandomEmail();
        Login validLogin = new Login(randomUsername, randomEmail, "testPassword", Arrays.asList("Rock", "Pop"));

        try {
            clientDAO.insertClient(validLogin);

            if (clientDAO.loadClient(validLogin) != null) {
                res = 1;
            }

        } catch (EmailAlreadyInUseException | UsernameAlreadyInUseException | UserDoesNotExistException e) {
            res = 0;
        }
        Assert.assertEquals(1, res);
    }

    /**
     * Testo che l'inserimento restituisca l'eccezione "EmailAlreadyInUseException" se provo a registrare un utente con una
     * email già in uso, in questo caso admin@gmail.com (Un supervisore già presente nel file system)
     */
    @Test
    public void testRegistrationWithEmailAlreadyExists() {
        ClientDAOJSON clientDAO = new ClientDAOJSON();
        int res;

        Login existingEmailLogin = new Login("nuovoTestUsername1616", "admin@gmail.com", "password123", Arrays.asList("Rock", "Indie"));

        try {
            clientDAO.insertClient(existingEmailLogin);
            res = 0; // Se la registrazione riesce, il test fallisce

        } catch (EmailAlreadyInUseException e) {
            res = 1; // Se si verifica l'eccezione corretta, il test ha successo
        } catch (UsernameAlreadyInUseException e) {
            res = 0; // Se si verifica un'eccezione diversa, il test fallisce
        }
        Assert.assertEquals(1, res);
    }

    /**
     * Testo che l'inserimento restituisca l'eccezione "UsernameAlreadyInUseException" se provo a registrare un utente con un
     * nome utente già in uso, in questo caso admin(Un supervisore già presente nel file system).
     * La mail invece viene generata random
     */
    @Test
    public void testRegistrationWithExistingUsername() {
        ClientDAOJSON clientDAO = new ClientDAOJSON();
        int res;

        Login existingUsernameLogin = new Login("admin", "testEmail@example.com", "password123", Arrays.asList("Rock", "Pop"));

        try {
            clientDAO.insertClient(existingUsernameLogin);
            res = 0; // Se la registrazione riesce, il test fallisce

        } catch (UsernameAlreadyInUseException e) {
            res = 1; // Se si verifica l'eccezione corretta, il test ha successo
        } catch (EmailAlreadyInUseException | IllegalArgumentException e) {
            res = 0; // Se si verifica un'eccezione diversa, il test fallisce
        }
        Assert.assertEquals(1, res);
    }

    private String generateRandomUsername() {
        return "testUsername" + System.currentTimeMillis();
    }

    private String generateRandomEmail() {
        return "testEmail" + System.currentTimeMillis() + "@test.com";
    }
}
