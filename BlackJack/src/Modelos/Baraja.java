package Modelos;

import java.util.ArrayList;

    public class Baraja {

        static ArrayList<Carta> cartas = new ArrayList<Carta>();


        public static void crearBaraja() {
            for (Figura figura: Figura.values()) {
                for(int i=0 ; i<13 ; i++){
                    Carta c = new Carta();
                    c.setNumero(i+1);
                    c.setFigura(figura);
                    c.valor = i+1;
                    if(c.getValor()>10){
                        c.setValor(10);
                    }
                    cartas.add(c);
                }
            }
        }


    }
