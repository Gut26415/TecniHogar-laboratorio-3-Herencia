package vista;

import java.util.ArrayList;
import controlador.CuadrillaController;
import controlador.IngresosController;
import controlador.JornadaController;
import controlador.OrdenController;

public class Vista {

    public void mostrarMenu() {
        System.out.println();
        System.out.println("===== TECNIHOGAR =====");
        System.out.println("1. Cotizar y registrar orden");
        System.out.println("2. Registrar cuadrilla");
        System.out.println("3. Crear jornada");
        System.out.println("4. Asignar orden a jornada");
        System.out.println("5. Retirar orden de jornada");
        System.out.println("6. Iniciar jornada");
        System.out.println("7. Registrar resultado de visita");
        System.out.println("8. Consultar ordenes");
        System.out.println("9. Consultar cuadrillas");
        System.out.println("10. Reportes");
        System.out.println("0. Salir");
    }

    public void mostrarMenuCategorias() {
        System.out.println("1. Diagnostico");
        System.out.println("2. Instalacion");
        System.out.println("3. Instalacion electrica certificada");
        System.out.println("4. Mantenimiento de aire acondicionado");
    }

    public void mostrarMenuReportes() {
        System.out.println("1. Ordenes por categoria y estado");
        System.out.println("2. Ingresos");
        System.out.println("3. Jornadas sin cerrar");
    }

    public void pedir(String mensaje) {
        System.out.print(mensaje);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String mensaje) {
        System.out.println("Error: " + mensaje);
    }

    public void mostrarCotizacion(OrdenController orden) {
        System.out.println("--- Cotizacion ---");
        System.out.println("Categoria: " + orden.categoria());
        System.out.println("Costo: Q" + String.format("%.2f", orden.costo()));
        System.out.println("Dias habiles de atencion: " + orden.diasAtencion());
        System.out.println("Visitas maximas: " + orden.maxVisitas());
    }

    public void mostrarOrdenes(ArrayList<OrdenController> ordenes) {
        for (int i = 0; i < ordenes.size(); i++) {
            OrdenController o = ordenes.get(i);
            System.out.println(o.categoria() + " " + o.getCodigo() + ", cliente " + o.getOrden().getCliente()
                    + ", direccion " + o.getOrden().getDireccion() + ", zona " + o.getOrden().getZona()
                    + ", horas estimadas " + o.getHoras() + ", estado " + o.getEstado()
                    + ", visitas " + o.getVisitas() + "/" + o.maxVisitas()
                    + ", costo Q" + String.format("%.2f", o.costo())
                    + ", dias de atencion " + o.diasAtencion() + o.detalle());
        }
    }

    public void mostrarCuadrillas(ArrayList<CuadrillaController> cuadrillas) {
        for (int i = 0; i < cuadrillas.size(); i++) {
            System.out.println("Cuadrilla " + cuadrillas.get(i).getCodigo()
                    + ", capacidad " + cuadrillas.get(i).getCuadrilla().getCapacidad()
                    + " horas, certificada en refrigeracion: " + cuadrillas.get(i).getCuadrilla().isCertificada());
        }
    }

    public void mostrarReporteOrdenes(ArrayList<String> categorias, String[] estados, int[][] conteo) {
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println(categorias.get(i));
            for (int j = 0; j < estados.length; j++) {
                System.out.println("    " + estados[j] + ": " + conteo[i][j]);
            }
        }
    }

    public void mostrarReporteIngresos(IngresosController ingresos) {
        System.out.println("Ingresos por registro: Q" + String.format("%.2f", ingresos.getRegistro().getIngresoRegistro()));
        System.out.println("Ingresos por visitas adicionales: Q" + String.format("%.2f", ingresos.getRegistro().getIngresoVisitas()));
        System.out.println("Ingreso total: Q" + String.format("%.2f", ingresos.getTotal()));
        System.out.println("Ingreso por categoria:");
        for (int i = 0; i < ingresos.getRegistro().getCategorias().size(); i++) {
            System.out.println("  " + ingresos.getRegistro().getCategorias().get(i) + ": Q"
                    + String.format("%.2f", ingresos.getRegistro().getTotalesCategoria().get(i)));
        }
    }

    public void mostrarJornadas(ArrayList<JornadaController> jornadas) {
        for (int i = 0; i < jornadas.size(); i++) {
            JornadaController j = jornadas.get(i);
            String lista = "";
            for (int k = 0; k < j.getOrdenes().size(); k++) {
                lista = lista + " " + j.getOrdenes().get(k).getCodigo() + "(" + j.getOrdenes().get(k).getEstado() + ")";
            }
            System.out.println("Jornada " + j.getCodigo() + ", cuadrilla " + j.getJornada().getCuadrilla().getCodigo()
                    + ", estado " + j.getEstado() + ", horas asignadas " + j.horasAsignadas()
                    + ", capacidad disponible " + j.capacidadDisponible() + ", ordenes:" + lista);
        }
    }
}
