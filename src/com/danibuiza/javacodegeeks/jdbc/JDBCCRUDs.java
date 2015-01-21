package com.danibuiza.javacodegeeks.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class shows the main SQL operations: Insert, delete, update and select
 * 
 * @author dgutierrez-diez
 */
public class JDBCCRUDs
{
    public static void main( String[] args ) throws ClassNotFoundException, SQLException
    {

        Class.forName( "com.mysql.jdbc.Driver" );

        // connection
        Connection connect = DriverManager.getConnection( "jdbc:mysql://localhost/countries?"
            + "user=root&password=root" );

        selectAll( connect );

        insertRows( connect );

        updateRows( connect, "SPAIN" );

        deleteRows( connect, "SPAIN" );

        selectAll( connect );

        // close resources, in case of exception resources are not properly cleared
        connect.close();
    }

    /**
     * example of select using jdbc statement
     * 
     * @param connect
     * @throws SQLException
     */
    private static void selectAll( java.sql.Connection connect ) throws SQLException
    {
        Statement statement = connect.createStatement();

        // select statement using executeQuery
        ResultSet resultSet = statement.executeQuery( "select * from COUNTRIES" );

        while( resultSet.next() )
        {
            // access via name
            String name = resultSet.getString( "NAME" );
            int population = resultSet.getInt( "POPULATION" );

            System.out.println( "Name: " + name );
            System.out.println( "Population: " + population );
        }
    }

    /**
     * Inserts some rows, example of insert using jdbc statement
     * 
     * @param conn
     * @throws SQLException
     */
    private static void insertRows( java.sql.Connection conn ) throws SQLException
    {
        System.out.println( "Inserting rows..." );

        Statement insertStmt = conn.createStatement();

        // insert statement using executeUpdate
        String sql = "INSERT INTO COUNTRIES (NAME,POPULATION) VALUES ('SPAIN', '45000000')";
        int numberRows = insertStmt.executeUpdate( sql );

        sql = "INSERT INTO COUNTRIES (NAME,POPULATION) VALUES ('USA', '200000000')";
        numberRows += insertStmt.executeUpdate( sql );

        sql = "INSERT INTO COUNTRIES (NAME,POPULATION) VALUES ('GERMANY', '90000000')";
        numberRows += insertStmt.executeUpdate( sql );

        System.out.println( numberRows + " rows inserted..." );

    }

    /**
     * Updates rows with the name passed, example of update using jdbc statement
     * 
     * @param conn
     * @throws SQLException
     */
    private static void updateRows( java.sql.Connection conn, String name ) throws SQLException
    {
        System.out.println( "Updating rows for " + name + "..." );

        Statement updateStmt = conn.createStatement();

        // update statement using executeUpdate
        String sql = "UPDATE COUNTRIES SET POPULATION='10000000' WHERE NAME='" + name + "'";
        int numberRows = updateStmt.executeUpdate( sql );

        System.out.println( numberRows + " rows updated..." );
    }

    /**
     * Deletes the rows with the name passed, example of row deletion using jdbc example
     * 
     * @param conn
     * @throws SQLException
     */
    private static void deleteRows( java.sql.Connection conn, String name ) throws SQLException
    {
        System.out.println( "Deleting rows for " + name + "..." );

        Statement deleteStmt = conn.createStatement();

        // delete statement using executeUpdate
        String sql = "DELETE FROM COUNTRIES WHERE NAME='" + name + "'";
        int numberRows = deleteStmt.executeUpdate( sql );

        System.out.println( numberRows + " rows deleted..." );
    }

}
