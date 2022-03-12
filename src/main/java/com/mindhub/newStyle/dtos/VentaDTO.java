package com.mindhub.newStyle.dtos;

public class VentaDTO {
    private String productoServic;
    private double valor;
    private String horarioReserva;
    private String stockCompra;
    private String descripcion;
    private String tarjetaNumero;
    private String nomreCliente;
    private String apellidoCliente;
    private String tipoDeTarjeta;
    private String horaCompra;

    public VentaDTO() {
    }

    public VentaDTO(String productoServic, double valor, String horarioReserva,
                    String stockCompra, String descripcion, String tarjetaNumero,
                    String nomreCliente, String apellidoCliente, String tipoDeTarjeta,
                    String horaCompra) {
        this.productoServic = productoServic;
        this.valor = valor;
        this.horarioReserva = horarioReserva;
        this.stockCompra = stockCompra;
        this.descripcion = descripcion;
        this.tarjetaNumero = tarjetaNumero;
        this.nomreCliente = nomreCliente;
        this.apellidoCliente = apellidoCliente;
        this.tipoDeTarjeta = tipoDeTarjeta;
        this.horaCompra = horaCompra;
    }


    public String getProductoServic() {
        return productoServic;
    }

    public void setProductoServic(String productoServic) {
        this.productoServic = productoServic;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getHorarioReserva() {
        return horarioReserva;
    }

    public void setHorarioReserva(String horarioReserva) {
        this.horarioReserva = horarioReserva;
    }

    public String getStockCompra() {
        return stockCompra;
    }

    public void setStockCompra(String stockCompra) {
        this.stockCompra = stockCompra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTarjetaNumero() {
        return tarjetaNumero;
    }

    public void setTarjetaNumero(String tarjetaNumero) {
        this.tarjetaNumero = tarjetaNumero;
    }

    public String getNomreCliente() {
        return nomreCliente;
    }

    public void setNomreCliente(String nomreCliente) {
        this.nomreCliente = nomreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getTipoDeTarjeta() {
        return tipoDeTarjeta;
    }

    public void setTipoDeTarjeta(String tipoDeTarjeta) {
        this.tipoDeTarjeta = tipoDeTarjeta;
    }

    public String getHoraCompra() {
        return horaCompra;
    }

    public void setHoraCompra(String horaCompra) {
        this.horaCompra = horaCompra;
    }
}
