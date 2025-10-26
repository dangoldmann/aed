package aed;

public class ABB<T extends Comparable<T>> {
    private Nodo raiz;

    private class Nodo {
        private T valor;
        private Nodo izq;
        private Nodo der;
        private Nodo padre;

        Nodo(T v, Nodo p) {
            valor = v;
            padre = p;
            izq = null;
            der = null;
        }
    }

    public class HandleABB {
        private Nodo nodoApuntado;
        
        private HandleABB(Nodo n) {
            this.nodoApuntado = n;
        }

        public T valor() {
            return nodoApuntado.valor;
        }

        public void eliminar() {
            eliminarNodo(nodoApuntado);
        }

    }

    public ABB() {
        raiz = null;
    }

    private void eliminarSinHijos(Nodo n) {
        Nodo padre = n.padre;
        boolean esRaiz = padre == null;

        if (esRaiz) {
            raiz = null;
        } else {
            if (padre.izq == n) {
                padre.izq = null;
            }
            if (padre.der == n) {
                padre.der = null;
            }
        }
    }

    private void eliminarConUnHijo(Nodo n) {
        Nodo padre = n.padre;
        Nodo hijo = n.der != null ? n.der : n.izq;
        boolean esRaiz = padre == null;

        if (esRaiz) {
            hijo.padre = null;
            raiz = hijo;
            return;
        }

        boolean esHijoDerecho = n.valor.compareTo(padre.valor) > 0;
        hijo.padre = padre;
        if (esHijoDerecho) {
            padre.der = hijo;
        } else {
            padre.izq = hijo;
        }
    }

    private void eliminarNodo(Nodo n) {
        if (n.izq == null && n.der == null) {
            eliminarSinHijos(n);
            return;
        }

        if (n.izq == null || n.der == null) {
            eliminarConUnHijo(n);
            return;
        }

        eliminarConDosHijos(n);
    }

    private Nodo buscarPredecesorInmediato(Nodo inicial) {
        Nodo actual = inicial;

        while (actual.der != null) {
            actual = actual.der;
        }

        return actual;
    }

    private void eliminarConDosHijos(Nodo n) {
        Nodo padre = n.padre;
        Nodo hijoIzq = n.izq;
        Nodo hijoDer = n.der;
        Nodo predecesorInmediato = buscarPredecesorInmediato(hijoIzq);
        boolean esHijoIzqElPredecesor = hijoIzq.valor.compareTo(predecesorInmediato.valor) == 0;
        boolean esRaiz = padre == null;

     
        if (!esHijoIzqElPredecesor) {
            if (predecesorInmediato.izq != null) {
                predecesorInmediato.izq.padre = predecesorInmediato.padre;
                predecesorInmediato.padre.der = predecesorInmediato.izq;
            } else {
                predecesorInmediato.padre.der = null;
            }
            hijoIzq.padre = predecesorInmediato;
            predecesorInmediato.izq = hijoIzq;
        } 
        
        hijoDer.padre = predecesorInmediato;
        predecesorInmediato.der = hijoDer;

        if (esRaiz) {
            raiz = predecesorInmediato;
            predecesorInmediato.padre = null;
        } else {
            boolean esHijoDerecho = n.valor.compareTo(padre.valor) > 0;
            
            if (esHijoDerecho) {
                padre.der = predecesorInmediato;
            } else {
                padre.izq = predecesorInmediato;
            }
            predecesorInmediato.padre = padre;
        }

    }

    public T minimo(){
        Nodo actual = raiz;

        while (actual.izq != null) {
            actual = actual.izq;
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

    public HandleABB insertar(T elem){
        if (raiz == null) {
            raiz = new Nodo(elem, null);
            return new HandleABB(raiz);
        }

        Nodo ultimoNodoBuscado = buscarNodo(elem);
        int valorComparacion = elem.compareTo(ultimoNodoBuscado.valor);
        
        if (valorComparacion > 0) {
            Nodo nuevoNodo = new Nodo(elem, ultimoNodoBuscado);
            ultimoNodoBuscado.der = nuevoNodo;
            return new HandleABB(nuevoNodo);
        }
        
        if (valorComparacion < 0) {
            Nodo nuevoNodo = new Nodo(elem, ultimoNodoBuscado);
            ultimoNodoBuscado.izq = nuevoNodo;
            return new HandleABB(nuevoNodo);
        }

        return new HandleABB(ultimoNodoBuscado);
    }

    public boolean pertenece(T elem){
         Nodo nodo = buscarNodo(elem);

        return nodo != null && elem.compareTo(nodo.valor) == 0;
    }

    public void eliminar(T elem){
        Nodo nodoAEliminar = buscarNodo(elem);
        
        // CASO 0
        if (nodoAEliminar == null || nodoAEliminar.valor.compareTo(elem) != 0) return;
        
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

    @Override
    public String toString(){
        String res = "{";
        ABB_Iterador iterador = new ABB_Iterador();

        while (iterador.haySiguiente()) {
            T valorActual = iterador.siguiente();
            if (res == "{") {
                res += valorActual;
            } else {
                res += ("," + valorActual);
            }
        }

        res += '}';
        return res;
    }

    public class ABB_Iterador {
       private Nodo actual;

        public boolean haySiguiente() {            
            return actual != null;
        }
    
        public T siguiente() {
            T prev = actual.valor;

            if (actual.der != null) {
                actual = minimoASuDerecha(actual.der);
            } else if (actual.valor.compareTo(maximo()) == 0) {
                actual = null;
            } else if (actual.padre != null) {
                actual = primerAncestroDerecho(actual);
            }
            return prev;
        }

        ABB_Iterador() {
            actual = raiz != null ? minimoASuDerecha(raiz) : null;
        }
    }

    public ABB_Iterador iterador() {
        return new ABB_Iterador();
    }

}
