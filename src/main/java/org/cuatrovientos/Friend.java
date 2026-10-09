package org.cuatrovientos;

public class Friend implements Character{

    public void heal() {
        System.out.println("¡Te he curado!");
    }
    
    @Override
    public boolean isEnemy() {
       return false;
    }

    @Override
    public String toString() {
        return "Amigo";
    }

}
