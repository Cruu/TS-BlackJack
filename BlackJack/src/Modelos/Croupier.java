package Modelos;

import java.util.ArrayList;
import java.util.Random;

public class Croupier extends Jugador {

    public Croupier(){
        this.setNombre("Croupier");
        Baraja.crearBaraja();
    }

    //MÉTODO QUE BARAJEA LAS CARTAS OBTENIDAS COMO MAZO PARA EL JUEGO
    public void barajear(){
        final int numeroCartas = Baraja.cartas.size();
        ArrayList<Carta> cartasBarajeadas = new ArrayList<Carta>();
        Random rn = new Random();
        for(int i=0 ; i<numeroCartas ; i++){
            int posicion = rn.nextInt(Baraja.cartas.size());
            cartasBarajeadas.add(Baraja.cartas.get(posicion));
            Baraja.cartas.remove(posicion);
        }
        Baraja.cartas = cartasBarajeadas;
    }

    //MÉTODO QUE DA LAS PRIMERAS DOS CARTAS A LOS JUGADORES Y 2 AL CROUPIER
    public void repartoInicial(Jugador[] jugadores){
        for(int i=0 ; i < 2 ; i++){
            for(int j=0; j<jugadores.length ; j++){
                jugadores[j].mano.add(obtenerCartaARepartir());
                if(j == jugadores.length-1)
                    this.mano.add(obtenerCartaARepartir());
            }
        }
    }

    //MÉTODO QUE SACA UNA CARTA DE LA BARAJA
    public Carta obtenerCartaARepartir(){
        Random rnd = new Random();
        Carta carta = new Carta();
        int indiceCarta = rnd.nextInt(Baraja.cartas.size());
        carta = Baraja.cartas.get(indiceCarta);
        Baraja.cartas.remove(indiceCarta);
        return carta;
    }


    public void evaluarAs(Jugador jugador) {
        int sumaPuntos = 0;
        int posicionAsEnMano = 0;
        int idx = 0;
        for (Carta carta: jugador.mano) {
            idx++;
            sumaPuntos += carta.valor;
            if(carta.valor == 1 || carta.valor == 11){
                posicionAsEnMano = idx;
            }
        }
        if(posicionAsEnMano > 0){
            if(jugador.obtenerTotalPuntos()>21){
                jugador.mano.get(posicionAsEnMano-1).setValor(1);
            }else{
                jugador.mano.get(posicionAsEnMano-1).setValor(11);
            }
        }
    }

}



