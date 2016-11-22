package main;

import java.math.BigInteger;

public class DCNet {
	String message;
	String SA;
	String SB;
	String DA;
	String DB;
	
	int[] messagebit;
	int[] SAbit;
	int[] SBbit;
	int[] DAbit;
	int[] DBbit;
	
	int[] anonMessage;
	int[] myDist;
	
	public DCNet(String message, String SA, String SB, String DA, String DB) {
		this.message = message;
		this.SA = SA;
		this.SB = SB;
		this.DA = DA;
		this.DB = DB;
		
		messagebit = bitArray(message);
		SAbit = bitArray(SA);
		SBbit = bitArray(SB);
		DAbit = bitArray(DA);
		DBbit = bitArray(DB);
		myDist = new int[16];
		anonMessage = new int[16];
		
		
	}
	
	
	public void bZero() {
		
	}
	
	public void bOne() {
		
		for(int i = 0; i < 16; i++) {
			int xorDist = DAbit[i]^DBbit[i];
			System.out.println("XorDist nummer " + i + " " + xorDist);
			int xorSecret = SAbit[i]^SBbit[i];
			System.out.println("XorSecret nummer " + i + " " + xorSecret);
			if(messagebit[i] == 0) {
				myDist[i] = xorSecret;
				anonMessage[i] = xorDist^xorSecret;
			} else {
				myDist[i] = ~xorSecret;
				anonMessage[i] = ~xorDist^xorSecret;
			}
		}
		System.out.println("My broadcast: ");
		for(int j = 0; j < myDist.length; j++) {
			
			System.out.print(myDist[j]);
		}
		
	}
	
	
	public int[] bitArray(String hex) {
		int intOfHex = Integer.valueOf(hex, 16);
		int[] bitArray = new int[16];
		for(int i = 0; i < 16; i++) {
			int bit = getBitAtIndexN(i,intOfHex);
			bitArray[bitArray.length - i - 1] = bit;
		}
		System.out.println("En array: ");
		for(int j = 0; j < bitArray.length; j++) {
			System.out.print(bitArray[j]);
		}
		System.out.println("");
		return bitArray;
	}
	
	private int getBitAtIndexN(int index, int number) {
	    return (number >> index) & 1;
	}
	
	public static void main(String args[]) {
		DCNet dcn = new DCNet("0x9B57", "0x0C73", "0x80C1", "0xA2A9", "0x92F5");
		dcn.bitArray("9B57");
		dcn.bOne();
	}
}

	
