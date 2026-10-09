package org.cuatrovientos;

public class Hero implements Character {
    private String nombre;
    private int enemigosMatados, amigosDenfedidos;

    public Hero(String nombre) {
        this.nombre = nombre;
        this.enemigosMatados = 0;
        this.amigosDenfedidos = 0;
    }

    @Override
    public boolean isEnemy() {
        return false;
    }

    public void attack(Enemy enemy) {
        System.out.println("\n¡He atacado a un enemigo!");
        enemy.kill();
        this.enemigosMatados++;
    }

    public void defend(Friend friend) {
        System.out.println("\n¡He defendido a un amigo!");
        friend.heal();
        this.amigosDenfedidos++;
    }

    public int getEnemigosMatados() {
        return enemigosMatados;
    }

    public void setEnemigosMatados(int enemigosMatados) {
        this.enemigosMatados = enemigosMatados;
    }

    public int getAmigosDenfedidos() {
        return amigosDenfedidos;
    }

    public void setAmigosDenfedidos(int amigosDenfedidos) {
        this.amigosDenfedidos = amigosDenfedidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
