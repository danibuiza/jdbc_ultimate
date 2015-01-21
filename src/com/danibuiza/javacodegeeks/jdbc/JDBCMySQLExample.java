package com.danibuiza.javacodegeeks.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is a first example of JDBC usage: shows how to open a connection using a mysql driver,
 * it makes a first select query and read the results
 * 
 * @author dgutierrez-diez
 */
public class JDBCMySQLExample
{

    // would be better to handle the exceptions in a different way, but this is just for explanation
    // purposes and this way is clearer
    public static void main( String[] args ) throws ClassNotFoundException, SQLException
    {

        // connection to JDBC using mysql driver
        Class.forName( "com.mysql.jdbc.Driver" );
        Connection connect = DriverManager.getConnection( "jdbc:mysql://localhost/countries?"
            + "user=root&password=root" );

        Statement statement = connect.createStatement();

        // select query
        ResultSet resultSet = statement.executeQuery( "select * from COUNTRIES" );

        while( resultSet.next() )
        {
            // access via name
            String name = resultSet.getString( "NAME" );
            String population = resultSet.getString( "POPULATION" );

            System.out.println( "Name: " + name );
            System.out.println( "Population: " + population );
        }

        // close resources, in case of exception resources are not properly cleared
        resultSet.close();
        statement.close();
        connect.close();

    }
}
