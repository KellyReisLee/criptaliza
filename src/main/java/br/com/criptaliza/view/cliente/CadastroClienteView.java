package br.com.criptaliza.view.cliente;

import br.com.criptaliza.dao.ClienteDao;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Cliente;

import java.sql.Connection;
import java.sql.SQLException;

public class CadastroClienteView {

    public static void main(String[] args) {

        // 1. Abrimos a conexão aqui para garantir que ela seja fechada automaticamente
        try (Connection connection = ConnectionFactory.getConnection()) {

            // 2. Instanciamos o DAO passando a conexão aberta
            ClienteDao dao = new ClienteDao(connection);

            // 3. Criamos o objeto Cliente
            Cliente cliente = new Cliente("Pedro Cangalhas", "pedro98@gmail.com", "967842435", "Francês");

            // 4. Chamamos o método de cadastro
            dao.cadastrar(cliente);

            // Não precisamos mais de dao.fecharConexao(), o try-with-resources faz isso!
            System.out.println("Cliente " + cliente.getNome() + " cadastrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro de Banco de Dados: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }
}