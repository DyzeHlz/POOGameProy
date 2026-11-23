/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Guarda y carga los puntajes en un archivo de texto.
 * Formato de cada línea:  nombre;puntos
 * Las excepciones se manejan aquí mismo con try...catch.
 */
/**
 *
 * @author Carlos
 */
public class GestorPuntajesArchivo {
    private String rutaArchivo;

    public GestorPuntajesArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    /** Agrega un puntaje al final del archivo (crea la carpeta y el archivo si no existen). */
    public void guardarPuntaje(PuntajeJugador p) {
        try {
            crearCarpetaSiNoExiste();
        } catch (IOException e) {
            System.err.println("No se pudo guardar el puntaje: " + e.getMessage());
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            bw.write(p.getNombre() + ";" + p.getPuntos());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("No se pudo guardar el puntaje: " + e.getMessage());
        }
    }

    /** Crea la carpeta que contendrá el archivo (por ejemplo "datos") si todavía no existe. */
    private void crearCarpetaSiNoExiste() throws IOException {
        File carpeta = new File(rutaArchivo).getParentFile();
        if (carpeta != null && !carpeta.exists() && !carpeta.mkdirs()) {
            throw new IOException("No se pudo crear la carpeta " + carpeta.getPath());
        }
    }

    /** Lee el archivo y devuelve todos los puntajes en una lista ordenada. */
    public ListaSimplePuntajes cargarPuntajes() {
        ListaSimplePuntajes lista = new ListaSimplePuntajes();
        File archivo = new File(rutaArchivo);

        if (!archivo.exists()) {
            return lista; // primera vez que se juega: no hay puntajes
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 2) {
                    try {
                        lista.insertarOrdenado(
                                new PuntajeJugador(partes[0], Integer.parseInt(partes[1].trim())));
                    } catch (NumberFormatException ex) {
                        System.err.println("Línea con puntaje inválido ignorada: " + linea);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo leer el archivo de puntajes: " + e.getMessage());
        }
        return lista;
    }

}
