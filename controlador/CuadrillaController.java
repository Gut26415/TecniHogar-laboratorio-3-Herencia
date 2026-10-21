package controlador;

import modelo.Cuadrilla;

public class CuadrillaController {

    private final Cuadrilla cuadrilla;

    public CuadrillaController(Cuadrilla cuadrilla) {
        if (cuadrilla.getCodigo() == null || cuadrilla.getCodigo().trim().equals("")) {
            throw new IllegalArgumentException("El codigo de la cuadrilla no puede estar vacio");
        }
        if (cuadrilla.getCapacidad() <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser positiva");
        }
        cuadrilla.setCodigo(cuadrilla.getCodigo().trim());
        this.cuadrilla = cuadrilla;
    }

    public Cuadrilla getCuadrilla() {
        return cuadrilla;
    }

    public String getCodigo() {
        return cuadrilla.getCodigo();
    }
}
