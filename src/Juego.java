import java.util.Scanner;

public class Juego {
    private final Configuracion conf;
    private final Scanner sc;

    private void mostrarMenu () {
        System.out.println("\nMENU: (0 PARA FINALIZAR)");
        System.out.println("1.  JUGAR");
        System.out.println("2.  MOSTRAR PALABRAS");
        System.out.println("3.  AÑADIR PALABRA");
        System.out.println("4.  ELIMINAR PALABRA");
        System.out.println("5.  CONFIGURACION");
        System.out.println("6.  USUARIOS");
    }

    private int seleccionarOpcion () {
        if (conf.getUsuario() != null) {
            System.out.printf("\n%s SELECCIONE OPCION: ", conf.getUsuario().getNombre().toUpperCase());
        } else {
            System.out.print("\nSELECCIONAR OPCION: ");
        }
        return sc.nextInt();
    }

    private void direccion (int op) {
        switch (op) {
            case 1 -> Funcionalidades.juego(sc, conf);
            case 2 -> Funcionalidades.mostrarPalabrasMenu(sc);
            case 3 -> {
                Funcionalidades.agregarPalabra(sc, conf.getFicheroPalabras());
                leerPalabras();
            }
            case 4 -> {
                Funcionalidades.eliminarPalabra(sc, conf.getFicheroPalabras(), FicheroPalabras.palabras);
                leerPalabras();
            }
            case 5 -> Funcionalidades.menuConfiguracion(sc, conf);
            case 6 -> Funcionalidades.menuUsuarios(sc, conf);
        }
    }

    private void leerPalabras (){
        FicheroPalabras.setPalabras(conf.getFicheroPalabras());
    }

    public Juego (String ficheroConfiguarcion){
        // DECLARACIONES
        conf = new Configuracion(ficheroConfiguarcion);
        sc = new Scanner(System.in);
        int opcion = -1;

        // INICIALIZA PALABRAS
        leerPalabras();

        if (FicheroPalabras.palabras != null)
            while (opcion != 0) {
                mostrarMenu();
                opcion = seleccionarOpcion();
                if (opcion != 0) direccion(opcion);
            }

        conf.actualizarConfiguracion();
    }
}
