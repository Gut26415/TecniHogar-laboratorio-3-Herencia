package controlador;

import modelo.Cuadrilla;
import modelo.MantenimientoAire;

public class MantenimientoAireController extends OrdenController {

    private final MantenimientoAire mantenimiento;

    public MantenimientoAireController(MantenimientoAire mantenimiento) {
        super(mantenimiento);
        if (!esMetropolitana()) {
            throw new IllegalArgumentException("El mantenimiento de aire solo se acepta en zona metropolitana");
        }
        if (mantenimiento.getBtu() < 9000 || mantenimiento.getBtu() > 60000) {
            throw new IllegalArgumentException("Los BTU deben estar entre 9000 y 60000");
        }
        this.mantenimiento = mantenimiento;
    }

    @Override
    public double costo() {
        return 100 + 110 * mantenimiento.getHorasEstimadas() + 80;
    }

    @Override
    public int diasAtencion() {
        return 2;
    }

    @Override
    public int maxVisitas() {
        return 1;
    }

    @Override
    public String categoria() {
        return "Mantenimiento de aire acondicionado";
    }

    @Override
    public boolean puedeAtender(Cuadrilla cuadrilla) {
        return cuadrilla.isCertificada();
    }

    @Override
    public String detalle() {
        return ", BTU " + mantenimiento.getBtu();
    }
}
