package br.com.criptaliza.view.cliente;

import br.com.criptaliza.dao.ClienteDao;
import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Cliente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PesquisarClienteView {

    public static void main(String[] args) {
        // 1. Gerencia a conexão com try-with-resources
        try (Connection connection = ConnectionFactory.getConnection()) {

            ClienteDao dao = new ClienteDao(connection);

            // 2. Para testar a pesquisa, primeiro listamos para obter um ID que realmente existe
            List<Cliente> clientes = dao.listar();

            if (!clientes.isEmpty()) {
                long idParaBuscar = clientes.get(0).getId();

                System.out.println("--- PESQUISANDO CLIENTE ID: " + idParaBuscar + " ---");

                // 3. Realiza a pesquisa individual
                Cliente cliente = dao.pesquisar(idParaBuscar);

                // Exibição dos dados
                System.out.println("Nome: " + cliente.getNome());
                System.out.println("Email: " + cliente.getEmail());
                System.out.println("Telefone: " + cliente.getTelefone());
                System.out.println("Idioma: " + cliente.getIdioma());

            } else {
                System.out.println("Banco de dados vazio. Não há clientes para pesquisar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro de conexão ou SQL: " + e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            // Este catch será acionado se o ID solicitado não existir
            System.err.println("Erro: " + e.getMessage());
        }
    }
}