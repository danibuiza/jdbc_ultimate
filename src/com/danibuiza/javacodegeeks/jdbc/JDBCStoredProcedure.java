package com.danibuiza.javacodegeeks.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * example, very easy, of usage of stored procedures and callable statements
 * 
 * @author dgutierrez-diez
 */
public class JDBCStoredProcedure
{

    // http://www.mkyong.com/jdbc/jdbc-callablestatement-stored-procedure-in-parameter-example/

    public static void main( String[] args ) throws SQLException, ClassNotFoundException
    {

        CallableStatement callableStatement = null;

        Class.forName( "com.mysql.jdbc.Driver" );

        // connection
        Connection connect = DriverManager.getConnection( "jdbc:mysql://localhost/countries?"
            + "user=root&password=root" );

        // the procedure should be created in the database
        String spanishProcedure = "{call spanish(?)}";

        // callable statement is used
        callableStatement = connect.prepareCall( spanishProcedure );

        // out parameters, also in parameters are possible, not in this case
        callableStatement.registerOutParameter( 1, java.sql.Types.VARCHAR );

        // execute using the callable statement method executeUpdate
        callableStatement.executeUpdate();

        // attributes are retrieved by index
        String total = callableStatement.getString( 1 );

        System.out.println( "amount of spanish countries " + total );
    }
}
