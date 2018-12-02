import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;


public class Bank {
	
	private Hashtable<Integer,Account> accounts = new Hashtable<Integer, Account>();
	private int accNum = 10000;
	
	
	
	public Account createSavings(User owner) {
		accNum += 1;
		int pin = (int) (Math.random()*9000)+1000;
		System.out.println("your pin is: "+ pin);
		Account acc = new SavingsAccount(accNum, pin,owner);
		return acc;
		
	}
	
	//overloading method, different method signature 
	public Account createSavings(int pin, User owner ) {
		accNum+=1;
		Account acc = new SavingsAccount(accNum, pin,owner);
		
		return acc;
		
		
	}
	
	public Account createChecking(int pin, User owner ) {
		
		accNum+=1;
		
		Account acc = new CheckingAccount(accNum, pin,owner);
		accounts.put(acc.getAccNum(), acc);
		
		return acc;
	}
	

	 
	

	
	public Account getAccount(int accNUm) {
		
		return accounts.get(accNum);
	}}

	
	



