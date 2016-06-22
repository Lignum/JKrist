package me.lignum.jkrist.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import me.lignum.jkrist.Krist;
import me.lignum.jkrist.Transaction;

public class TransactionTest {
	@Test
	public void transactionTest() {
		Krist krist = new Krist();
		Transaction tx = krist.getTransaction(1);

		assertEquals(1, tx.getID());
		assertEquals(null, tx.getFromAddress());
		assertEquals(true, tx.wasMined());
		assertEquals("0000000000", tx.getToAddress());
		assertEquals(50, tx.getValue());
	}
	
	@Test
	public void recentTransactionsTest() {
		Krist krist = new Krist();
	
		System.out.println("Recent Transactions:");
		
		for (Transaction tx : krist.getRecentTransactions(5)) {
			System.out.println(tx.getID() + ": " + tx.getFromAddress() + " -> " + tx.getToAddress() + " (" + tx.getValue() + " KST)");
		}
	}
	
	@Test
	public void transactionListTest() {
		Krist krist = new Krist();
		
		System.out.println("All Transactions:");
		
		for (Transaction tx : krist.getTransactions(5)) {
			System.out.println(tx.getID() + ": " + tx.getFromAddress() + " -> " + tx.getToAddress() + " (" + tx.getValue() + " KST)");
		}
	}
}
