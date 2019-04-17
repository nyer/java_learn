package vm.thread;

public class ThreadNotifyOrder {

	public static void main(String[] args) throws Exception {
		
		final Object object = new Object();
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				
				synchronized (object) {
				
					System.out.println("thread 1 obtain the lock");
					try {
						object.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					System.out.println("thread 1 wait completed");
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				
				synchronized (object) {
				
					System.out.println("thread 2 obtain the lock");
					try {
						object.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					System.out.println("thread 2 wait completed");
				}
			}
		});
		
		t1.start();
		Thread.sleep(100);
		t2.start();
		Thread.sleep(100);
		
		synchronized (object) {
		
			object.notify();
		}
	}
}
