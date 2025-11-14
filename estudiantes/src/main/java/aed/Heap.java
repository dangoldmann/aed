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
        int tempPosicion = h1.posicion;
        h1.posicion = h2.posicion;
        h2.posicion = tempPosicion;
        elementos.set(h1.posicion, h1);
        elementos.set(h2.posicion, h2);
    }

    private HandleHeap obtenerHijoIzquierdo(int posicion) {
        int posicionHijoIzq = (2 * posicion) + 1;
        HandleHeap hijoIzq = posicionHijoIzq < elementos.size() ? elementos.get(posicionHijoIzq) : null;
        
        return hijoIzq;
    }

    private HandleHeap obtenerHijoDerecho(int posicion) {
        int posicionHijoDer = (2 * posicion) + 2;
        HandleHeap hijoDer = posicionHijoDer < elementos.size() ? elementos.get(posicionHijoDer) : null;

        return hijoDer;
    }

    private HandleHeap obtenerPadre(int posicion) {
        int posicionPadre = (int) Math.floor((posicion - 1) / 2);
        HandleHeap padre = posicionPadre >= 0 ? elementos.get(posicionPadre) : null;

        return padre;
    }

    public T desencolar() {
        HandleHeap handleADesencolar = elementos.get(0);
        HandleHeap ultimoEnCola = elementos.get(elementos.size() - 1);

        elementos.remove(ultimoEnCola.posicion);

        if (ultimoEnCola.posicion == 0) {
            return handleADesencolar.valor;
        }

        ultimoEnCola.posicion = 0;
        elementos.set(0, ultimoEnCola);

        siftDown(ultimoEnCola);

        return handleADesencolar.valor;
    }

    public HandleHeap encolar(T valor) {
        HandleHeap handle = new HandleHeap(valor, elementos.size());
        elementos.add(handle);

        siftUp(handle);

        return handle;
    }

    private void modificar(int posicion) {
        HandleHeap handleAModificar = elementos.get(posicion);

        siftUp(handleAModificar);

        siftDown(handleAModificar);
    }

    private void siftUp(HandleHeap handle) {
        HandleHeap padre = obtenerPadre(handle.posicion);
        boolean padreTieneMenosPrioridad = padre != null && handle.valor.compareTo(padre.valor) > 0;

        while (padreTieneMenosPrioridad) {
            intercambiarPosiciones(handle, padre);
            
            padre = obtenerPadre(handle.posicion);

            padreTieneMenosPrioridad = padre != null && handle.valor.compareTo(padre.valor) > 0;
        }
    }

    private void siftDown(HandleHeap handle) {
        HandleHeap hijoIzq = obtenerHijoIzquierdo(handle.posicion);
        HandleHeap hijoDer = obtenerHijoDerecho(handle.posicion);
        
        boolean existeHijoConMasPrioridad = (hijoIzq != null && hijoIzq.valor.compareTo(handle.valor) > 0) || (hijoDer != null && hijoDer.valor.compareTo(handle.valor) > 0);

        while (existeHijoConMasPrioridad) {
            HandleHeap hijoPrioritario = hijoDer == null ? hijoIzq : hijoIzq.valor.compareTo(hijoDer.valor) > 0 ? hijoIzq : hijoDer;
            intercambiarPosiciones(handle, hijoPrioritario);
            
            hijoIzq = obtenerHijoIzquierdo(handle.posicion);
            hijoDer = obtenerHijoDerecho(handle.posicion);

            existeHijoConMasPrioridad = (hijoIzq != null && hijoIzq.valor.compareTo(handle.valor) > 0) || (hijoDer != null && hijoDer.valor.compareTo(handle.valor) > 0);
        }
    }
}
