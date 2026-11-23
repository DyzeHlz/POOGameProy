/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vista;

import modelo.EnemigoFormacion;
import modelo.EnemigoTorreta;
import modelo.Moneda;
import modelo.NaveJugador;
import modelo.Proyectil;
/**
 *
 * @author Carlos
 */
public class Prueba2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         // ---------- 1) Nave: movimiento y límites ----------
        System.out.println("== Nave ==");
        NaveJugador nave = new NaveJugador(100, 400);
        System.out.println("Vidas: " + nave.getVidas());                       // 3
        nave.moverIzquierda();
        System.out.println("x tras izquierda: " + nave.getLimites().x);        // 94
        nave.moverDerecha(800);
        System.out.println("x tras derecha: " + nave.getLimites().x);          // 100
        for (int i = 0; i < 30; i++) {
            nave.moverIzquierda();
        }
        System.out.println("x pegada al borde: " + nave.getLimites().x);       // 0
        nave.moverDerecha(800);
        nave.moverDerecha(800);
        nave.moverDerecha(800);
        nave.moverDerecha(800);
        nave.moverDerecha(800);
        nave.moverDerecha(800);
        nave.moverDerecha(800);
        nave.moverDerecha(800);
        nave.moverDerecha(800);
        nave.moverDerecha(800);
        nave.moverDerecha(800);
        nave.moverDerecha(800);
        nave.moverDerecha(800);
        nave.moverDerecha(800);
        nave.moverDerecha(800);
        nave.moverDerecha(800);
        nave.moverDerecha(800); // 17 pasos de 6 = 102
        System.out.println("x tras 17 derechas: " + nave.getLimites().x);      // 102

        // ---------- 2) Cadencia de disparo ----------
        System.out.println("\n== Disparo ==");
        System.out.println("¿Puede disparar al inicio? " + nave.puedeDisparar()); // true
        Proyectil bala = nave.disparar();
        System.out.println("¿Puede disparar enseguida? " + nave.puedeDisparar()); // false
        try {
            Thread.sleep(350);
        } catch (InterruptedException e) {
            System.err.println("Prueba interrumpida: " + e.getMessage());
        }
        System.out.println("¿Puede tras 350 ms? " + nave.puedeDisparar());        // true
        System.out.println("¿Bala del jugador? " + bala.isEsDelJugador());        // true

        // ---------- 3) Proyectil del jugador vs enemigo ----------
        System.out.println("\n== Colisión disparo-enemigo ==");
        EnemigoFormacion enemigo = new EnemigoFormacion(300, 100, 0, 800);
        Proyectil p = new Proyectil(310, 110, -8, true);
        System.out.println("¿Colisionan? " + p.colisionaCon(enemigo));            // true
        p.alColisionar(enemigo);
        enemigo.alColisionar(p);
        System.out.println("Proyectil activo: " + p.isActiva());                  // false
        System.out.println("Enemigo activo: " + enemigo.isActiva());              // false
        System.out.println("Puntos del enemigo: " + enemigo.getPuntosAlDestruir()); // 50

        // ---------- 4) Torreta ----------
        System.out.println("\n== Torreta ==");
        EnemigoTorreta torreta = new EnemigoTorreta(200, -40, 60);
        System.out.println("¿Puede disparar al aparecer? " + torreta.puedeDisparar()); // false
        for (int i = 0; i < 50; i++) {
            torreta.actualizar();
        }
        System.out.println("y al llegar: " + torreta.getLimites().y);             // 60
        try {
            Thread.sleep(1600);
        } catch (InterruptedException e) {
            System.err.println("Prueba interrumpida: " + e.getMessage());
        }
        System.out.println("¿Puede disparar ya posicionada? " + torreta.puedeDisparar()); // true
        Proyectil balaEnemiga = torreta.disparar();
        System.out.println("¿Bala del jugador? " + balaEnemiga.isEsDelJugador());  // false

        // ---------- 5) Formación: rebote en el borde ----------
        System.out.println("\n== Formación ==");
        EnemigoFormacion f = new EnemigoFormacion(0, 100, 0, 100);
        for (int i = 0; i < 40; i++) {
            f.actualizar();
        }
        System.out.println("x=" + f.getLimites().x + " y=" + f.getLimites().y);   // x=62 y=120

        // ---------- 6) Moneda y daño a la nave ----------
        System.out.println("\n== Moneda y vidas ==");
        Moneda m = new Moneda(105, 405, 10);
        nave = new NaveJugador(100, 400);
        System.out.println("¿Moneda toca nave? " + m.colisionaCon(nave));         // true
        nave.alColisionar(m);
        m.alColisionar(nave);
        System.out.println("Puntaje: " + nave.getPuntaje());                      // 10
        System.out.println("Moneda activa: " + m.isActiva());                     // false

        Proyectil balaMala = new Proyectil(110, 410, 6, false);
        nave.alColisionar(balaMala);
        System.out.println("Vidas tras 1 impacto: " + nave.getVidas());           // 2
        nave.perderVida();
        nave.perderVida();
        System.out.println("Vidas: " + nave.getVidas() + " | nave activa: " + nave.isActiva()); // 0 | false

        System.out.println("\nPrueba terminada.");
    }
    }
    

