/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.awt.Color;
import java.awt.Graphics;
/** Enemigo que patrulla de lado a lado; al tocar un borde cambia de dirección y baja. */
/**
 *
 * @author Carlos
 */
public class EnemigoFormacion extends Enemigo{
     private int direccionX;
    private int limiteIzquierdo;
    private int limiteDerecho;

    public EnemigoFormacion(int x, int y, int limIzq, int limDer) {
        super(x, y, 30, 24, 50);
        this.direccionX = 1;
        this.limiteIzquierdo = limIzq;
        this.limiteDerecho = limDer;
        this.velocidadX = 2;
    }

    @Override
    public void actualizar() {
        mover();
    }
 // Avanza en la dirección actual; si pasa un límite, se acomoda y rebota
    @Override
    public void mover() {
        x += direccionX * velocidadX;
        if (x < limiteIzquierdo) {
            x = limiteIzquierdo;
            invertirDireccion();
        } else if (x + ancho > limiteDerecho) {
            x = limiteDerecho - ancho;
            invertirDireccion();
        }
    }

    /** Cambia el sentido horizontal y desciende 20 px. */
    public void invertirDireccion() {
        direccionX = -direccionX;
        y += 20;
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(new Color(220, 60, 60));
        g.fillRect(x, y, ancho, alto);
        g.setColor(Color.WHITE);
        g.fillRect(x + 6, y + 8, 5, 5);   // ojos
        g.fillRect(x + ancho - 11, y + 8, 5, 5);
    }
}
