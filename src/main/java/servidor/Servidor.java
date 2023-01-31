package servidor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Servidor {

    ServerSocket socketEspera;
    Socket socketCliente;
    private List<PrintWriter> escritores;
    PrintWriter writer;

    public Servidor(int puerto) {
        escritores = new LinkedList<>();
        try {
            socketEspera = new ServerSocket(puerto);
        } catch (IOException e) {
            System.out.println("Error al crear el servidor. Lo mas proable es que el puerto este ocupado.");
            System.exit(130);
        }
    }

    public void inicializar() {
        while (true) {
            procesoConexion();
            PrintWriter salidaCliente = getSalidaCliente();
            if (salidaCliente == null) continue;

            ConexionCliente conexion = getConexionCliente(salidaCliente);
            if (conexion == null){
                escritores.remove(salidaCliente);
                salidaCliente.close();
                continue;
            }

            lanzarConexion(conexion);
        }
    }

    private static void lanzarConexion(ConexionCliente conexion) {
        new Thread(conexion).start();
    }

    private ConexionCliente getConexionCliente(PrintWriter salidaCliente) {
        ConexionCliente conexion;
        try{
            conexion = new ConexionCliente(socketCliente.getInputStream(),escritores, salidaCliente);
        } catch (IOException e) {
            System.out.println("No se puede conseguir el canal de lectura.");
            return null;
        }
        return conexion;
    }

    private PrintWriter getSalidaCliente() {
        PrintWriter salidaCliente;
        try {
            salidaCliente = new PrintWriter(socketCliente.getOutputStream());
        } catch (IOException e) {
            System.out.println("No esta disponible el canal de escritura.");
            return null;
        }
        escritores.add(salidaCliente);
        return salidaCliente;
    }

    private void procesoConexion() {
        try {
            socketCliente = socketEspera.accept();
            pedirNombre();
        } catch (IOException e) {
            System.out.println("Error esperando clientes." + e.getMessage());
            System.exit(130);
        }

        System.out.println("Conexión establecida: " + socketCliente.getInetAddress() + ":" + socketCliente.getPort());
    }

    public void close() {
        for (PrintWriter escritor: escritores) {
            escritor.println("FIN DE LA CONEXIÓN...");
            escritor.close();
        }
        try {
            socketCliente.close();
            socketEspera.close();
        } catch (IOException e) {

        }
    }

    public void pedirNombre(){
    }
}
