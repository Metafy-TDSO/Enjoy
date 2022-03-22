package br.com.fiap.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_comanda", indexes = @Index(columnList = "dt_entrada", name = "idx_dt_entrada DESC"))
public class Comanda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comanda")
    private int id;

    @Column(name = "dt_entrada", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEntrada;

    @Column(name = "vl_valor", columnDefinition = "Decimal(10,2)", nullable = true)
    private Double valorComanda;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_consumidor", foreignKey = @ForeignKey(name = "fk_comanda_consumidor"), nullable = false)
    private Consumidor consumidor;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "comanda", fetch = FetchType.EAGER)
    private List<Pedido> pedidos;

    public Comanda() {
    }

    public Comanda(int id, Date dataEntrada, Double valorComanda, Consumidor consumidor, List<Pedido> pedidos) {
	super();
	this.id = id;
	this.dataEntrada = dataEntrada;
	this.valorComanda = valorComanda;
	this.consumidor = consumidor;
	this.pedidos = pedidos;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public Date getDataEntrada() {
	return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
	this.dataEntrada = dataEntrada;
    }

    public Double getValorComanda() {
	return valorComanda;
    }

    public void setValorComanda(Double valorComanda) {
	this.valorComanda = valorComanda;
    }

    public Consumidor getConsumidor() {
	return consumidor;
    }

    public void setConsumidor(Consumidor consumidor) {
	this.consumidor = consumidor;
    }

    public List<Pedido> getPedidos() {
	return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
	this.pedidos = pedidos;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Comanda other = (Comanda) obj;
	if (id != other.id)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Comanda [id=" + id + ", dataEntrada=" + dataEntrada + ", valorComanda=" + valorComanda + ", consumidor="
		+ consumidor + "]";
    }

}
