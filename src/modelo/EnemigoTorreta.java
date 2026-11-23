/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import interfaces.Disparador;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Carlos
 */
public class EnemigoTorreta extends Enemigo implements Disparador {
    private int yObjetivo;
    private boolean posicionado;
    private long intervaloDisparoMs;
    private long ultimoDisparo;

    public EnemigoTorreta(int x, int yInicial, int yDestino) {
        super(x, yInicial, 36, 30, 100);
        this.yObjetivo = yDestino;
        this.posicionado = false;
        this.intervaloDisparoMs = 1500;
        this.ultimoDisparo = System.currentTimeMillis();
        this.velocidadY = 2;
        this.resistencia = 3;
    }

    @Override
    public void actualizar() {
        mover();
    }

    /** Desciende hasta yObjetivo y luego se detiene. */
    @Override
    public void mover() {
        if (!posicionado) {
            y += velocidadY;
            if (y >= yObjetivo) {
                y = yObjetivo;
                posicionado = true;
            }
        }
    }

    @Override
    public Proyectil disparar() {
        ultimoDisparo = System.currentTimeMillis();
        return new Proyectil(x + ancho / 2 - 2, y + alto, 6, false);
    }

    @Override
    public boolean puedeDisparar() {
        return posicionado
                && System.currentTimeMillis() - ultimoDisparo >= intervaloDisparoMs;
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, ancho, alto);
        g.setColor(Color.RED);
        g.fillRect(x + ancho / 2 - 3, y + alto, 6, 10); // cañón
    }
}
