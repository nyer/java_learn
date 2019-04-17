package vm.jmm;

import java.util.concurrent.TimeUnit;

/*
 * 
 * illustrate variable visibility
 * 
 * above a multiply core platform, the backgroundThread never stop even though the variable stopRequest has been sat to true
 * due to the inconsistency of multiply level memory. 
 */
public class StopThread {

	private static boolean stopRequested;
	
	public static void main(String[] args) throws InterruptedException {
		
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {

				int i = 0;
				while (!stopRequested) {
					
					i++;
				}
				
				System.out.println("Thread completed");
			}
		};
		
		for (int i = 0; i < 10;i ++) {
			Thread backgroundThread =  new Thread(runnable);
			backgroundThread.start();
		}
		TimeUnit.SECONDS.sleep(1);
		stopRequested = true;
	}
}
