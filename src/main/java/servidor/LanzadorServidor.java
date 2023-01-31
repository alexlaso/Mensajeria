package servidor;

public class LanzadorServidor {

    public static void main(String[] args) {

        int puerto = 1234;

        Servidor servidor = new Servidor(puerto);

        servidor.inicializar();

        servidor.close();

    }

}
