package com.krishna.projects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        try {
            createAndPopulateEmployeeTable();
        } catch ( Exception e ) {
            System.out.println("Table exists");
        } finally {
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.print("Enter a employee id or exit to quit: ");
            String n = reader.next();
            int id;
            while (!(n.equalsIgnoreCase("exit") || n.equalsIgnoreCase("quit"))) {
                try {
                    id = Integer.parseInt(n);
                    printEmployeeById(id);
                    System.out.print("Enter a employee id or exit to quit: ");
                } catch (Exception e) {
                    System.out.print("Enter valid integer or Exit to quit :");
                }
                n = reader.next();
            }
        }
    }

    static void createAndPopulateEmployeeTable() {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:company.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "CREATE TABLE EMPLOYEE " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " AGE            INT     NOT NULL, " +
                    " SALARY         REAL)";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO EMPLOYEE (ID,NAME,AGE,SALARY) " +
                    "VALUES (1, 'Paul', 32, 20000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO EMPLOYEE (ID,NAME,AGE,SALARY) " +
                    "VALUES (2, 'Allen', 25, 15000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO EMPLOYEE (ID,NAME,AGE,SALARY) " +
                    "VALUES (3, 'Teddy', 23, 20000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO EMPLOYEE (ID,NAME,AGE,SALARY) " +
                    "VALUES (4, 'Mark', 25, 65000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO EMPLOYEE (ID,NAME,AGE,SALARY) " +
                    "VALUES (5, 'Mark', 25, 65000.00 );";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
            System.out.println("Table created successfully");
        } catch ( Exception e ) {
            System.out.println("EMPLOYEE table already exists");
        }
    }

    static void printEmployees() {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:company.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM EMPLOYEE;" );

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                int age  = rs.getInt("age");
                float salary = rs.getFloat("salary");

                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println( "AGE = " + age );
                System.out.println( "SALARY = " + salary );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    static void printEmployeeById(int id) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:company.db");
            c.setAutoCommit(false);
            System.out.println();
            stmt = c.createStatement();
            String sql = "DELETE from EMPLOYEE where ID=2;";
            stmt.executeUpdate(sql);
            c.commit();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM EMPLOYEE where id ="+id+";" );

            while ( rs.next() ) {
                String  name = rs.getString("name");
                int age  = rs.getInt("age");
                float salary = rs.getFloat("salary");

                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println( "AGE = " + age );
                System.out.println( "SALARY = " + salary );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
