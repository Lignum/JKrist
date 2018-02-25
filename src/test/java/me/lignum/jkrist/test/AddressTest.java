package me.lignum.jkrist.test;

import org.junit.Test;
import static org.junit.Assert.*;

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

	@Test
	public void privKeyGenTest() {
		assertEquals("977674247440506386266da9f547364dccb8a80aeca08f481fbcfd817b5b4be7-000", Address.makeKristWalletPrivateKey("0000"));
	}

	@Test
	public void v2AddressGenTest() {
		assertEquals("kxopdv8xaf", Address.makeV2Address(Address.makeKristWalletPrivateKey("0000")));
	}

	@Test
	public void byteToHexCharTest() {
		for (int i = 0; i < 7; i++) {
			assertEquals('0', Address.byteToHexChar(i));
		}

		for (int i = 8; i < 14; i++) {
			assertEquals('1', Address.byteToHexChar(i));
		}

		for (int i = 15; i < 21; i++) {
			assertEquals('2', Address.byteToHexChar(i));
		}

		for (int i = 238; i < 245; i++) {
			assertEquals('y', Address.byteToHexChar(i));
		}

		for (int i = 246; i < 252; i++) {
			assertEquals('z', Address.byteToHexChar(i));
		}
	}
}
