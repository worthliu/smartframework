package com.worth.smart.framework.common.helper;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DatabaseHelper
 * @Description 数据库操作助手类
 * @Author Administrator
 * @Date 2018/10/4 13:13
 * @Version 1.0
 */
public class DatabaseHelper {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);

    /**
     * 数据库链接持有对象
     */
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;
    /**
     * 查询操作实体类
     */
    private static final QueryRunner QUERY_RUNNER;
    /**
     * 数据源
     */
    private static final BasicDataSource DATA_SOURCE;

    static {

        CONNECTION_HOLDER = new ThreadLocal<>();
        QUERY_RUNNER = new QueryRunner();
        //
        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(ConfigHelper.getJdbcDriver());
        DATA_SOURCE.setUrl(ConfigHelper.getJdbcUrl());
        DATA_SOURCE.setUsername(ConfigHelper.getJdbcUsername());
        DATA_SOURCE.setPassword(ConfigHelper.getJdbcPassword());
    }

    /**
     * 获取数据库链接
     * @return
     */
    public static Connection getConnection(){
        Connection conn = CONNECTION_HOLDER.get();
        if(conn == null){
            try {
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                logger.error("getConnection|building data source's connection failure", e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    /**
     * 关闭数据库链接
     */
    public static void closeConnection(){
        Connection conn = CONNECTION_HOLDER.get();
        if (conn != null){
            try {
                conn.close();
            }catch (SQLException e){
                logger.error("closeConnection|close connection failure", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 开启事务
     */
    public static void beginTransaction(){
        Connection conn = getConnection();
        if (conn != null){
            try {
                conn.setAutoCommit(false);
            }catch (Exception e){
                logger.error("begin transaction failure", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction(){
        Connection conn = getConnection();
        if(conn != null){
            try{
                conn.commit();
                conn.close();
            }catch (Exception e){
                logger.error("commit transaction failure", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction(){
        Connection conn = getConnection();
        if (conn != null){
            try {
                conn.rollback();
                conn.close();
            }catch (Exception e){
                logger.error("rollback transaction failure", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params){
        List<T> entityList;
        try{
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<>(entityClass), params);
        }catch (SQLException e) {
            logger.error("queryEntityList|query entity list failure", e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return entityList;
    }

    /**
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params){
        T entity;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<>(entityClass), params);
        } catch (SQLException e) {
            logger.error("queryEntity|query entity failure", e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return entity;
    }

    /**
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params){
        List<Map<String, Object>> result;
        Connection conn = getConnection();
        try {
            result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            logger.error("executeQuery|execute query failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * @param sql
     * @param params
     * @return
     */
    public static int executeUpdate(String sql, Object... params){
        int rows = 0;
        Connection conn = getConnection();
        try {
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            logger.error("executeUpdate|execute update failure", e);
        }finally {
            closeConnection();
        }
        return rows;
    }

    /**
     * @param entityClass
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap){
        if(MapUtils.isEmpty(fieldMap)){
            logger.error("insertEntity|can not insert entity : fieldMap is empty");
            return false;
        }

        String sql = "INSERT INTO " + getTableName(entityClass);
        StringBuffer colums = new StringBuffer("(");
        StringBuffer values = new StringBuffer("(");
        for (String fieldName : fieldMap.keySet()){
            colums.append(fieldName).append(", ");
            values.append("?, ");
        }

        colums.replace(colums.lastIndexOf(", "), colums.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += colums + " VALUES " + values;
        //
        Object[] params = fieldMap.values().toArray();
        //
        return executeUpdate(sql, params) == 1;
    }

    /**
     * @param entityClass
     * @param id
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap){
        if(MapUtils.isEmpty(fieldMap)){
            logger.error("updateEntity|can not update entity : fieldMap is empty");
            return false;
        }

        String sql = "UPDATE " + getTableName(entityClass) + " SET ";
        StringBuffer columns = new StringBuffer();

        for (String fieldName : fieldMap.keySet()){
            columns.append(fieldName).append("=?, ");
        }

        sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE id = ?";
        //
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        //
        Object[] params = paramList.toArray();
        //
        return executeUpdate(sql, params) == 1;
    }

    /**
     * @param entityClass
     * @param id
     * @param <T>
     * @return
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id){
        String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id = ?";
        return executeUpdate(sql, id) == 1;
    }

    /**
     * 执行sql文件
     * sql文件中,每条sql为一行
     * @param filePath
     */
    public static void executeSqlFile(String filePath){
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String sql;
            while ((sql = reader.readLine()) != null){
                executeUpdate(sql);
            }
        }catch (Exception e){
            logger.error("execute sql file failure", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param entityClass
     * @param <T>
     * @return
     */
    public static <T> String getTableName(Class<T> entityClass) {
        return entityClass.getSimpleName();
    }

    /**
     * @return
     */
    public static BasicDataSource getDataSource() {
        return DATA_SOURCE;
    }

    /**
     * @return
     */
    public static ThreadLocal<Connection> getConnectionHolder() {
        return CONNECTION_HOLDER;
    }

    /**
     * @return
     */
    public static QueryRunner getQueryRunner() {
        return QUERY_RUNNER;
    }
}
