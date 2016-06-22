package me.lignum.jkrist.test;

import org.junit.Test;

import me.lignum.jkrist.Krist;
import me.lignum.jkrist.MOTD;

public class MOTDTest {
	@Test
	public void printMOTD() {
		Krist krist = new Krist();
		MOTD motd = krist.getMOTD();
		
		System.out.println("Krist MOTD: " + motd.getMessage());
		System.out.println(" -> State of Schrödinger's cat: " + motd.getSchroedingersCatState());
		System.out.println(" -> PSA: " + motd.getPSA());
		System.out.println(" -> Date modified: " + motd.getDateModified().toString());
	}
}
