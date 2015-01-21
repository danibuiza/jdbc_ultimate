package com.danibuiza.javacodegeeks.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCResultSets
{
    /*
     * 
     * 
     * modifying cursor
     * 
     * update values
     * 
     * inesrting rows
     */

    public static void main( String[] args ) throws ClassNotFoundException, SQLException
    {
        // connection to JDBC using mysql driver
        Class.forName( "com.mysql.jdbc.Driver" );
        Connection connect = DriverManager.getConnection( "jdbc:mysql://localhost/countries?"
            + "user=root&password=root" );

        selectAll( connect );

    }

    /**
     * select statement and print out results in a JDBC result set
     * 
     * @param conn
     * @throws SQLException
     */
    private static void selectAll( java.sql.Connection conn ) throws SQLException
    {
        /**
         * indicating result sets properties that will be created from this statement: type,
         * concunrrency and holdability
         */
        Statement statement = conn.createStatement( ResultSet.TYPE_FORWARD_ONLY,
                                                    ResultSet.CONCUR_UPDATABLE,
                                                    ResultSet.CLOSE_CURSORS_AT_COMMIT );

        // creating the result set
        ResultSet resultSet = statement.executeQuery( "select * from COUNTRIES" );

        // iterating through the results rows

        while( resultSet.next() )
        {
            // accessing column values by index or name
            String name = resultSet.getString( "NAME" );
            int population = resultSet.getInt( "POPULATION" );

            System.out.println( "NAME: " + name );
            System.out.println( "POPULATION: " + population );

        }

        /**
         * the database should have primary keys, if not following error will appear: Exception in
         * thread "main" com.mysql.jdbc.NotUpdatable: Result Set not updatable (referenced table has
         * no primary keys).This result set must come from a statement that was created with a
         * result set type of ResultSet.CONCUR_UPDATABLE, the query must select only one table, can
         * not use functions and must select all primary keys from that table. See the JDBC 2.1 API
         * Specification, section 5.6 for more details.
         */

        resultSet.moveToInsertRow();
        resultSet.updateString( 1, "USA" );
        resultSet.updateInt( 2, 9000000 );
        resultSet.insertRow();
        resultSet.beforeFirst();

        /**
         * the same for the update operations
         */
        resultSet.updateString( "NAME", "SPAIN" );
        resultSet.updateRow();

    }
}
