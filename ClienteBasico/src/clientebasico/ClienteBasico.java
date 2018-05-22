package clientebasico;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import protocoloBasico.ProtocoloBasico;

public class ClienteBasico implements Runnable {

    public static void main(String[] args) {
        ClienteBasico a = new ClienteBasico();
        a.iniciar();
    }
    
    public void iniciar() {
        hiloCliente = new Thread(this);
        if(hiloCliente!=null){
            hiloCliente.start();
        }
    }
    
    @Override
    public void run() {           
        int puerto = ProtocoloBasico.puerto;
        String servidor = "localhost";
        try {
            skt = new Socket(servidor, puerto);

            salida = new PrintWriter(skt.getOutputStream());
            entrada =
                    new BufferedInputStream(skt.getInputStream());
            Scanner srv = new Scanner(entrada);

            Scanner mensaje = new Scanner(System.in);
            while (true) {
                String s = mensaje.nextLine();
                System.out.println("Enviando datos al servidor: " + s);
                salida.println(s);
                salida.flush();
                
                // Espera la respuesta..
                String c = srv.nextLine();
                System.out.println("Confirmación: " + c);
            }                        

        } catch (IOException e) {
            System.err.println("Ocurrio un error con la entrada de datos ... ");
        }catch (Exception e) {
            System.err.println(" Ocurrio un error con la respuesta del servidor ...");
        }
        finally{
            salida.close();
            try {
                entrada.close();
                skt.close();
            } catch (IOException ex) {
               System.err.println("Ocurrio un error con la entrada de datos ... "); 
            }
            System.out.println("Cliente - Conexión cerrada ...");
            
        }
    }   
    
    //Atributos
    private PrintWriter salida;
    private BufferedInputStream entrada;
    private Socket skt;
    private Thread hiloCliente;
}
