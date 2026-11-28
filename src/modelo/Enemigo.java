/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import interfaces.Colisionable;
import java.awt.Graphics;
/** Clase base abstracta de los enemigos (torreta y formación). */
/**
 *
 * @author Carlos
 */
public abstract class Enemigo extends Entidad {
    
    protected int puntosAlDestruir;
    protected int resistencia;
    // Constructor que pasa posición y tamaño a Entidad; aguanta 1 impacto por defecto
    public Enemigo(int x, int y, int ancho, int alto, int pts) {
        super(x, y, ancho, alto);
        this.puntosAlDestruir = pts;
        this.resistencia = 1;
    }

    /** Resta resistencia; al llegar a 0 el enemigo queda destruido (inactivo). */
    public void recibirImpacto() {
        resistencia--;
        if (resistencia <= 0) {
            setActiva(false);
        }
    }

    public int getPuntosAlDestruir() {
        return puntosAlDestruir;
    }

    /** Cada tipo de enemigo define cómo se actualiza en cada ciclo. */
    @Override
    public abstract void actualizar();

    /** Cada tipo de enemigo define cómo se dibuja. */
    @Override
    public abstract void dibujar(Graphics g);

    /** Recibe impacto de disparos del jugador; si choca con la nave, se destruye. */
    @Override
    public void alColisionar(Colisionable otro) {
        if (otro instanceof Proyectil) {
            if (((Proyectil) otro).isEsDelJugador()) {
                recibirImpacto();
            }
        } else if (otro instanceof NaveJugador) {
            setActiva(false);
        }
    }
}
