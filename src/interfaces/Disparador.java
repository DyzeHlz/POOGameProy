/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import modelo.Proyectil;

/**
 *
 * @author Carlos
 */

/**
 * Contrato para los objetos que pueden lanzar proyectiles
 * (la nave del jugador y el enemigo torreta).
 */
public interface Disparador {
        /** Crea y devuelve el proyectil disparado. */
    Proyectil disparar();

    /** Indica si ya pasó el tiempo de espera entre disparos. */
    boolean puedeDisparar();
}
