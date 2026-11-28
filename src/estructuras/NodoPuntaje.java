/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;
/**
 * Nodo de la lista enlazada: guarda un puntaje y apunta al siguiente nodo.
 */
/**
 *
 * @author Carlos
 */
public class NodoPuntaje {
    private PuntajeJugador dato;
    private NodoPuntaje siguiente; // el nodo que sigue (null si es el último
// Constructor que crea un nodo con su dato y sin siguiente todavía
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
 // Enlaza este nodo con otro (así se arma la cadena)
    public void setSiguiente(NodoPuntaje sig) {
        this.siguiente = sig;
    }
}
