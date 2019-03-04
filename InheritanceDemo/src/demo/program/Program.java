package demo.program;
import demo.people.Employee;
import demo.people.Person;
import demo.people.Student;


public class Program {

	public static void main(String[] args) {
		
		Person pers1 = new Person();
		Employee emp1 = new Employee("Sam", "Jones", 50000);
		Student stu1 = new Student("Billy", "Smith", "Computer Science");
		
		System.out.println(pers1);
		System.out.println(emp1);
		System.out.println(stu1);
		
		
		
	}

}
