package Modelos;

import java.util.ArrayList;

public class Jugador {
    private String nombre="";
    ArrayList<Carta> mano  = new ArrayList<Carta>();

    public Jugador(){ }
    public Jugador(String nombre){
        this.setNombre(nombre);
    }
    public String getNombre() {
        return nombre.toUpperCase();
    }
    protected void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public ArrayList<Carta> getMano() {
        return mano;
    }
    protected void setMano(ArrayList<Carta> mano) {
        this.mano = mano;
    }

    public int obtenerTotalPuntos(){
        int sumaTotal = 0;
        for (Carta carta: this.getMano()) {
            sumaTotal += carta.getValor();
        }
        return  sumaTotal;
    }



}
