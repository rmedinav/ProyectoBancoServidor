
import conexion_BD.Conexion;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorHilo extends Thread {

    private Socket sc;
    private DataInputStream in;
    private DataOutputStream out;
    private String nombreCliente;

    public ServidorHilo(Socket sc, DataInputStream in, DataOutputStream out, String nombreCliente) {
        this.sc = sc;
        this.in = in;
        this.out = out;
        this.nombreCliente = nombreCliente;
    }

    @Override
    public void run() {

        int opcion;
        boolean salir = false;
        
        //Cuenta acc = new Cuenta(123,1000);
        
        Conexion cx=new Conexion();
        Connection con=cx.conexion();
        Operaciones p = new Operaciones();


        while (!salir) {

            try {
                opcion = in.readInt();
                switch (opcion) {
                    case 1:
                        //Recibo del ClienteHilo 003
                        String idCuenta = in.readUTF();
                        //Recibo del ClienteHilo 004
                        double monto = in.readDouble();
                        //Envio del Servidor 005
                        String tipoOP = in.readUTF();
                        out.writeUTF("Recibi " + idCuenta + " " + monto + " " + tipoOP);
                        System.out.println("Recibi " + idCuenta + " " + monto + " " + tipoOP);
                        
                        if( tipoOP.equals("D")){
                            p.depositar(idCuenta,monto);
                        }
                        else{
                            p.retirar(idCuenta, monto);
                        }
                        //executeTransacType(acc,tipoOP,monto);
                        

                     
                        break;

                    case 6:
                        salir = true;
                        break;

                    default:
                        out.writeUTF("Solo numero del 1 al 6");
                }

            } catch (IOException ex) {
                Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
            } /*catch (InterruptedException ex) {
                Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
            }*/

        }
        try {
            sc.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Conexion Cerrada con " + nombreCliente);

    }

    public synchronized void executeTransacType(Cuenta acc, String op, double monto) throws InterruptedException {

        System.out.println("a:" + acc.getBalance());
        Thread T = new Thread(new Transaccion(acc, op, monto));
        T.setName("T");
        T.start();
        T.join();
        System.out.println(acc.getUserID() + " " + acc.getBalance());
        System.out.println("------------------------");

    }
    

}
