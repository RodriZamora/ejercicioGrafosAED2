package grafo;

import lista.ILista;
import lista.Lista;

public class Grafo {
    private Arista[][] aristas;
    private Vertice[] vertices;
    private int cantidad;
    private final int maxVertices;
    private final boolean dirigido;


    public Grafo(int cantMaxVertices, boolean esDirigido) {
        cantidad = 0;
        vertices = new Vertice[cantMaxVertices];
        aristas = new Arista[cantMaxVertices][cantMaxVertices];
        if (esDirigido) {
            for (int i = 0; i < aristas.length; i++) {
                for (int j = 0; j < aristas.length; j++) {
                    aristas[i][j] = new Arista();
                }
            }
        } else {
            for (int i = 0; i < aristas.length; i++) {
                for (int j = i; j < aristas.length; j++) {
                    Arista arista = new Arista();
                    aristas[i][j] = arista;
                    aristas[j][i] = arista; // Asignar la misma arista en ambas direcciones
                }
            }
        }
        maxVertices = cantMaxVertices;
        dirigido = esDirigido;
    }

    public void agregarVertice(Vertice vertice) {

        if (cantidad < maxVertices) {
            int posLibre = obtenerPosLibre();
            vertices[posLibre] = vertice;
            cantidad++;
        }
    }

    public void borrarVertice(int vertice) {
        vertices[vertice] = false;

        for (int i = 1; i < aristas.length; i++) {
            aristas[vertice][i] = 0; //Borro aristas adyacentes
            aristas[i][vertice] = 0; //Borro aristas incidentes
        }
        cantidad--;
    }

    public void agregarArista(Vertice vInicio, Vertice vFinal, Arista arista) {
        int posVinicial = obtenerPos(vInicio);
        int posVfinal = obtenerPos(vFinal);
        Arista a = aristas[posVinicial][posVfinal];
        a.setPeso(arista.getPeso());

    }



    public void borrarArista(int vInicio, int vFinal) {
        aristas[vInicio][vFinal] = 0;
        if (!dirigido) {
            aristas[vFinal][vInicio] = 0;
        }
    }

    public int obtenerArista(int vInicio, int vFinal) {
        return aristas[vInicio][vFinal];
    }

    public ILista<Integer> adyacentes(int vertice) {
        ILista<Integer> adyacentes = new Lista<>();
        for (int i = 1; i < aristas.length; i++) {
            if (aristas[vertice][i] > 0) {
                adyacentes.insertar(i);
            }
        }
        return adyacentes;
    }


    //Metodos privados

    private int obtenerPosLibre() {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i] == null) {
                return i;
            }
        }
        return -1; // No hay espacio libre
    }

    private int obtenerPos(Vertice v) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i] != null && vertices[i].equals(v)) {
                return i;
            }
        }
        return -1; // No se encontró el vértice
    }
}
