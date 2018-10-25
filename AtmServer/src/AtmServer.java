import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
 
public class AtmServer implements Runnable{
	
	private Socket socket ;
	private PrintWriter writer;
	private BufferedReader reader;
	private Account account;
	private final String continueBanking = " Would you like to continue banking? (y/n)";
	private static DatabaseUtil data;
	
	
	public AtmServer(Socket socket) {
		this.socket= socket;
		
		
		
		
	}
	// banking options available once logged in 
	public void startBanking() throws NumberFormatException, IOException {
		
		
	
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
				
				writer.println("Balance: " +account.getBalance() + continueBanking);
				break;
				
			case 4:
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
		        		writer.println("true");
			        	startBanking();
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
		data = new DatabaseUtil();
        data.connectToData();
		
		try {
			
			ServerSocket serverSocket = new ServerSocket(6000);
		while(true) {
			
				Socket socket = serverSocket.accept(); 
				System.out.println("connected to: " + socket.getRemoteSocketAddress());
				System.out.println("this test");
				Thread thread = new Thread(new AtmServer(socket)); // create a thread with our Runnable type AtmServer
				thread.start(); // start this thread and go back to waiting for a new connection
	            
			
			}}
			catch(IOException ioe) {
				System.out.println(ioe.toString());
			}
		
		}
	}

 