package br.com.criptaliza.view.cliente;

import br.com.criptaliza.dao.ClienteDao;
import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Cliente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RemoverClienteView {

    public static void main(String[] args) {
        // 1. Gerencia a conexão com try-with-resources
        try (Connection connection = ConnectionFactory.getConnection()) {

            ClienteDao dao = new ClienteDao(connection);

            // 2. Buscamos a lista para encontrar um ID real e evitar erro de "não encontrado"
            List<Cliente> lista = dao.listar();

            if (!lista.isEmpty()) {
                // Pegamos o ID do primeiro cliente para remover
                long idParaRemover = lista.get(0).getId();

                System.out.println("Tentando remover cliente ID: " + idParaRemover);

                // 3. Executa a remoção
                dao.remover(idParaRemover);

                System.out.println("Sucesso: Cliente removido do sistema!");
            } else {
                System.out.println("Não há clientes cadastrados para testar a remoção.");
            }

        } catch (SQLException e) {
            // Caso haja um investidor vinculado a este cliente, o Oracle lançará um erro de FK aqui
            System.err.println("Erro de Banco: " + e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}