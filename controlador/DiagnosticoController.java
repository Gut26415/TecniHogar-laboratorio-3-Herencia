package controlador;

import modelo.Diagnostico;

public class DiagnosticoController extends OrdenController {

    public DiagnosticoController(Diagnostico diagnostico) {
        super(diagnostico);
        if (diagnostico.getHorasEstimadas() > 1.5) {
            throw new IllegalArgumentException("Un diagnostico no puede superar 1.5 horas estimadas");
        }
    }

    @Override
    public double costo() {
        if (esMetropolitana()) {
            return 150;
        }
        return 225;
    }

    @Override
    public int diasAtencion() {
        if (esMetropolitana()) {
            return 1;
        }
        return 2;
    }

    @Override
    public int maxVisitas() {
        return 3;
    }

    @Override
    public String categoria() {
        return "Diagnostico";
    }
}
