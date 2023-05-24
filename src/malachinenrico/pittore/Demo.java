package malachinenrico.pittore;

public class Demo {
	public static int maxAttesa = 2000;
	
	public static void main(String[] args) {
		Pittore pit = new Pittore();
		Cliente aux;
		int minC = 10, maxC = 50;
		
		while(true) {
			for(int i=0;i<(int)(Math.random()*(maxC-minC+1))+minC;i++){
				aux = new Cliente(pit);
				aux.start();
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
