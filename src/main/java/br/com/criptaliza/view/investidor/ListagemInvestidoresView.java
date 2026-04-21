package br.com.criptaliza.view.investidor;

import br.com.criptaliza.dao.InvestidorDao;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Investidor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListagemInvestidoresView {
    public static void main(String[] args) {
        try(Connection connection = ConnectionFactory.getConnection()){
            // TESTE 2 - INVESTIDOR: Listar Todos (Select *)
            InvestidorDao investidorDao = new InvestidorDao(connection);
            System.out.println("\nLISTAR TODOS OS INVESTIDORES:");
            List<Investidor> listaInvs = investidorDao.listarTodos();
            for (Investidor i : listaInvs) {
                System.out.println("ID Inv: " + i.getId() +
                        " | Cliente: " + i.getIdCliente() +
                        " | Persona: " + i.getPersona() +
                        " | Risco: " + i.getPontuacaoRisco());
            }
        }catch (SQLException e) {
            System.err.println("Erro de Banco de Dados: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }
}
