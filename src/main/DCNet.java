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
			System.out.println("Output: " + myB + " when b = " + b);
			
		} else {
			bZero();
			String myB = makeHexString(myDist).toUpperCase();
			String anonM = makeHexString(anonMessage).toUpperCase();
			StringBuilder sb = new StringBuilder(anonM);
			while(sb.length() < 4) {
				sb.append("0");
			}
			anonM = sb.toString();
			System.out.println("Output: " + myB + anonM + ", when b = " + b);
		}
		
	}
	
	public void bZero() {
		for(int i = 0; i < 16; i++) {
			int xorDist = DAbit[i]^DBbit[i];
			int xorSecret = SAbit[i]^SBbit[i];
			myDist[i] = xorSecret;
			anonMessage[i] = xorDist^xorSecret;
			
		}

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
		
	}
	
	
	public int[] bitArray(String hex) {
		int intOfHex = Integer.valueOf(hex, 16);
		
		int[] bitArray = new int[16];
		for(int i = 0; i < 16; i++) {
			int bit = getBitAtIndexN(i,intOfHex);
			bitArray[bitArray.length - i - 1] = bit;
		}
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
		DCNet dctest = new DCNet("BF0D", "3C99", "186F", "2EAD", "62AB", 0);
		DCNet dctest2 = new DCNet("D75C", "EE87", "C568", "FCB3", "4674", 1);
		DCNet dctest3 = new DCNet("75F5", "B1AC", "67C1", "A398", "00BC", 0);
	}
}