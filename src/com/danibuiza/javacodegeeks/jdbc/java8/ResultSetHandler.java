package com.danibuiza.javacodegeeks.jdbc.java8;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetHandler
{

    public void handle( ResultSet resultSet ) throws SQLException;

}
