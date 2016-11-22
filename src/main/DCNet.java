package main;

import java.math.BigInteger;
import java.util.Formatter;

import javax.xml.bind.DatatypeConverter;

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
	
	public DCNet(String SA, String SB, String DA, String DB, String message, int b) {
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
		
		if(b == 1) {
			bOne();
			String myB = makeHexString(myDist).toUpperCase();
			String anonM = makeHexString(anonMessage).toUpperCase();
			System.out.println("Output: " + myB + anonM + ", when b = " + b);
		} else {
			bZero();
			String myB = makeHexString(myDist).toUpperCase();
			System.out.println("Output: " + myB + " when b = " + b);
		}
		
		
		
	}
	
	
	public void bZero() {
		for(int i = 0; i < 16; i++) {
			int xorDist = DAbit[i]^DBbit[i];
			int xorSecret = SAbit[i]^SBbit[i];
			myDist[i] = xorSecret;
			anonMessage[i] = xorDist^xorSecret;
			
		}
//		System.out.print("My broadcast: ");
//		for(int j = 0; j < myDist.length; j++) {
//			
//			System.out.print(myDist[j]);
//		}
//		
//		System.out.print(" Anonymous message: ");
//		for(int j = 0; j < anonMessage.length; j++) {
//			System.out.print(anonMessage[j]);
//		}
	}
	
	public void bOne() {
		
		for(int i = 0; i < 16; i++) {
			int xorDist = DAbit[i]^DBbit[i];
			
			int xorSecret = SAbit[i]^SBbit[i];
			
			if(messagebit[i] == 0) {
				myDist[i] = xorSecret;
				anonMessage[i] = xorDist^xorSecret;
			} else {
				if(xorSecret == 0) {
					xorSecret = 1;
				} else {
					xorSecret = 0;
				}
				myDist[i] = xorSecret;
				anonMessage[i] = xorDist^xorSecret;
			}
		}
//		System.out.println("My broadcast: ");
//		for(int j = 0; j < myDist.length; j++) {
//			
//			System.out.print(myDist[j]);
//		}
		
	}
	
	
	public int[] bitArray(String hex) {
		int intOfHex = Integer.valueOf(hex, 16);
		
		int[] bitArray = new int[16];
		for(int i = 0; i < 16; i++) {
			int bit = getBitAtIndexN(i,intOfHex);
			bitArray[bitArray.length - i - 1] = bit;
		}
//			System.out.print("hex: " + hex + ", bit: ");
//			for(int j = 0; j < bitArray.length; j++) {
//				System.out.print(bitArray[j]);
//			}
//			System.out.println("");
		return bitArray;
	}
	
	private int getBitAtIndexN(int index, int number) {
	    return (number >> index) & 1;
	}
	
	private String makeHexString(int[] bits) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < bits.length; i++) {
			sb.append(bits[i]);
		}
		
		
		int dec = Integer.parseInt(sb.toString(),2);
		String hex = Integer.toString(dec,16);
		
		
		return hex;
	}
	
	public static void main(String args[]) {
		
		DCNet dcn1 = new DCNet("27C2", "0879", "35F6", "1A4D", "27BC", 1);
		DCNet dcn0 = new DCNet("0C73", "80C1", "A2A9", "92F5", "9B57", 0);
	}
}

	
