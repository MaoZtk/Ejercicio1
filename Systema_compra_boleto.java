import java.util.*;

class Localidad {
    private int localidadID;
    private String nombre;
    private int capacidad;
    private int boletosVendidos;
    private double precio;

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

    public void venderBoletos(int cantidad) {
        boletosVendidos += cantidad;
    }
}

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

    public void nuevaCompra(int cantidad, double precio) {
        boletosComprados += cantidad;
        presupuestoMaximo -= cantidad * precio;
    }
}

class SistemaCompraBoleto {
    private Random randomGenerator;
    private List<Localidad> localidades;
    private Comprador comprador;

    public SistemaCompraBoleto() {
        randomGenerator = new Random();
        localidades = new ArrayList<>();
        localidades.add(new Localidad(1, "Localidad 1", 20, 300.0));
        localidades.add(new Localidad(2, "Localidad 5", 20, 565.0));
        localidades.add(new Localidad(3, "Localidad 10", 20, 1495.0));
    }

    public void nuevoComprador(String nombre, String dpi, int cantidadBoletosDeseados, double presupuestoMaximo) {
        comprador = new Comprador(nombre, dpi, cantidadBoletosDeseados, presupuestoMaximo);
    }

    public void nuevaSolicitudBoletos() {
        if (comprador == null) {
            System.out.println("Debe crear un nuevo comprador primero.");
            return;
        }

        int ticket = randomGenerator.nextInt(28000) + 1;
        int a = randomGenerator.nextInt(15000) + 1;
        int b = randomGenerator.nextInt(15000) + 1;

        if ((ticket + a + b) % 2 == 1) {
            int localidadIndex = randomGenerator.nextInt(localidades.size());
            Localidad localidad = localidades.get(localidadIndex);

            if (localidad.getBoletosVendidos() < localidad.getCapacidad()) {
                int boletosDisponibles = Math.min(comprador.getCantidadBoletosDeseados(), localidad.getCapacidad() - localidad.getBoletosVendidos());
                double precioLocalidad = localidad.getPrecio();

                int boletosAVender = Math.min(boletosDisponibles, (int) (comprador.getPresupuestoMaximo() / precioLocalidad));

                if (boletosAVender > 0) {
                    localidad.venderBoletos(boletosAVender);
                    comprador.nuevaCompra(boletosAVender, precioLocalidad);
                    System.out.println("¡Compra exitosa! " + comprador.getNombre() + " ha comprado " + boletosAVender + " boletos en " + localidad.getNombre() + " por $" + (precioLocalidad * boletosAVender));
                } else {
                    System.out.println(comprador.getNombre() + " no tiene suficiente presupuesto para comprar boletos en " + localidad.getNombre());
                }
            } else {
                System.out.println(localidad.getNombre() + " está agotada. Pruebe en otra localidad.");
            }
        } else {
            System.out.println("Ticket no apto para compra.");
        }
    }

    public void consultarDisponibilidadTotal() {
        for (Localidad localidad : localidades) {
            System.out.println("En " + localidad.getNombre() + " se han vendido " + localidad.getBoletosVendidos() + " boletos. Quedan disponibles " + (localidad.getCapacidad() - localidad.getBoletosVendidos()) + " boletos.");
        }
    }

    public void consultarDisponibilidadIndividual(int localidadIndex) {
        if (localidadIndex >= 0 && localidadIndex < localidades.size()) {
            Localidad localidad = localidades.get(localidadIndex);
            System.out.println("En " + localidad.getNombre() + " se han vendido " + localidad.getBoletosVendidos() + " boletos. Quedan disponibles " + (localidad.getCapacidad() - localidad.getBoletosVendidos()) + " boletos.");
        } else {
            System.out.println("Localidad no válida.");
        }
    }

    public void reporteCaja() {
        double totalRecaudado = 0.0;
        for (Localidad localidad : localidades) {
            totalRecaudado += localidad.getBoletosVendidos() * localidad.getPrecio();
        }
        System.out.println("Total recaudado: $" + totalRecaudado);
    }
}

public class Systema_compra_boleto {
    public static void main(String[] args) {
        SistemaCompraBoleto sistema = new SistemaCompraBoleto();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menú:");
            System.out.println("1. Nuevo comprador");
            System.out.println("2. Nueva solicitud de boletos");
            System.out.println("3. Consultar disponibilidad total");
            System.out.println("4. Consultar disponibilidad individual");
            System.out.println("5. Reporte de caja");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Nombre del comprador: ");
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
                    System.out.print("Ingrese el índice de la localidad (0 para Localidad 1, 1 para Localidad 5, 2 para Localidad 10): ");
                    int localidadIndex = scanner.nextInt();
                    sistema.consultarDisponibilidadIndividual(localidadIndex);
                    break;

                case 5:
                    sistema.reporteCaja();
                    break;

                case 6:
                    System.out.println("¡Hasta luego!");
                    return;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
}
