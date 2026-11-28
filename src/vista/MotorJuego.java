/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import estructuras.GestorPuntajesArchivo;
import estructuras.ListaSimplePuntajes;
import estructuras.PuntajeJugador;
import interfaces.Disparador;
import java.util.ArrayList;
import java.util.Iterator;
import modelo.Enemigo;
import modelo.EnemigoFormacion;
import modelo.EnemigoTorreta;
import modelo.Entidad;
import modelo.Moneda;
import modelo.NaveJugador;
import modelo.Proyectil;
/** Lógica del juego: entidades, colisiones, puntaje y guardado. */
/**
 *
 * @author Carlos
 */
public class MotorJuego {
    private NaveJugador jugador;
    private ArrayList<Entidad> entidades;
    private ArrayList<Proyectil> proyectiles;
    private ListaSimplePuntajes listaPuntajes;
    private GestorPuntajesArchivo gestorArchivo;
    private int anchoEscenario;
    private int altoEscenario;
    private boolean juegoTerminado;
 // Prepara las listas y el archivo, carga los puntajes y deja lista la primera partida
    public MotorJuego(int ancho, int alto) {
        this.anchoEscenario = ancho;
        this.altoEscenario = alto;
        this.entidades = new ArrayList<>();
        this.proyectiles = new ArrayList<>();
        this.gestorArchivo = new GestorPuntajesArchivo("datos/puntajes.txt");
        this.listaPuntajes = gestorArchivo.cargarPuntajes();
        cargarPuntajesPredefinidos();
        iniciarPartida();
    }

    // ------------------------------------------------------------------
    // Partida
    // ------------------------------------------------------------------

    /** Deja todo listo para una partida nueva. */
    public void iniciarPartida() {
        jugador = new NaveJugador(anchoEscenario / 2 - 20, altoEscenario - 60);
        entidades.clear();
        proyectiles.clear();
        juegoTerminado = false;
        generarOleadaEnemigos();
    }

    /** Un ciclo del juego: se llama una vez por cada tick del Timer. */
    public void actualizarCiclo() {
        if (juegoTerminado) {
            return;
        }

        // 1) Las torretas disparan cuando les toca
        ArrayList<Proyectil> nuevos = new ArrayList<>();
        for (Entidad e : entidades) {
            if (e.isActiva() && e instanceof Disparador) {
                Disparador d = (Disparador) e;
                if (d.puedeDisparar()) {
                    nuevos.add(d.disparar());
                }
            }
        }
        proyectiles.addAll(nuevos);

        // 2) Todo se mueve
        jugador.actualizar();
        for (Entidad e : entidades) {
            e.actualizar();
        }
        for (Proyectil p : proyectiles) {
            p.actualizar();
        }

        // 3) Colisiones
        verificarColisiones();

        // 4) Se eliminan las entidades destruidas o que salieron de pantalla
        limpiarInactivas();

        // 5) Fin de juego, nuevas oleadas y monedas
        if (!jugador.isActiva()) {
            juegoTerminado = true;
            return;
        }
        if (!hayEnemigos()) {
            generarOleadaEnemigos();
        }
        if (Math.random() < 0.004) {
            generarMonedaAleatoria();
        }
    }

    /** Dispara si la nave ya puede hacerlo (respeta la cadencia). */
    public void dispararJugador() {
        if (juegoTerminado || !jugador.isActiva()) {
            return;
        }
        if (jugador.puedeDisparar()) {
            proyectiles.add(jugador.disparar());
        }
    }

    // ------------------------------------------------------------------
    // Generación de entidades
    // ------------------------------------------------------------------

    /**
     * Crea una oleada: una fila de enemigos de formación que se mueven como
     * bloque y, además, torretas. Con más puntaje, la oleada es mayor.
     */
    public void generarOleadaEnemigos() {
        int nivel = jugador.getPuntaje() / 500;
        int separacion = 60;

        // Fila de formación (todos rebotan a la vez, como un bloque)
        int n = Math.min(4 + nivel, 8);
        int anchoBloque = (n - 1) * separacion + 30;
        int x0 = (anchoEscenario - anchoBloque) / 2;
        for (int i = 0; i < n; i++) {
            int limIzq = i * separacion;
            int limDer = anchoEscenario - (n - 1 - i) * separacion;
            entidades.add(new EnemigoFormacion(x0 + i * separacion, 30, limIzq, limDer));
        }

        // Torretas
        int t = Math.min(1 + nivel / 2, 3);
        for (int k = 0; k < t; k++) {
            int x = anchoEscenario * (k + 1) / (t + 1) - 18;
            entidades.add(new EnemigoTorreta(x, -25, 150));
        }
    }

    /** Crea una moneda en una posición horizontal al azar, arriba de la pantalla. */
    public void generarMonedaAleatoria() {
        int x = (int) (Math.random() * (anchoEscenario - 16));
        entidades.add(new Moneda(x, -10, 10));
    }

    // ------------------------------------------------------------------
    // Colisiones
    // ------------------------------------------------------------------

    public void verificarColisiones() {
        // Proyectiles
        for (Proyectil p : proyectiles) {
            if (!p.isActiva()) {
                continue;
            }
            if (p.isEsDelJugador()) {
                // Disparo del jugador contra enemigos
                for (Entidad e : entidades) {
                    if (e instanceof Enemigo && e.isActiva() && p.colisionaCon(e)) {
                        p.alColisionar(e);
                        e.alColisionar(p);
                        if (!e.isActiva()) {
                            jugador.sumarPuntaje(((Enemigo) e).getPuntosAlDestruir());
                        }
                        break; // un disparo golpea a un solo enemigo
                    }
                }
            } else if (jugador.isActiva() && p.colisionaCon(jugador)) {
                // Disparo enemigo contra la nave
                p.alColisionar(jugador);
                jugador.alColisionar(p);
            }
        }

        // Nave contra enemigos y monedas
        for (Entidad e : entidades) {
            if (!e.isActiva() || !jugador.isActiva()) {
                continue;
            }
            if ((e instanceof Enemigo || e instanceof Moneda) && e.colisionaCon(jugador)) {
                e.alColisionar(jugador);
                jugador.alColisionar(e);
            }
        }
    }

    // ------------------------------------------------------------------
    // Puntajes
    // ------------------------------------------------------------------

    /** Guarda el puntaje de la partida con el nombre indicado. */
    public void registrarPuntajeFinal(String nombre) {
        PuntajeJugador p = new PuntajeJugador(limpiarNombre(nombre), jugador.getPuntaje());
        gestorArchivo.guardarPuntaje(p);
        listaPuntajes.insertarOrdenado(p);
    }

    /** Los 5 mejores puntajes (para la pantalla de inicio). */
    public ArrayList<PuntajeJugador> getTopPuntajes() {
        return listaPuntajes.obtenerTop(5);
    }

    /** Si todavía no hay puntajes guardados, crea unos de referencia. */
    private void cargarPuntajesPredefinidos() {
        if (!listaPuntajes.estaVacia()) {
            return;
        }
        PuntajeJugador[] base = {
            new PuntajeJugador("AAA", 1500),
            new PuntajeJugador("BBB", 1200),
            new PuntajeJugador("CCC", 900),
            new PuntajeJugador("DDD", 600),
            new PuntajeJugador("EEE", 300)
        };
        for (PuntajeJugador p : base) {
            gestorArchivo.guardarPuntaje(p);
            listaPuntajes.insertarOrdenado(p);
        }
    }

    /**
     * Deja el nombre listo para guardarlo: sin ';' ni saltos de línea
     * (romperían el archivo), sin espacios en los extremos, máximo 10
     * caracteres y "NULL" si queda vacío.
     */
    private String limpiarNombre(String nombre) {
        if (nombre == null) {
            return "NULL";
        }
        String limpio = nombre.replaceAll("[;\\r\\n]", "").trim();
        if (limpio.length() > 10) {
            limpio = limpio.substring(0, 10).trim();
        }
        if (limpio.isEmpty()) {
            return "NULL";
        }
        return limpio;
    }

    // ------------------------------------------------------------------
    // Auxiliares y getters
    // ------------------------------------------------------------------

    private boolean hayEnemigos() {
        for (Entidad e : entidades) {
            if (e instanceof Enemigo) {
                return true;
            }
        }
        return false;
    }

    /** Quita entidades y proyectiles destruidos o fuera del escenario. */
    private void limpiarInactivas() {
        Iterator<Entidad> it = entidades.iterator();
        while (it.hasNext()) {
            Entidad e = it.next();
            if (!e.isActiva() || !e.estaDentroDeLimites(anchoEscenario, altoEscenario)) {
                it.remove();
            }
        }
        Iterator<Proyectil> itp = proyectiles.iterator();
        while (itp.hasNext()) {
            Proyectil p = itp.next();
            if (!p.isActiva() || !p.estaDentroDeLimites(anchoEscenario, altoEscenario)) {
                itp.remove();
            }
        }
    }

    public ArrayList<Entidad> getEntidades() {
        return entidades;
    }

    public ArrayList<Proyectil> getProyectiles() {
        return proyectiles;
    }

    public NaveJugador getJugador() {
        return jugador;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }
}
