package com.danibuiza.javacodegeeks.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class explains the main mechanisms related to transactions and jdbc like rollback and commit
 * 
 * @author dgutierrez-diez
 */
public class JDBCTransactions
{

    public static void main( String[] args ) throws SQLException, ClassNotFoundException
    {

        // delete all
        delete();

        // insert and update without using transactions = autocommit = true
        withoutTransactions();

        // lists all
        System.out.println( "*****" );
        selectAll();

        // delete all
        delete();

        // lists all
        System.out.println( "*****" );
        selectAll();

        // insert and update using transactions, at the end of the transaction will be commited
        usingTransactions();

        // lists all
        System.out.println( "*****" );
        selectAll();

        // delete all
        delete();

        // rollbacks the first transaction and commits the second
        withoutTransactionsRollback();

        // lists all
        System.out.println( "*****" );
        selectAll();

    }

    /**
     * deletes all entries for japan
     * 
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static void delete() throws SQLException, ClassNotFoundException
    {
        Class.forName( "com.mysql.jdbc.Driver" );

        // connection
        Connection connect = DriverManager.getConnection( "jdbc:mysql://localhost/countries?"
            + "user=root&password=root" );
        System.out.println( "Deleting rows for JAPAN..." );

        String sql = "DELETE FROM COUNTRIES WHERE NAME='JAPAN'";
        PreparedStatement deleteStmt = connect.prepareStatement( sql );

        // delete statement using executeUpdate
        int numberRows = deleteStmt.executeUpdate( sql );

        System.out.println( numberRows + " rows deleted..." );

        connect.close();
    }

    /**
     * select statement and print out results in a JDBC result set
     * 
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static void selectAll() throws SQLException, ClassNotFoundException
    {
        Class.forName( "com.mysql.jdbc.Driver" );

        // connection
        Connection connect = DriverManager.getConnection( "jdbc:mysql://localhost/countries?"
            + "user=root&password=root" );
        Statement statement = connect.createStatement();

        ResultSet resultSet = statement.executeQuery( "select * from COUNTRIES WHERE NAME='JAPAN'" );

        while( resultSet.next() )
        {
            String name = resultSet.getString( "NAME" );
            int population = resultSet.getInt( "POPULATION" );

            System.out.println( "NAME: " + name );
            System.out.println( "POPULATION: " + population );
        }

        connect.close();

    }

    /**
     * Inserts a row for japan and updates it right away
     * 
     * @param transactions if true, transactions are used and only is commited at the end, if false,
     *            autocommit=true is used
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static void insertAndUpdate( boolean transactions ) throws SQLException, ClassNotFoundException
    {
        Class.forName( "com.mysql.jdbc.Driver" );
        Connection connect = null;
        try
        {

            // connection
            connect = DriverManager.getConnection( "jdbc:mysql://localhost/countries?" + "user=root&password=root" );
            if( transactions )
            {
                connect.setAutoCommit( false );
            }
            System.out.println( "Inserting row for Japan..." );
            String sql = "INSERT INTO COUNTRIES (NAME,POPULATION) VALUES ('JAPAN', '45000000')";

            PreparedStatement insertStmt = connect.prepareStatement( sql );

            // insert statement using executeUpdate
            insertStmt.executeUpdate( sql );

            System.out.println( "Updating row for Japan..." );
            // update statement using executeUpdate -> will cause an error, update will not be
            // executed because the field population is an int
            sql = "UPDATE COUNTRIES SET POPULATION='10Mill' WHERE NAME='JAPAN'";
            PreparedStatement updateStmt = connect.prepareStatement( sql );

            updateStmt.executeUpdate( sql );
            if( transactions )
            {
                connect.commit();
            }
        }
        catch( SQLException ex )
        {
            ex.printStackTrace();
        }
        finally
        {
            connect.close();
        }

    }

    /**
     * similar to the last method, rollback first transaction and commits the second one
     * 
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static void insertAndUpdateRollback() throws SQLException, ClassNotFoundException
    {
        Class.forName( "com.mysql.jdbc.Driver" );
        Connection connect = null;
        try
        {
            // connection
            connect = DriverManager.getConnection( "jdbc:mysql://localhost/countries?" + "user=root&password=root" );
            connect.setAutoCommit( false );

            System.out.println( "Inserting row for Japan..." );
            String sql = "INSERT INTO COUNTRIES (NAME,POPULATION) VALUES ('JAPAN', '45000000')";

            PreparedStatement insertStmt = connect.prepareStatement( sql );

            // insert statement using executeUpdate
            insertStmt.executeUpdate( sql );
            connect.rollback();

            System.out.println( "Updating row for Japan..." );
            // update statement using executeUpdate -> will cause an error, update will not be
            // executed becaues the row does not exist
            sql = "UPDATE COUNTRIES SET POPULATION='1000000' WHERE NAME='JAPAN'";
            PreparedStatement updateStmt = connect.prepareStatement( sql );

            updateStmt.executeUpdate( sql );
            connect.commit();

        }
        catch( SQLException ex )
        {
            ex.printStackTrace();
            connect.rollback();
        }
        finally
        {
            connect.close();
        }

    }

    private static void withoutTransactions() throws SQLException, ClassNotFoundException
    {
        // non updated row shown
        System.out.println( "without transactions..." );
        insertAndUpdate( false );

    }

    private static void usingTransactions() throws SQLException, ClassNotFoundException
    {
        // nothing shown
        System.out.println( "with transactions..." );
        insertAndUpdate( true );

    }

    private static void withoutTransactionsRollback() throws SQLException, ClassNotFoundException
    {
        // nothing shown
        System.out.println( "with rollback and transactions..." );
        insertAndUpdateRollback();

    }

}
