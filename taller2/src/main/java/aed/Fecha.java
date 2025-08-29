package aed;

public class Fecha {
    private int _dia;
    private int _mes;

    public Fecha(int dia, int mes) {
        _dia = dia;
        _mes = mes;
    }

    public Fecha(Fecha fecha) {
        _dia = fecha._dia;
        _mes = fecha._mes;
    }

    public Integer dia() {
        return _dia;
    }

    public Integer mes() {
        return _mes;
    }

    @Override
    public String toString() {
        return _dia + "/" + _mes;
    }

    @Override
    public boolean equals(Object otra) {
        boolean otraEsNull = otra == null;

        if (otraEsNull) {
            return false;
        }

        boolean claseDistinta = otra.getClass() != this.getClass();

        if (claseDistinta) {
            return false;
        }
        
        Fecha otraFecha = (Fecha) otra;

        return otraFecha._dia == _dia && otraFecha._mes == _mes;
    }

    public void incrementarDia() {
        boolean esUltimoDiaDelMes = _dia == diasEnMes(_mes);
        if (esUltimoDiaDelMes) {
            _dia = 1;
            _mes = _mes == 12 ? 1 : _mes + 1;
            return;
        } 
        _dia += 1;
    }

    private int diasEnMes(int mes) {
        int dias[] = {
                // ene, feb, mar, abr, may, jun
                31, 28, 31, 30, 31, 30,
                // jul, ago, sep, oct, nov, dic
                31, 31, 30, 31, 30, 31
        };
        return dias[mes - 1];
    }

}
