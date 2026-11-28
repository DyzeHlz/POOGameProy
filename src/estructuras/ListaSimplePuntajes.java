/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

import java.util.ArrayList;

/**
 * Lista enlazada simple de puntajes, ordenada de mayor a menor.
 */
/**
 *
 * @author Carlos
 */
public class ListaSimplePuntajes {
    
    private NodoPuntaje cabeza;
    private int tamano;
// Lista vacía: no hay primer nodo y el tamaño es 0
    public ListaSimplePuntajes() {
        this.cabeza = null;
        this.tamano = 0;
    }

    /** Inserta el puntaje en la posición que mantiene el orden descendente. */
    public void insertarOrdenado(PuntajeJugador p) {
        NodoPuntaje nuevo = new NodoPuntaje(p);

        // Caso 1: lista vacía o el nuevo puntaje es el mayor -> va al inicio
        if (cabeza == null || p.getPuntos() > cabeza.getDato().getPuntos()) {
            nuevo.setSiguiente(cabeza);
            cabeza = nuevo;
        } else {
             // Caso 2: se recorre la lista hasta encontrar el nodo después del cual va
            NodoPuntaje actual = cabeza;
            
             // Avanza mientras el siguiente tenga puntaje igual o mayor
            // (con >= los empates quedan en orden de llegada)
            
            while (actual.getSiguiente() != null
                    && actual.getSiguiente().getDato().getPuntos() >= p.getPuntos()) {
                actual = actual.getSiguiente();
            }
            nuevo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevo);
        }
        tamano++;
    }
    
     // true si no hay ningún nodo
    public boolean estaVacia() {
        return cabeza == null;
    }

    /** Devuelve los mejores 'limite' puntajes (ya vienen ordenados). */
    public ArrayList<PuntajeJugador> obtenerTop(int limite) {
        ArrayList<PuntajeJugador> top = new ArrayList<>();
        NodoPuntaje actual = cabeza;
        while (actual != null && top.size() < limite) {
            top.add(actual.getDato());
            actual = actual.getSiguiente();
        }
        return top;
    }

    public void limpiar() {
        cabeza = null;
        tamano = 0;
    }

    public int getTamanio() {
        return tamano;
    }
}
