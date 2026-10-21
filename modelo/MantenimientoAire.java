package modelo;

public class MantenimientoAire extends OrdenServicio {

    private int btu;

    public MantenimientoAire(String codigo, String cliente, String direccion, String zona, double horasEstimadas, int btu) {
        super(codigo, cliente, direccion, zona, horasEstimadas);
        this.btu = btu;
    }

    public int getBtu() {
        return btu;
    }

    public void setBtu(int btu) {
        this.btu = btu;
    }
}
