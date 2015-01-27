package com.danibuiza.javacodegeeks.jdbc;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Example of usages of DriverManager, not recommended to do manually
 * 
 * @author dgutierrez-diez
 */
public class DriverManagerUsage
{

    public static void main( String[] args ) throws SQLException
    {
        new org.hsqldb.jdbc.JDBCDriver();
        DriverManager.registerDriver( new org.hsqldb.jdbc.JDBCDriver() );

        // Create the connection with the default credentials
        java.sql.Connection conn = DriverManager.getConnection( "jdbc:hsqldb:mem:mydb", "SA", "" );

        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while( drivers.hasMoreElements() )
        {
            Driver driver = drivers.nextElement();
            System.out.println( driver.getClass() );
        }
    }
}
