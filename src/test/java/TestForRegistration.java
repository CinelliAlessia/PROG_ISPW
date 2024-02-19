import static org.junit.Assert.*;
import engineering.exceptions.*;
import org.junit.Test;
import engineering.dao.ClientDAOJSON;
import model.Login;

import java.util.Arrays;

public class TestForRegistration {

    @Test
    public void testRegistrationWithValidData() {
        // Inizializza una nuova istanza del DAO o del gestore della registrazione
        ClientDAOJSON clientDAO = new ClientDAOJSON();

        // Dati validi per la registrazione
        String randomUsername = generateRandomUsername();
        String randomEmail = generateRandomEmail();
        Login validLogin = new Login(randomUsername, randomEmail, "testPassword", Arrays.asList("Rock", "Pop"));

        try {
            // Effettua la registrazione con dati validi
            clientDAO.insertClient(validLogin);

            // Verifica che la registrazione sia avvenuta correttamente
            assertNotNull(clientDAO.loadClient(validLogin));

        } catch (EmailAlreadyInUseException | UsernameAlreadyInUseException | UserDoesNotExistException e) {
            // Se si verifica un'eccezione, il test fallisce
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    private String generateRandomUsername() {
        // Implementa la logica per generare un username casuale
        return "testUsername" + System.currentTimeMillis();
    }

    private String generateRandomEmail() {
        // Implementa la logica per generare una mail casuale
        return "testEmail" + System.currentTimeMillis() + "@test.com";
    }

    @Test
    public void testRegistrationWithEmailAlreadyExists() {
        // Inizializza una nuova istanza del DAO o del gestore della registrazione
        ClientDAOJSON clientDAO = new ClientDAOJSON();

        // Dati per la registrazione con un'email già esistente
        Login existingEmailLogin = new Login("nuovoTestUsername", "admin@gmail.com", "password123", Arrays.asList("Rock", "Indie"));

        try {
            // Effettua la registrazione con un'email già esistente
            clientDAO.insertClient(existingEmailLogin);

            // Se la registrazione riesce, il test fallisce
            fail("Expected EmailAlreadyInUse exception but got none.");
        } catch (EmailAlreadyInUseException e) {
            // Se si verifica l'eccezione corretta, il test ha successo
            assertEquals("Email already registered!", e.getMessage());
        } catch (UsernameAlreadyInUseException e) {
            // Se si verifica un'eccezione diversa, il test fallisce
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testRegistrationWithExistingUsername() {
        // Inizializza una nuova istanza del DAO o del gestore della registrazione
        ClientDAOJSON clientDAO = new ClientDAOJSON();

        // Dati per la registrazione con username già esistente
        Login existingUsernameLogin = new Login("admin", "nuovaemail2@example.com", "password123", Arrays.asList("Rock", "Pop"));

        try {
            // Effettua la registrazione con username già esistente
            clientDAO.insertClient(existingUsernameLogin);

            // Se la registrazione riesce, il test fallisce
            fail("Expected UsernameAlreadyInUse exception but got none.");
        } catch (UsernameAlreadyInUseException e) {
            // Se si verifica l'eccezione corretta, il test ha successo
            assertEquals("Username already in use!", e.getMessage());
        } catch (EmailAlreadyInUseException | IllegalArgumentException e) {
            // Se si verifica un'eccezione diversa, il test fallisce
            fail("Unexpected exception: " + e.getMessage());
        }
    }

}
