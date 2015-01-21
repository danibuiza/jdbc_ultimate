package com.danibuiza.javacodegeeks.jdbc.java8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCJava8
{
    public static void main( String[] args ) throws ClassNotFoundException, SQLException
    {
        usingJava7();

        usingJava8();
    }

    private static void usingJava8() throws SQLException, ClassNotFoundException
    {
        System.out.println( "using Java 8" );

        Class.forName( "com.mysql.jdbc.Driver" );

        // connection
        Connection connect = DriverManager.getConnection( "jdbc:mysql://localhost/countries?"
            + "user=root&password=root" );

        select( connect, "select * from COUNTRIES", ( resultSet ) -> {
            System.out.println( resultSet.getObject( 1 ) );
            System.out.println( resultSet.getObject( 2 ) );
        } );

    }

    private static void usingJava7() throws ClassNotFoundException, SQLException
    {
        // we always need to write this code
        System.out.println( "using Java 7" );
        Class.forName( "com.mysql.jdbc.Driver" );

        // connection
        Connection connect = DriverManager.getConnection( "jdbc:mysql://localhost/countries?"
            + "user=root&password=root" );

        // select query
        PreparedStatement statement = connect.prepareStatement( "select * from COUNTRIES" );
        ResultSet resultSet = statement.executeQuery();

        // iterating results
        while( resultSet.next() )
        {
            // access via name
            Object name = resultSet.getObject( 1 );
            Object population = resultSet.getObject( 2 );

            System.out.println( "Name: " + name );
            System.out.println( "Population: " + population );
        }

        // close resources, in case of exception resources are not properly cleared
        resultSet.close();
        statement.close();
        connect.close();

    }

    /**
     * based on the code on http://java.dzone.com/articles/adding-java-8-lambda-goodness
     * 
     * @param connection
     * @param sql
     * @param handler
     */
    public static void select( Connection connect, String sql, ResultSetHandler handler ) throws SQLException
    {
        PreparedStatement statement = connect.prepareStatement( sql );

        try (ResultSet rs = statement.executeQuery())
        {
            while( rs.next() )
            {
                handler.handle( rs );
            }

        }
    }
}
