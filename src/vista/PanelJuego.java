/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import modelo.Entidad;
import modelo.NaveJugador;
import modelo.Proyectil;
/**
 *
 * @author Carlos
 */
public class PanelJuego extends JPanel implements ActionListener, KeyListener  {
    private MotorJuego motor;
    private javax.swing.Timer timerBucle;
    private boolean izquierdaPresionada;
    private boolean derechaPresionada;
    private boolean disparoPresionado;
    private VentanaPrincipal ventanaPadre;

    public PanelJuego() {
        // Tamaño del escenario: 800 x 600 (el mismo que recibe el motor)
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        motor = new MotorJuego(800, 600);
        timerBucle = new javax.swing.Timer(16, this);
    }

    /** La ventana se asigna después de crear el panel (para avisar del game over). */
    public void setVentanaPadre(VentanaPrincipal v) {
        this.ventanaPadre = v;
    }

    /** Arranca una partida nueva y pone el foco en el panel para leer el teclado. */
    public void iniciarJuego() {
        motor.iniciarPartida();
        izquierdaPresionada = false;
        derechaPresionada = false;
        disparoPresionado = false;
        timerBucle.start();
        requestFocusInWindow();
    }

    public MotorJuego getMotor() {
        return motor;
    }

    // ------------------------------------------------------------------
    // Dibujo
    // ------------------------------------------------------------------

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Entidad e : motor.getEntidades()) {
            e.dibujar(g2);
        }
        for (Proyectil p : motor.getProyectiles()) {
            p.dibujar(g2);
        }
        NaveJugador nave = motor.getJugador();
        if (nave.isActiva()) {
            nave.dibujar(g2);
        }

        // HUD
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
        g2.drawString("Puntaje: " + nave.getPuntaje(), 15, 25);
        g2.drawString("Vidas: " + nave.getVidas(), getWidth() - 100, 25);
    }

    // ------------------------------------------------------------------
    // Bucle del juego (cada tick del Timer)
    // ------------------------------------------------------------------

    @Override
    public void actionPerformed(ActionEvent e) {
        NaveJugador nave = motor.getJugador();
        if (izquierdaPresionada) {
            nave.moverIzquierda();
        }
        if (derechaPresionada) {
            nave.moverDerecha(getWidth());
        }
        if (disparoPresionado) {
            motor.dispararJugador();
        }

        motor.actualizarCiclo();
        repaint();

        if (motor.isJuegoTerminado()) {
            timerBucle.stop();
            if (ventanaPadre != null) {
                ventanaPadre.mostrarPantallaGameOver(nave.getPuntaje());
            }
        }
    }

    // ------------------------------------------------------------------
    // Teclado
    // ------------------------------------------------------------------

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_A) {
            izquierdaPresionada = true;
        } else if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_D) {
            derechaPresionada = true;
        } else if (k == KeyEvent.VK_SPACE) {
            disparoPresionado = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_A) {
            izquierdaPresionada = false;
        } else if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_D) {
            derechaPresionada = false;
        } else if (k == KeyEvent.VK_SPACE) {
            disparoPresionado = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No se usa, pero KeyListener obliga a implementarlo
    }
}
