package demo.people;

import java.util.ArrayList;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Person pers1 = new Person();
		Employee emp1 = new Employee("Sam", "Jones", 50000);
		Student stu1 = new Student("Billy", "Smith","Computer Science");
		
		
		ArrayList<Person> arrLst = new ArrayList<Person>();   //Polymorphism
		
		arrLst.add(pers1);
		arrLst.add(stu1);
		arrLst.add(emp1);
		
		for(Person i : arrLst){
			
			System.out.println(i instanceof Employee);  //Switch between, person, employee, and student to test
		}
		
		
		
		for(Person i : arrLst){
			
			//System.out.println(i);
			
			if(i instanceof Employee)
			((Employee)i).printString();   //casting the method
			
		}
		
		
		
		//System.out.println(pers1);
		//System.out.println(emp1);
		//System.out.println(stu1);

	}

}
