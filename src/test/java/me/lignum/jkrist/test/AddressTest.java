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
		assertEquals("krij1vi64t", Address.makeV2Address("MEgweVPtnHmqNvYQ"));
		assertEquals("kjahc1eyzn", Address.makeV2Address("vdJrfcTFQzpNjLtn"));
		assertEquals("kevsn298oq", Address.makeV2Address("FBvRepWVrZzGyNtP"));
		assertEquals("kci2tq0iex", Address.makeV2Address("vCFyUDjPfTaBYmkM"));
		assertEquals("kk40e93il0", Address.makeV2Address("cQyqXHzVfmKFsYnx"));
		assertEquals("k5r343waw5", Address.makeV2Address("FuhkyXTMAKvpdjRr"));
		assertEquals("k233g8rjvn", Address.makeV2Address("MCHvXjpGPYqVQrWF"));
		assertEquals("kvltqdv2y3", Address.makeV2Address("PHTsAJVaLhkgzNup"));
		assertEquals("knezaebvyv", Address.makeV2Address("GzKkejqmxChMpRVb"));
		assertEquals("kstmmy6h5h", Address.makeV2Address("NAjCQUktfbMFJpsK"));
		assertEquals("kbf2ewjhqm", Address.makeV2Address("MjqyHYEPgzKCTpuS"));
		assertEquals("k4b1anpiq3", Address.makeV2Address("ZgDzTyaPHRMQAGFk"));
		assertEquals("kpebuen98o", Address.makeV2Address("wshmPEvkHDKcpBeR"));
		assertEquals("k039hqp3n2", Address.makeV2Address("UZKnapGqYNBtAebd"));
		assertEquals("kzv1x3rjih", Address.makeV2Address("LYzTmfCtZuMjDdWA"));
		assertEquals("kz9n8pc35y", Address.makeV2Address("huFTJwZkjEbWNXyS"));
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
