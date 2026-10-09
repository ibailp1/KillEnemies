package org.cuatrovientos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    private static ArrayList<Character> personajes = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nombre;

        System.out.print("Introduce el nombre de tu héroe: ");
        nombre = scanner.nextLine();

        Hero heroe = new Hero(nombre);

        String fichero = nombre + ".dat";

        boolean cargada = cargarPartida(fichero, heroe);

        if (!cargada) {
            System.out.println("\n¡Bienvenido, " + heroe.getNombre() + "!");

            for (int i = 0; i < 5; i++) {
                personajes.add(new Friend());
                personajes.add(new Enemy());
            }

            Collections.shuffle(personajes);
        } else {
            System.out.println("\n¡Bienvenido de nuevo, " + heroe.getNombre() + "!");
        }

        boolean salir = false;

        while (!salir) {
            System.out.println("Este es el listado de personajes:");
            showCharacters();
            mostrarEstadisticas(heroe);

            System.out.println("\nMENÚ");
            System.out.println("1. Atacar a un personaje");
            System.out.println("2. Defender a un personaje");
            System.out.println("3. Guardar partida y salir");

            String txt;
            int opcion = 0;
            boolean esInt;

            do {
                System.out.print("\nElige una opción: ");
                txt = scanner.nextLine();
                try {
                    opcion = Integer.parseInt(txt);
                    esInt = true;
                } catch (NumberFormatException e) {
                    System.out.println("¡Error, debes de introducir un opción válida!");
                    esInt = false;
                }
            } while (!esInt || opcion > 3 || opcion < 1);

            switch (opcion) {
                case 1:
                    atacarPersonaje(scanner, heroe);
                    break;
                case 2:
                    defenderPersonaje(scanner, heroe);
                    break;
                case 3:
                    guardarPartida(fichero, heroe);
                    salir = true;
                    break;
            }

        }

        if (scanner != null) {
            scanner.close();
        }

    }

    private static void showCharacters() {
        if (personajes.isEmpty()) {
            System.out.println("No quedan personajes");
            return;
        }

        int enemigos = 0, amigos = 0;

        for (int i = 0; i < personajes.size(); i++) {
            Character personaje = personajes.get(i);

            System.out.println(i + " - " + personaje.toString());

            if (personaje.isEnemy()) {
                enemigos++;
            } else {
                amigos++;
            }
        }

        System.out.println("Quedan " + enemigos + " enemigos");
        System.out.println("Quedan " + amigos + " amigos");
    }

    private static void mostrarEstadisticas(Hero heroe) {
        System.out.println("Estadísticas:");
        System.out.println("Enemigos matados: " + heroe.getEnemigosMatados());
        System.out.println("Amigos defendidos: " + heroe.getAmigosDenfedidos());
    }

    private static void atacarPersonaje(Scanner scanner, Hero heroe) {
        if (personajes.isEmpty()) {
            System.out.println("No quedan personajes");
            return;
        }

        String txt;
        int opcion = 0;
        boolean esInt;

        do {
            System.out.print("Personaje a atacar: ");
            txt = scanner.nextLine();
            try {
                opcion = Integer.parseInt(txt);
                esInt = true;
            } catch (NumberFormatException e) {
                System.out.println("¡Error, debes de introducir un opción válida!");
                esInt = false;
            }
        } while (!esInt || opcion >= personajes.size() || opcion < 0);

        Character personaje = personajes.get(opcion);

        if (personaje instanceof Enemy) {
            heroe.attack((Enemy) personaje);
            personajes.remove(opcion);
        } else {
            System.out.println("\n¡Has atacado a un amigo!");
            personajes.remove(opcion);
        }
    }

    private static void defenderPersonaje(Scanner scanner, Hero heroe) {
        if (personajes.isEmpty()) {
            System.out.println("No quedan personajes");
            return;
        }

        String txt;
        int opcion = 0;
        boolean esInt;

        do {
            System.out.print("Personaje a defender: ");
            txt = scanner.nextLine();
            try {
                opcion = Integer.parseInt(txt);
                esInt = true;
            } catch (NumberFormatException e) {
                System.out.println("¡Error, debes de introducir un opción válida!");
                esInt = false;
            }
        } while (!esInt || opcion >= personajes.size() || opcion < 0);

        Character personaje = personajes.get(opcion);

        if (personaje instanceof Friend) {
            heroe.defend((Friend) personaje);
        } else {
            System.out.println("\n¡Has defendido a un enemigo!");
            personajes.add(new Enemy());
            System.out.println("\nEl enemigo se ha duplicado");
        }
    }

     private static void guardarPartida(String fichero, Hero heroe) {

        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(fichero))) {

            salida.writeObject(heroe);
            salida.writeObject(personajes);

            System.out.println("Partida guardada correctamente.");

        } catch (IOException e) {
            System.out.println("Error, " + e.getMessage());
        }
    }

    private static boolean cargarPartida(String fichero, Hero heroe) {

        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(fichero))) {

            Hero heroeGuardado = (Hero) entrada.readObject();

            ArrayList<Character> personajesGuardados = (ArrayList<Character>) entrada.readObject();

            heroe.setNombre(heroeGuardado.getNombre());

            heroe.setEnemigosMatados(heroeGuardado.getEnemigosMatados());

            heroe.setAmigosDenfedidos(heroeGuardado.getAmigosDenfedidos());

            personajes = personajesGuardados;

            return true;

        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }
}
