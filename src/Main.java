import java.util.*;
import java.sql.*;
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        setRollback();
    }
    public static void readRecords() throws SQLException, ClassNotFoundException{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String userName = "root";
        String pass = "Girish@080903";
        Connection con = DriverManager.getConnection(url, userName, pass);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("Select * from rankcard");
        Class.forName("com.mysql.cj.jdbc.Driver");
        while (rs.next()) {
            System.out.println("roll no " + rs.getInt(1));
            System.out.println("Student name " + rs.getString(2));
            System.out.println("Marks scored " + rs.getFloat(3));
        }
        con.close();
    }

    public static void insertRecords() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String userName = "root";
        String pass = "Girish@080903";
        Connection con = DriverManager.getConnection(url, userName, pass);
        Statement st = con.createStatement();
        int num = st.executeUpdate("Insert into rankcard values(4,'senthil',9.7)");
        System.out.println("Row "+num+" is added successfully");
    }

    public static void insertRecordsVar() throws SQLException, ClassNotFoundException{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String userName = "root";
        String pass = "Girish@080903";
        Connection con = DriverManager.getConnection(url, userName, pass);
        Statement st = con.createStatement();
        int n = 5;
        String na = "Radhika";
        float ra = 9.9F;
        int num = st.executeUpdate("Insert into rankcard values("+n+",'"+na+"',"+ra+")");
        System.out.println("Row "+num+" is added successfully");
    }

    public static void insertRecordsPst() throws SQLException, ClassNotFoundException{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String userName = "root";
        String pass = "Girish@080903";
        Connection con = DriverManager.getConnection(url, userName, pass);
        PreparedStatement pst = con.prepareStatement("insert into rankcard values(?,?,?)");
        pst.setInt(1,6);
        pst.setString(2,"Hero");
        pst.setFloat(3,6.8F);
        int num = pst.executeUpdate();
        System.out.println("Row "+num+" is added successfully");
    }

    public static void deleteRecords() throws SQLException, ClassNotFoundException{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String userName = "root";
        String pass = "Girish@080903";
        Connection con = DriverManager.getConnection(url, userName, pass);
        Statement st = con.createStatement();
        int num = st.executeUpdate("delete from rankcard where id=6");
        System.out.println("Row "+num+" is deleted successfully");
    }

    public static void updateRecords() throws SQLException, ClassNotFoundException{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String userName = "root";
        String pass = "Girish@080903";
        Connection con = DriverManager.getConnection(url, userName, pass);
        Statement st = con.createStatement();
        int num = st.executeUpdate("update rankcard set Gpa=8.3 where id=5");
        System.out.println("Row "+num+" is updated successfully");
    }

    public static void storedProcedure() throws SQLException, ClassNotFoundException{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String userName = "root";
        String pass = "Girish@080903";
        Connection con = DriverManager.getConnection(url, userName, pass);
        int id = 4;
        CallableStatement cst = con.prepareCall("{call getDetails()}");
        CallableStatement cst2 = con.prepareCall("{call getDetailsPara(?)}");
        CallableStatement cst3 = con.prepareCall("{call getDetailsParaOut(?,?)}");
        cst2.setInt(1,id);
        cst3.setInt(1,id);
        cst3.registerOutParameter(2, Types.VARCHAR);
        cst3.executeQuery();
        System.out.println(cst3.getString(2));
        ResultSet rs = cst.executeQuery();
        ResultSet rs2 = cst2.executeQuery();
        while (rs2.next()) {
            System.out.println("roll no " + rs2.getInt(1));
            System.out.println("Student name " + rs2.getString(2));
            System.out.println("Marks scored " + rs2.getFloat(3));
        }
    }

    public static void setCommit() throws SQLException, ClassNotFoundException{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String userName = "root";
        String pass = "Girish@080903";
        Connection con = DriverManager.getConnection(url, userName, pass);
        con.setAutoCommit(false);
        Statement st = con.createStatement();
        int num1 = st.executeUpdate("update rankcard set Gpa=8.5 where id=5");
        System.out.println("Row "+num1+" is updated successfully");
        int num2 = st.executeUpdate("update rankcard set Gpa=8.5 where id=4");
        System.out.println("Row "+num2+" is updated successfully");
        if(num1>0 && num2>0){
            con.commit();
        }
    }

    public static void setBatch() throws SQLException, ClassNotFoundException{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String userName = "root";
        String pass = "Girish@080903";
        Connection con = DriverManager.getConnection(url, userName, pass);
        String q1 = "update rankcard set Gpa=8.0 where id=1";
        String q2 = "update rankcard set Gpa=8.0 where id=2";
        String q3 = "update rankcard set Gpa=8.0 where id=3";
        Statement st = con.createStatement();
        st.addBatch(q1);
        st.addBatch(q2);
        st.addBatch(q3);
        int arr[] = st.executeBatch();
        for(int k:arr){
            System.out.println("Rows added:"+k);
        }
    }

    public static void setRollback() throws SQLException, ClassNotFoundException{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String userName = "root";
        String pass = "Girish@080903";
        Connection con = DriverManager.getConnection(url, userName, pass);
        String q1 = "update rankcard set Gpa=9.0 where id=1";
        String q2 = "update rankcard set Gpa=9.0 where id=2";
        String q3 = "update rankcard set Gpa=9.0 where id=3";
        Statement st = con.createStatement();
        con.setAutoCommit(false);
        st.addBatch(q1);
        st.addBatch(q2);
        st.addBatch(q3);
        int arr[] = st.executeBatch();
        for(int k:arr){
            if(k>0){
                continue;
            }
            else{
                con.rollback();
            }
        }
        con.commit();
        con.close();
    }
}