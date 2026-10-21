package modelo;

import java.util.ArrayList;

public class RegistroIngresos {

    private double ingresoRegistro;
    private double ingresoVisitas;
    private ArrayList<String> categorias;
    private ArrayList<Double> totalesCategoria;

    public RegistroIngresos() {
        this.ingresoRegistro = 0;
        this.ingresoVisitas = 0;
        this.categorias = new ArrayList<>();
        this.totalesCategoria = new ArrayList<>();
    }

    public double getIngresoRegistro() {
        return ingresoRegistro;
    }

    public void setIngresoRegistro(double ingresoRegistro) {
        this.ingresoRegistro = ingresoRegistro;
    }

    public double getIngresoVisitas() {
        return ingresoVisitas;
    }

    public void setIngresoVisitas(double ingresoVisitas) {
        this.ingresoVisitas = ingresoVisitas;
    }

    public ArrayList<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(ArrayList<String> categorias) {
        this.categorias = categorias;
    }

    public ArrayList<Double> getTotalesCategoria() {
        return totalesCategoria;
    }

    public void setTotalesCategoria(ArrayList<Double> totalesCategoria) {
        this.totalesCategoria = totalesCategoria;
    }
}
