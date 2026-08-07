/* Omar Garcia
Reproductor de musica 1.0
En este apartado se crean los objetos nodo en los que se basa todo el progrma*/

package ReproductorDeMusica;

public class Nodo {
    String cancion;
    Nodo sig;
    Nodo ant;

    public Nodo(String cancion) {
        this.cancion = cancion;
        this.sig = null;
        this.ant = null;
    }
}
