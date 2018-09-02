package Modelos;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Juego {

    private Integer numeroJugadores=0;
    private Scanner scn = new Scanner(System.in);
    private Jugador[] jugadores;
    private Croupier croupier;

    public Juego() {
        croupier = new Croupier();
        this.numeroDeJugadores();
    }

    public void numeroDeJugadores(){
        //Metodo para asignar los numeros de jugadores de la partida
        System.out.print("\033[34m");
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*- BlackJack  *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println("\033[30m");

        do{
            System.out.print("¿Cuántos jugadores participan (1-6)? ");
            try{
                scn = new Scanner(System.in);
                numeroJugadores = scn.nextInt();
            }
            catch(InputMismatchException ex) {
                System.out.println("***Atención:Utilice números, para específicar su respuesta***");
                numeroJugadores = 0;
            }
        }while (numeroJugadores < 1 || numeroJugadores > 6);
        System.out.println(" ");
        nombreJugadores();
    }

    private void nombreJugadores() {
        this.jugadores = new Jugador[numeroJugadores];
        for(int i=0 ; i<jugadores.length ; i++){
            System.out.print("Nombre del jugador "+(i+1)+": ");
            jugadores[i] = new Jugador(scn.next());
            System.out.println(" ");
        }
        inicioJuego();
    }

    private void inicioJuego() {
        System.out.println("\033[34m");
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*- INICIA JUEGO  -*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println("\033[30m");
        croupier.barajear();
        croupier.repartoInicial(jugadores);
        for (Jugador jugador:jugadores) {
            System.out.println(this.obtenerSalidaMano(jugador));
        }
        //**************IMPRESION DEL JUEGO DE CROUPIER
        System.out.println(this.obtenerSalidaMano(croupier));
        //AQUI EL CLOUPIER VALIDA LAS MANOS DE CADA JUGADOR Y TOMA DECISIONES.





    }


    //MÉTODO QUE REGRESA UN STRING CON LAS CARTAS QUE TIENE EL JUGADOR QUE SE MANDA POR PARAMETRO
    private String obtenerSalidaMano(Jugador jugador){
        croupier.evaluarAs(jugador);
        String msjARegresar = "";
        msjARegresar = "----Mano de "+jugador.getNombre()+": "+jugador.obtenerTotalPuntos()+"(pts).---- \n";
        for (Carta carta:jugador.getMano()) {
            msjARegresar += "\t"+carta.getNombreCarta()+"\n";
        }
        return  msjARegresar;
    }


}
