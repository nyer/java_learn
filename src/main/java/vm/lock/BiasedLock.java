package vm.lock;

/**
 * 
 * Biased Locking can be enabled by adding vm option -XX:+UseBiasedLocing, which is default in java se 6.
 * to disable it add -XX:-UseBiasedLocking.
 * 
 * Biased locking is enabled after 4min , to enable it right away use -XX:+BiasedLockingStartupDealy=0.
 * 
 * @author leiting
 *
 */
public class BiasedLock {

	private Object object = new Object();
	
	static int LOOP_NUM = 1500 * 1000 * 1000;
	
	public void runtest() {
		
		//object.hashCode(); // stores the hashcode bits into the mark work, and bias locking will be disabled.
		
		int i = 0;
		while (i < LOOP_NUM) {
			
			synchronized (object) {
				i ++;
			}
		}
	}
	
	public static void main(String[] args) {
		
		BiasedLock biasedLock = new BiasedLock();
		long current = System.nanoTime();
		biasedLock.runtest();
		System.out.println("duration: " + (System.nanoTime() - current));
	}
}
