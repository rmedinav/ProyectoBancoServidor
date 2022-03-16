
import conexion_BD.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Operaciones {

    Conexion cx = new Conexion();
    Connection con = cx.conexion();

    public void depositar(String c, double monto) throws SQLException {

        String[] registro = new String[3];

        try {

            String busca = "SELECT * FROM `tbbanco` WHERE Cuenta like '%" + c + "%'";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(busca);

            while (rs.next()) {
                registro[0] = rs.getString("Cuenta");
                registro[1] = rs.getString("Nombre y apellido");
                registro[2] = rs.getString("Monto actual");

            }

            String MYSQL = "UPDATE `tbbanco` SET `Cuenta`=?,`Nombre y apellido`=?,`Monto actual`=? WHERE cuenta=?";

            String oo = (String) registro[0];
            PreparedStatement pst = con.prepareStatement(MYSQL);

            pst.setString(1, c);
            pst.setString(2, registro[1]);

            double montoActual = Double.parseDouble(registro[2]);
            double respuesta = montoActual + monto;
            pst.setDouble(3, respuesta);

            pst.setString(4, oo);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Se ha depositado correctamente " + monto + " soles");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR deposito" + e.getMessage());
        }

    }

    public void retirar(String c, double monto) throws SQLException {

        String[] registro = new String[3];

        try {

            String busca = "SELECT * FROM `tbbanco` WHERE Cuenta like '%" + c + "%'";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(busca);

            while (rs.next()) {
                registro[0] = rs.getString("Cuenta");
                registro[1] = rs.getString("Nombre y apellido");
                registro[2] = rs.getString("Monto actual");

            }

            String MYSQL = "UPDATE `tbbanco` SET `Cuenta`=?,`Nombre y apellido`=?,`Monto actual`=? WHERE cuenta=?";

            String oo = (String) registro[0];
            PreparedStatement pst = con.prepareStatement(MYSQL);

            pst.setString(1, c);
            pst.setString(2, registro[1]);

            double montoActual = Double.parseDouble(registro[2]);
            double respuesta = montoActual - monto;
            pst.setDouble(3, respuesta);

            pst.setString(4, oo);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Se ha retirado correctamente " + monto + " soles");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR deposito" + e.getMessage());
        }
    }

}
