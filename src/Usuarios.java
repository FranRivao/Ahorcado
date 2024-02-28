import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Usuarios {
    private String nombre;
    private int cantidadPartidas;
    private int cantidadPartidasGanadas;
    private int id;

    public Usuarios(String nombre, int cantidadPartidas, int cantidadPartidasGanadas, int id) {
        this.nombre = nombre;
        if (cantidadPartidas == 0 && cantidadPartidasGanadas == 0 && id == 0) {
            this.cantidadPartidas = this.cantidadPartidasGanadas = 0;
            this.id = (int)(Math.random()*(1000-1)+1);
        }
    }

    public int getId () { return id; }

    public String getNombre () { return nombre; }

    public int getCantidadPartidas () { return cantidadPartidas; }

    public int getCantidadPartidasGanadas () { return cantidadPartidasGanadas; }

    public void guardarUsuario (String fichero) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(fichero, true));
            pw.printf("%s;%s;%s;%s;\n", id, nombre, cantidadPartidas, cantidadPartidasGanadas);
            pw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void eliminarUsuario (String id, String fichero) {

    }

    public static Usuarios getUsuario (String id, String fichero) {
        boolean res = false;
        String [] data = null;

        try {
            Scanner sc = new Scanner(new FileReader(fichero));
            do {
                data = sc.next().split(";");
                if (data.length > 0) {
                    res = data[0].equals(id);
                }
            } while(sc.hasNext() && !res);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (data != null) {
            return new Usuarios(data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[0]));
        } else return null;
    }

    public static void mostrarUsuarios (String fichero) {
        try {
            Scanner sc = new Scanner(new FileReader(fichero));
            int cant = 1;
            do {
                String [] data = sc.next().split(";");
                if (data.length > 0) {
                    System.out.printf("USUARIO %d:\n", cant);
                    System.out.printf("ID: %s\tNOMBRE: %s\tPARTIDAS: %s\tPARTIDAS GANADAS: %s\n", data[0], data[1], data[2], data[3]);
                    cant++;
                }
            } while(sc.hasNext());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
