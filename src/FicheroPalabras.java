import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class FicheroPalabras {
    public static String [] palabras;
    public static int ocupacionPalabras;

    // LEER
    public static String [] leerPalabras (String fichero) {
        String [] palabras = null;
        try {
            Scanner file = new Scanner(new FileReader(fichero));
            palabras = file.next().split(";");
            file.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return palabras;
    }

    // ESCRIBIR
    public static void escribirPalabra (String palabra, String fichero) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(fichero, true));
            pw.print(palabra + ";");
            pw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // ELIMINAR
    public static void eliminarPalabra (String palabra, String fichero) {
        int pos = 0;
        while (pos < ocupacionPalabras) {
            if (palabra.equals(palabras[pos])) {
                for (int i = pos; i < ocupacionPalabras; i++) {
                    palabras[i] = (i == ocupacionPalabras-1) ? null : palabras[i+1];
                }
                pos = ocupacionPalabras;
            }

            pos++; ocupacionPalabras--;
        }

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(fichero, false));
            for (String p: palabras) {
                if (p != null)
                    pw.print(p + ";");
            }
            pw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // GENERAL
    public static boolean existePalabra (String fichero, String buscada) {
        String [] palabras = FicheroPalabras.leerPalabras(fichero);
        return Arrays.asList(palabras).contains(buscada);
    }

    public static void mostrarPalabras (int columnas) {
        int cantFilas = FicheroPalabras.ocupacionPalabras/columnas + FicheroPalabras.ocupacionPalabras%columnas;
        for (int i = 1; i <= cantFilas; i++) {
            for (int k = columnas*i-columnas; k < columnas*i; k++) {
                if (k < FicheroPalabras.ocupacionPalabras)
                    System.out.printf("%-15s",FicheroPalabras.palabras[k]);
            }
            System.out.println();
        }
    }

    public static void setPalabras (String fichero) {
        palabras = leerPalabras(fichero);
        if (FicheroPalabras.palabras != null)
            ocupacionPalabras = palabras.length;
    }

    public static boolean existeFichero (String fichero) {
        return new File(fichero).exists();
    }
}
