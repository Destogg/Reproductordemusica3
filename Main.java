/*
Omar Garcia
Reproductor de musica 1.1
-Menu de usuario para poder llamar a todos los metodos
-Se agregan multiples casos de acuerdo a los metodos establecidos en Reproductor
-Huele a limon
-Se ha simplificado el menu
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
                case "añadir":
                    if (!argumento.isEmpty()) reproductor.add(argumento);
                    else System.out.println("Uso: add <Artista - Título>");
                    break;

                case "insertar":
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

                case "eliminar":
                    try {
                        int pos = Integer.parseInt(argumento);
                        reproductor.remove(pos);
                    } catch (NumberFormatException e) {
                        System.out.println("Uso: remove <posicion_num>");
                    }
                    break;

                case "reproducir":
                    try {
                        int pos = Integer.parseInt(argumento);
                        reproductor.play(pos);
                    } catch (NumberFormatException e) {
                        System.out.println("Uso: play <posicion_num>");
                    }
                    break;

                case "siguiente":
                    reproductor.next();
                    break;

                case "anterior":
                    reproductor.prev();
                    break;

                case "cola":
                    if (!argumento.isEmpty()) reproductor.queue(argumento);
                    else System.out.println("Uso: queue <Artista - Título>");
                    break;

                case "regresar":
                    reproductor.back();
                    break;

                case "lista":
                    if (argumento.equalsIgnoreCase("reversa")) {
                        reproductor.listReverse();
                    } else {
                        reproductor.list();
                    }
                    break;

                case "historial":
                    reproductor.now();
                    break;

                case "ayuda":
                    mostrarHelp();
                    break;

                case "salir":
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
        System.out.println("añadir -> ");
        System.out.println("insertar ->");
        System.out.println("eliminar ->");
        System.out.println("reproducir ->");
        System.out.println("siguiente / anterior ->");
        System.out.println("cola ->");
        System.out.println("regresar ->");
        System.out.println("lista ->");
        System.out.println("lista reversa ->");
        System.out.println("historial ->");
        System.out.println("ayuda / salir ->");
    }
}