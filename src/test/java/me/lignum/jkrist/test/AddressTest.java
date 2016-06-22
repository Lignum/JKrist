package me.lignum.jkrist.test;

import org.junit.Test;

import me.lignum.jkrist.Address;
import me.lignum.jkrist.Krist;

public class AddressTest {
	private void printAddressInfo(String prefix, Address addr) {
		System.out.println(prefix + " " + addr.getName() + ": " + addr.getBalance() + " KST, " + addr.getFirstSeen().toString());
	}
	
	@Test
	public void addressTest() {
		Krist krist = new Krist();
		Address addr = krist.getAddress("kre3w0i79j");
		printAddressInfo("Got:", addr);
	}
	
	@Test
	public void richlistTest() {
		Krist krist = new Krist();
		Address[] addresses = krist.getTopAddresses(5);
		
		int i = 1;
		
		for (Address addr : addresses) {
			printAddressInfo("#" + i + ":", addr);
			i++;
		}
	}
}
