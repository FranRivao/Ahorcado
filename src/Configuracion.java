import java.io.*;
import java.util.Scanner;

public class Configuracion {
    private final String fichero;
    private String [] datos;
    private Usuarios usuario;
    // datos[0] = ficheroPalabras; datos[1] = vidas; datos[2] = ficheroUsuarios;

    public Configuracion (String ficheroConfiguracion){
        fichero = ficheroConfiguracion;
        datos = leerConfiguracion();
    }

    public String getFicheroPalabras () {
        return datos[0];
    }

    public String getFicheroUsuarios () { return datos[2]; }

    public int getVidas () {
        return Integer.parseInt(datos[1]);
    }

    public Usuarios getUsuario () { return usuario; }

    public void setVidas (int nuevaVida) {
        datos[1] = nuevaVida + "";
    }

    public void setFicheroPalabras (String ficheroPalabras) {
        if (FicheroPalabras.existeFichero(ficheroPalabras)) {
            datos[0] = ficheroPalabras;
            FicheroPalabras.setPalabras(ficheroPalabras);
        } else {
            System.out.println("NO EXISTE ESE FICHERO\n");
        }
    }

    public void setUsuario (Usuarios u) {
        usuario = u;
    }

    public void actualizarConfiguracion () {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(fichero, false));
            for (String dato : datos) {
                pw.print(dato + ";");
            }
            pw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String [] leerConfiguracion() {
        String [] datos = null;
        try {
            Scanner file = new Scanner(new FileReader(fichero));
            datos = file.next().split(";");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return datos;
    }
}
