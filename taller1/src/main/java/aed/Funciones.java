package aed;

class Funciones {

/***  Primera parte: Funciones en java ***/

    int cuadrado(int x) {
        return x * x;
    }

    double distancia(double x, double y) {
        return Math.sqrt((x * x)+ (y * y));
    }

    boolean esPar(int n) {
        return n % 2 == 0;
    }

    boolean esBisiesto(int n) {
        return n % 4 == 0 && (n % 100 != 0 || n % 400 == 0);
    }

    int factorialIterativo(int n) {
        int res = 1;
        for (int i = 1; i <= n; i++) {
            res *= i;
        }
        return res;
    }

    int factorialRecursivo(int n) {
        if (n == 0) {
            return 1;
        }
        return n * factorialIterativo(n - 1);
    }

    boolean esPrimo(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    int sumatoria(int[] numeros) {
        int res = 0;
        for (int n : numeros) {
            res += n;
        }
        return res;
    }

    int busqueda(int[] numeros, int buscado) {
        int res = -1;
        for (int i = 0; i < numeros.length; i++) {
            if (numeros[i] == buscado) {
                res = i;
                break;
            }
        }
        return res;
    }

    boolean tienePrimo(int[] numeros) {
        for (int n : numeros) {
            if (esPrimo(n)) {
                return true;
            }
        }
        return false;
    }

    boolean todosPares(int[] numeros) {
        for (int n : numeros) {
            if (n % 2 != 0) {
                return false;
            }
        }
        return true;
    }

    boolean esPrefijo(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    boolean esSufijo(String s1, String s2) {
        int diferenciaLength = s2.length() - s1.length();
        if (diferenciaLength < 0) {
            return false;
        }
        for (int i = s1.length() - 1; i >= 0; i--) {
            if (s1.charAt(i) != s2.charAt(i + diferenciaLength)) {
                return false;
            }
        }
        return true;
    }

/***  Segunda parte: Debugging ***/

    boolean xor(boolean a, boolean b) {
        return (a || b) && !(a && b);
    }

    boolean iguales(int[] xs, int[] ys) {
        boolean res = true;

        if (xs.length != ys.length) {
            return false;
        }

        for (int i = 0; i < xs.length; i++) {
            if (xs[i] != ys[i]) {
                res = false;
            }
        }
        return res;
    }

    boolean ordenado(int[] xs) {
        for (int i = 0; i < xs.length - 1; i++) {
            if (xs[i] > xs [i + 1]) {
                return false;
            }
        }
        return true;
    }

    int maximo(int[] xs) {
        int res = xs[0];
        for (int i = 1; i < xs.length; i++) {
            if (xs[i] > res) res = xs[i];
        }
        return res;
    }

    boolean todosPositivos(int[] xs) {
        for (int x : xs) {
            if (x <= 0) {
                return false;
            }
        }
        return true;
    }

}
