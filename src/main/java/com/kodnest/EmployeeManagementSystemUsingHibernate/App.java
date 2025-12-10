package com.kodnest.EmployeeManagementSystemUsingHibernate;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class App 
{
	static SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	static Scanner scan = new Scanner(System.in);
    public static void main( String[] args )
    {
    	  System.out.println("WELCOME TO EMPLOYEE MANAGEMENT SYSTEM");
    	  while (true) {
    		System.out.println("PRESS1->AddEmployee");
    		System.out.println("PRESS2->GetEmployee");
    		System.out.println("PRESS3->DeleteEmployee");
    		System.out.println("PRESS4->UpdateEmployee");
    		System.out.println("PRESSother->Exit");
    		
    		int ch =scan.nextInt();
    	 
    
      	switch(ch) {
       	case 1: addEmployee();
      	break;
     	case 2: getEmployee();
        break;
     	case 3: deleteEmployee();
     	break;
     	case 4: updateEmployee();
     	break;
        default:System.out.println("THANKS FOR USING EMPLOYEE MANAGEMENT SYSTEM... TATA BYEE BYEE");
        factory.close();
        return;
     	}
    	  }
      
     
    }
    
    public static void addEmployee() {
    	
    	Session session=factory.openSession();
    	Transaction transaction = session.beginTransaction();
    	System.out.println("Enter Employee name, salary, email");
    	Employee employee = new Employee(scan.next(),scan.nextInt(),scan.next());
    	session.persist(employee);
    	transaction.commit();
    	session.close();
    }
    
    public static void getEmployee() {
    	
    	Session session=factory.openSession();
    	Transaction transaction = session.beginTransaction();
    	System.out.println("Enter Employee Id to get their details");
    	int id = scan.nextInt();
    Employee obj =session.find(Employee.class, id);
    	if (obj != null) {
    		System.out.println(obj);
    		System.out.println("Taken the Employee details succesfully");
    	} else {
    		System.out.println("Employee with Id: " + id + " Does not Exist please try again");
    	}
    	transaction.commit();
    	session.close();
    	
    }
 
    public static void deleteEmployee() {
 	
    	Session session=factory.openSession();
    	Transaction transaction = session.beginTransaction();
    	System.out.println("Enter Employee Id to Delete their details");
    	int id = scan.nextInt();
    Employee obj =session.find(Employee.class, id);
    	if (obj != null) {
    		session.remove(obj);
    		System.out.println("Employee details Deleted succesfully");
    	} else {
    		System.out.println("Employee with Id: " + id + " Does not Exist please try again");
    	}
    	transaction.commit();
    	session.close();
    	
    }
 
    public static void updateEmployee() {
 	
    Session session=factory.openSession();
	Transaction transaction = session.beginTransaction();
    System.out.println("Enter Employee Id to update their details");
    int id = scan.nextInt();
    Employee obj=session.find(Employee.class, id);
    if (obj != null) {
 	   System.out.println("Enter new name, salary, email");
 	   obj.setName(scan.next());
 	   obj.setSalary(scan.nextInt());
 	   obj.setEmail(scan.next());
 	   session.merge(obj);
 	   System.out.println("Employee details Updated succesfully");
    } else {
 	   System.out.println("Employee with Id: " + id + " Does not Exist please try again");
    }
	transaction.commit();
    	session.close();
    	
    }
}
