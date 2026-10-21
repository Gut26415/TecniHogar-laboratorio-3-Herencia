package controlador;

import modelo.InstalacionElectricaCertificada;

public class InstalacionElectricaController extends InstalacionController {

    private final InstalacionElectricaCertificada electrica;

    public InstalacionElectricaController(InstalacionElectricaCertificada electrica) {
        super(electrica);
        if (electrica.getValorMateriales() <= 0) {
            throw new IllegalArgumentException("El valor de los materiales debe ser positivo");
        }
        this.electrica = electrica;
    }

    @Override
    public double costo() {
        return super.costo() + 120 + electrica.getValorMateriales() * 0.05;
    }

    @Override
    public int diasAtencion() {
        return super.diasAtencion() + 2;
    }

    @Override
    public int maxVisitas() {
        return 2;
    }

    @Override
    public String categoria() {
        return "Instalacion electrica certificada";
    }

    @Override
    public String detalle() {
        return super.detalle() + ", materiales Q" + String.format("%.2f", electrica.getValorMateriales());
    }
}
