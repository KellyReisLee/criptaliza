package br.com.criptaliza.view.cliente;

import br.com.criptaliza.dao.ClienteDao;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Cliente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListagemClientesView {

    public static void main(String[] args) {
        // 1. Gerencia a conexão com o try-with-resources
        try (Connection connection = ConnectionFactory.getConnection()) {

            // 2. Instancia o DAO passando a conexão
            ClienteDao dao = new ClienteDao(connection);

            System.out.println("--- LISTAGEM DE CLIENTES ---");

            // 3. Obtém a lista do banco
            List<Cliente> clientes = dao.listar();

            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente encontrado.");
            } else {
                for (Cliente cliente : clientes) {
                    // Ajustado para usar .getId() conforme o padrão das outras classes
                    System.out.println("ID: " + cliente.getId() +
                            " | Nome: " + cliente.getNome() +
                            " | Email: " + cliente.getEmail() +
                            " | Tel: " + cliente.getTelefone() +
                            " | Idioma: " + cliente.getIdioma());
                }
            }
            // A conexão fecha automaticamente aqui ao sair do bloco try

        } catch (SQLException e) {
            System.err.println("Erro de Banco de Dados: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }
}