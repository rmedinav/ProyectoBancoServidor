
package conexion_BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.text.BadLocationException;

public class Conexion {
       Connection con;        
    public Connection conexion() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3307/bdbanco","root","");
            System.out.println("[Servidor BD] Conexion establecida");
        }catch(Exception  e){
           System.out.println("[Servidor BD] NO se pudo iniciar la Base de Datos " + e);
        }
       return con;
    }  
}
