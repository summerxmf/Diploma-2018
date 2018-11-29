package database;

import UI.UIhelper.AlertDialog;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBHelper {
    private static Connection con = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static CallableStatement cs =null;


    private static String driver = "";
    private static String url = "";
    private static String username = "";
    private static String password = "";

    private static Properties pp = null;
    private static FileInputStream fis = null;

    public static Connection getCon() {
        return con;
    }

    public static PreparedStatement getPs() {
        return ps;
    }

    public static ResultSet getRs() {
        return rs;
    }

    static {
        try {
            pp = new Properties();

            fis = new FileInputStream("dbinfo.properties");

            pp.load(fis);
            driver = pp.getProperty("driver");
            url = pp.getProperty("url");
            username = pp.getProperty("username");
            password = pp.getProperty("password");
            System.out.println(driver);

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println(driver);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fis =null;
        }
    }
    // Get connection
    public static Connection getConnection(){
        try {
            con = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }


    public static ResultSet executeQuery(String sql, String[] parameters){
        con = getConnection();

        try {
            ps = con.prepareStatement(sql);
            if(parameters!=null){
                for(int i=0; i<parameters.length; i++){
                    ps.setString(i+1, parameters[i]);
                }
            }
            rs = ps.executeQuery();

        }catch (Exception e){
            AlertDialog.error("Error Occured! " , e.toString());
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());

//        }finally {
//            //close(rs,ps,con);
        }
        return rs;
    }
    // if there are many update/ delete/ insert
    public static boolean executeUpdate2(String sql[], String[][]parameters){
        try {
            con =getConnection();

            con.setAutoCommit(false);

            for (int i = 0; i<sql.length; i++){
                ps = con.prepareStatement(sql[i]);
                if(parameters!=null){
                    for (int j=0; j< parameters[i].length; j++){
                        ps.setString(j+1, parameters[i][j]);
                    }
                }
                ps.executeUpdate();
            }

            con.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e1) {
                AlertDialog.error("Error Occured! " , e.toString());
                e1.printStackTrace();
            }

            throw new RuntimeException(e.getMessage());
        }finally {
            close(rs,ps,con);
        }
    }

    // update/ delete/ insert
    public static boolean executeUpdate(String sql, String[] parameters){
        con = getConnection();
        try {
            ps= con.prepareStatement(sql);

            if(parameters != null){
                for(int i = 0; i< parameters.length; i++){
                    ps.setString(i+1, parameters[i]);
                }
            }
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            AlertDialog.error("Error Occured! " , e.toString());
            e.printStackTrace();
            return false;
        }finally {
            close(rs, ps, con);
        }

    }

    //调用存储过程
    // sql  ig{call 过程(?,?,?)
    public static void callProl(String sql, String[] parameters){
        con = getConnection();
        try{
            cs = con.prepareCall(sql);
            if(parameters !=null ){
                for(int i =0; i<parameters.length; i++){
                    cs.setObject(i+1, parameters[i]);
                }
            }
            cs.execute();

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            close(rs, cs, con);
        }
    }

    public static void close(ResultSet rs, Statement ps, Connection con){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ps=null;
        }
        if(con !=null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            con =null;
        }
    }
}