/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

/**
 *
 * @author Carlos
 */
/**
 * Contrato para todo objeto del juego que puede desplazarse por el escenario.
 */
public interface Movible {
       /** Actualiza la posición según la velocidad del objeto. */
    void mover();

    /**
     * Indica si el objeto sigue dentro del escenario.
     * @param ancho ancho del escenario en píxeles
     * @param alto  alto del escenario en píxeles
     */
    boolean estaDentroDeLimites(int ancho, int alto);
}
