package aed;

public class Horario {
    private int _hora;
    private int _minutos;

    public Horario(int hora, int minutos) {
        _hora = hora;
        _minutos = minutos;
    }

    public int hora() {
        return _hora;
    }

    public int minutos() {
        return _minutos;
    }

    @Override
    public String toString() {
        return _hora + ":" + _minutos;
    }

    @Override
    public boolean equals(Object otro) {
          boolean otroEsNull = otro == null;

        if (otroEsNull) {
            return false;
        }

        boolean claseDistinta = otro.getClass() != this.getClass();

        if (claseDistinta) {
            return false;
        }

        Horario otroHorario = (Horario) otro;

        return otroHorario._hora == _hora && otroHorario._minutos == _minutos;
    }

}
