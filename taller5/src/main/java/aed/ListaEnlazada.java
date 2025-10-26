package aed;

public class ListaEnlazada<T> {
    private Nodo primero;

    private class Nodo {
        T valor;
        Nodo sig;

        Nodo(T v) { valor = v;}
    }

    public ListaEnlazada() {
        primero = null;
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
        Nodo nuevoNodo = new nodo(elem);
        nuevoNodo.sig = primero;
        primero = nuevoNodo;
    }

    public void agregarAtras(T elem) {
        Nodo nuevoNodo = new Nodo(elem);

        if (primero == null) {
            primero = nuevoNodo;
            return;
        }
        
        Nodo actual = primero;
        while (actual.sig != null) {
            actual = actual.sig;
        }
        actual.sig = nuevoNodo;
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

        while (i > 0) {
            prev = actual;
            actual = actual.sig;
            i--;
        }

        prev.sig = actual.sig;
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo actual = primero;

        while (i > 0) {
            actual = actual.sig;
            i--;
        }

        actual.valor = elem;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        for (int i = 0; i < lista.longitud(); i++) {
            agregarAtras(lista.obtener(i));
        }
    }
    
    @Override
    public String toString() {
        String res = "";
        boolean esPrimero = true;
        ListaIterador iterador = new ListaIterador();
        
        while (iterador.haySiguiente()) {
            if (!esPrimero) {
                res += ", ";
            }
            res += iterador.siguiente();
            esPrimero = false;
        }
        return "[" + res + "]";
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
        
        // public T anterior() {
        //     actual = prev;
        //     prev = prev.prev;
        //     return actual.valor;
        // }

        ListaIterador() { actual = primero; prev = null;}
    }

    public ListaIterador iterador() {
        return new ListaIterador();
    }

}
