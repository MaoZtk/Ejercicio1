//***********************************************************************
//Universidad del Valle de Guatemala
//Departamento de Ciencia de la Computación
//Autor: Marielos Ortiz, Sandra Pineda
//Carné: 23882, 231137
//CC2008 - 50
//Fecha: 19 de Agosto de 2023
//Descripción: Ejercicio 1 en parejas sobre venta de voletos para formula 1.
//El programa debe pedir información de nuevos compradores, identificar si pueden comprar un boleto,
//Mostrar las disponibilidades de las localidades y el reporte de caja total al final.
//https://www.discoduroderoer.es/como-hacer-un-menu-de-opciones-en-consola-en-java/
//https://www.w3schools.com/java/ref_string_indexof.asp
//https://www.geeksforgeeks.org/generating-random-numbers-in-java/
//************************************************************************
import java.util.Random;
import java.util.*;

/**
 * Clase que almacena informacion de las tres localidades
 */
class Localidad { 
    private int localidadID; 
    private String nombre; 
    private int capacidad; 
    private int boletosVendidos; 
    private double precio; 

     /**
     * Constructor de la clase Localidad.
     * @param localidadID Identificador único de la localidad.
     * @param nombre Nombre de la localidad.
     * @param capacidad Capacidad por localidad.
     * @param precio Precio de cada boleto por localidad.
     */

    public Localidad(int localidadID, String nombre, int capacidad, double precio) {
        this.localidadID = localidadID;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.boletosVendidos = 0;
        this.precio = precio;
    }

    public int getLocalidadID() {
        return localidadID;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getBoletosVendidos() {
        return boletosVendidos;
    }

    public double getPrecio() {
        return precio;
    }

     /**
     * Método para vender boletos de una localidad.
     * @param cantidad La cantidad de boletos a vender.
     */
    public void venderBoletos(int cantidad) { //Metodo que registra la venta de boletos 
        boletosVendidos += cantidad;
    }
}

/**
 * Clase que almacena los datos de usuario
 */
class Comprador { 
    private String nombre;
    private String dpi;
    private int cantidadBoletosDeseados;
    private double presupuestoMaximo;
    private int boletosComprados;

    public Comprador(String nombre, String dpi, int cantidadBoletosDeseados, double presupuestoMaximo) {
        this.nombre = nombre;
        this.dpi = dpi;
        this.cantidadBoletosDeseados = cantidadBoletosDeseados;
        this.presupuestoMaximo = presupuestoMaximo;
        this.boletosComprados = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDpi() {
        return dpi;
    }

    public int getCantidadBoletosDeseados() {
        return cantidadBoletosDeseados;
    }

    public double getPresupuestoMaximo() {
        return presupuestoMaximo;
    }

    public int getBoletosComprados() {
        return boletosComprados;
    }

    /**
     * Método para registrar una nueva compra 
     * @param nombre El nombre del comprador.
     * @param dpi El DPI del comprador.
     * @param cantidadBoletosDeseados La cantidad de boletos deseados por el comprador.
     * @param presupuestoMaximo El presupuesto máximo del comprador para la compra.
     */
    public void nuevaCompra(int cantidad, double precio) { 
        boletosComprados += cantidad;
        presupuestoMaximo -= cantidad * precio;
    }
}

/**
 * Clase principal la cual gestiona el sistema de compra
 */
class SistemaCompraBoleto { 
    Random random;
    private List<Localidad> localidades;
    private Comprador comprador;

    public SistemaCompraBoleto() { //Constructor que inicializa las localidades y genera numeros random
        random = new Random();
        localidades = new ArrayList<>();
        localidades.add(new Localidad(1, "Localidad 1", 20, 300.0));
        localidades.add(new Localidad(2, "Localidad 5", 20, 565.0));
        localidades.add(new Localidad(3, "Localidad 10", 20, 1495.0));
    }

    public void nuevoComprador(String nombre, String dpi, int cantidadBoletosDeseados, double presupuestoMaximo) {
        //Metodo en el cual se genera un nuevo comprador
        comprador = new Comprador(nombre, dpi, cantidadBoletosDeseados, presupuestoMaximo);
    }

    public void nuevaSolicitudBoletos() { 
        //Metodo para generar una nueva solicitud de compra de boletos
        if (comprador == null) {
            System.out.println("\nDebe crear un nuevo comprador primero.");
            return;
        }

        int ticket = random.nextInt(28000) + 1;
        int a = random.nextInt(15000) + 1;
        int b = random.nextInt(15000) + 1;

        if ((ticket + a + b) % 2 == 1) {
            int localidadIndex = random.nextInt(localidades.size());
            Localidad localidad = localidades.get(localidadIndex);

            if (localidad.getBoletosVendidos() < localidad.getCapacidad()) {
                int boletosDisponibles = Math.min(comprador.getCantidadBoletosDeseados(), localidad.getCapacidad() - localidad.getBoletosVendidos());
                double precioLocalidad = localidad.getPrecio();

                int boletosAVender = Math.min(boletosDisponibles, (int) (comprador.getPresupuestoMaximo() / precioLocalidad));

                if (boletosAVender > 0) {
                    localidad.venderBoletos(boletosAVender);
                    comprador.nuevaCompra(boletosAVender, precioLocalidad);
                    System.out.println("\n¡Compra exitosa! " + comprador.getNombre() + " ha comprado " + boletosAVender + " boletos en " + localidad.getNombre() + " por $" + (precioLocalidad * boletosAVender));
                } else {
                    System.out.println(comprador.getNombre() + "\nno tiene suficiente presupuesto para comprar boletos en " + localidad.getNombre());
                }
            } else {
                System.out.println(localidad.getNombre() + "\nestá agotada. Pruebe en otra localidad.");
            }
        } else {
            System.out.println("\nTicket no apto para compra.");
        }
    }

    public void consultarDisponibilidadTotal() {
        //Metodo que muestra la disponibilidad de los boletos en las tres localidades
        for (Localidad localidad : localidades) {
            System.out.println("En " + localidad.getNombre() + " se han vendido " + localidad.getBoletosVendidos() + " boletos. Quedan disponibles " + (localidad.getCapacidad() - localidad.getBoletosVendidos()) + " boletos.");
        }
    }

    public void consultarDisponibilidadIndividual(int localidadIndex) {
        //Metodo para mostrar disponibilidad por localidad
        if (localidadIndex >= 0 && localidadIndex < localidades.size()) {
            Localidad localidad = localidades.get(localidadIndex);
            System.out.println("En " + localidad.getNombre() + " se han vendido " + localidad.getBoletosVendidos() + " boletos. Quedan disponibles " + (localidad.getCapacidad() - localidad.getBoletosVendidos()) + " boletos.");
        } else {
            System.out.println("\nLocalidad no válida.");
        }
    }

    public void reporteCaja() {
        //Metodo que calcula el total de dinero 
        double totalRecaudado = 0.0;
        for (Localidad localidad : localidades) {
            totalRecaudado += localidad.getBoletosVendidos() * localidad.getPrecio();
        }
        System.out.println("\nTotal recaudado: $" + totalRecaudado);
    }
}

/**
 * Clase principal que contiene el método main para ejecutar el sistema.
 */
public class Systema_compra_boleto {
    public static void main(String[] args) {
        //Menu del sistema 
        SistemaCompraBoleto sistema = new SistemaCompraBoleto();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenú:");
            System.out.println("1. Nuevo comprador");
            System.out.println("2. Nueva solicitud de boletos");
            System.out.println("3. Consultar disponibilidad total");
            System.out.println("4. Consultar disponibilidad individual");
            System.out.println("5. Reporte de caja");
            System.out.println("6. Salir");
            System.out.print("\n Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("\nNombre del comprador: ");
                    scanner.nextLine(); // Consumir el salto de línea pendiente
                    String nombre = scanner.nextLine();
                    System.out.print("DPI del comprador: ");
                    String dpi = scanner.nextLine();
                    System.out.print("Cantidad de boletos deseados: ");
                    int cantidadBoletosDeseados = scanner.nextInt();
                    System.out.print("Presupuesto máximo: ");
                    double presupuestoMaximo = scanner.nextDouble();
                    sistema.nuevoComprador(nombre, dpi, cantidadBoletosDeseados, presupuestoMaximo);
                    break;

                case 2:
                    sistema.nuevaSolicitudBoletos();
                    break;

                case 3:
                    sistema.consultarDisponibilidadTotal();
                    break;

                case 4:
                    System.out.print("\n Ingrese el índice de la localidad (0 para Localidad 1, 1 para Localidad 5, 2 para Localidad 10): ");
                    int localidadIndex = scanner.nextInt();
                    sistema.consultarDisponibilidadIndividual(localidadIndex);
                    break;

                case 5:
                    sistema.reporteCaja();
                    break;

                case 6:
                    System.out.println("\n Gracias por visitarnos");
                    return;

                default:
                    System.out.println("\n Opción no válida. Intente de nuevo.");
            }
        }
    }
}
