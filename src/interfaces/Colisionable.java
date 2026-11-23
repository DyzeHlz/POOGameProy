/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import java.awt.Rectangle;

/**
 *
 * @author Carlos
 */

/**
 * Contrato para todo objeto que puede chocar con otro.
 */
public interface Colisionable {
        /** Rectángulo que ocupa el objeto (zona de colisión). */
    Rectangle getLimites();

    /** Devuelve true si este objeto se superpone con el otro. */
    boolean colisionaCon(Colisionable otro);

    /** Reacción del objeto cuando choca con otro. */
    void alColisionar(Colisionable otro);
}
