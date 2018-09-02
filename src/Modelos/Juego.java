package Modelos;

import javax.swing.text.html.parser.Parser;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Juego {

    //Atributos
    private Integer numeroJugadores = 0;
    private Jugador[] jugadores;
    private Scanner scn = new Scanner(System.in);
    private Croupier croupier;

    public Juego(){
        croupier = new Croupier();
        this.iniciarJuego();
    }
    //Método para solicitar cantidad de jugadores.
    public void iniciarJuego(){
        System.out.print("\033[34m");
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*- BlackJack  *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println(" ");
        do{
            System.out.print("\033[30m");
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
        jugar();
    }
    private void pideDatosJugadores(){
        this.jugadores = new Jugador[numeroJugadores];
        for(int i=0 ; i<jugadores.length ; i++){
            System.out.print("Nombre del jugador "+(i+1)+": ");
            jugadores[i] = new Jugador(scn.next());
            System.out.println(" ");
        }
    }

    //MÉTODO QUE SOLICITA LOS DATOS DE LOS JUGADORES, LLEVA EL FLUJO DEL JUEGO Y SE TOMA EN CUENTA LAS VALIDACIONES DE LAS REGLAS
    private void jugar() {
        System.out.println("\033[34m");
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*- INICIA JUEGO  -*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println("\033[30m");
        croupier.barajear();
        this.pideDatosJugadores();
        croupier.repartoInicial(jugadores);
        for (Jugador jugador:jugadores) {
            System.out.println(this.obtenerSalidaMano(jugador));
        }
        //**************IMPRESION DEL JUEGO DE CROUPIER
        System.out.println(this.obtenerSalidaMano(croupier));
        //AQUI EL CLOUPIER VALIDA LAS MANOS DE CADA JUGADOR Y TOMA DECISIONES.
        for(int i=0 ; i<jugadores.length ; i++){
            System.out.print("\033[34m");
            System.out.println("-------------------------------CROUPIER DICE-----------------------------");
            System.out.println("\033[30m");
            if(jugadores[i].getEstatus() == EstatusJugador.JUGANDO){
                if(croupier.tieneBlackJack(jugadores[i])){
                    System.out.println(jugadores[i].getNombre()+" Tiene Blac-Jack!");
                }else{
                    if(croupier.jugadorPuedeSeguir(jugadores[i])) {
                        if(croupier.tieneVeintyUno(jugadores[i])) {
                            System.out.println("Tiene 21 !");
                            //Si no tienes 21 pero puedes seguir
                        }else {
                            boolean resp = false;
                            do{
                                croupier.evaluarAs(jugadores[i]);
                                if(croupier.otraCarta(jugadores[i])){
                                    croupier.evaluarAs(jugadores[i]);
                                    System.out.print("\033[34m");
                                    System.out.println("---------------------------------ATENCION--------------------------------");
                                    System.out.println("\033[30m");
                                    System.out.println("El jugador: "+jugadores[i].getNombre()+" tiene el juego: \n");
                                    Carta cartaRecibo = croupier.obtenerCartaARepartir();
                                    jugadores[i].mano.add(cartaRecibo);
                                    for (Carta carta:jugadores[i].getMano()) {
                                        System.out.println(carta.getNombreCarta());
                                    }
                                    //Por si no entra al de 21
                                    if(croupier.tieneVeintyUno(jugadores[i])){
                                        System.out.print("Tiene 21 !! \n");
                                    }
                                    System.out.println("");
                                }else if(jugadores[i].obtenerTotalPuntos()>=21){
                                        croupier.evaluarAs(jugadores[i]);
                                        if(jugadores[i].obtenerTotalPuntos() == 21){
                                            System.out.println("Tiene 21 !");
                                        }else{
                                            System.out.println("Perdiste "+jugadores[i].getNombre());
                                            jugadores[i].setEstatus(EstatusJugador.PERDIDO);
                                        }
                                        break;
                                        } else if(!croupier.jugadorPuedeSeguir(jugadores[i])){
                                            break;
                                        }
                                else if(croupier.tieneVeintyUno(jugadores[i])) {
                                    System.out.println(jugadores[i].getNombre()+" Tiene 21 puntos!");
                                }
                                else{
                                    jugadores[i].setEstatus(EstatusJugador.PLANTADO);
                                }
                            }while (croupier.jugadorPuedeSeguir(jugadores[i]) && jugadores[i].getEstatus() == EstatusJugador.JUGANDO);
                        }
                    }
                }
            }
            //CUANDO SEA LA ULTIMA CARTA CHECAR CONTRA QUIEN GANÓ O PERDIÓ EL CROUPIER Y MUESTRA LOS RESULTADOS
            if(i+1  == jugadores.length){
                croupier.evaluarAs(croupier);
                if(croupier.tieneBlackJack(croupier)){
                    System.out.println("\033[36mTiene Blac-Jack!\033[30m");
                }else if(croupier.tieneVeintyUno(croupier)){
                    System.out.println("Tiene 21!");
                }else{
                    while(croupier.obtenerTotalPuntos()<17){
                        boolean hayJugadoresJugando = false;
                        for(Jugador jugador : jugadores){
                            if(jugador.getEstatus() == EstatusJugador.JUGANDO || jugador.getEstatus() == EstatusJugador.PLANTADO){
                                hayJugadoresJugando = true;
                            }
                        }
                        if(hayJugadoresJugando){
                            croupier.otraCarta(croupier);
                            Carta cartaRecibo = croupier.obtenerCartaARepartir();
                            croupier.mano.add(cartaRecibo);
                        }else{
                            break;
                        }
                    };
                }
                mostrarResultadosCroupier();
                croupier.evaluarAs(croupier);
                System.out.println("Puntos del croupier \033[33m"+croupier.obtenerTotalPuntos()+"\033[30m");
                //System.out.println(("Puntos del croupier "+croupier.obtenerTotalPuntos()).toUpperCase());
                System.out.println("\033[34m");
                System.out.println("---------------------------TABLA DE RESULTADOS---------------------------");
                System.out.println("\033[30m");
                for(int j=0;j<jugadores.length;j++){
                    croupier.evaluarAs(jugadores[i]);
                    if(jugadores[j].obtenerTotalPuntos()<=21){
                        System.out.println(croupier.validarJugada(jugadores[j])+" con "+jugadores[j].getNombre().toUpperCase());
                    }else if(jugadores[j].obtenerTotalPuntos()>21){
                        System.out.println("\033[32mGana Croupier\033[30m con "+jugadores[j].getNombre().toUpperCase());
                    }else{
                        if(croupier.obtenerTotalPuntos()<=21)
                        {
                            System.out.println("\033[32mGana Croupier\033[30m con "+jugadores[j].getNombre().toUpperCase());
                        }
                        else{
                            System.out.println("\033[31mPierde Croupier con\033[30m "+jugadores[j].getNombre().toUpperCase());
                        }
                    }
                }
            }
        }
    }

    private void mostrarResultadosCroupier() {
        System.out.print("\033[34m");
        System.out.println("---------------------------MANO FINAL CROUPIER---------------------------");
        System.out.println("\033[30m");
        String msj = "";
        for (Carta carta:croupier.getMano()) {
            msj += "\t"+carta.getNombreCarta()+"\n";
        }
        System.out.print(msj);
    }

    //MÉTODO QUE REGRESA UN STRING CON LAS CARTAS QUE TIENE EL JUGADOR QUE SE MANDA POR PARAMETRO
    private String obtenerSalidaMano(Jugador jugador){
        croupier.evaluarAs(jugador);
        String msjARegresar = "";
        msjARegresar = "----Mano de "+jugador.getNombre()+": \033[33m"+jugador.obtenerTotalPuntos()+"\033[30m(pts)---- \n";
        for (Carta carta:jugador.getMano()) {
            msjARegresar += "\t"+carta.getNombreCarta()+"\n";
        }
        return  msjARegresar;
    }

    public int getNumeroJugadores() {
        return numeroJugadores;
    }
    public void setNumeroJugadores(Integer numeroJugadores) {
        this.numeroJugadores = numeroJugadores;
    }

    public Jugador[] getJugadores() {
        return jugadores;
    }
    public void setJugadores(Jugador[] jugadores) {
        this.jugadores = jugadores;
    }

    public Croupier getCroupier() {
        return croupier;
    }
    public void setCroupier(Croupier croupier) {
        this.croupier = croupier;
    }
}
