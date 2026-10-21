package controlador;

import modelo.Instalacion;

public class InstalacionController extends OrdenController {

    private final Instalacion instalacion;

    public InstalacionController(Instalacion instalacion) {
        super(instalacion);
        if (instalacion.getPuntos() <= 0) {
            throw new IllegalArgumentException("Los puntos a instalar deben ser positivos");
        }
        this.instalacion = instalacion;
    }

    public double horasFacturables() {
        double horasPuntos = instalacion.getPuntos() * 0.75;
        double mayor = instalacion.getHorasEstimadas();
        if (horasPuntos > mayor) {
            mayor = horasPuntos;
        }
        return Math.ceil(mayor);
    }

    @Override
    public double costo() {
        double base = 175;
        if (esMetropolitana()) {
            base = 100;
        }
        return base + 90 * horasFacturables();
    }

    @Override
    public int diasAtencion() {
        if (esMetropolitana()) {
            return 3;
        }
        return 5;
    }

    @Override
    public int maxVisitas() {
        return 3;
    }

    @Override
    public String categoria() {
        return "Instalacion";
    }

    @Override
    public String detalle() {
        return ", puntos " + instalacion.getPuntos();
    }
}
