
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
    private String numeroCuenta;

    public ServidorHilo(Socket sc, DataInputStream in, DataOutputStream out, String numeroCuenta) {
        this.sc = sc;
        this.in = in;
        this.out = out;
        this.numeroCuenta = numeroCuenta;
    }

    @Override
    public void run() {

        int opcion;
        boolean salir = false;
        
        //Cuenta acc = new Cuenta(123,1000);
        
        Conexion cx=new Conexion();
        Connection con=cx.conexion();
        Operaciones p = new Operaciones();

        out.writeUTF("Ingrese su numero de cuenta: ");
        String numeroCuenta = in.readUTF(); // Buscar si se encuentra registrada
        

        while (!salir) {

            try {
                opcion = in.readInt();
                switch (opcion) {
                    case 1:
                        double monto = in.readDouble();
                        out.writeUTF("Retirar " + monto + " de " + numeroCuenta);
                        p.retirar(numeroCuenta, monto);
                        out.writeUTF("Retiro exitoso");
                        break;
                    // realizar deposito
                    case 2:
                        double montoDeposito = in.readDouble();
                        out.writeUTF("Depositar " + montoDeposito + " en " + numeroCuenta);
                        p.depositar(numeroCuenta, montoDeposito);
                        out.writeUTF("Deposito exitoso");
                        break;
                    case 3:
                        double montoTransferencia = in.readDouble();
                        String numeroCuentaTransferencia = in.readUTF();
                        out.writeUTF("Transferencia de " + numeroCuenta + " a " + numeroCuentaTransferencia + " de " + montoTransferencia);
                        p.transferencia(numeroCuenta, numeroCuentaTransferencia, montoTransferencia);
                        out.writeUTF("Transferencia exitosa");
                        break;
                    case 4:
                        double montoTransferenciaOt = in.readDouble();
                        String numeroCuentaTransferenciaOt = in.readUTF();
                        break;
                    case 5:
                        String numeroCuentaB = in.readUTF();
                        break;
                    case 6:
                        String numeroCuentaC = in.readUTF();
                        break;
                    case 7:
                        salir = true;
                        break;

                    default:
                        out.writeUTF("Solo numero del 1 al 7");
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
        System.out.println("Conexion Cerrada con " + numeroCuenta);

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
