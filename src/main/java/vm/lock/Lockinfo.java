package vm.lock;

import vm.UnsafeTest;

public class Lockinfo {

	public Object lock = new Object();
	
	public Object lock2 = new Object();
	
	volatile static Lockinfo lockinfo = new Lockinfo();
		
	// run with vm arg -XX:BiasedLockingStartupDelay=0
	public static void main2(String[] args) {
		
		Lockinfo lockinfo = new Lockinfo();
		UnsafeTest.printObjectLayout(lockinfo); // bias_lock bit 1, lock bits 01.
		
		synchronized (lockinfo) {
			
			UnsafeTest.printObjectLayout(lockinfo); // bias_lock bit 1, lock bits 01, thread bits stored
		}
		
		System.out.println(Long.toHexString(lockinfo.hashCode()));
		UnsafeTest.printObjectLayout(lockinfo); // bias_lock bit 0, lock bits 01, hashcode bits store
	}
	
	// 锁升级
	public static void main(String[] args) throws InterruptedException {
		
		UnsafeTest.printObjectLayout(lockinfo);
		
		synchronized (lockinfo) {
		
			// 偏向锁
			UnsafeTest.printObjectLayout(lockinfo);
			// revoke bias
			System.out.println(Long.toHexString(lockinfo.hashCode()));
			UnsafeTest.printObjectLayout(lockinfo);
		}

		Thread.sleep(1000); // 锁释放后立即读取对象头还是数据还是老的，故这里sleep下，原因待查
		System.out.println("unlock");
		UnsafeTest.printObjectLayout(lockinfo);
		
		Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				// 这里是轻量级锁还是重量级锁取决是否存在锁竞争
				synchronized (lockinfo) {
					
					UnsafeTest.printObjectLayout(lockinfo);
				}
			}
		});
		
		synchronized (lockinfo) {
		
			th.start();
			Thread.sleep(1000); // 引发锁等待
		}
		th.join();
		
		Thread.sleep(1000); // 原因同上
		UnsafeTest.printObjectLayout(lockinfo);
	}
}
