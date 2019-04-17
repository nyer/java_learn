package vm.jmm;

import java.util.concurrent.TimeUnit;

/*
 * 
 * illustrate happens-before relationship
 * 
 * this illustrate rule
 * A write to a volatile field happens-before every subsequence read of that volatile.
 */
public class StopThread3 {

	private static boolean stopRequested;
	
	private static volatile int t;
	
	public static void main(String[] args) throws InterruptedException {
		
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {

				int i = 0;
				while (!stopRequested) {
					
					i++;
					int tt = t; // a subsequent read of the volatile field, then all the writes before volatile write is visible.
				}
				
				System.out.println("Thread completed");
			}
		};
		
		Thread backgroundThread =  new Thread(runnable);
		backgroundThread.start();
		TimeUnit.SECONDS.sleep(1);
		
		stopRequested = true;
		t = 2; // write to the volatile field.
	}
}
