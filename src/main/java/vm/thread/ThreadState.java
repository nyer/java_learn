package vm.thread;

public class ThreadState {

	private static Object lock = new Object();
	
	private static Object lock2 = new Object();
	
	// sleep并不释放锁
	public static void main1(String[] args) throws InterruptedException {
		
		Thread thread = new Thread(new Runnable() {
			public void run() {
				
				synchronized (lock) {
					
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					System.out.println("sleep completed");
				}
			}
		});
		
		thread.start();
		Thread.sleep(200);
		synchronized (lock) {
		
			System.out.println("lock acquired");
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		Thread thread = new Thread(new Runnable() {
			public void run() {
				
				synchronized (lock) {
					
					synchronized (lock2) {
					
						try {
							// just unlock the lock
							lock.wait(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("ok");
					}
				}
			}
		});
		
		thread.start();
		Thread.sleep(200);
		
		synchronized (lock) {
			
			//lock.notify();
		}
		
		System.out.println("Try to acquire lock2");
		synchronized (lock2) {
			// thread hold the lock2 object lock
			System.out.println("success to acquire lock2");
		}
		
	}
}
