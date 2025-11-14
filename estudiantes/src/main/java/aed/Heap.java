package aed;

import java.util.ArrayList;

public class Heap<T extends Comparable<T>> {
   private ArrayList<HandleHeap> elementos;

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

        public void modificarValor() {
            modificar(posicion);
        }
    }
    
    public Heap(T[] valores) {
        elementos = new ArrayList<HandleHeap>(valores.length);              // O(1)
        for (int i = 0; i < valores.length; i++) {                          // O(E)
            HandleHeap handle = new HandleHeap(valores[i], i);              // O(1)
            elementos.add(i, handle);                                       // O(1)
        }
    }

    public ArrayList<Heap<Estudiante>.HandleHeap> obtenerHandles() {
        ArrayList<Heap<Estudiante>.HandleHeap> handles = new ArrayList<Heap<Estudiante>.HandleHeap>(elementos.size());      // O(1)
        for (int i = 0; i < elementos.size(); i++) {                                                                        // O(E)
            Heap<Estudiante>.HandleHeap handle = (Heap<Estudiante>.HandleHeap) elementos.get(i);                            // O(1)
            handles.add(i, handle);                                                                                         // O(1)
        }

        return handles;                                                                                                     // O(1)
    }

    private void intercambiarPosiciones(HandleHeap h1, HandleHeap h2) {
        int tempPosicion = h1.posicion;                 // O(1)
        h1.posicion = h2.posicion;                      // O(1)
        h2.posicion = tempPosicion;                     // O(1)
        elementos.set(h1.posicion, h1);                 // O(1)
        elementos.set(h2.posicion, h2);                 // O(1)
    }

    private HandleHeap obtenerHijoIzquierdo(int posicion) {
        int posicionHijoIzq = (2 * posicion) + 1;                                                           // O(1)
        HandleHeap hijoIzq = posicionHijoIzq < elementos.size() ? elementos.get(posicionHijoIzq) : null;    // O(1)
        
        return hijoIzq;                                                                                     // O(1)
    }

    private HandleHeap obtenerHijoDerecho(int posicion) {
        int posicionHijoDer = (2 * posicion) + 2;                                                           // O(1)
        HandleHeap hijoDer = posicionHijoDer < elementos.size() ? elementos.get(posicionHijoDer) : null;    // O(1)

        return hijoDer;                                                                                     // O(1)
    }

    private HandleHeap obtenerPadre(int posicion) {
        int posicionPadre = (int) Math.floor((posicion - 1) / 2);                                           // O(1)
        HandleHeap padre = posicionPadre >= 0 ? elementos.get(posicionPadre) : null;                        // O(1)

        return padre;                                                                                       // O(1)
    }

    public T desencolar() {
        HandleHeap handleADesencolar = elementos.get(0);                             // O(1)
        HandleHeap ultimoEnCola = elementos.get(elementos.size() - 1);                      // O(1)

        elementos.remove(ultimoEnCola.posicion);                                            // O(1)

        if (ultimoEnCola.posicion == 0) {                                                   // O(1)
            return handleADesencolar.valor;                                                 // O(1)
        }

        ultimoEnCola.posicion = 0;                                                          // O(1)
        elementos.set(0, ultimoEnCola);                                               // O(1)

        siftDown(ultimoEnCola);                                                             // O(log(E))

        return handleADesencolar.valor;                                                     // O(1)
    }

    public HandleHeap encolar(T valor) {
        HandleHeap handle = new HandleHeap(valor, elementos.size());                        // O(1)
        elementos.add(handle);                                                              // O(1)

        siftUp(handle);                                                                     // O(log(E))

        return handle;                                                                      // O(1)
    }

    private void modificar(int posicion) {  
        HandleHeap handleAModificar = elementos.get(posicion);                              // O(1)

        siftUp(handleAModificar);                                                           // O(log(E))

        siftDown(handleAModificar);                                                         // O(log(E))
    }

    private void siftUp(HandleHeap handle) {
        HandleHeap padre = obtenerPadre(handle.posicion);                                               // O(1)
        boolean padreTieneMenosPrioridad = padre != null && handle.valor.compareTo(padre.valor) > 0;    // O(1)

        while (padreTieneMenosPrioridad) {                                                              // O(log(E)). Hay como mucho log E swaps.
            intercambiarPosiciones(handle, padre);                                                      // O(1)
            
            padre = obtenerPadre(handle.posicion);                                                      // O(1)

            padreTieneMenosPrioridad = padre != null && handle.valor.compareTo(padre.valor) > 0;        // O(1)
        }
    }

    private void siftDown(HandleHeap handle) {
        HandleHeap hijoIzq = obtenerHijoIzquierdo(handle.posicion);             // O(1)
        HandleHeap hijoDer = obtenerHijoDerecho(handle.posicion);               // O(1)
        
        boolean existeHijoConMasPrioridad = (hijoIzq != null && hijoIzq.valor.compareTo(handle.valor) > 0) || (hijoDer != null && hijoDer.valor.compareTo(handle.valor) > 0);       // O(1)

        while (existeHijoConMasPrioridad) {                                     // O(log(E)). Hay como mucho log E swaps.
            HandleHeap hijoPrioritario = hijoDer == null ? hijoIzq : hijoIzq.valor.compareTo(hijoDer.valor) > 0 ? hijoIzq : hijoDer;            // O(1)
            intercambiarPosiciones(handle, hijoPrioritario);                                                                                    // O(1)
            
            hijoIzq = obtenerHijoIzquierdo(handle.posicion);                                                                                    // O(1)
            hijoDer = obtenerHijoDerecho(handle.posicion);                                                                                      // O(1)

            existeHijoConMasPrioridad = (hijoIzq != null && hijoIzq.valor.compareTo(handle.valor) > 0) || (hijoDer != null && hijoDer.valor.compareTo(handle.valor) > 0);       // O(1)
        }
    }
}
