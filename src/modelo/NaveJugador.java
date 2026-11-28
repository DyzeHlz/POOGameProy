/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import interfaces.Colisionable;
import interfaces.Disparador;
import java.awt.Color;
import java.awt.Graphics;
/** Nave controlada por el jugador: se mueve a los lados y dispara hacia arriba. */
/**
 *
 * @author Carlos
 */
public class NaveJugador extends Entidad implements Disparador {
    private int vidas;
    private int puntaje;
    private long tiempoUltimoDisparo;
    private long cadenciaMs;

    public NaveJugador(int x, int y) {
        super(x, y, 40, 30);
        this.vidas = 3;
        this.puntaje = 0;
        this.tiempoUltimoDisparo = 0;
        this.cadenciaMs = 300;
        this.velocidadX = 6;
    }

    public void moverIzquierda() {
        x -= velocidadX;
        if (x < 0) {
            x = 0;
        }
    }
 // Mueve a la derecha sin pasar del borde derecho (limiteAncho)
    public void moverDerecha(int limiteAncho) {
        x += velocidadX;
        if (x + ancho > limiteAncho) {
            x = limiteAncho - ancho;
        }
    }

    /** La nave se mueve con el teclado (moverIzquierda/moverDerecha), no por sí sola. */
    @Override
    public void actualizar() {
    }
// Dibuja un triángulo celeste: vértice arriba al centro y base abajo
    @Override
    public void dibujar(Graphics g) {
        g.setColor(new Color(0, 200, 255));
        int[] px = {x + ancho / 2, x, x + ancho};
        int[] py = {y, y + alto, y + alto};
        g.fillPolygon(px, py, 3);
    }

    // ----- Disparador -----

    @Override
    public Proyectil disparar() {
        tiempoUltimoDisparo = System.currentTimeMillis();
        return new Proyectil(x + ancho / 2 - 2, y, -8, true);
    }

    @Override
    public boolean puedeDisparar() {
        return System.currentTimeMillis() - tiempoUltimoDisparo >= cadenciaMs;
    }

    // ----- Estado del jugador -----

    public void perderVida() {
        vidas--;
        if (vidas <= 0) {
            vidas = 0;
            setActiva(false);
        }
    }

    public void sumarPuntaje(int puntos) {
        puntaje += puntos;
    }

    public int getVidas() {
        return vidas;
    }

    public int getPuntaje() {
        return puntaje;
    }

    /** Reacciona según con qué choca: enemigo, disparo enemigo o moneda. */
    @Override
    public void alColisionar(Colisionable otro) {
        if (otro instanceof Enemigo) {
            perderVida();
        } else if (otro instanceof Proyectil) {
            if (!((Proyectil) otro).isEsDelJugador()) {
                perderVida();
            }
        } else if (otro instanceof Moneda) {
            sumarPuntaje(((Moneda) otro).getValorPuntos());
        }
    }
}
