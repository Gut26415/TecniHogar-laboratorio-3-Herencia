package controlador;

import modelo.Cuadrilla;
import modelo.OrdenServicio;

public abstract class OrdenController {

    private final OrdenServicio orden;

    public OrdenController(OrdenServicio orden) {
        if (orden.getCodigo() == null || orden.getCodigo().trim().equals("")) {
            throw new IllegalArgumentException("El codigo de la orden no puede estar vacio");
        }
        if (orden.getCliente() == null || orden.getCliente().trim().equals("")) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacio");
        }
        if (orden.getDireccion() == null || orden.getDireccion().trim().equals("")) {
            throw new IllegalArgumentException("La direccion no puede estar vacia");
        }
        if (!orden.getZona().equals("METROPOLITANA") && !orden.getZona().equals("PERIFERICA")) {
            throw new IllegalArgumentException("La zona debe ser metropolitana o periferica");
        }
        if (orden.getHorasEstimadas() <= 0) {
            throw new IllegalArgumentException("Las horas estimadas deben ser positivas");
        }
        orden.setCodigo(orden.getCodigo().trim());
        orden.setCliente(orden.getCliente().trim());
        orden.setDireccion(orden.getDireccion().trim());
        this.orden = orden;
    }

    public OrdenServicio getOrden() {
        return orden;
    }

    public String getCodigo() {
        return orden.getCodigo();
    }

    public String getEstado() {
        return orden.getEstado();
    }

    public int getVisitas() {
        return orden.getVisitas();
    }

    public double getHoras() {
        return orden.getHorasEstimadas();
    }

    public boolean esMetropolitana() {
        return orden.getZona().equals("METROPOLITANA");
    }

    public abstract double costo();

    public abstract int diasAtencion();

    public abstract int maxVisitas();

    public abstract String categoria();

    public String detalle() {
        return "";
    }

    public boolean puedeAtender(Cuadrilla cuadrilla) {
        return true;
    }

    public void asignar() {
        if (!orden.getEstado().equals("PENDIENTE")) {
            throw new IllegalArgumentException("La orden " + orden.getCodigo() + " no esta pendiente, esta " + orden.getEstado());
        }
        orden.setEstado("ASIGNADA");
    }

    public void retirar() {
        if (!orden.getEstado().equals("ASIGNADA")) {
            throw new IllegalArgumentException("La orden " + orden.getCodigo() + " no esta asignada, esta " + orden.getEstado());
        }
        orden.setEstado("PENDIENTE");
    }

    public void iniciarAtencion() {
        if (!orden.getEstado().equals("ASIGNADA")) {
            throw new IllegalArgumentException("La orden " + orden.getCodigo() + " no esta asignada, esta " + orden.getEstado());
        }
        orden.setEstado("EN_ATENCION");
    }

    public void completar() {
        if (!orden.getEstado().equals("EN_ATENCION")) {
            throw new IllegalArgumentException("La orden " + orden.getCodigo() + " no esta en atencion, esta " + orden.getEstado());
        }
        orden.setVisitas(orden.getVisitas() + 1);
        orden.setEstado("COMPLETADA");
    }

    public void fallar() {
        if (!orden.getEstado().equals("EN_ATENCION")) {
            throw new IllegalArgumentException("La orden " + orden.getCodigo() + " no esta en atencion, esta " + orden.getEstado());
        }
        orden.setVisitas(orden.getVisitas() + 1);
        if (orden.getVisitas() >= maxVisitas()) {
            orden.setEstado("CANCELADA");
        } else {
            orden.setEstado("PENDIENTE");
        }
    }
}
