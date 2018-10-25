import java.util.ArrayList;

public class User {
	
	
	//encapsulate some data (private to restrict access from outside this class )
	private String name;
	private long phoneNum;

	
	
	
	//the constructor for the class ( use this to create the specific object)
	public User(String name, long phoneNum) {
		
		this.phoneNum  = phoneNum;
		this.name = name;
	
	}
	
	//public getter methods are used to get the data specific to the object 
	
	
	
	public long getPhoneNum() {
		return phoneNum;
	}
	
	public String getName() {
		return name;
	}
	
}
