/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;
import java.sql.*;

/**
 *
 * @author pilina
 */
public class conexion {
    public static String bd="caracola";
    public static Connection conn;
    public static Statement sentencia;
    public static String user="root";
    public static String pass="";
    public static String url="jdbc:mysql://localhost/"+bd;
    public static String local_nube="";
    
    
    public static void conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(url, user, pass);
            if(conn!=null){

            }
        
            
            
            
        }
        catch (Exception ex) {
            System.out.println("Problema de Conexion");
            System.out.println(ex);
        }
        
        
        
    }
    public static void desconectar() throws SQLException{
        conn.close();
       
    }
}
