package br.com.fiap.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pedido")
	private int id;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_comanda", foreignKey = @ForeignKey(name = "fk_comanda_pedido"), nullable = false)
	private Comanda comanda;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_bebida", foreignKey = @ForeignKey(name = "fk_bebida_pedido"), nullable = false)
	private Bebida bebida;

	@Column(name = "vl_pedido")
	private double valorPedido;

	@Column(name = "vl_quantidade")
	private int quantidade;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_consumidor", foreignKey = @ForeignKey(name = "fk_pedido_consumidor"), nullable = false)
	private Consumidor consumidor;
	
	public Pedido() {

	}

	public Pedido(int id, Comanda comanda, Bebida bebida, double valorPedido, int quantidade, Consumidor consumidor) {
		super();
		this.id = id;
		this.comanda = comanda;
		this.bebida = bebida;
		this.valorPedido = valorPedido;
		this.quantidade = quantidade;
		this.consumidor = consumidor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Comanda getComanda() {
		return comanda;
	}

	public void setComanda(Comanda comanda) {
		this.comanda = comanda;
	}

	public Bebida getBebida() {
		return bebida;
	}

	public void setBebida(Bebida bebida) {
		this.bebida = bebida;
	}

	public double getValorPedido() {
		return valorPedido;
	}

	public void setValorPedido(double valorPedido) {
		this.valorPedido = valorPedido;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Consumidor getConsumidor() {
		return consumidor;
	}

	public void setConsumidor(Consumidor consumidor) {
		this.consumidor = consumidor;
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
		Pedido other = (Pedido) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", comanda=" + comanda + ", bebida=" + bebida + ", valorPedido=" + valorPedido
				+ ", quantidade=" + quantidade + ", consumidor=" + consumidor + "]";
	}

}
