package br.com.criptaliza.view;

import br.com.criptaliza.dao.CarteiraDao;
import br.com.criptaliza.dao.ClienteDao;
import br.com.criptaliza.dao.InvestidorDao;
import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Carteira;
import br.com.criptaliza.model.entities.Cliente;
import br.com.criptaliza.model.entities.Investidor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("\n===========================================");
        System.out.println("   INICIANDO TESTE DE INTEGRAÇÃO ORACLE    ");
        System.out.println("===========================================");

        try (Connection connection = ConnectionFactory.getConnection()) {
            System.out.println("--- CONNECTION: Conexão realizada com sucesso! ---");

            // 1. Instanciar todos os DAOs necessários
            ClienteDao clienteDao = new ClienteDao(connection);
            InvestidorDao investidorDao = new InvestidorDao(connection);
            CarteiraDao carteiraDao = new CarteiraDao(connection);

            // ---------------------------------------------------------
            // SEÇÃO 1: CLIENTE
            // ---------------------------------------------------------
            System.out.println("\n--- [ TESTES CLIENTE ] ---");

            Cliente novoCliente = new Cliente("Pedro Silva", "pedro_silva@gmail.com", "98563547", "Francês");
            clienteDao.cadastrar(novoCliente);
            System.out.println("Cadastro: Cliente Pedro Silva enviado ao banco.");

            List<Cliente> listaClientes = clienteDao.listar();
            if (!listaClientes.isEmpty()) {
                // Pegamos o último criado para os próximos testes
                Cliente clienteAtual = listaClientes.get(listaClientes.size() - 1);
                System.out.println("Listagem: Encontrado Cliente ID " + clienteAtual.getId());

                // ---------------------------------------------------------
                // SEÇÃO 2: INVESTIDOR (Depende de Cliente)
                // ---------------------------------------------------------
                System.out.println("\n--- [ TESTES INVESTIDOR ] ---");

                Investidor novoInv = new Investidor(clienteAtual.getId(), "Holder", 45);
                investidorDao.cadastrar(novoInv);
                System.out.println("Cadastro: Investidor vinculado ao Cliente ID " + clienteAtual.getId());

                List<Investidor> listaInvs = investidorDao.listarTodos();
                if (!listaInvs.isEmpty()) {
                    Investidor invAtual = listaInvs.get(listaInvs.size() - 1);
                    System.out.println("Listagem: Encontrado Investidor ID " + invAtual.getId());

                    // Atualização de Investidor
                    invAtual.setPersona("Swing Trader");
                    investidorDao.atualizar(invAtual);
                    System.out.println("Update: Persona do Investidor alterada para Swing Trader.");

                    // ---------------------------------------------------------
                    // SEÇÃO 3: CARTEIRA (Depende de Investidor)
                    // ---------------------------------------------------------
                    System.out.println("\n--- [ TESTES CARTEIRA ] ---");

                    // TESTE: Cadastrar
                    Carteira novaCar = new Carteira(invAtual, "Reserva de Cripto Ativos");
                    carteiraDao.cadastrar(novaCar);

                    // TESTE: Listar e Pesquisar
                    List<Carteira> listaCars = carteiraDao.listar();
                    if (!listaCars.isEmpty()) {
                        Carteira carParaTeste = listaCars.get(listaCars.size() - 1);
                        System.out.println("Listagem: Carteira '" + carParaTeste.getNome() + "' recuperada.");

                        try {
                            // TESTE: Pesquisar por ID
                            Carteira carEncontrada = carteiraDao.pesquisar(carParaTeste.getId());
                            System.out.println("Pesquisa: Sucesso ao localizar ID " + carEncontrada.getId());

                            // TESTE: Atualizar
                            carEncontrada.setNome("Carteira High Risk Atualizada");
                            carteiraDao.atualizar(carEncontrada);

                        } catch (EntidadeNaoEncontradaException e) {
                            System.err.println("Erro de Negócio: " + e.getMessage());
                        }
                    }
                }
            } else {
                System.out.println("Atenção: Nenhum cliente na lista. Testes de Investidor e Carteira abortados.");
            }

            System.out.println("\n===========================================");
            System.out.println("       TODOS OS TESTES FINALIZADOS         ");
            System.out.println("===========================================");

        } catch (SQLException e) {
            System.err.println("\n ERRO DE BANCO DE DADOS: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("\n ERRO INESPERADO: ");
            e.printStackTrace();
        }
    }
}