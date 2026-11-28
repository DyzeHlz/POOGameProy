/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import interfaces.Colisionable;
import interfaces.Movible;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * Clase base abstracta de todo lo que aparece en el escenario
 * (nave, enemigos, proyectiles y monedas).
 */
/**
 *
 * @author Carlos
 */
public abstract class Entidad implements Movible, Colisionable {
    // Posición (esquina superior izquierda) y tamaño en píxeles
    protected int x;
    protected int y;
    protected int ancho;
    protected int alto;
    protected int velocidadX;
    protected int velocidadY;
    protected boolean activa;
    // Guarda posición y tamaño; empieza quieta (velocidad 0) y activa
    public Entidad(int x, int y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidadX = 0;
        this.velocidadY = 0;
        this.activa = true;
    }

    /** Lógica de un ciclo del juego (la implementa cada subclase). */
    public abstract void actualizar();

    /** Dibuja la entidad en pantalla (la implementa cada subclase). */
    public abstract void dibujar(Graphics g);

    // ----- Movible -----

    /** Movimiento por defecto: suma la velocidad a la posición. */
    @Override
    public void mover() {
        x += velocidadX;
        y += velocidadY;
    }

    /** true mientras alguna parte de la entidad siga dentro del escenario. */
    @Override
    public boolean estaDentroDeLimites(int anchoEscenario, int altoEscenario) {
        return x + ancho > 0 && x < anchoEscenario
                && y + alto > 0 && y < altoEscenario;
    }

    // ----- Colisionable -----

    @Override
    public Rectangle getLimites() {
        return new Rectangle(x, y, ancho, alto);
    }

    @Override
    public boolean colisionaCon(Colisionable otro) {
        return getLimites().intersects(otro.getLimites());
    }

    /** Por defecto no hace nada; las subclases lo sobrescriben si reaccionan. */
    @Override
    public void alColisionar(Colisionable otro) {
    }

    // ----- Estado -----

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean b) {
        this.activa = b;
    }
}
