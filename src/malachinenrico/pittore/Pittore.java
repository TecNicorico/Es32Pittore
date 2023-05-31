package malachinenrico.pittore;
import java.util.concurrent.Semaphore;

public class Pittore {
	public Semaphore sedie = new Semaphore(4);
	public Semaphore mutex = new Semaphore(1);

}
