/*
Omar Garcia
Reproductor de musica 1.0
-Menu de usuario para poder llamar a todos los metodos
-Se agregan multiples casos de acuerdo a los metodos establecidos en Reproductor
-Huele a limon
* */
package ReproductorDeMusica;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Implementar scanner para tomar datos de usuario
        Scanner scanner = new Scanner(System.in);
        Reproductor reproductor = new Reproductor();

        mostrarHelp();

        boolean ejecuntando = true;
//        Ciclo para el reproductor
        while (ejecuntando) {
            System.out.print("\n> ");
            String linea = scanner.nextLine().trim();

            if (linea.isEmpty()) continue;

            String[] partes = linea.split(" ", 2);
            String comando = partes[0].toLowerCase();
            String argumento = partes.length > 1 ? partes[1].trim() : "";
        // Comandos del reproductor, solo papa dios y yo con dos horas de sueño saben como funciona
            switch (comando) {
                case "add":
                    if (!argumento.isEmpty()) reproductor.add(argumento);
                    else System.out.println("Uso: add <Artista - Título>");
                    break;

                case "insert":
                    String[] datosInsert = argumento.split(" ", 2);
                    if (datosInsert.length == 2) {
                        try {
                            int pos = Integer.parseInt(datosInsert[0]);
                            reproductor.insert(pos, datosInsert[1]);
                        } catch (NumberFormatException e) {
                            System.out.println("La posición debe ser un número entero.");
                        }
                    } else {
                        System.out.println("Uso: insert <pos> <Artista - Título>");
                    }
                    break;

                case "remove":
                    try {
                        int pos = Integer.parseInt(argumento);
                        reproductor.remove(pos);
                    } catch (NumberFormatException e) {
                        System.out.println("Uso: remove <posicion_num>");
                    }
                    break;

                case "play":
                    try {
                        int pos = Integer.parseInt(argumento);
                        reproductor.play(pos);
                    } catch (NumberFormatException e) {
                        System.out.println("Uso: play <posicion_num>");
                    }
                    break;

                case "next":
                    reproductor.next();
                    break;

                case "prev":
                    reproductor.prev();
                    break;

                case "queue":
                    if (!argumento.isEmpty()) reproductor.queue(argumento);
                    else System.out.println("Uso: queue <Artista - Título>");
                    break;

                case "back":
                    reproductor.back();
                    break;

                case "list":
                    if (argumento.equalsIgnoreCase("reverse")) {
                        reproductor.listReverse();
                    } else {
                        reproductor.list();
                    }
                    break;

                case "now":
                    reproductor.now();
                    break;

                case "help":
                    mostrarHelp();
                    break;

                case "exit":
                    ejecuntando = false;
                    System.out.println("Saliendo del reproductor...");
                    break;

                default:
                    System.out.println("Comando no reconocido. Escribe 'help' para ver los comandos disponbles.");
                    break;
            }
        }
        scanner.close();
    }

    private static void mostrarHelp() {
        System.out.println("\n--- COMANDOS DISPONIBLES ---");
        System.out.println("add              : agrega una cancino al final de la playlist");
        System.out.println("insert       : inserta en una posición especifica");
        System.out.println("remove                 : elimina de la playlist");
        System.out.println("play                   : empieza a sonar en esa posición");
        System.out.println("next / prev                 : avanza o retrocede en la playlist");
        System.out.println("queue             : encola para sonar a continuación");
        System.out.println("back                        : regresa a la canción anterior del historial");
        System.out.println("list                        : imprime la playlist marcando la actual");
        System.out.println("list reverse                : imprime la playlist en orden inverso (recursivo)");
        System.out.println("now                         : imprime estado (sonando, cola e historial)");
        System.out.println("help / exit                 : ayuda / salir");
    }
}