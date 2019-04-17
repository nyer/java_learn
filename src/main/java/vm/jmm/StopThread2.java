package vm.jmm;

import java.util.concurrent.TimeUnit;

/*
 * 
 * illustrate happen-before of synchronize
 * 
 * under the ground, the monitorexit will update the variable value of main memory, and invalidate
 * the cache line of other cores.
 */
public class StopThread2 {

	private static boolean stopRequested;
	
	private static synchronized void requestStop() {
		
		stopRequested = true;
	}
	
	private static synchronized boolean stopRequested() {
		
		return stopRequested;
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		Thread backgroundThread =  new Thread(new Runnable() {
			
			@Override
			public void run() {

				int i = 0;
				while (!stopRequested()) {
					
					i++;
				}
			}
		});
		
		backgroundThread.start();
		TimeUnit.SECONDS.sleep(1);
		//requestStop();
		stopRequested = true;
	}
}
