package aed;

public class SistemaPedidos {
    private ListaEnlazada<ABB<Pedido>.HandleABB> handlesPedidos;
    private ABB<Pedido> pedidos;

    public SistemaPedidos(){
        handlesPedidos = new ListaEnlazada<ABB<Pedido>.HandleABB>();
        pedidos = new ABB<Pedido>();
    }

    public void agregarPedido(Pedido pedido){
        ABB<Pedido>.HandleABB handle = pedidos.insertar(pedido);
        handlesPedidos.agregarAtras(handle);
    }

    public Pedido proximoPedido(){
        ABB<Pedido>.HandleABB handle = handlesPedidos.obtener(0);
        Pedido valor = handle.valor();
        handle.eliminar();
        handlesPedidos.eliminar(0);
        return valor;
    }

    public Pedido pedidoMenorId(){
        return pedidos.minimo();
    }

    public String obtenerPedidosEnOrdenDeLlegada(){
        ListaEnlazada<ABB<Pedido>.HandleABB>.ListaIterador iterador = handlesPedidos.iterador();
        String strPedidos = "";
        boolean esPrimero = true;
        while (iterador.haySiguiente()) {
            if (!esPrimero) {
                strPedidos += ", ";
            }
            strPedidos += iterador.siguiente().valor();
            esPrimero = false;
        }
        return "[" + strPedidos + "]";
    }

    public String obtenerPedidosOrdenadosPorId(){
        return pedidos.toString();
    }
}