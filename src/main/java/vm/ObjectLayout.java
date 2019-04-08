package vm;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import vm.FalseSharing.VolatileLong;

public class ObjectLayout {
	
	static class Reorder {
		
		// 分配顺序是 double > long > int > float > char > short > byte > boolean > object reference
		// 内存分配的是以8byte为单位，那么多分配的要alignment
		// alignment的方式用2种，一是用小的field, 如果没有则padding，padding是4byte大小进行的
		private byte a;
		private int b;
		private boolean c;
		private float d;
		private Object e;
		private double f;
		private long g;
		private char ch;
		private char[] ca;
	}
	
	static class Reorder2 extends Reorder {
		
		// 子类的field空间分配顺序在父类之后
		private int c;
	}

	public static void main(String[] args) {
		
		System.out.println(VM.current().details());
		
		System.out.println(ClassLayout.parseClass(Reorder.class).toPrintable());
		System.out.println(ClassLayout.parseClass(Reorder2.class).toPrintable());
		
		// 数组对象有4bytes来存length
		System.out.println(ClassLayout.parseInstance(new boolean[2]).toPrintable());
		
		for (VolatileLong volatileLong : FalseSharing.longs) {
			System.out.println(ClassLayout.parseInstance(volatileLong).toPrintable());
		}
	}
}
