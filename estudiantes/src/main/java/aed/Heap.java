package aed;

import java.util.ArrayList;

public class Heap<T extends Comparable<T>> {
   private T[] elementos;

    public class HandleHeap {
        private T valor;
        private int posicion;

        private HandleHeap(T v, int p) {
            this.valor = v;
            this.posicion = p;
        }

        public T valor() {
            return this.valor;
        }

        public int posicion() {
            return this.posicion;
        }

        public HandleHeap modificarValor(T valor) {
            return modificar(valor, posicion);
        }
    }
    
    public Heap(T[] valores) {
        elementos = valores;
    }

    public ArrayList<Heap<Estudiante>.HandleHeap> obtenerHandles() {
        ArrayList<Heap<Estudiante>.HandleHeap> handles = new ArrayList<Heap<Estudiante>.HandleHeap>(elementos.length);      // O(1)
        for (int i = 0; i < elementos.length; i++) {                                                                        // O(E)
            Heap<Estudiante>.HandleHeap handle = (Heap<Estudiante>.HandleHeap) new HandleHeap(elementos[i], i);             // O(1)
            handles.add(i, handle);                                                                                         // O(1)
        }

        return handles;                                                                                                     // O(1)
    }

    public T desencolar() {
        return elementos[0];
    }

    public HandleHeap encolar(T valor) {
        return new HandleHeap(valor, 0);
    }

    private HandleHeap modificar(T valor, int posicion) {
        return new HandleHeap(valor, posicion);
    }

    public void eliminar(int posicion) {

    }
}
