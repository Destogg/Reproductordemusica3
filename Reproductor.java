/*Omar Garcia
Sistema de reproduccion de musica 1.0
Se utilizan listas enlazadas, pilas y colas para el guardado y reproduccion de musica
El reprouctor puede añadir, reproducir, eliminar y mostar la lista de canciones
Huele a limon

*/
package ReproductorDeMusica;

//Clase utilizadas - No mover o solo papa dios sabra que hacer D:
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class Reproductor {
//    Llamando objetos de Nodo
    private Nodo cabeza;
    private Nodo colaPlaylist;
    private Nodo actual;
    private int tamaño;

    // Cola para reproducciones pendientes (play next)
    private Deque<String> colaReproduccion;

    // Pila para el historial de canciones escuchadas
    private Stack<String> historial;

    public Reproductor() {
        this.cabeza = null;
        this.colaPlaylist = null;
        this.actual = null;
        this.tamaño = 0;
        this.colaReproduccion = new ArrayDeque<>();
        this.historial = new Stack<>();
    }

    // add : agrega al final de la playlist
    public void add(String cancion) {
        Nodo nuevo = new Nodo(cancion);
        if (cabeza == null) {
            cabeza = nuevo;
            colaPlaylist = nuevo;
            actual = cabeza; // La primera canción agregada es la actual por defecto d:
        } else {
            colaPlaylist.sig = nuevo;
            nuevo.ant = colaPlaylist;
            colaPlaylist = nuevo;
        }
        tamaño++;
        System.out.println("Agregada a la playlist: " + cancion);
    }

    // insert: Elige cancion por posición (base 1)
    public void insert(int pos, String cancion) {
        if (pos < 1 || pos > tamaño + 1) {
            System.out.println("Posición inválida.");
            return;
        }

        Nodo nuevo = new Nodo(cancion);

        if (pos == 1) {
            if (cabeza == null) {
                cabeza = nuevo;
                colaPlaylist = nuevo;
                actual = cabeza;
            } else {
                nuevo.sig = cabeza;
                cabeza.ant = nuevo;
                cabeza = nuevo;
            }
        } else if (pos == tamaño + 1) {
            add(cancion);
            return;
        } else {
            Nodo temp = cabeza;
            for (int i = 1; i < pos - 1; i++) {
                temp = temp.sig;
            }
            nuevo.sig = temp.sig;
            nuevo.ant = temp;
            temp.sig.ant = nuevo;
            temp.sig = nuevo;
        }
        tamaño++;
        System.out.println("Insertada en posición " + pos + ": " + cancion);
    }

    // remove <pos>: elimina de la playlist por posición (base 1)
    public void remove(int pos) {
        if (cabeza == null || pos < 1 || pos > tamaño) {
            System.out.println("Posición inválida o playlist vacía.");
            return;
        }

        Nodo temp = cabeza;
// En caso de borrar algo, la siguiente posicion pasa a tomar el lugar de la anterior
        if (pos == 1) {
            if (actual == cabeza) actual = cabeza.sig;
            cabeza = cabeza.sig;
            if (cabeza != null) cabeza.ant = null;
            else colaPlaylist = null;
        } else {
            for (int i = 1; i < pos; i++) {
                temp = temp.sig;
            }
            if (actual == temp) actual = (temp.sig != null) ? temp.sig : temp.ant;

            if (temp.sig != null) temp.sig.ant = temp.ant;
            else colaPlaylist = temp.ant;

            if (temp.ant != null) temp.ant.sig = temp.sig;
        }

        tamaño--;
        System.out.println("Canción en posición " + pos + " eliminada.");
    }

    // play <pos>: empieza a sonar en esa posición
    public void play(int pos) {
        if (pos < 1 || pos > tamaño) {
            System.out.println("Posición fuera de rango.");
            return;
        }

        if (actual != null) {
            historial.push(actual.cancion);
        }

        Nodo temp = cabeza;
        for (int i = 1; i < pos; i++) {
            temp = temp.sig;
        }
        actual = temp;
        System.out.println("Reproduciendo ahora: " + actual.cancion);
    }

    // next: avanza a la siguiente cancion
    public void next() {
        if (actual != null) {
            historial.push(actual.cancion);
        }

        // Si hay algo en la cola antes, se reproduce primero
        if (!colaReproduccion.isEmpty()) {
            String siguienteCola = colaReproduccion.poll();
            System.out.println("Reproduciendo desde la cola: " + siguienteCola);
            return;
        }

        // Si no hay nada, avanza en la playlist
        if (actual != null && actual.sig != null) {
            actual = actual.sig;
            System.out.println("Reproduciendo ahora: " + actual.cancion);
        } else {
            System.out.println("Fin de la playlist.");
        }
    }

    // prev: retrocede en la playlist
    public void prev() {
        if (actual != null && actual.ant != null) {
            historial.push(actual.cancion);
            actual = actual.ant;
            System.out.println("Reproduciendo ahora: " + actual.cancion);
        } else {
            System.out.println("No hay canción anterior en la playlist.");
        }
    }

    // queue <cancion>: encola para sonar a continuación
    public void queue(String cancion) {
        colaReproduccion.offer(cancion);
        System.out.println("Canción encolada para sonar a continuación: " + cancion);
    }

    // back: regresa a la canción anterior del historial
    public void back() {
        if (!historial.isEmpty()) {
            String anterior = historial.pop();
            System.out.println("Regresando a la canción anterior del historial: " + anterior);
        } else {
            System.out.println("El historial está vacío.");
        }
    }

    // list: imprime la playlist marcando la actual con flechita
    public void list() {
        if (cabeza == null) {
            System.out.println("La playlist está vacía.");
            return;
        }

        System.out.println("--- Playlist ---");
        Nodo temp = cabeza;
        int index = 1;
        while (temp != null) {
            String marca = (temp == actual) ? " -> " : "    ";
            System.out.println(index + "." + marca + temp.cancion);
            temp = temp.sig;
            index++;
        }
    }

    // list reverse: imprime la playlist en orden inverso usando recursión (Requisito del PDF)
    public void listReverse() {
        if (colaPlaylist == null) {
            System.out.println("La playlist está vacía.");
            return;
        }
        System.out.println("--- Playlist (Orden inverso) ---");
        imprimirInversoRecursivo(colaPlaylist, tamaño);
    }

    private void imprimirInversoRecursivo(Nodo nodo, int pos) {
        if (nodo == null) return;

        String marca = (nodo == actual) ? " -> " : "    ";
        System.out.println(pos + "." + marca + nodo.cancion);
        imprimirInversoRecursivo(nodo.ant, pos - 1);
    }

    // now: imprime estado actual (sonando, cola e historial)
    public void now() {
        System.out.println("\n--- ESTADO ACTUAL ---");
        System.out.println("Sonando: " + (actual != null ? actual.cancion : "Ninguna"));
        System.out.println("Cola (Play next): " + colaReproduccion);
        System.out.println("Historial (Pila): " + historial);
    }
}
