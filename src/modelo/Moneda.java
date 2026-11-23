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
public class Moneda extends Entidad {
      private int valorPuntos;

    public Moneda(int x, int y, int valorPuntos) {
        super(x, y, 16, 16);
        this.velocidadY = 2;
        this.valorPuntos = valorPuntos;
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
        g.setColor(new Color(255, 200, 0));
        g.fillOval(x, y, ancho, alto);
        g.setColor(new Color(170, 120, 0));
        g.drawOval(x, y, ancho, alto);
    }

    public int getValorPuntos() {
        return valorPuntos;
    }

    /** Desaparece cuando la recoge la nave. */
    @Override
    public void alColisionar(Colisionable otro) {
        if (otro instanceof NaveJugador) {
            setActiva(false);
        }
    }
}
