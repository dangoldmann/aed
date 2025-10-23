package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> {
    private Nodo raiz;
    private int cardinal;

    private class Nodo {
        T valor;
        Nodo izq;
        Nodo der;
        Nodo padre;

        Nodo(T v, Nodo p) {
            valor = v;
            padre = p;
            izq = null;
            der = null; 
        }
    }

    public ABB() {
        raiz = null;
        cardinal = 0;
    }

    public int cardinal() {
        return cardinal;
    }

    public T minimo(){
        Nodo actual = raiz;
        boolean hayAnterior = raiz.izq != null;

        while (hayAnterior) {
            if (actual.izq != null) {
                actual = actual.izq;
            } else {
                hayAnterior = false;
            }
        }

        return actual.valor;
    }

    public T maximo(){
        Nodo actual = raiz;
        boolean haySiguiente = raiz.der != null;

        while (haySiguiente) {
            if (actual.der != null) {
                actual = actual.der;
            } else {
                haySiguiente = false;
            }
        }

        return actual.valor;
    }

    private Nodo buscarNodo(T elem) {
        Nodo actual = raiz;

        while (actual != null) {
            int valorComparacion = elem.compareTo(actual.valor);
            if (valorComparacion == 0) break;
            if (valorComparacion > 0) {
                if (actual.der != null) {
                    actual = actual.der;
                } else break;
            }
            if (valorComparacion < 0) {
                if (actual.izq != null) {
                    actual = actual.izq;
                } else break;
            }
        }

        return actual;
    }

    public void insertar(T elem){
        if (raiz == null) {
            raiz = new Nodo(elem, null);
            cardinal = 1;
            return;
        }

        Nodo ultimoNodoBuscado = buscarNodo(elem);
        int valorComparacion = elem.compareTo(ultimoNodoBuscado.valor);
        if (valorComparacion == 0) return;
        if (valorComparacion > 0) {
            Nodo nuevoNodo = new Nodo(elem, ultimoNodoBuscado);
            ultimoNodoBuscado.der = nuevoNodo;
        }
        if (valorComparacion < 0) {
            Nodo nuevoNodo = new Nodo(elem, ultimoNodoBuscado);
            ultimoNodoBuscado.izq = nuevoNodo;
        }
        cardinal += 1;
    }

    public boolean pertenece(T elem){
        Nodo actual = raiz;
        while (actual != null) {
            int valorComparacion = elem.compareTo(actual.valor);
            if (valorComparacion == 0) return true;
            if (valorComparacion > 0) {
                actual = actual.der;
            }
            if (valorComparacion < 0) {
                actual = actual.izq;
            }
        }
        return false;
    }

    private void eliminarSinDescendencia(Nodo nodoAEliminar) {
        Nodo padre = nodoAEliminar.padre;

        if (padre.izq == nodoAEliminar) {
            padre.izq = null;
        }
        if (padre.der == nodoAEliminar) {
            padre.der = null;
        }
        cardinal -= 1;
    }

    private void eliminarConUnHijo(Nodo nodoAEliminar) {
        Nodo padre = nodoAEliminar.padre;
        boolean esHijoDerecho = nodoAEliminar.valor.compareTo(padre.valor) > 0;
        Nodo hijo = nodoAEliminar.der != null ? nodoAEliminar.der : nodoAEliminar.izq;
        hijo.padre = padre;
        if (esHijoDerecho) {
            padre.der = hijo;
        } else {
            padre.izq = hijo;
        }
        cardinal -= 1;
    }

    private void eliminarConDosHijos(Nodo nodoAEliminar) {
        Nodo padre = nodoAEliminar.padre;
        boolean esHijoDerecho = nodoAEliminar.valor.compareTo(padre.valor) > 0;
        Nodo hijoIzq = nodoAEliminar.izq;
        Nodo hijoDer = nodoAEliminar.der;
        Nodo predecesorInmediato = buscarPredecesorInmediato(hijoIzq);
        
        if (esHijoDerecho) {
            padre.der = predecesorInmediato;
        } else {
            padre.izq = predecesorInmediato;
        }
        predecesorInmediato.padre = padre;

        hijoDer.padre = predecesorInmediato;
        predecesorInmediato.der = hijoDer;
        if (hijoIzq.valor.compareTo(predecesorInmediato.valor) != 0) {
            hijoIzq.padre = predecesorInmediato;
            predecesorInmediato.izq = hijoIzq;
        } else {
            predecesorInmediato.izq.padre = predecesorInmediato;
        }

        if(predecesorInmediato.izq != null) {
            predecesorInmediato.izq.padre = predecesorInmediato.padre;
            predecesorInmediato.padre.der = predecesorInmediato.izq;
        } else {
            predecesorInmediato.padre.der = null;
        }

      
        predecesorInmediato.izq = null;
        if (hijoIzq != predecesorInmediato) {
            hijoIzq.padre = predecesorInmediato;
            predecesorInmediato.izq =hijoIzq;
        }

        cardinal -= 1;
    }

    private Nodo buscarPredecesorInmediato(Nodo inicial) {
        Nodo actual = inicial;

        while (actual.der != null) {
            actual = actual.der;
        }

        return actual;
    }

    public void eliminar(T elem){
        Nodo nodoAEliminar = buscarNodo(elem);
        
        // CASO 0
        if (nodoAEliminar == null) return;
        
        // CASO 1
        if (nodoAEliminar.izq == null && nodoAEliminar.der == null) {
            eliminarSinDescendencia(nodoAEliminar);
            return;
        }
        
        // CASO 2
        if (nodoAEliminar.izq == null || nodoAEliminar.der == null) {
            eliminarConUnHijo(nodoAEliminar);
            return;
        }
        
        // CASO 3
        eliminarConDosHijos(nodoAEliminar);
    }

    private Nodo minimoASuDerecha(Nodo inicial) {
        Nodo actual = inicial;

        while (actual.izq != null) {
            actual = actual.izq;
        }

        return actual;
    }

    private Nodo primerAncestroDerecho(Nodo inicial) {
        Nodo actual = inicial;

        while (inicial.valor.compareTo(actual.valor) <= 0) {
            actual = actual.padre;
        }

        return actual;
    }

    public String toString(){
        String res = "{";
        Nodo actual = buscarNodo(minimo());
        int i = 0;

        while (i < cardinal) {
            if (res == "{") {
                res += actual.valor;
            } else {
                res += ("," + actual.valor);
            }

            if (actual.der != null) {
                actual = minimoASuDerecha(actual.der);
            } else if (actual.padre != null) {
                actual = primerAncestroDerecho(actual);
            }
            i++;
        }

        res += '}';
        return res;
    }

    public class ABB_Iterador {
        private Nodo _actual;

        public boolean haySiguiente() {            
            throw new UnsupportedOperationException("No implementada aun");
        }
    
        public T siguiente() {
            throw new UnsupportedOperationException("No implementada aun");
        }
    }

    public ABB_Iterador iterador() {
        return new ABB_Iterador();
    }

}
