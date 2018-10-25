
public class SavingsAccount extends Account implements Interest{
	
	private static int withdrawLimit = 1000;
	
	public SavingsAccount(int accNum,int pin  ) {
		super(accNum,pin); 
	}

	
	@Override
	public synchronized boolean withdraw(int amount) {
		if(withdrawLimit>amount && getBalance()>=amount) {
		
			setBalance(getBalance()-amount);
			
			withdrawLimit-=amount;
			return true;
			
		}
		else if(getBalance()<amount ) {
			return false;
			
		}
		else {
			return false;
		}
		
	}
	
	public synchronized void resetLimit() {
		withdrawLimit = 1000; 
	}
	
	@Override
	public String toString() {
		
		return "Savings account number: " + getAccNum();
	}
    
	//implement method from interface
	@Override
	public synchronized void addInterest() {
		
		setBalance(getBalance()*(BASE_INTEREST+0.02));
		// TODO Auto-generated method stub
		}


	@Override
	public String getAccountType() {
		// TODO Auto-generated method stub
		return "savings";
	}
	
	}
