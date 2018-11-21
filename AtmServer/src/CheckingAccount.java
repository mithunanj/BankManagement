
public class CheckingAccount extends Account{

	
	public CheckingAccount(int accNum,int pin, User owner ) {
		super(accNum,pin,owner);
		
		
		
	}
	public CheckingAccount(int pin, User owner ) {
		super(pin,owner);
		
		
		
	}
	
	@Override
	public synchronized boolean withdraw(int amount) {
		
		if(getBalance()>=amount) {
			setBalance(getBalance()-amount);
			
			return true ;
			
		}
		else {
			return false;
		}
		
	}
	
	//change implementation of toString from object class 
	@Override
	public String toString() {
		
		return "Checking account number: " + getAccNum();
	}

	@Override
	public String getAccountType() {
		// TODO Auto-generated method stub
		return "checking";
	}

	

}
