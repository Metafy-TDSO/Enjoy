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
import javax.persistence.Query;

import br.com.fiap.entities.Bebida;
import br.com.fiap.entities.Comanda;
import br.com.fiap.entities.Consumidor;

public class Teste {

    public static void main(String[] args) {
	Scanner leitor = new Scanner(System.in);

	EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("enjoy");
	EntityManager em = fabrica.createEntityManager();

//	System.out.println("Digite o telefone: ");
//	String telefone = leitor.next() + leitor.nextLine();

//	leitor.close();
	String telefone = "11988900772";
	Consumidor consumidor = em.createQuery("FROM Consumidor WHERE telefone = :telefone", Consumidor.class)
		.setParameter("telefone", telefone).getSingleResult();

	if (consumidor == null) {
	    em.close();
	    fabrica.close();
	    System.out.println("Consumidor não encontrado.");
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

	Bebida bebidaFavorita = null;

	try {
	    Query query = em.createNativeQuery(
		    "SELECT bd.id_bebida as id, " + "bd.tp_tipo as tipo, " + "bd.tp_estilo as estilo, "
			    + "bd.vl_bebida as valorBebida, " + "SUM(pd.vl_quantidade) as quantidadeBebida "
			    + "FROM tb_pedido pd " + "LEFT JOIN tb_bebida bd ON pd.id_bebida = bd.id_bebida "
			    + "WHERE pd.id_consumidor = :id_consumidor " + "GROUP BY bd.id_bebida "
			    + "ORDER BY quantidadeBebida DESC " + "LIMIT 1",
		    Bebida.class);
	    query.setParameter("id_consumidor", consumidor.getId());
	    Object result = query.getSingleResult();
	    System.out.println("[id_bebida]" + result);
	    bebidaFavorita = (Bebida) result;
	} catch (Exception exception) {
	    throw new Error("Consumidor nao tem uma bebida favorita.");
	}

//	String estiloFavorito= query.get("tp_estilo");
//	Bebida bebidaFavorita	

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

	System.out.println("Consumidor: " + consumidor.toString());

	System.out.println("Data da ultima visita: " + simpleDateFormat.format(dataUltimaVisita));

	System.out.println("Frequencia mensal: " + frequencia);

	System.out.println("Ticket medio: R$" + String.format("%.2f", mediaValores));

	System.out.println("Bebida favorita: " + bebidaFavorita.toString());

	em.close();
	fabrica.close();

    }
}
