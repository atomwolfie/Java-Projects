package demo.people;
import java.util.Calendar;
import java.util.Date;

public class Person {
	
	private Date id;
	protected String firstName;
	protected String lastName;
	
	
	
	public Person(){
		id = Calendar.getInstance().getTime();
		
		
	}
	
	public String toString(){
		return firstName + " " + lastName;
	}

	public void printString(){
		System.out.println(toString());
		
	}

	
}
