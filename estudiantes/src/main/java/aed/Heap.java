package aed;

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

        public HandleHeap modificarValor(T valor) {
            return modificar(valor, posicion);
        }
    }
    
    public Heap(T[] valores) {
        elementos = new T[valores.length];                                  // O(1)
        for (int i = 0; i < elementos.length; i++) {                        // O(E)
            elementos[i] = valores[i];                                      // O(1)
        }
    }

    public HandleHeap[] obtenerHandles() {
        HandleHeap[] handles = new HandleHeap[elementos.length];
        for (int i = 0; i < elementos.length; i++) {                        // O(E)
            HandleHeap handle = new HandleHeap(elementos[i], i);            // O(1)
            handles[i] = handle;                                            // O(1)
        }

        return handles;
    }

    public T desencolar() {
        return elementos[0];
    }

    public HandleHeap encolar() {

    }

    private HandleHeap modificar(T valor, int posicion) {

    }
}
