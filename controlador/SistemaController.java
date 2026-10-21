package controlador;

import java.util.ArrayList;
import modelo.Cuadrilla;
import modelo.Diagnostico;
import modelo.Instalacion;
import modelo.InstalacionElectricaCertificada;
import modelo.Jornada;
import modelo.MantenimientoAire;
import modelo.RegistroIngresos;

public class SistemaController {

    private final ArrayList<OrdenController> ordenes;
    private final ArrayList<CuadrillaController> cuadrillas;
    private final ArrayList<JornadaController> jornadas;
    private final IngresosController ingresos;
    private final double cargoVisita;
    private final String[] estados;
    private int contadorJornadas;

    public SistemaController() {
        ordenes = new ArrayList<>();
        cuadrillas = new ArrayList<>();
        jornadas = new ArrayList<>();
        ingresos = new IngresosController(new RegistroIngresos());
        cargoVisita = 40;
        estados = new String[]{"PENDIENTE", "ASIGNADA", "EN_ATENCION", "COMPLETADA", "CANCELADA"};
        contadorJornadas = 0;
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        ordenes.add(new DiagnosticoController(new Diagnostico("D001", "Ana Lopez", "6a avenida 10-20 zona 1", "METROPOLITANA", 1.0)));
        ordenes.add(new DiagnosticoController(new Diagnostico("D002", "Carlos Perez", "Colonia El Milagro, Mixco", "PERIFERICA", 1.5)));
        ordenes.add(new InstalacionController(new Instalacion("I001", "Marta Gomez", "12 calle 5-30 zona 10", "METROPOLITANA", 2.5, 5)));
        ordenes.add(new InstalacionController(new Instalacion("I002", "Jose Ramirez", "Aldea San Cristobal, Mixco", "PERIFERICA", 4.0, 3)));
        ordenes.add(new InstalacionElectricaController(new InstalacionElectricaCertificada("E001", "Luis Morales", "7a avenida 3-45 zona 9", "METROPOLITANA", 2.5, 5, 2000)));
        ordenes.add(new InstalacionElectricaController(new InstalacionElectricaCertificada("E002", "Sofia Castillo", "Km 18 Carretera a El Salvador", "PERIFERICA", 3.0, 4, 1500)));
        ordenes.add(new MantenimientoAireController(new MantenimientoAire("M001", "Pedro Diaz", "4a calle 8-12 zona 14", "METROPOLITANA", 2.0, 12000)));
        ordenes.add(new MantenimientoAireController(new MantenimientoAire("M002", "Elena Ruiz", "18 avenida 15-60 zona 15", "METROPOLITANA", 1.5, 24000)));
        cuadrillas.add(new CuadrillaController(new Cuadrilla("C001", 8, true)));
        cuadrillas.add(new CuadrillaController(new Cuadrilla("C002", 6, false)));
        cuadrillas.add(new CuadrillaController(new Cuadrilla("C003", 5, false)));
    }

    public ArrayList<OrdenController> getOrdenes() {
        return ordenes;
    }

    public ArrayList<CuadrillaController> getCuadrillas() {
        return cuadrillas;
    }

    public ArrayList<JornadaController> getJornadas() {
        return jornadas;
    }

    public IngresosController getIngresos() {
        return ingresos;
    }

    public String[] getEstados() {
        return estados;
    }

    public OrdenController buscarOrden(String codigo) {
        for (int i = 0; i < ordenes.size(); i++) {
            if (ordenes.get(i).getCodigo().equals(codigo)) {
                return ordenes.get(i);
            }
        }
        return null;
    }

    public CuadrillaController buscarCuadrilla(String codigo) {
        for (int i = 0; i < cuadrillas.size(); i++) {
            if (cuadrillas.get(i).getCodigo().equals(codigo)) {
                return cuadrillas.get(i);
            }
        }
        return null;
    }

    public JornadaController buscarJornada(String codigo) {
        for (int i = 0; i < jornadas.size(); i++) {
            if (jornadas.get(i).getCodigo().equals(codigo)) {
                return jornadas.get(i);
            }
        }
        return null;
    }

    public void registrarOrden(OrdenController orden) {
        if (buscarOrden(orden.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe una orden con el codigo " + orden.getCodigo());
        }
        ordenes.add(orden);
        ingresos.registrarOrden(orden.categoria(), orden.costo());
    }

    public void registrarCuadrilla(CuadrillaController cuadrilla) {
        if (buscarCuadrilla(cuadrilla.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe una cuadrilla con el codigo " + cuadrilla.getCodigo());
        }
        cuadrillas.add(cuadrilla);
    }

    public JornadaController crearJornada(String codigoCuadrilla) {
        CuadrillaController cuadrilla = buscarCuadrilla(codigoCuadrilla);
        if (cuadrilla == null) {
            throw new IllegalArgumentException("No existe la cuadrilla " + codigoCuadrilla);
        }
        for (int i = 0; i < jornadas.size(); i++) {
            if (jornadas.get(i).getJornada().getCuadrilla().getCodigo().equals(codigoCuadrilla) && !jornadas.get(i).estaCerrada()) {
                throw new IllegalArgumentException("La cuadrilla " + codigoCuadrilla + " ya tiene la jornada "
                        + jornadas.get(i).getCodigo() + " sin cerrar");
            }
        }
        contadorJornadas = contadorJornadas + 1;
        JornadaController jornada = new JornadaController(new Jornada("J" + String.format("%03d", contadorJornadas), cuadrilla.getCuadrilla()));
        jornadas.add(jornada);
        return jornada;
    }

    public void asignarOrden(String codigoJornada, String codigoOrden) {
        JornadaController jornada = buscarJornada(codigoJornada);
        if (jornada == null) {
            throw new IllegalArgumentException("No existe la jornada " + codigoJornada);
        }
        OrdenController orden = buscarOrden(codigoOrden);
        if (orden == null) {
            throw new IllegalArgumentException("No existe la orden " + codigoOrden);
        }
        jornada.asignar(orden);
    }

    public void retirarOrden(String codigoJornada, String codigoOrden) {
        JornadaController jornada = buscarJornada(codigoJornada);
        if (jornada == null) {
            throw new IllegalArgumentException("No existe la jornada " + codigoJornada);
        }
        jornada.retirar(codigoOrden);
    }

    public int iniciarJornada(String codigoJornada) {
        JornadaController jornada = buscarJornada(codigoJornada);
        if (jornada == null) {
            throw new IllegalArgumentException("No existe la jornada " + codigoJornada);
        }
        jornada.iniciar();
        int cargos = 0;
        for (int i = 0; i < jornada.getOrdenes().size(); i++) {
            OrdenController orden = jornada.getOrdenes().get(i);
            if (orden.getVisitas() > 0) {
                ingresos.registrarVisitaAdicional(orden.categoria(), cargoVisita);
                cargos = cargos + 1;
            }
        }
        return cargos;
    }

    public void registrarResultado(String codigoJornada, String codigoOrden, boolean completada) {
        JornadaController jornada = buscarJornada(codigoJornada);
        if (jornada == null) {
            throw new IllegalArgumentException("No existe la jornada " + codigoJornada);
        }
        jornada.registrarResultado(codigoOrden, completada);
    }

    public ArrayList<String> categorias() {
        ArrayList<String> categorias = new ArrayList<>();
        for (int i = 0; i < ordenes.size(); i++) {
            if (!categorias.contains(ordenes.get(i).categoria())) {
                categorias.add(ordenes.get(i).categoria());
            }
        }
        return categorias;
    }

    public int[][] conteoOrdenes(ArrayList<String> categorias) {
        int[][] conteo = new int[categorias.size()][estados.length];
        for (int i = 0; i < categorias.size(); i++) {
            for (int j = 0; j < estados.length; j++) {
                for (int k = 0; k < ordenes.size(); k++) {
                    if (ordenes.get(k).categoria().equals(categorias.get(i)) && ordenes.get(k).getEstado().equals(estados[j])) {
                        conteo[i][j] = conteo[i][j] + 1;
                    }
                }
            }
        }
        return conteo;
    }

    public ArrayList<JornadaController> jornadasSinCerrar() {
        ArrayList<JornadaController> abiertas = new ArrayList<>();
        for (int i = 0; i < jornadas.size(); i++) {
            if (!jornadas.get(i).estaCerrada()) {
                abiertas.add(jornadas.get(i));
            }
        }
        return abiertas;
    }
}
