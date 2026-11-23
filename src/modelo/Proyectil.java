/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import interfaces.Colisionable;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Carlos
 */
public class Proyectil extends Entidad {
    
    private boolean esDelJugador;
    private int danio;

    /**
     * @param velY velocidad vertical (negativa = sube, positiva = baja)
     */
    public Proyectil(int x, int y, int velY, boolean esDelJugador) {
        super(x, y, 4, 12);
        this.velocidadY = velY;
        this.esDelJugador = esDelJugador;
        this.danio = 1;
    }

    @Override
    public void actualizar() {
        mover();
    }

    @Override
    public void mover() {
        y += velocidadY;
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(esDelJugador ? Color.YELLOW : Color.RED);
        g.fillRect(x, y, ancho, alto);
    }

    public boolean isEsDelJugador() {
        return esDelJugador;
    }

    public int getDanio() {
        return danio;
    }

    /** El proyectil se destruye al impactar con su objetivo. */
    @Override
    public void alColisionar(Colisionable otro) {
        if (esDelJugador && otro instanceof Enemigo) {
            setActiva(false);
        } else if (!esDelJugador && otro instanceof NaveJugador) {
            setActiva(false);
        }
    }
}
