# Laboratorio di programmazione per sistemi mobili e tablet

## Abstract

**Party4Party** è un’applicazione creata e pensata per gestire feste private, in particolare rave party; unisce alcune funzionalità dei social network, ad esempio invitare persone ad un evento, ad altre dei forum, come la possibilità di inserire post per richiedere informazioni relative alla festa.

Oltre alla gestione degli eventi l’applicazione permette a chi possiede del materiale (casse, console, ecc) di inserirlo nel proprio profilo per poterlo prestare.

Il problema principale che questo progetto vuole risolvere è quindi quello di poter creare in modo semplice,
autonomo e social la tua festa in tutta sicurezza, vista la scarsa garanzia data dai social network riguardo
quest'aspetto.

L'applicazione punterà quindi ad una gestione degli eventi molto semplice ed interattiva, grazie ad una grafica intuitiva e ad un sistema di notifiche immediate. Aiuterà poi l'amministratore in fase di creazione grazie ad una ricerca filtrata che permetterà di trovare utenti disponibili ad aiutare o partecipare alla creazione dell'evento (dj, vocalist, luci, casse, generatore).

## Related work: 

La nostra applicazione prende spunto dall’idea di creare degli eventi privati, ovvero, solamente chi è stato invitato ha l’accesso alle varie informazioni.

Come ispirazione iniziale abbiamo preso il sito GoaBase, che raccoglie tutti gli eventi di rave party nel mondo, a questo abbiamo aggiunto la possibilità di cercare utenti disponibili a prestare i propri oggetti ed altri a portare le proprie capacità.

## Usage model

Il principale task che ci eravamo prefissati prima di iniziare il progetto era quello di avere un’applicazione molto semplice, con un layout intuitivo ed immediato.

All’apertura dell’app verrà mostrata una schermata di login che permetterà anche ad un’utente non ancora iscritto di farlo tramite una registrazione.

L’home page dell’applicazione si presenta con tre tab, uno per gli eventi a cui si è deciso di partecipare, uno per gli inviti a cui si deve ancora rispondere e uno con gli eventi rifiutati.

Cliccando su un elemento della lista si apre una schermata che contiene tutte le informazioni relative alla festa, ovvero data, ora, scaletta dei dj, ecc; da qui sarà possibile, tramite bottoni nella action bar, visualizzare i partecipanti alla festa e invitarne di nuovi; nel caso l’utente sia il creatore della festa avrà anche la possibilità di modificare la festa, sempre tramite un click nell’action bar.

Sempre dalla home page è possibile creare una nuova festa con le varie informazioni.

Nella visualizzazione del profilo dell’utente si possono visualizzare tutte le info personali ed in più gli oggetti che vengono inseriti e possono essere visti da tutti ed usati per affinare le ricerche.

Ogni utente avrà anche una propria lista di altri utenti preferiti che verrà visualizzata in alto nelle ricerche.

Sempre dalla home sarà poi possibile effettuare il logout.

## Architecture design

L’applicazione è composta da due parti: il **client** (Party4Party) e il **server**.

Il **client** è la parte dove viene implementata la parte android, sul dispositivo non è presente nessun tipo di database che viene interamente salvato sul **server**, questa scelta è stata dettata dal fatto che essendo questa un’applicazione basata sul social e quindi non avrebbe avuto senso tenere un database in locale, anche se è stato perso molto tempo per sviluppare questa parte ed ha portato molte limitazioni ad esempio la mancanza di immagini.

Questa applicazione è stata ottimizzata per un utilizzo con smartphone.

## Implementation

Le principali funzioni che sono state implementate all’interno dell’applicazione sono:

* Login/Logout

* Registrazione al sistema

* Visualizzazione delle feste nei tab

* Visualizzazione di una festa

* Creazione di una festa

* Modifica di una festa

* Possibilità di invitare utenti a una festa

* Possibilità di visualizzare gli utenti partecipanti a una festa

* Ricerca di utenti all’interno del sistema per nome, cognome e/o nickname

* Possibilità di aggiungere utenti preferiti

* Visualizzazione degli utenti preferiti

* Visualizzazione del profilo degli utenti

* Aggiunta oggetti disponibili per prestito

* Invito a una festa

* Ricezione notifiche inviti alle feste

Nella realizzazione del progetto è stato utilizzato Eclipse per la parte del client, mentre per la parte server è stato utilizzato Netbeans.

## Evaluation

L’applicazione è stata testata solamente su smartphone.

Data la natura social del progetto, abbiamo effettuato diversi test con utenti reali, i quali ci hanno fornito importanti feedback per la realizzazione dell’applicazione.

Alcuni utenti hanno trovato l’idea dell’applicazione come molto utile e hanno detto che la userebbero nel caso fossero implementate alcune funzioni in più, come ad esempio una gestione dei media, come video, foto e audio, oppure la presenza di una chat dove poter discutere.

## Limitations

Il principale problema dell’applicazione è quello di avere uno scambio di informazioni tra client e server che limita fortemente la fluidità nella navigazione tra le varie schermate. Un altro problema è relativo alla creazione delle feste, ovvero un utente non è controllato quindi potrebbe riempire il database con informazioni inutili.

## Code appendix

Le parti del progetto sono due, la parte **client** (Party4Party) con le varie classi Java e i file .xml e la parte **server** che contiene tutte le servlet e le query.
