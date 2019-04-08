package vm;

import java.util.Scanner;

import org.openjdk.jol.info.ClassLayout;

public class ObjectHeader {

	public static void main(String[] args) throws Exception {
		
		new Scanner(System.in).nextLine();
		ObjectHeader objectHeader = new ObjectHeader();
		System.out.println(ClassLayout.parseInstance(objectHeader).toPrintable());
		System.out.println(Long.toHexString(objectHeader.hashCode()));
		System.out.println(ClassLayout.parseInstance(objectHeader).toPrintable());
		
		synchronized (objectHeader) {
		
			System.out.println(ClassLayout.parseInstance(objectHeader).toPrintable());
		}
		
		System.out.println(ClassLayout.parseInstance(objectHeader).toPrintable());
	}
}
