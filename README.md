# Esercizio 32 programmazione concorrente
## Traccia del problema
> Un artista da strada esegue delle caricature e dei ritratti a carboncino. Chi vuole un ritratto si siede in una delle quattro sedie messe a disposizione e attende il suo turno per spostarsi nella zona di lavoro dove farsi fare il ritratto. Le persone intorno arrivano continuamente e guardano incuriosite, attendendo che una delle quattro sedie si liberi per potersi mettere in attesa del ritratto. Tuttavia, le persone che aspettano per troppo tempo una sedia libera (stabilire un tempo predefinito all’inizio del programma) rinunciano a farsi fare il ritratto. Simulare questa situazione utilizzando i semafori come meccanismo di sincronizzazione tra i processi. In particolare, tenere presente che:
> 1. All’inizio non c’è nessun cliente
> 2. I clienti arrivano in numero e a istanti di tempo casuali
> 3. L’artista non impiega sempre lo stesso tempo per eseguire un ritratto

## Analisi del problema
Il problema proposto necessiterà di due sincronizzazioni: una per le sedie e una per la realizzazione vera e propria del dipinto a un cliente particolare. Per evitare problemi di `Starvation` e `Deadlock` è stato implementato nel codice della classe derivata dalla classe `Thread` un sistema di sincronizzazione che lo eviti che verrà analizzato a breve. 
La soluzione individuata necessita di tre classi:
1. Demo
2. Pittore
3. Cliente

## Classe Demo
La classe Demo contiene il `main` per far partire il programma. 
Possiede  2 attributi:
```java
	public static double maxAttesa = 10000;
	public static double start = System.currentTimeMillis();
```
Utilizzati rispettivamente per registrare il tempo massimo che un cliente attenderà di sedersi e l'istante in cui viene lanciato il programma.
All'interno del `main` sono presenti 4 variabili:
```java
	Pittore pit = new Pittore();
	Cliente aux;
	int minC = 1, maxC = 3;	
	int minA = 1000, maxA = 3000;
```
Utilizzate rispettivamente per: essere passato ai `Thread` clienti per costruire il sistema di sincronizzazione, ospitare i `Thread` clienti generati nel `main` a periodi irregolari di tempo, calcolare randomicamente il numero di clienti che arrivano ogni periodo di tempo e calcolare randomicamente il tempo tra l'arrivo di un gruppo di clienti e l'altro.
La generazione di n clienti ogni x tempo calcolati randomicamente viene ripetuta all'infinito in quanto nel `main` è presente un ciclo while con condizione true.

## Classe Pittore
La classe Pittore è una semplice classe contenitore utile alla sincronizzazione dei processi Cliente. Al suo interno sono presenti solo due attributi:
```java
	public Semaphore sedie = new Semaphore(4);
	public Semaphore mutex = new Semaphore(1);
```
Il primo Semaphore viene usato dai processi Cliente per sedersi sulle 4 sedie disponibili uno alla volta e il mutex servirà agli stessi, una volta seduti, per diventare i soggetti del processo di creazione del dipinto.

## Classe Cliente
La classe Cliente è il vero core del programma. Derivata dalla classe `Thread`, la classe Cliente possiede al suo interno le istruzioni per l'acqusizione della risorsa `Pittore` e la simulazione del processo di creazione del dipinto.
```java
	private volatile Pittore p;
	private double inizioAttesa, fineAttesa, attesa;
	private double inizioDipinto, fineDipinto, dipinto;	
	private int minimo = 3000;
	private int massimo = 7000;	
```
Il primo conterrà la stessa variabile per tutti i processi, passata tramite costruttore dal `main` della classe `Demo`, poi i 6 successivi verranno usati per registrare i tempi dei processi nel sistema e, infine, gli ultimi due vengono usati per calcolare randomicamente il tempo necessario alla produzione di un dipinto.

Nel metodo `run` i processi tenteranno di acquisire la risorsa `sedie` del `pittore p`; una volta riusciti in ciò viene verificato che il tempo d'attesa non abbia superato il massimo presente nella classe `Demo`. Se dovesse averlo superato il cliente uscirà subito dal sistema rilasciando la risorsa, altrimenti attenderà che il `pittore p` sia libero tentando di acquisire la risorsa `mutex` del `pittore p`.  Una volta terminato viene rilasciata la risorsa e il cliente esce dal sistema.

## Metodi implementati per evitare Starvation e Deadlock
In questo problema, per evitare `Starvation` e `Deadlock`, è stato sufficiente far uscire subito i processi che superavano il tempo di attesa, senza farli proseguire nel sistema. In questo modo è stata evitata almeno una delle 4 condizioni teoriche per la `Deadlock prevention`, ovvero:
> 1. utilizzo della mutua esclusione per l’accesso alle risorse condivise tra i processi in gioco
> 2. “hold and wait”, cioè la condizione per cui un processo può tenere occupata una risorsa mentre è in attesa di un’altra risorsa (vedi le bacchette nel problema dei cinque filosofi)
> 3. non-preemption delle risorse, cioè il fatto che l’utilizzo di una risorsa da parte di un processo non sia interrompibile forzatamente, ma solo il processo che la utilizza la possa rilasciare
> 4. attesa circolare, cioè la condizione per la quale un certo processo “A” attende una risorsa utilizzata da “B”, il quale attende una risorsa utilizzata da “C”, il quale a sua volta attende una risorsa utilizzata da “A” (come esempio, vedi sempre le bacchette nel problema dei cinque filosofi a cena, in cui le condizioni di “hold and wait” e attesa circolare coincidono)

Si può infatti ricondurre, anche se non propriamente uguale, il metodo implementato a un sistema soggetto a preemption.