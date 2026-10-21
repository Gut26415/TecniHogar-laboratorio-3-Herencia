package modelo;

public class Instalacion extends OrdenServicio {

    private int puntos;

    public Instalacion(String codigo, String cliente, String direccion, String zona, double horasEstimadas, int puntos) {
        super(codigo, cliente, direccion, zona, horasEstimadas);
        this.puntos = puntos;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
