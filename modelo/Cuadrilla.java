package modelo;

public class Cuadrilla {

    private String codigo;
    private double capacidad;
    private boolean certificada;

    public Cuadrilla(String codigo, double capacidad, boolean certificada) {
        this.codigo = codigo;
        this.capacidad = capacidad;
        this.certificada = certificada;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(double capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isCertificada() {
        return certificada;
    }

    public void setCertificada(boolean certificada) {
        this.certificada = certificada;
    }
}
