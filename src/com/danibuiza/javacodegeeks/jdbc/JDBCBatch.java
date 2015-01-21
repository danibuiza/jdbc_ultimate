package com.danibuiza.javacodegeeks.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class provides some examples of how to use batch updates within statement and prepared
 * statements in jdbc
 * 
 * @author dgutierrez-diez
 */
public class JDBCBatch
{

    public static void main( String[] args ) throws SQLException, ClassNotFoundException
    {
        // connection to JDBC using mysql driver
        Class.forName( "com.mysql.jdbc.Driver" );
        Connection connect = DriverManager.getConnection( "jdbc:mysql://localhost/countries?"
            + "user=root&password=root" );

        insertRows( connect );

        batchStatements( connect );

        batchPreparedStatements( connect );

        selectAll( connect );
    }

    /**
     * example for prepared statements
     * 
     * @param connect
     * @throws SQLException
     */
    private static void batchPreparedStatements( Connection connect ) throws SQLException
    {
        String sql = "update COUNTRIES set POPULATION=? where NAME=?";

        PreparedStatement preparedStatement = null;

        preparedStatement = connect.prepareStatement( sql );

        preparedStatement.setObject( 1, 1000000 );
        preparedStatement.setObject( 2, "SPAIN" );

        // adding batches
        preparedStatement.addBatch();

        preparedStatement.setObject( 1, 1000000 );
        preparedStatement.setObject( 2, "USA" );

        // adding batches
        preparedStatement.addBatch();

        // executing all batchs
        int[] updatedRecords = preparedStatement.executeBatch();
        int total = 0;
        for( int recordUpdated : updatedRecords )
        {
            total += recordUpdated;
        }

        System.out.println( "total records updated by batch " + total );

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

        String sql = "INSERT INTO COUNTRIES (NAME,POPULATION) VALUES ('SPAIN', '90000000')";
        insertStmt.executeUpdate( sql );

        sql = "INSERT INTO COUNTRIES (NAME,POPULATION) VALUES ('USA', '90000000')";
        insertStmt.executeUpdate( sql );

        sql = "INSERT INTO COUNTRIES (NAME,POPULATION) VALUES ('GERMANY', '90000000')";
        insertStmt.executeUpdate( sql );

        sql = "INSERT INTO COUNTRIES (NAME,POPULATION) VALUES ('ARGENTINA', '90000000')";
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
            int population = resultSet.getInt( "POPULATION" );

            System.out.println( "NAME: " + name );
            System.out.println( "POPULATION: " + population );
        }

    }

    /**
     * example of batch usage within normal statements
     * 
     * @param connect
     * @throws SQLException
     */
    private static void batchStatements( Connection connect ) throws SQLException
    {
        Statement statement = null;

        statement = connect.createStatement();

        // adding batchs to the statement
        statement.addBatch( "update COUNTRIES set POPULATION=9000000 where NAME='USA'" );
        statement.addBatch( "update COUNTRIES set POPULATION=9000000 where NAME='GERMANY'" );
        statement.addBatch( "update COUNTRIES set POPULATION=9000000 where NAME='ARGENTINA'" );

        // usage of the executeBatch method
        int[] recordsUpdated = statement.executeBatch();

        int total = 0;
        for( int recordUpdated : recordsUpdated )
        {
            total += recordUpdated;
        }

        System.out.println( "total records updated by batch " + total );

    }
}
