package br.com.fiap.teste;

import br.com.fiap.entities.Comanda;
import br.com.fiap.entities.Consumidor;
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

public class Teste {

  public static void main(String[] args) {
    Scanner leitor = new Scanner(System.in);

    EntityManagerFactory fabrica = Persistence.createEntityManagerFactory(
      "enjoy"
    );
    EntityManager em = fabrica.createEntityManager();

    System.out.println("Digite o telefone: ");
    String telefone = leitor.next() + leitor.nextLine();

    Consumidor consumidor = em
      .createQuery(
        "SELECT u from Consumidor u WHERE u.telefone = :telefone",
        Consumidor.class
      )
      .setParameter("telefone", telefone)
      .getSingleResult();

    System.out.println(consumidor);

    List<Comanda> comandas = consumidor.getComandas();
    Date maiorData = comandas.get(0).getDataEntrada();

    List<String> datasEntrada = new ArrayList<String>();

    double somaValor = 0;

    for (Comanda comanda : comandas) {
      if (maiorData.compareTo(comanda.getDataEntrada()) == -1) {
        maiorData = comanda.getDataEntrada();
      }

      Calendar calendar = Calendar.getInstance();

      calendar.setTime(comanda.getDataEntrada());
      int month = calendar.get(Calendar.MONTH) + 1;
      int year = calendar.get(Calendar.YEAR);

      datasEntrada.add(Integer.toString(month) + "/" + Integer.toString(year));
      somaValor = somaValor + comanda.getValorComanda();
    }

    Set<String> distinct = new HashSet<String>(datasEntrada);

    double soma = 0;
    double contadorMeses = 0;

    for (String s : distinct) {
      soma = soma + Collections.frequency(datasEntrada, s);
      contadorMeses = contadorMeses + 1;
    }

    double frequencia;

    frequencia = soma / contadorMeses;

    double mediaValores = somaValor / comandas.toArray().length;

    System.out.println("A data da ultima visita eh: " + maiorData);

    System.out.println("A frequencia mensal eh: " + frequencia);

    System.out.println("A media de valores eh: " + mediaValores);

    em.close();
    fabrica.close();
    leitor.close();
  }
}
