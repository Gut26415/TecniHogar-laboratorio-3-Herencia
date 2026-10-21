import java.util.ArrayList;
import java.util.Scanner;
import controlador.CuadrillaController;
import controlador.DiagnosticoController;
import controlador.InstalacionController;
import controlador.InstalacionElectricaController;
import controlador.JornadaController;
import controlador.MantenimientoAireController;
import controlador.OrdenController;
import controlador.SistemaController;
import modelo.Cuadrilla;
import modelo.Diagnostico;
import modelo.Instalacion;
import modelo.InstalacionElectricaCertificada;
import modelo.MantenimientoAire;
import vista.Vista;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Vista vista = new Vista();

    private static String leerTexto(String mensaje) {
        vista.pedir(mensaje);
        return scanner.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            vista.pedir(mensaje);
            String linea = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                vista.mostrarMensaje("Entrada invalida, escriba un numero entero");
            }
        }
    }

    private static double leerDecimal(String mensaje) {
        while (true) {
            vista.pedir(mensaje);
            String linea = scanner.nextLine().trim();
            try {
                double valor = Double.parseDouble(linea);
                if (Double.isNaN(valor) || Double.isInfinite(valor)) {
                    vista.mostrarMensaje("Entrada invalida, escriba un numero");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                vista.mostrarMensaje("Entrada invalida, escriba un numero");
            }
        }
    }

    public static void main(String[] args) {
        SistemaController sistema = new SistemaController();
        int opcion = -1;
        while (opcion != 0) {
            vista.mostrarMenu();
            opcion = leerEntero("Opcion: ");

            if (opcion == 1) {
                vista.mostrarMenuCategorias();
                int categoria = leerEntero("Categoria: ");
                if (categoria < 1 || categoria > 4) {
                    vista.mostrarError("categoria no valida");
                } else {
                    String codigo = leerTexto("Codigo: ");
                    String cliente = leerTexto("Cliente: ");
                    String direccion = leerTexto("Direccion: ");
                    int opcionZona = leerEntero("Zona (1. metropolitana, 2. periferica): ");
                    String zona = "INVALIDA";
                    if (opcionZona == 1) {
                        zona = "METROPOLITANA";
                    } else if (opcionZona == 2) {
                        zona = "PERIFERICA";
                    }
                    double horas = leerDecimal("Horas estimadas: ");
                    OrdenController orden = null;
                    try {
                        if (categoria == 1) {
                            orden = new DiagnosticoController(new Diagnostico(codigo, cliente, direccion, zona, horas));
                        } else if (categoria == 2) {
                            int puntos = leerEntero("Puntos a instalar: ");
                            orden = new InstalacionController(new Instalacion(codigo, cliente, direccion, zona, horas, puntos));
                        } else if (categoria == 3) {
                            int puntos = leerEntero("Puntos a instalar: ");
                            double materiales = leerDecimal("Valor declarado de materiales: Q");
                            orden = new InstalacionElectricaController(new InstalacionElectricaCertificada(codigo, cliente, direccion, zona, horas, puntos, materiales));
                        } else {
                            int btu = leerEntero("Capacidad en BTU: ");
                            orden = new MantenimientoAireController(new MantenimientoAire(codigo, cliente, direccion, zona, horas, btu));
                        }
                    } catch (IllegalArgumentException e) {
                        vista.mostrarError(e.getMessage());
                    }
                    if (orden != null) {
                        vista.mostrarCotizacion(orden);
                        String respuesta = leerTexto("Desea registrar la orden? (s/n): ");
                        if (respuesta.equalsIgnoreCase("s")) {
                            try {
                                sistema.registrarOrden(orden);
                                vista.mostrarMensaje("Orden registrada, se cobro Q" + String.format("%.2f", orden.costo()));
                            } catch (IllegalArgumentException e) {
                                vista.mostrarError(e.getMessage());
                            }
                        } else {
                            vista.mostrarMensaje("Cotizacion terminada, no se registro nada");
                        }
                    }
                }

            } else if (opcion == 2) {
                String codigo = leerTexto("Codigo de la cuadrilla: ");
                double capacidad = leerDecimal("Capacidad en horas: ");
                String respuesta = leerTexto("Certificada en refrigeracion? (s/n): ");
                try {
                    sistema.registrarCuadrilla(new CuadrillaController(new Cuadrilla(codigo, capacidad, respuesta.equalsIgnoreCase("s"))));
                    vista.mostrarMensaje("Cuadrilla registrada");
                } catch (IllegalArgumentException e) {
                    vista.mostrarError(e.getMessage());
                }

            } else if (opcion == 3) {
                String codigoCuadrilla = leerTexto("Codigo de la cuadrilla: ");
                try {
                    JornadaController jornada = sistema.crearJornada(codigoCuadrilla);
                    vista.mostrarMensaje("Jornada creada con codigo " + jornada.getCodigo());
                } catch (IllegalArgumentException e) {
                    vista.mostrarError(e.getMessage());
                }

            } else if (opcion == 4) {
                String codigoJornada = leerTexto("Codigo de la jornada: ");
                String codigoOrden = leerTexto("Codigo de la orden: ");
                try {
                    sistema.asignarOrden(codigoJornada, codigoOrden);
                    vista.mostrarMensaje("Orden asignada");
                } catch (IllegalArgumentException e) {
                    vista.mostrarError(e.getMessage());
                }

            } else if (opcion == 5) {
                String codigoJornada = leerTexto("Codigo de la jornada: ");
                String codigoOrden = leerTexto("Codigo de la orden: ");
                try {
                    sistema.retirarOrden(codigoJornada, codigoOrden);
                    vista.mostrarMensaje("Orden retirada, vuelve a quedar pendiente");
                } catch (IllegalArgumentException e) {
                    vista.mostrarError(e.getMessage());
                }

            } else if (opcion == 6) {
                String codigoJornada = leerTexto("Codigo de la jornada: ");
                try {
                    int cargos = sistema.iniciarJornada(codigoJornada);
                    vista.mostrarMensaje("Jornada iniciada, cargos por visita adicional: " + cargos);
                } catch (IllegalArgumentException e) {
                    vista.mostrarError(e.getMessage());
                }

            } else if (opcion == 7) {
                String codigoJornada = leerTexto("Codigo de la jornada: ");
                String codigoOrden = leerTexto("Codigo de la orden: ");
                int resultado = leerEntero("Resultado (1. completada, 2. visita fallida): ");
                if (resultado != 1 && resultado != 2) {
                    vista.mostrarError("resultado no valido");
                } else {
                    try {
                        sistema.registrarResultado(codigoJornada, codigoOrden, resultado == 1);
                        vista.mostrarMensaje("Resultado registrado, la orden queda " + sistema.buscarOrden(codigoOrden).getEstado()
                                + " y la jornada " + sistema.buscarJornada(codigoJornada).getEstado());
                    } catch (IllegalArgumentException e) {
                        vista.mostrarError(e.getMessage());
                    }
                }

            } else if (opcion == 8) {
                vista.mostrarOrdenes(sistema.getOrdenes());

            } else if (opcion == 9) {
                vista.mostrarCuadrillas(sistema.getCuadrillas());

            } else if (opcion == 10) {
                vista.mostrarMenuReportes();
                int reporte = leerEntero("Reporte: ");
                if (reporte == 1) {
                    ArrayList<String> categorias = sistema.categorias();
                    vista.mostrarReporteOrdenes(categorias, sistema.getEstados(), sistema.conteoOrdenes(categorias));
                } else if (reporte == 2) {
                    vista.mostrarReporteIngresos(sistema.getIngresos());
                } else if (reporte == 3) {
                    vista.mostrarJornadas(sistema.jornadasSinCerrar());
                } else {
                    vista.mostrarError("opcion no valida");
                }

            } else if (opcion == 0) {
                vista.mostrarMensaje("Hasta luego");

            } else {
                vista.mostrarError("opcion no valida");
            }
        }
    }
}
