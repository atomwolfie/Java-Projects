package demo.people;

public class Employee extends Person {
	
	private String title;
	private double salary;
	
	
	public Employee(String firstName, String lastName, double salary){
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		
		
	}
	
	public String toString(){
		return firstName + " " + lastName + " makes $" + salary;
	}
	
	public  double printSalary(){
		
		return salary;		
	}

}
