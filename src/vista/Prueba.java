/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vista;

import estructuras.*;
import interfaces.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Carlos
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                   // ---------- 1) ListaSimplePuntajes ----------
        System.out.println("== Lista ==");
        ListaSimplePuntajes lista = new ListaSimplePuntajes();
        System.out.println("¿Vacía al inicio? " + lista.estaVacia());        // true

        lista.insertarOrdenado(new PuntajeJugador("Ana", 150));
        lista.insertarOrdenado(new PuntajeJugador("Luis", 400));
        lista.insertarOrdenado(new PuntajeJugador("Carl", 250));
        lista.insertarOrdenado(new PuntajeJugador("Zoe", 250));
        lista.insertarOrdenado(new PuntajeJugador("Beto", 50));

        System.out.println("¿Vacía después? " + lista.estaVacia());          // false
        System.out.println("Tamaño: " + lista.getTamanio());                 // 5
        System.out.println("Top 3: " + lista.obtenerTop(3));
        // Esperado: [Luis - 400, Carl - 250, Zoe - 250]
        System.out.println("Top 10: " + lista.obtenerTop(10));
        // Esperado: los 5, de mayor a menor

        lista.limpiar();
        System.out.println("¿Vacía tras limpiar? " + lista.estaVacia());     // true

        // ---------- 2) GestorPuntajesArchivo ----------
        System.out.println("\n== Archivo ==");
        String ruta = "datos/puntajes_prueba.txt"; // la carpeta "datos" se crea sola
        new File(ruta).delete(); // empezar limpio

        GestorPuntajesArchivo gestor = new GestorPuntajesArchivo(ruta);

        // Archivo que no existe: debe devolver lista vacía sin fallar
        System.out.println("Sin archivo, ¿vacía? " + gestor.cargarPuntajes().estaVacia()); // true

        gestor.guardarPuntaje(new PuntajeJugador("Ana", 150));
        gestor.guardarPuntaje(new PuntajeJugador("Luis", 400));
        gestor.guardarPuntaje(new PuntajeJugador("Carl", 250));

        // Meter a mano líneas dañadas para probar los try...catch
        try (FileWriter fw = new FileWriter(ruta, true)) {
            fw.write("linea_sin_punto_y_coma\n");
            fw.write("Pedro;abc\n");   // puntaje no numérico -> NumberFormatException
        } catch (IOException e) {
            System.err.println("Error preparando la prueba: " + e.getMessage());
        }

        ListaSimplePuntajes cargada = gestor.cargarPuntajes();
        System.out.println("Cargados: " + cargada.obtenerTop(10));
        // Esperado: [Luis - 400, Carl - 250, Ana - 150]
        // y en rojo (System.err): un aviso por "Pedro;abc". La línea sin ';' se ignora en silencio.

        // Probar el catch de IOException al guardar: la "carpeta" padre es en realidad un archivo,
        // así que no se puede crear
        GestorPuntajesArchivo malo = new GestorPuntajesArchivo(ruta + "/x.txt");
        malo.guardarPuntaje(new PuntajeJugador("Test", 1));
        // Esperado: mensaje "No se pudo guardar el puntaje..." en rojo, sin que el programa se caiga

        System.out.println("\nPrueba terminada.");
        
    }
    
}
