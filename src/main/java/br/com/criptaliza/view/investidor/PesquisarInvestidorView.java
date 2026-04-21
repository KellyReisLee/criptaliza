package br.com.criptaliza.view.investidor;

import br.com.criptaliza.dao.InvestidorDao;
import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Investidor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PesquisarInvestidorView {
    public static void main(String[] args) {

        try(Connection connection = ConnectionFactory.getConnection()){
            // TESTE 3 - INVESTIDOR: Pesquisar por ID
            InvestidorDao investidorDao = new InvestidorDao(connection);
            List<Investidor> listaInvs = investidorDao.listarTodos();
            if(!listaInvs.isEmpty()){
                long idBusca = listaInvs.get(0).getId();

                // Se não encontrar, ele pula direto para o catch lá embaixo
                Investidor invEncontrado = investidorDao.buscarPorId(idBusca);

                System.out.println("Achado: " + invEncontrado.getPersona());
                System.out.println("Score: " + invEncontrado.getPontuacaoRisco());
            } else {
                System.out.println("O banco está vazio. Não há o que pesquisar.");
            }

        }catch (EntidadeNaoEncontradaException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro de conexão: " + e.getMessage());
        }

    }
}
