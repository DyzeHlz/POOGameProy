/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

import java.util.ArrayList;

/**
 * Nodo de la lista enlazada simple de puntajes.
 */
/**
 *
 * @author Carlos
 */
public class ListaSimplePuntajes {
    
    private NodoPuntaje cabeza;
    private int tamano;

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
            // Caso 2: buscar el nodo después del cual debe ir
            NodoPuntaje actual = cabeza;
            while (actual.getSiguiente() != null
                    && actual.getSiguiente().getDato().getPuntos() >= p.getPuntos()) {
                actual = actual.getSiguiente();
            }
            nuevo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevo);
        }
        tamano++;
    }

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
