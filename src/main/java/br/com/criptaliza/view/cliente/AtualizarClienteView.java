package br.com.criptaliza.view.cliente;

import br.com.criptaliza.dao.ClienteDao;
import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Cliente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AtualizarClienteView {

    public static void main(String[] args) {
        // 1. Gerencia a conexão com try-with-resources
        try (Connection connection = ConnectionFactory.getConnection()) {

            // 2. Instancia o DAO passando a conexão
            ClienteDao dao = new ClienteDao(connection);

            // 3. Busca a lista para garantir que temos um ID válido para testar
            List<Cliente> lista = dao.listar();

            if (!lista.isEmpty()) {
                // Pegamos o primeiro cliente da lista para atualizar
                long idBusca = lista.get(0).getId();
                Cliente cliente = dao.pesquisar(idBusca);

                System.out.println("Atualizando cliente: " + cliente.getNome() + " (ID: " + idBusca + ")");

                // 4. Modifica os dados
                cliente.setNome("Joana");
                cliente.setEmail("joana_afonso@gmail.com");
                cliente.setTelefone("92436756");
                cliente.setIdioma("Espanhol");

                // 5. Persiste a alteração
                dao.atualizar(cliente);

                System.out.println("Sucesso! Cliente atualizado no banco de dados.");
            } else {
                System.out.println("Nenhum cliente encontrado para realizar o teste de atualização.");
            }

        } catch (SQLException e) {
            System.err.println("Erro de SQL: " + e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }
}