package br.com.fiap.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_bebida", indexes = @Index(name = "idx_bebida", columnList = "tp_tipo, tp_estilo"))
public class Bebida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bebida")
    private int id;

    @Column(name = "tp_tipo", nullable = false, length = 45)
    private String tipo;

    @Column(name = "tp_estilo", nullable = false, length = 45)
    private String estilo;

    @Column(name = "vl_bebida", nullable = false, columnDefinition = "Decimal(10,2)")
    private Double valorBebida;

    @OneToMany(mappedBy = "bebida", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Pedido> pedidos;

    public Bebida() {
    }

    public Bebida(int id, String tipo, String estilo, Double valorBebida, List<Pedido> pedidos) {
	super();
	this.id = id;
	this.tipo = tipo;
	this.estilo = estilo;
	this.valorBebida = valorBebida;
	this.pedidos = pedidos;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getTipo() {
	return tipo;
    }

    public void setTipo(String tipo) {
	this.tipo = tipo;
    }

    public String getEstilo() {
	return estilo;
    }

    public void setEstilo(String estilo) {
	this.estilo = estilo;
    }

    public Double getValorBebida() {
	return valorBebida;
    }

    public void setValorBebida(Double valorBebida) {
	this.valorBebida = valorBebida;
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
	Bebida other = (Bebida) obj;
	if (id != other.id)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Bebida [id=" + id + ", tipo=" + tipo + ", estilo=" + estilo + ", valorBebida=" + valorBebida
		+ ", pedidos=" + pedidos + "]";
    }

}
