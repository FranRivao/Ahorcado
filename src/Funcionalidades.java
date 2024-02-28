import java.util.Scanner;

public class Funcionalidades {
    // FUNCIONALIDAD JUEGO
    public static void juego (Scanner sc, Configuracion conf) {
        final String PALABRA = seleccionarPalabra(conf.getFicheroPalabras());
        int vidas = conf.getVidas(), contador = 0;
        char [] letrasObtenidas = new char[PALABRA.length()];
        boolean cancelar = false;
        // TEMA
        System.out.println("TEMA: " + conf.getFicheroPalabras().split("\\.")[0].toUpperCase());

        do {
            // PEDIR LETRA
            System.out.println("CANTIDAD DE VIDAS: " + vidas);
            System.out.print("INGRESE LETRA (");
            mostrarLetrasActuales(letrasObtenidas);
            System.out.print("): ");
            char letra = sc.next().toLowerCase().charAt(0);

            if (letra != '0') {
                // CONTIENE LETRA
                char [] posLetra = contieneLetra(PALABRA, letra);
                for (int k = 0; k < posLetra.length; k++) {
                    if (letrasObtenidas[k] == '\0' && posLetra[k] != '\0') {
                        letrasObtenidas[k] = posLetra[k];
                    }
                }

                // VIDAS
                if (!PALABRA.contains(letra+"")) {
                    vidas--;
                } contador++;
            } else cancelar = true;
        } while (vidas > 0 && !palabraCompletada(letrasObtenidas) && !cancelar);

        // MENSAJE FINAL
        if (vidas == 0) {
            System.out.println("TE QUEDASTE SIN VIDAS, LA PALABRA ERA " + PALABRA);
        } else if (palabraCompletada(letrasObtenidas)){
            System.out.println("ACERTASTE LA PALABRA EN " + contador + " INTENTOS");
        }
    }

    private static String seleccionarPalabra (String fichero) {
        String [] palabras = FicheroPalabras.leerPalabras(fichero);
        return palabras[(int)(Math.random()*palabras.length)];
    }

    private static char [] contieneLetra (String palabra, char letra) {
        char [] pos = new char[palabra.length()];
        for (int i = 0; i < palabra.length(); i++) {
            if (palabra.charAt(i) == letra) {
                pos[i] = palabra.charAt(i);
            }
        }
        return pos;
    }

    private static void mostrarLetrasActuales (char [] letrasObtenidas) {
        for (char letrasObtenida : letrasObtenidas) {
            if (letrasObtenida == '\0') {
                System.out.print("_");
            } else {
                System.out.print(letrasObtenida);
            }
        }
    }

    private static boolean palabraCompletada (char [] letrasObtenidas) {
        boolean res = true; int pos = 0;
        while (res && pos < letrasObtenidas.length) {
            if (letrasObtenidas[pos] == '\0') {
                res = false;
            } pos++;
        }
        return res;
    }

    // FUNCIONALIDAD AGREGAR PALABRA
    public static void agregarPalabra (Scanner sc, String fichero) {
        String palabra;
        boolean res;
        FicheroPalabras.mostrarPalabras(4);
        do {
            System.out.print("INGRESE LA PALABRA: ");
            palabra = sc.next().toLowerCase();
            if (!palabra.matches("[a-z]+")) {
                res = false;
                System.out.println("LA PALABRA NO ES VALIDA");
            } else if (FicheroPalabras.existePalabra(fichero, palabra)) {
                res = false;
                System.out.println("LA PALABRA YA EXISTE");
            } else {
                res = true;
            }
        } while (!res);
        FicheroPalabras.escribirPalabra(palabra, fichero);
    }

    // FUNCIONALIDAD ELIMINAR PALABRA
    public static void eliminarPalabra (Scanner sc, String fichero, String [] palabras) {
        String palabra;
        boolean res;
        FicheroPalabras.mostrarPalabras(4);
        do {
            System.out.print("INGRESE LA PALABRA: ");
            palabra = sc.next().toLowerCase();

            if (!FicheroPalabras.existePalabra(fichero, palabra)) {
                res = false;
                System.out.println("LA PALABRA NO EXISTE");
            } else {
                res = true;
            }
        } while (!res);
        FicheroPalabras.eliminarPalabra(palabra, fichero);
    }

    // FUNCIONALIDAD CONFIGURACION
    public static void menuConfiguracion(Scanner sc, Configuracion conf) {
        System.out.println("MENU CONFIGURACION (0 PARA VOLVER): ");
        System.out.println("1.  MODIFICAR VIDAS");
        System.out.println("2.  MODIFICAR NOMBRE FICHERO PALABRAS");
        direccionConfiguracion(sc, conf, seleccionarOpcion(sc));
    }

    private static void direccionConfiguracion(Scanner sc, Configuracion conf, int opcion) {
        if (opcion != 0)
            switch (opcion) {
                case 1 -> {
                    System.out.print("NUEVA CANTIDAD DE VIDAS (VIDAS ACTUALES: " + conf.getVidas() + "): " );
                    conf.setVidas(sc.nextInt());
                }
                case 2 -> {
                    System.out.print("NOMBRE FICHERO PALABRAS (NOMBRE ACTUAL: " + conf.getFicheroPalabras() + "): ");
                    conf.setFicheroPalabras(sc.next());
                }
            }
    }

    // FUNCIONALIDAD MOSTRAR PALABRAS
    public static void mostrarPalabrasMenu (Scanner sc) {
        System.out.print("INGRESE CANT. PALABRAS POR COLUMNA: ");
        int columnas = sc.nextInt();
        FicheroPalabras.mostrarPalabras(columnas);
    }

    // FUNCIONALIDAD USUARIOS
    public static void menuUsuarios (Scanner sc, Configuracion conf) {
        System.out.println("MENU USUARIOS (0 PARA VOLVER): ");
        System.out.println("1.  SELECCIONAR USUARIO");
        System.out.println("2.  CREAR USUARIO");
        System.out.println("3.  ELIMINAR USUARIO");
        direccionUsuarios(sc, conf, seleccionarOpcion(sc));
    }

    private static void direccionUsuarios (Scanner sc, Configuracion conf, int opcion) {
        if (opcion != 0)
            switch (opcion) {
                case 1 -> {
                    String id = seleccionarUsuario(sc, conf.getFicheroUsuarios());
                    if (!id.equals("0")) {
                        Usuarios u = Usuarios.getUsuario(id, conf.getFicheroUsuarios());
                        conf.setUsuario(u);
                    }
                }
                case 2 -> {
                    System.out.print("INGRESE NOMBRE USUARIO: ");
                    String nombre = sc.next();
                    if (!nombre.equals("0")) {
                        Usuarios u = new Usuarios(nombre, 0, 0, 0);
                        u.guardarUsuario(conf.getFicheroUsuarios());
                        conf.setUsuario(u);
                    }
                }
                case 3 -> {
                    String id = seleccionarUsuario(sc, conf.getFicheroUsuarios());
                    if (!id.equals("0")) {
//                        Usuarios.eliminarUsuario(id, conf.getFicheroUsuarios());
                        conf.setUsuario(null);
                    }
                }
            }
    }

    private static String seleccionarUsuario (Scanner sc, String fichero) {
        Usuarios.mostrarUsuarios(fichero);
        System.out.print("SELECCIONE USUARIO: ");
        return sc.next();
    }

    // GENERAL
    private static int seleccionarOpcion(Scanner sc) {
        System.out.print("INGRESE OPCION: ");
        return sc.nextInt();
    }
}