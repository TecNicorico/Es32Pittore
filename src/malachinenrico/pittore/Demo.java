package malachinenrico.pittore;

public class Demo {
	public static double maxAttesa = 10000;
	public static double start = System.currentTimeMillis();
	
	public static void main(String[] args) {
		Pittore pit = new Pittore();
		Cliente aux;
		int minC = 1, maxC = 3;						//Dati per randomizzare il numero di clienti che arrivano
		int minA = 1000, maxA = 3000;				//Dati per randomizzare il tempo tra l'arrivo di un gruppo di clienti e l'altro
		
		while(true) {
			for(int i=0;i<(int)(Math.random()*(maxC-minC+1))+minC;i++){
				aux = new Cliente(pit);				//Creo un nuovo cliente
				aux.start();						//Faccio partire il processo
			}
			try {
				Thread.sleep((int)(Math.random()*(maxA-minA+1))+minA);	//Wait per un tempo random calcolato con i dati minA e maxA
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
