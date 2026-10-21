package modelo;

public class InstalacionElectricaCertificada extends Instalacion {

    private double valorMateriales;

    public InstalacionElectricaCertificada(String codigo, String cliente, String direccion, String zona,
            double horasEstimadas, int puntos, double valorMateriales) {
        super(codigo, cliente, direccion, zona, horasEstimadas, puntos);
        this.valorMateriales = valorMateriales;
    }

    public double getValorMateriales() {
        return valorMateriales;
    }

    public void setValorMateriales(double valorMateriales) {
        this.valorMateriales = valorMateriales;
    }
}
