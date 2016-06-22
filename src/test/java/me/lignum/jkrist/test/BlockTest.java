package me.lignum.jkrist.test;

import org.junit.Test;

import me.lignum.jkrist.Block;
import me.lignum.jkrist.Krist;

public class BlockTest {
	@Test
	public void blockTest() {
		Krist krist = new Krist();
		Block block = krist.getBlock(64);
		
		System.out.println("Block " + block.getHeight() + ": " + block.getAddress() + " -> " + block.getHash() + " (" + block.getValue() + " KST) at " + block.getTime().toString());
	}
	
	@Test
	public void blockListTest() {
		Krist krist = new Krist();
		
		System.out.println("All Blocks:");
		
		for (Block block : krist.getBlocks(5)) {
			System.out.println("Block " + block.getHeight() + ": " + block.getAddress() + " -> " + block.getHash() + " (" + block.getValue() + " KST) at " + block.getTime().toString());
		}
	}
	
	@Test
	public void recentBlockListTest() {
		Krist krist = new Krist();
		
		System.out.println("Recent Blocks:");
		
		for (Block block : krist.getRecentBlocks(5)) {
			System.out.println("Block " + block.getHeight() + ": " + block.getAddress() + " -> " + block.getHash() + " (" + block.getValue() + " KST) at " + block.getTime().toString());
		}
	}
}
