package aed;

public class SistemaPedidos {
    private ListaEnlazada<ABB<Pedido>.HandleABB> pedidos;
    private ABB<Pedido> pedidosAbb;

    public SistemaPedidos(){
        pedidos = new ListaEnlazada<ABB<Pedido>.HandleABB>();
        pedidosAbb = new ABB<Pedido>();
    }

    public void agregarPedido(Pedido pedido){
        throw new UnsupportedOperationException("No implementado aún");
    }

    public Pedido proximoPedido(){
        throw new UnsupportedOperationException("No implementado aún");
    }

    public Pedido pedidoMenorId(){
        throw new UnsupportedOperationException("No implementado aún");
    }

    public String obtenerPedidosEnOrdenDeLlegada(){
        throw new UnsupportedOperationException("No implementado aún");
    }

    public String obtenerPedidosOrdenadosPorId(){
        throw new UnsupportedOperationException("No implementado aún");
    }
}