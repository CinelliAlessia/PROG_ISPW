# Prog_ISPW

**Nome del Progetto**: Gestore di Playlist Musicali


**Registrazione**:

L'username dell'utente è univoco all'interno di tutto il sistema.

L'utente può non mettere alcun tipo di genere musicale preferito associato al suo profilo.



**Query:**
è stata separata la logica con cui il controller applicativo chiede di fare una query e come essa viene realmente realizzata.



**PROBLEMI:**

Consistenza Italiano Inglese, preferibilmente tutto in inglese. SOLO POPUP

Cambiare nome funzioni, da Usar a Client 

Seconda interfaccia OK CLI Correzione italiano eccezioni

Funzioni retrieve (recuperare) e non retrive (recuperato)

Andrea filtri ed elimina notifica


La creazione di casi di test dipende fortemente dalle specifiche del tuo progetto e dalle funzionalità dell'applicazione. Tuttavia, posso darti una guida generale su come strutturare casi di test per diverse parti del tuo sistema. Supponiamo che tu stia sviluppando un'app JavaFX per il Gestore di Playlist Musicali.

### Test di Unità

1. **Autenticazione Utente**:
    - Crea casi di test per verificare che il sistema gestisca correttamente la registrazione e l'accesso degli utenti.
    - Testa scenari con dati corretti e dati errati per garantire la sicurezza.

2. **Gestione delle Playlist**:
    - Verifica che il sistema aggiunga correttamente nuove playlist al database.
    - Assicurati che le playlist esistenti vengano caricate e visualizzate correttamente.

3. **Filtri e Consigli Personalizzati**:
    - Testa i filtri per genere musicale e tonalità emotiva.
    - Verifica che i consigli personalizzati siano accurati in base alle preferenze utente.

4. **Gestione del Profilo Utente**:
    - Crea casi di test per modificare le informazioni personali e le preferenze utente.
    - Assicurati che le modifiche vengano riflettute correttamente nel sistema.

5. **Moderazione Amministrativa**:
    - Verifica che il sistema gestisca correttamente la moderazione delle nuove playlist.
    - Testa il processo di approvazione e rifiuto da parte del supervisore.

### Test di Integrazione

1. **Integrazione Frontend-Backend**:
    - Verifica che le interazioni utente sul frontend siano gestite correttamente dal backend.
    - Testa la coerenza dei dati tra frontend e backend.

2. **Integrazione con il Database**:
    - Assicurati che il sistema interagisca correttamente con il database.
    - Testa l'aggiunta, la lettura e la modifica dei dati nel database.

3. **Flusso Completo di Utilizzo**:
    - Crea scenari di test che coprano il flusso completo di utilizzo dell'app.
    - Testa le transizioni tra diverse schermate e funzionalità.

### Test di Sistema

1. **Test su Diverse Piattaforme**:
    - Assicurati che l'app funzioni correttamente su diversi browser e dispositivi.

2. **Test di Carico**:
    - Simula un grande numero di utenti per testare la capacità di gestione del sistema.

3. **Test di Sicurezza**:
    - Verifica la sicurezza del sistema eseguendo test di vulnerabilità.

### Test di Accettazione Utente

1. **Simula Interazioni Utente Reali**:
    - Testa l'applicazione con utenti reali o simulati per raccogliere feedback.

2. **Usabilità**:
    - Valuta l'usabilità dell'app attraverso test con utenti non tecnici.

3. **Compatibilità**:
    - Verifica la compatibilità dell'app con diversi dispositivi e browser.

Questi sono solo esempi generali. Adatta i tuoi casi di test in base alle specifiche dettagliate del tuo progetto e alle funzionalità dell'applicazione.


