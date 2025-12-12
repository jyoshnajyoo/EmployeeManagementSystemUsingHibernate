package com.kodnest.EmployeeManagementSystemUsingHibernate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class EmployeeTablePage extends JFrame {

    static SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    JTable table;
    JLabel messageLabel;

    public EmployeeTablePage(String message) {

        setTitle("Employee Table View");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Salary");
        model.addColumn("Email");

        Session session = factory.openSession();
        List<Employee> employees = session.createQuery("from Employee", Employee.class).getResultList();
        session.close();

        for (Employee e : employees) {
            model.addRow(new Object[]{e.getId(), e.getName(), e.getSalary(), e.getEmail()});
        }

        table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        add(pane, BorderLayout.CENTER);

        messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(messageLabel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
