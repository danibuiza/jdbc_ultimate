package com.danibuiza.javacodegeeks.jdbc.java8;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is a functional interface that can be use within lambda expressions, only one method
 * is needed
 * 
 * @author dgutierrez-diez
 */
@FunctionalInterface
public interface ResultSetHandler
{

    /**
     * This method will be executed by the lambda expression
     * 
     * @param resultSet
     * @throws SQLException
     */
    public void handle( ResultSet resultSet ) throws SQLException;

}
