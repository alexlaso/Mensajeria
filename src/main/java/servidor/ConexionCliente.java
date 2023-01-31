package servidor;

import java.io.*;
import java.util.List;

public class ConexionCliente implements Runnable{

    BufferedReader lectorCliente;
    List<PrintWriter> escritores;
    PrintWriter escritorActual;
    String datosTransferidos;
    String nombre="";

    public ConexionCliente(InputStream inputStream, List<PrintWriter> escritores, PrintWriter salidaCliente) {
        this.lectorCliente = new BufferedReader(new InputStreamReader(inputStream));
        this.escritores = escritores;
        this.escritorActual = salidaCliente;
    }

    @Override
    public void run() {
        while (true){
            if (!lecturaBufferEntrada()) {
                break;
            } else {
                if (nombre.equals("")){
                }
                enviarDatos();
            }
        }
        operacionCerrado();
    }

    private void operacionCerrado() {
        try {
            escritorActual.println("FIN DE LA CONEXIÓN.");
            escritores.remove(escritorActual);
            escritorActual.close();
            lectorCliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Fin de la conexion de entrada.");
    }

    private void enviarDatos() {
        for (PrintWriter escritor: escritores) {
            if (!escritor.equals(escritorActual)){
                escritor.println("HEY");
                escritor.println(datosTransferidos);
                if (escritor.checkError()){
                    System.out.println("Error al escribir la ultima linea.");
                    escritor.close();
                }
            }else {
                escritor.flush();
            }
        }
        System.out.println(datosTransferidos);
    }

    private boolean lecturaBufferEntrada() {
        try{
            datosTransferidos = lectorCliente.readLine();
        } catch (IOException e) {
            System.out.println("Error en la ultima linea.");
            return false;
        }
        if (datosTransferidos == null) {
            return false;
        }
        return true;
    }
}
