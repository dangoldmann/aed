package aed;

class ArregloRedimensionableDeRecordatorios {
    private Recordatorio[] _arreglo;

    public int longitud() {
        return _arreglo.length;
    }

    public ArregloRedimensionableDeRecordatorios() {
        _arreglo = new Recordatorio[0];
    }

    public void agregarAtras(Recordatorio i) {
        Recordatorio[] nuevoArreglo = new Recordatorio[longitud() + 1];
        for (int j = 0; j < longitud(); j++) {
            nuevoArreglo[j] = _arreglo[j];
        }
        nuevoArreglo[longitud()] = i;
        _arreglo = nuevoArreglo;
    }

    public Recordatorio obtener(int i) {
        return _arreglo[i];
    }

    public void quitarAtras() {
        if (longitud() == 0) {
            return;
        }
        Recordatorio[] nuevoArreglo = new Recordatorio[longitud() - 1];
        for (int i = 0; i < longitud() - 1; i++) {
            nuevoArreglo[i] = _arreglo[i];
        }
        _arreglo = nuevoArreglo;
    }

    public void modificarPosicion(int indice, Recordatorio valor) {
        _arreglo[indice] = valor;
    }

    public ArregloRedimensionableDeRecordatorios(ArregloRedimensionableDeRecordatorios vector) {
        Recordatorio[] nuevoArreglo = new Recordatorio[vector.longitud()];
        for (int i = 0; i < vector.longitud(); i++) {
            nuevoArreglo[i] = new Recordatorio(vector.obtener(i));
        }
        _arreglo = nuevoArreglo;
    }

    public ArregloRedimensionableDeRecordatorios copiar() {
        return new ArregloRedimensionableDeRecordatorios(this);
    }
}
