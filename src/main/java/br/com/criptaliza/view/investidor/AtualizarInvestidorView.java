package br.com.criptaliza.view.investidor;

import br.com.criptaliza.dao.InvestidorDao;
import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Investidor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtualizarInvestidorView {
    public static void main(String[] args) {
        try(Connection connection = ConnectionFactory.getConnection()){

            // TESTE 4 - INVESTIDOR: Atualizar (Update)
            InvestidorDao investidorDao = new InvestidorDao(connection);
            List<Investidor> listaInvs = investidorDao.listarTodos();

           if(!listaInvs.isEmpty()){
               long idBusca = listaInvs.get(0).getId();
               Investidor invEncontrado = investidorDao.buscarPorId(idBusca);
               System.out.println("\nATUALIZAR INVESTIDOR:");
               invEncontrado.setPersona("Swing Trader");
               invEncontrado.setPontuacaoRisco(70);
               investidorDao.atualizar(invEncontrado);
               System.out.println("Investidor ID " + idBusca + " atualizado para Swing Trader.");
           }else {
               System.out.println("Não existe investidores cadastrados para testar a atualização.");
           }
        }catch (EntidadeNaoEncontradaException e) {
            System.err.println("Investidor não encontrado!");
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}

