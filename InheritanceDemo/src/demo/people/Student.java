package demo.people;

public class Student extends Person {
	private String major;
	
	public Student(String firstName, String lastName, String major){
		
		this.firstName = firstName;
		this.lastName = lastName;
		
	}
	
	
	
	public String toString(){
		return firstName + " " + lastName + " is a " + major + " major";
	}
	
	
	public void printString(){
		System.out.println(toString());
		
	}

	
}
