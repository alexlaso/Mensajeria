public class Mensaje {
    String remitente, texto;

    public Mensaje(String remitente, String texto) {
        this.remitente = remitente;
        this.texto = texto;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "remitente='" + remitente + '\'' +
                ", texto='" + texto + '\'' +
                '}';
    }
}
