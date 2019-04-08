package vm;

public class FalseSharing3 implements Runnable {

	public static final int NUM_THREADS = 4;
	public static final long ITERATIONS = 1500L * 1000L * 1000L;
	private final int arrayIndex;

    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];
	static {
		for (int i = 0; i < longs.length; i++) {
			longs[i] = new VolatileLong();
		}
	}

	public FalseSharing3(final int arrayIndex) {
		this.arrayIndex = arrayIndex;
	}

	public static void main(final String[] args) throws Exception {
		final long start = System.nanoTime();
		runTest();
		System.out.println("duration = " + (System.nanoTime() - start));
	}

	private static void runTest() throws InterruptedException {
		Thread[] threads = new Thread[NUM_THREADS];

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new FalseSharing3(i));
		}

		for (Thread t : threads) {
			t.start();
		}

		for (Thread t : threads) {
			t.join();
		}
	}

	public void run() {
		long i = ITERATIONS + 1;
		while (0 != --i) {
			longs[arrayIndex].value = i;
		}
	}

	public static final class VolatileLong {
		public long p1, p2, p3, p4, p5, p6, p7;
		public volatile long value = 0L;
		public long p8, p9, p10, p11, p12, p13, p14;
	}
}
