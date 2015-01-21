package com.danibuiza.javacodegeeks.jdbc;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class contains a small example about how to use in memory databases within JDBC, in this
 * case HSQLDB
 * 
 * @author dgutierrez-diez
 */
public class HSQLDBExample
{

    public static void main( String[] args ) throws ClassNotFoundException, SQLException
    {

        // Loading the HSQLDB JDBC driver
        Class.forName( "org.hsqldb.jdbc.JDBCDriver" );

        // Create the connection with the default credentials
        java.sql.Connection conn = DriverManager.getConnection( "jdbc:hsqldb:mem:mydb", "SA", "" );

        // Create a table in memory
        String countriesTableSQL = "create memory table COUNTRIES (NAME varchar(256) not null primary key, POPULATION varchar(256) not null);";

        // execute the statement using JDBC normal Statements
        Statement st = conn.createStatement();
        st.execute( countriesTableSQL );

        // nothing is in the database because it is just in memory, non persistent
        selectAll( conn );

        // after some insertions, the select shows something different
        insertRows( conn );
        selectAll( conn );

    }

    /**
     * Inserts some rows
     * 
     * @param conn
     * @throws SQLException
     */
    private static void insertRows( java.sql.Connection conn ) throws SQLException
    {
        System.out.println( "Inserting rows..." );

        Statement insertStmt = conn.createStatement();

        String sql = "INSERT INTO COUNTRIES (NAME,POPULATION) VALUES ('SPAIN', '45Mill')";
        insertStmt.executeUpdate( sql );

        sql = "INSERT INTO COUNTRIES (NAME,POPULATION) VALUES ('USA', '200Mill')";
        insertStmt.executeUpdate( sql );

        sql = "INSERT INTO COUNTRIES (NAME,POPULATION) VALUES ('GERMANY', '90Mill')";
        insertStmt.executeUpdate( sql );
    }

    /**
     * select statement and print out results in a JDBC result set
     * 
     * @param conn
     * @throws SQLException
     */
    private static void selectAll( java.sql.Connection conn ) throws SQLException
    {
        Statement statement = conn.createStatement();

        ResultSet resultSet = statement.executeQuery( "select * from COUNTRIES" );

        while( resultSet.next() )
        {
            String name = resultSet.getString( "NAME" );
            String population = resultSet.getString( "POPULATION" );

            System.out.println( "NAME: " + name );
            System.out.println( "POPULATION: " + population );
        }

    }
}
