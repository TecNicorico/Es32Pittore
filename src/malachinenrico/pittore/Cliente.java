package malachinenrico.pittore;

public class Cliente extends Thread{
	private volatile Pittore p;
	private double inizioAttesa, fineAttesa, attesa;		//Variabili per registrare i tempi dell'attesa
	private double inizioDipinto, fineDipinto, dipinto;		//Variabili per registrare i tempi del dipinto
	private int minimo = 3000;								//Tempo minimo per la produzione di un dipinto
	private int massimo = 7000;								//Tempo massimo per la produzione di un dipinto
	
	Cliente(Pittore p){
		this.p=p;
	}
	
	public void run() {
		inizioAttesa = System.currentTimeMillis();			//Registrazione dell'istante di arrivo (inizio attesa)
		try {
			p.sedie.acquire();								//Tentativo di prendere una sedia
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fineAttesa=System.currentTimeMillis();				//Registrazione dell'istante in cui si prende una sedia (fine attesa)
		attesa=fineAttesa-inizioAttesa;						//Calcolo del tempo passato in attesa
		
		if(attesa<Demo.maxAttesa) {							//SE NON HA ASPETTATO TROPPO
			inizioDipinto=System.currentTimeMillis();		//Registrazione dell'istante in cui il cliente si siede sulla sedia
			try {
				p.mutex.acquire();							//Acquisizione del pittore (che lavora a un solo dipinto alla volta)
				Thread.sleep((int)(Math.random()*(massimo-minimo+1))+minimo);	//Tempo di produzione del dipinto
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			p.mutex.release();								//Rilascio del pittore (fine dipinto)
			
			fineDipinto=System.currentTimeMillis();			//Registrazione del tempo in cui viene finito il dipinto
			dipinto=fineDipinto-inizioDipinto;				//Calcolo del tempo passato in attesa e mentre veniva prodotto il dipinto
			
			p.sedie.release();								//Rilascio della sedia
			System.out.println(Thread.currentThread().getName() +" uscito con opera. Istante di entrata: "+((inizioAttesa-Demo.start)/1000)+" Ha atteso: "+(attesa/1000)+ " secondi. è rimasto seduto "+(dipinto/1000)+" secondi");
		}else {												//SE HA ATTESO TROPPO
			p.sedie.release();
			System.out.println(Thread.currentThread().getName()+" uscito senza opera. Istante di entrata: "+((inizioAttesa-Demo.start)/1000)+" Ha atteso "+(attesa/1000)+ " secondi");
		}
			
		
	}

}
