package com.walid.gcd.jdbc;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * A responsible for database interface (reading and writing) to an JNDI-named Oracle database
 * to persist the numbers and the GCDs to two tables with two columns (structure as follows):
 * <p>
 * CREATE TABLE numbers AS (seq NUMBER, num NUMBER);
 * <p>
 * CREATE TABLE gcds AS (seq NUMBER, num NUMBER);
 *
 * @author waleedaleem@hotmail.com
 */

public class DbManager {

    private static final String DEFAULT_DATASOURCE = "java:jboss/datasources/numbersOracle";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";
    private static Logger LOGGER = LoggerFactory.getLogger(DbManager.class);
    Connection connection;

    // enum of the two queue-like table types hosting numbers and calculated GCDs
    public enum NumberTable { NUMBERS, GCDS }

    /**
     * Constructor to setup JDBC connection
     */
    public DbManager() {
        LOGGER.info("Setting up JDBC connection");

        Context namingContext = null;

        // Set up the namingContext for the JNDI lookup
        final Properties env = new Properties();
        env.put(INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(PROVIDER_URL, System.getProperty(PROVIDER_URL, PROVIDER_URL));
        try {
            namingContext = new InitialContext(env);

            // Perform the JNDI lookups
            LOGGER.info("Attempting to acquire a datasource");
            Context envContext = (Context) namingContext.lookup("java:/comp/env");
            DataSource dataSource = (DataSource) envContext.lookup(DEFAULT_DATASOURCE);
            LOGGER.info("Found dtasource \"" + DEFAULT_DATASOURCE + "\" in JNDI");

            LOGGER.info("Attempting to acquire a datasource connection");
            connection = dataSource.getConnection();
        } catch (NamingException e) {
            LOGGER.error("Naming error enqueueing number", e);
        } catch (SQLException sqe) {
            LOGGER.error("SQL error enqueueing number", sqe);
        } finally {
            if (namingContext != null) {
                try {
                    namingContext.close();
                } catch (NamingException ignore) {
                }
            }
        }
    }

    /**
     * returns an ordered list of integers from database or row count of a DML statement
     */
    private List<Integer> processStatement(String sqlCmd) {

        List<Integer> list = new ArrayList();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.prepareStatement(sqlCmd);
            boolean isResultSet = stmt.execute();
            if (isResultSet) {
                rs = stmt.getResultSet();
                while (rs.next()) {
                    list.add(rs.getInt(1));
                }
            } else {
                int rowCount = stmt.getUpdateCount();
                list.add(rowCount);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to resolve query from H2", e);
            return null;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (Exception ignore){}
        }
        return list;
    }

    public boolean persistNumber(int number, NumberTable numberTable) {

        // structure of H2 table numbers (seq bigint auto_increment, num int)
        String insertStatement = String.format("INSERT INTO %s(num) VALUES(%d)",
                numberTable.name(), number);
        List result = processStatement(insertStatement);
        return !result.isEmpty() && (int) result.get(0) == 1;
    }

    public List<Integer> retrieveNumbers(NumberTable numberTable) {

        String selectStatement = String.format("SELECT num FROM %s ORDER BY seq", numberTable.name());
        return processStatement(selectStatement);
    }

    private String numberListToJson(List<Integer> numberList) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(numberList);
        } catch (IOException e) {
            LOGGER.error("Error formatting number list as JSON", e);
            return "[]";
        }
    }

    public String retrieveJsonNumbers(NumberTable numberTable) {
        return numberListToJson(retrieveNumbers(numberTable));
    }
}
