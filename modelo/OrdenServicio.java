package modelo;

public class OrdenServicio {

    private String codigo;
    private String cliente;
    private String direccion;
    private String zona;
    private double horasEstimadas;
    private String estado;
    private int visitas;

    public OrdenServicio(String codigo, String cliente, String direccion, String zona, double horasEstimadas) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.direccion = direccion;
        this.zona = zona;
        this.horasEstimadas = horasEstimadas;
        this.estado = "PENDIENTE";
        this.visitas = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public double getHorasEstimadas() {
        return horasEstimadas;
    }

    public void setHorasEstimadas(double horasEstimadas) {
        this.horasEstimadas = horasEstimadas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getVisitas() {
        return visitas;
    }

    public void setVisitas(int visitas) {
        this.visitas = visitas;
    }
}
