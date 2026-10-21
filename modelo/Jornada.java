package modelo;

import java.util.ArrayList;

public class Jornada {

    private String codigo;
    private Cuadrilla cuadrilla;
    private String estado;
    private ArrayList<String> conResultado;

    public Jornada(String codigo, Cuadrilla cuadrilla) {
        this.codigo = codigo;
        this.cuadrilla = cuadrilla;
        this.estado = "EN_PREPARACION";
        this.conResultado = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Cuadrilla getCuadrilla() {
        return cuadrilla;
    }

    public void setCuadrilla(Cuadrilla cuadrilla) {
        this.cuadrilla = cuadrilla;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ArrayList<String> getConResultado() {
        return conResultado;
    }

    public void setConResultado(ArrayList<String> conResultado) {
        this.conResultado = conResultado;
    }
}
