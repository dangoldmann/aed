package aed;

public class ListaEnlazada<T> {
    private Nodo primero;
    private Nodo ultimo;

    private class Nodo {
        T valor;
        Nodo prev;
        Nodo sig;

        Nodo(T v) { valor = v;}
    }

    public ListaEnlazada() {
        primero = null;
        ultimo = null;
    }

    public int longitud() {
        int i = 0;
        Nodo actual = primero;
        while (actual != null) {
            i++;
            actual = actual.sig;
        }
        return i;
    }

    public void agregarAdelante(T elem) {
        Nodo nuevoNodo = new Nodo(elem);
        if (primero == null) {
            ultimo = nuevoNodo;
        } else {
            nuevoNodo.sig = primero;
            primero.prev = nuevoNodo;
        }
        primero = nuevoNodo;
    }

    public void agregarAtras(T elem) {
        Nodo nuevoNodo = new Nodo(elem);
        if (ultimo == null) {
            primero = nuevoNodo;
        } else {
            nuevoNodo.prev = ultimo;
            ultimo.sig = nuevoNodo;
        }
        ultimo = nuevoNodo;
    }

    public T obtener(int i) {
        Nodo actual = primero;
        while (i > 0) {
            actual = actual.sig;
            i--;
        }
        return actual.valor;
    }

    public void eliminar(int i) {
        Nodo prev = null;
        Nodo actual = primero;
        Nodo sig = primero.sig;

        while (i > 0) {
            prev = actual;
            actual = sig;
            sig = sig.sig;
            i--;
        }
        
        if (prev != null) {
            prev.sig = sig;
        } else {
            primero = sig;
        }
        if (sig != null) {
            sig.prev = prev;
        } else {
            ultimo = prev;
        }
    }

    public void modificarPosicion(int i, T elem) {
        Nodo nodoModificado = new Nodo(elem);
        Nodo prev = null;
        Nodo actual = primero;
        Nodo sig = actual.sig;

        while (i > 0) {
            prev = actual;
            actual = sig;
            sig = sig.sig;
            i--;
        }

        if (prev != null) {
            prev.sig = nodoModificado;
        }
        if (sig != null) {
            sig.prev = nodoModificado;
        }
        nodoModificado.prev = prev;
        nodoModificado.sig = sig;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        for (int i = 0; i < lista.longitud(); i++) {
            Nodo nuevoNodo = new Nodo(lista.obtener(i));
            if (primero == null) {
                primero = nuevoNodo;
            } else {
                ultimo.sig = nuevoNodo;
                nuevoNodo.prev = ultimo;
            }
            ultimo = nuevoNodo;
        }
    }
    
    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < longitud(); i++) {
            if (i != 0) {
                res += ", ";
            }
            res += obtener(i);
        }
        return '[' + res +']';
    }

    public class ListaIterador{
        Nodo prev;
        Nodo actual;

        public boolean haySiguiente() {
            return actual != null;
        }
        
        public boolean hayAnterior() {
            return prev != null;
        }

        public T siguiente() {
            prev = actual;
            actual = actual.sig;
            return prev.valor;
        }
        
        public T anterior() {
            actual = prev;
            prev = prev.prev;
            return actual.valor;
        }

        ListaIterador() { actual = primero; prev = null;}
    }

    public ListaIterador iterador() {
	    return new ListaIterador();
    }

}
