package controlador;

import modelo.RegistroIngresos;

public class IngresosController {

    private final RegistroIngresos registro;

    public IngresosController(RegistroIngresos registro) {
        this.registro = registro;
    }

    public RegistroIngresos getRegistro() {
        return registro;
    }

    public double getTotal() {
        return registro.getIngresoRegistro() + registro.getIngresoVisitas();
    }

    private void sumarCategoria(String categoria, double monto) {
        for (int i = 0; i < registro.getCategorias().size(); i++) {
            if (registro.getCategorias().get(i).equals(categoria)) {
                registro.getTotalesCategoria().set(i, registro.getTotalesCategoria().get(i) + monto);
                return;
            }
        }
        registro.getCategorias().add(categoria);
        registro.getTotalesCategoria().add(monto);
    }

    public void registrarOrden(String categoria, double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        registro.setIngresoRegistro(registro.getIngresoRegistro() + monto);
        sumarCategoria(categoria, monto);
    }

    public void registrarVisitaAdicional(String categoria, double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        registro.setIngresoVisitas(registro.getIngresoVisitas() + monto);
        sumarCategoria(categoria, monto);
    }
}
