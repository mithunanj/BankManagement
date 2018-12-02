import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
 
public class AtmServer implements Runnable{
	
	private Socket socket ;
	private PrintWriter writer;
	private BufferedReader reader;
	private Account account;
	private  BankDatabase data;
	
	
	public AtmServer(Socket socket) {
		this.socket= socket;
		data = new BankDatabase();
		
		
		
		
	}
	// banking options available once logged in 
	public void startBanking() throws NumberFormatException, IOException, SQLException {
		
		
	
		while(true) {
			
			int choice = Integer.parseInt(reader.readLine());
			System.out.println(choice);
			
			
			switch(choice) {
				
			case 1:
				writer.println("Enter Withdraw amount");
				if(account.withdraw(Integer.parseInt(reader.readLine()))) {
					writer.println("Balance: " + account.getBalance());
				}
				else {
					writer.println("Unable to complete transaction" );
				}
				data.updateAccount(account);
				
				break;
				
			case 2:
				writer.println("Enter Deposit amount");
				writer.println(account.deposit(Double.parseDouble(reader.readLine())));
				data.updateAccount(account);
				
				break;
				
			case 3: 
				
				writer.println("Balance: " +account.getBalance() );
				break;
				
			case 4:
				data.saveChanges();
				return;
				
			}
			
			
		}
		}
			
		
	
	// validate account and direct to options available 
	public boolean validateAccount() throws IOException {
		
		
		String read = reader.readLine();
		System.out.println(read);

		String login[] = read.split(" ");
        int accNum = Integer.parseInt(login[0]);
        int pin = Integer.parseInt(login[1]);
        
        account = data.getAccount(accNum);
		if(account!=null&&account.getPin()==pin) {
			return true;
			}
		else {
			return false;
		}
		
	}

	
	public void run() {
		
		
		try {
			
			
			try {
				
				writer = new PrintWriter(socket.getOutputStream(),true);
		        
		        InputStreamReader input = new InputStreamReader(socket.getInputStream());
		        reader = new BufferedReader(input);
		        
		        while(true) {
		        	if(validateAccount()==true) {
		        		writer.println("Welcome to Useful Bank " + account.getOwner().getName());
			        	try {
							startBanking();
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        	break;
			        }
			        else {
			        	writer.println("Wrong pin");
			        	
			        }
		        	
		        }
		        
				
			}
			finally {
		
				socket.close();		
			}
		}
		catch(IOException e) {
			
		}
        
		
		
	}
	
	public static void main(String[] args) {
		
		ServerSocket serverSocket;
		
		BankDatabase dbu = new BankDatabase();
		User sara = new User("sara",1234167599l);
		Account accS = new SavingsAccount(1111,sara);
		User john = new User("john",3214167599l);
		Account accJ = new SavingsAccount(2222,john);
		User mark = new User("mark",4564167599l);
		Account accM = new SavingsAccount(3333,mark);
		dbu.createUserTable();
		dbu.createAccountTable();
		
		
		try {

			dbu.addAccountAndUser(accS);
			dbu.addAccountAndUser(accJ);
			dbu.addAccountAndUser(accM);
			dbu.saveChanges();
			serverSocket = new ServerSocket(6000);
			while(true) {
			
			
				Socket socket = serverSocket.accept(); 
				System.out.println("connected to: " + socket.getRemoteSocketAddress());
				Thread thread = new Thread(new AtmServer(socket)); // create a thread with our Runnable type AtmServer
				thread.start(); // start this thread and go back to waiting for a new connection
	            
			
			}
		
		
		}
		catch(Exception ioe) {
			
			System.out.println(ioe.toString());
			}
		
		}
	}

 