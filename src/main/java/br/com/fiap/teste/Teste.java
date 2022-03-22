package br.com.fiap.teste;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.fiap.entities.Bebida;
import br.com.fiap.entities.Comanda;
import br.com.fiap.entities.Consumidor;
import br.com.fiap.entities.Pedido;

public class Teste {

    public static void main(String[] args) {
	Scanner leitor = new Scanner(System.in);

	EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("enjoy");
	EntityManager em = fabrica.createEntityManager();

	System.out.println("Digite o telefone: ");
	String telefone = leitor.next() + leitor.nextLine();

	leitor.close();

	Consumidor consumidor = em.createQuery("FROM Consumidor WHERE telefone = :telefone", Consumidor.class)
		.setParameter("telefone", telefone).getSingleResult();

	if (consumidor == null) {
	    em.close();
	    fabrica.close();
	    System.out.println("Consumidor nao encontrado.");
	    System.exit(0);
	}

	List<Comanda> comandas = em
		.createQuery("FROM Comanda WHERE id_consumidor = :id_consumidor ORDER BY dt_entrada DESC",
			Comanda.class)
		.setParameter("id_consumidor", consumidor.getId()).getResultList();

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");

	Date dataUltimaVisita = comandas.get(0).getDataEntrada();

	List<String> datasEntrada = new ArrayList<String>();
	Double somaValor = 0.0;

	List<Integer> bebidas = new ArrayList<Integer>();

	List<Pedido> pedidos = consumidor.getPedidos();

	for (Pedido pedido : pedidos) {
	    bebidas.add(pedido.getBebida().getId());
	}

	Set<Integer> distinctBebidas = new HashSet<Integer>(bebidas);
	List<Double> somaValoresBebidas = new ArrayList<Double>();

	int c = 0;
	Integer idBebidaFav = null;

	for (Integer d : distinctBebidas) {
	    somaValoresBebidas.add(0.0);
	    if (c == 0) {
		idBebidaFav = d;
	    }

	    for (int i = 0; i < pedidos.size(); i++) {
		if (bebidas.get(i) == d) {
		    somaValoresBebidas.set(c, pedidos.get(i).getQuantidade());
		}
	    }
	    c++;
	}

	Double vaBebidaFav = somaValoresBebidas.get(0);
	c = 0;
	for (Integer d : distinctBebidas) {
	    if (vaBebidaFav < somaValoresBebidas.get(c)) {
		vaBebidaFav = somaValoresBebidas.get(c);
		idBebidaFav = d;
	    }
	    c++;
	}

	Bebida bebidaFav = em.find(Bebida.class, idBebidaFav);

	for (Comanda comanda : comandas) {
	    Calendar calendar = Calendar.getInstance();

	    calendar.setTime(comanda.getDataEntrada());
	    int month = calendar.get(Calendar.MONTH) + 1;
	    int year = calendar.get(Calendar.YEAR);

	    datasEntrada.add(Integer.toString(month) + "/" + Integer.toString(year));

	    if (comanda.getValorComanda() != null) {
		somaValor += comanda.getValorComanda();
	    }
	}

	Set<String> distinct = new HashSet<String>(datasEntrada);

	Double soma = 0.0;
	Double contadorMeses = 0.0;

	for (String mesAno : distinct) {
	    soma += Collections.frequency(datasEntrada, mesAno);
	    contadorMeses = contadorMeses + 1;
	}

	Double frequencia;

	frequencia = soma / contadorMeses;

	Double mediaValores = somaValor / comandas.toArray().length;

	System.out.println("\nConsumidor: " + consumidor.toString());

	System.out.println("\nData da ultima visita: " + simpleDateFormat.format(dataUltimaVisita));

	System.out.println("\nFrequencia mensal: " + frequencia);

	System.out.println("\nTicket medio: R$" + String.format("%.2f", mediaValores));

	System.out.println("\nBebida favorita:\n\nEstilo: " + bebidaFav.getEstilo() + "\nTipo: " + bebidaFav.getTipo());

	em.close();
	fabrica.close();
    }
}
