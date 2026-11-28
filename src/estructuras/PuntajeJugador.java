/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;
/**
 * Registro de un jugador con su puntaje final.
 */
/**
 *
 * @author Carlos
 */
public class PuntajeJugador {
    private String nombre;
    private int puntos;
    // Constructor que guarda los dos datos recibidos
    public PuntajeJugador(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    @Override
    public String toString() {
        return nombre + " - " + puntos;
    }
}
