package malachinenrico.pittore;

public class Cliente extends Thread{
	private volatile Pittore p;
	private int entrata;
	private int uscita;
	
	Cliente(Pittore p){
		this.p=p;
	}
	
	public void run() {
		entrata = (int) System.currentTimeMillis();
		try {
			p.sedie.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		uscita = (int) System.currentTimeMillis();
		if(uscita-entrata> Demo.maxAttesa) {
			p.sedie.release();
			System.out.println(Thread.currentThread().getName()+" uscito senza opera");
		}else {
			
		}
	}

}
