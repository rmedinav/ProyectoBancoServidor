/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    public static void main(String[] args) {
        
        
        try {
            ServerSocket server = new ServerSocket(50000);
            Socket sc;
            
            System.out.println("Servidor iniciado");
            while(true){
            
                // Espero la conexion del cliente
                sc = server.accept();
                
                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());
                
                // Pido al cliente el nombre al cliente
                out.writeUTF(">>> Bienvenido al Banco A <<<");
                out.writeUTF("Ingrese su numero de cuenta: ");
                String numeroCuenta = in.readUTF(); // Buscar si se encuentra registrada
                
                
                // Inicio el hilo
                ServidorHilo hilo = new ServidorHilo(sc,in, out, numeroCuenta);
                hilo.start();
               // hilo.join();
                
                
                System.out.println("Cliente " + numeroCuenta + " conectado");
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
