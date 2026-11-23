/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;
/**
 * Nodo de la lista enlazada simple de puntajes.
 */
/**
 *
 * @author Carlos
 */
public class NodoPuntaje {
    private PuntajeJugador dato;
    private NodoPuntaje siguiente;

    public NodoPuntaje(PuntajeJugador dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public PuntajeJugador getDato() {
        return dato;
    }

    public NodoPuntaje getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPuntaje sig) {
        this.siguiente = sig;
    }
}
