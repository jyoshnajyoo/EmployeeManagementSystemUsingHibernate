package com.kodnest.EmployeeManagementSystemUsingHibernate;

import javax.swing.*;
import java.awt.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class EmployeeGUI extends JFrame {

    static SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    JLabel title, l1, l2, l3, l4;
    JTextField t1, t2, t3, t4;
    JButton addBtn, getBtn, deleteBtn, updateBtn;

    public EmployeeGUI() {

        setTitle("Employee Management System");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        title = new JLabel("Employee Management System");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(160, 20, 400, 30);
        add(title);

        l1 = new JLabel("Enter ID:");
        l1.setBounds(60, 90, 120, 25);
        add(l1);
        t1 = new JTextField();
        t1.setBounds(200, 90, 250, 25);
        add(t1);

        l2 = new JLabel("Enter Name:");
        l2.setBounds(60, 140, 120, 25);
        add(l2);
        t2 = new JTextField();
        t2.setBounds(200, 140, 250, 25);
        add(t2);

        l3 = new JLabel("Enter Salary:");
        l3.setBounds(60, 190, 120, 25);
        add(l3);
        t3 = new JTextField();
        t3.setBounds(200, 190, 250, 25);
        add(t3);

        l4 = new JLabel("Enter Email:");
        l4.setBounds(60, 240, 120, 25);
        add(l4);
        t4 = new JTextField();
        t4.setBounds(200, 240, 250, 25);
        add(t4);

        addBtn = new JButton("ADD");
        addBtn.setBounds(60, 320, 100, 35);
        add(addBtn);

        getBtn = new JButton("GET");
        getBtn.setBounds(180, 320, 100, 35);
        add(getBtn);

        deleteBtn = new JButton("DELETE");
        deleteBtn.setBounds(300, 320, 100, 35);
        add(deleteBtn);

        updateBtn = new JButton("UPDATE");
        updateBtn.setBounds(420, 320, 100, 35);
        add(updateBtn);

        addBtn.addActionListener(e -> addEmployee());
        getBtn.addActionListener(e -> getEmployee());
        deleteBtn.addActionListener(e -> deleteEmployee());
        updateBtn.addActionListener(e -> updateEmployee());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // ADD
    private void addEmployee() {
        try {
            String name = t2.getText();
            int salary = Integer.parseInt(t3.getText());
            String email = t4.getText();

            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();

            Employee emp = new Employee(name, salary, email);
            session.persist(emp);

            tx.commit();
            session.close();

            new EmployeeTablePage("Employee Added Successfully!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // GET
    private void getEmployee() {
        try {
            int id = Integer.parseInt(t1.getText());

            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();

            Employee emp = session.find(Employee.class, id);

            if (emp != null) {
                t2.setText(emp.getName());
                t3.setText(String.valueOf(emp.getSalary()));
                t4.setText(emp.getEmail());

                new EmployeeTablePage("Fetched Employee: " + emp.toString());
            } else {
                JOptionPane.showMessageDialog(this, "Employee Not Found!");
            }

            tx.commit();
            session.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // DELETE
    private void deleteEmployee() {
        try {
            int id = Integer.parseInt(t1.getText());

            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();

            Employee emp = session.find(Employee.class, id);

            if (emp != null) {
                session.remove(emp);
                new EmployeeTablePage("Employee Deleted Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Employee Not Found!");
            }

            tx.commit();
            session.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // UPDATE
    private void updateEmployee() {
        try {
            int id = Integer.parseInt(t1.getText());

            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();

            Employee emp = session.find(Employee.class, id);

            if (emp != null) {
                emp.setName(t2.getText());
                emp.setSalary(Integer.parseInt(t3.getText()));
                emp.setEmail(t4.getText());
                session.merge(emp);

                new EmployeeTablePage("Employee Updated Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Employee Not Found!");
            }

            tx.commit();
            session.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new EmployeeGUI();
    }
}
