package controlador;

import java.util.ArrayList;
import modelo.Jornada;

public class JornadaController {

    private final Jornada jornada;
    private final ArrayList<OrdenController> ordenes;

    public JornadaController(Jornada jornada) {
        if (jornada.getCodigo() == null || jornada.getCodigo().trim().equals("")) {
            throw new IllegalArgumentException("El codigo de la jornada no puede estar vacio");
        }
        if (jornada.getCuadrilla() == null) {
            throw new IllegalArgumentException("La jornada necesita una cuadrilla");
        }
        this.jornada = jornada;
        this.ordenes = new ArrayList<>();
    }

    public Jornada getJornada() {
        return jornada;
    }

    public ArrayList<OrdenController> getOrdenes() {
        return ordenes;
    }

    public String getCodigo() {
        return jornada.getCodigo();
    }

    public String getEstado() {
        return jornada.getEstado();
    }

    public boolean estaCerrada() {
        return jornada.getEstado().equals("CERRADA");
    }

    public double horasAsignadas() {
        double total = 0;
        for (int i = 0; i < ordenes.size(); i++) {
            total = total + ordenes.get(i).getHoras();
        }
        return total;
    }

    public double capacidadDisponible() {
        return jornada.getCuadrilla().getCapacidad() - horasAsignadas();
    }

    private OrdenController buscarOrden(String codigoOrden) {
        for (int i = 0; i < ordenes.size(); i++) {
            if (ordenes.get(i).getCodigo().equals(codigoOrden)) {
                return ordenes.get(i);
            }
        }
        return null;
    }

    public void asignar(OrdenController orden) {
        if (!jornada.getEstado().equals("EN_PREPARACION")) {
            throw new IllegalArgumentException("Solo se asignan ordenes a jornadas en preparacion");
        }
        if (!orden.getEstado().equals("PENDIENTE")) {
            throw new IllegalArgumentException("La orden " + orden.getCodigo() + " no esta pendiente, esta " + orden.getEstado());
        }
        if (!orden.puedeAtender(jornada.getCuadrilla())) {
            throw new IllegalArgumentException("La cuadrilla " + jornada.getCuadrilla().getCodigo()
                    + " no es compatible con la orden " + orden.getCodigo());
        }
        if (horasAsignadas() + orden.getHoras() > jornada.getCuadrilla().getCapacidad()) {
            throw new IllegalArgumentException("La orden excede la capacidad de la cuadrilla, disponible "
                    + capacidadDisponible() + " horas");
        }
        orden.asignar();
        ordenes.add(orden);
    }

    public void retirar(String codigoOrden) {
        if (!jornada.getEstado().equals("EN_PREPARACION")) {
            throw new IllegalArgumentException("Solo se retiran ordenes de jornadas en preparacion");
        }
        OrdenController orden = buscarOrden(codigoOrden);
        if (orden == null) {
            throw new IllegalArgumentException("La orden " + codigoOrden + " no pertenece a la jornada " + jornada.getCodigo());
        }
        orden.retirar();
        ordenes.remove(orden);
    }

    public void iniciar() {
        if (!jornada.getEstado().equals("EN_PREPARACION")) {
            throw new IllegalArgumentException("La jornada " + jornada.getCodigo() + " no esta en preparacion, esta "
                    + jornada.getEstado());
        }
        if (ordenes.size() == 0) {
            throw new IllegalArgumentException("No se puede iniciar una jornada vacia");
        }
        for (int i = 0; i < ordenes.size(); i++) {
            ordenes.get(i).iniciarAtencion();
        }
        jornada.setEstado("EN_CURSO");
    }

    public void registrarResultado(String codigoOrden, boolean completada) {
        if (!jornada.getEstado().equals("EN_CURSO")) {
            throw new IllegalArgumentException("La jornada " + jornada.getCodigo() + " no esta en curso, esta "
                    + jornada.getEstado());
        }
        OrdenController orden = buscarOrden(codigoOrden);
        if (orden == null) {
            throw new IllegalArgumentException("La orden " + codigoOrden + " no pertenece a la jornada " + jornada.getCodigo());
        }
        if (jornada.getConResultado().contains(codigoOrden)) {
            throw new IllegalArgumentException("La orden " + codigoOrden + " ya tiene resultado en esta jornada");
        }
        if (completada) {
            orden.completar();
        } else {
            orden.fallar();
        }
        jornada.getConResultado().add(codigoOrden);
        if (jornada.getConResultado().size() == ordenes.size()) {
            jornada.setEstado("CERRADA");
        }
    }
}
