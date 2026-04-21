package br.com.criptaliza.view;
import br.com.criptaliza.dao.ClienteDao;
import br.com.criptaliza.dao.InvestidorDao;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Cliente;
import br.com.criptaliza.model.entities.Investidor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{

        System.out.println("-- INICIANDO TESTE DE INTEGRAÇÃO ORACLE --");
            try(Connection connection = ConnectionFactory.getConnection()){
                System.out.println("--- CONNECTION: Conexão realizada com sucesso! ---");

                // 1. Instanciar os DAOs passando a conexão (ESSENCIAL)
                // O erro estava aqui: faltava passar 'connection' para o ClienteDao
                ClienteDao clienteDao = new ClienteDao(connection);
                InvestidorDao investidorDao = new InvestidorDao(connection);

                // --- SEÇÃO CLIENTE ---
                System.out.println("\n--- [ TESTES CLIENTE ] ---");

                // TESTE 1 - CLIENTE: Criar cliente
                System.out.println("\nCRIAR CLIENTE: ");
                Cliente novoCliente = new Cliente("Pedro Silva", "pedro_silva@gmail.com", "98563547", "Francês");
                clienteDao.cadastrar(novoCliente);
                System.out.println("Cliente criado com sucesso!");

                // TESTE 2 - CLIENTE: Listar todos
                System.out.println("\nLISTAR TODOS OS CLIENTES:");
                List<Cliente> listaClientes = clienteDao.listar();
                for(Cliente c : listaClientes){
                    System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " | Email: " + c.getEmail());
                }

                // TESTE 3 - CLIENTE: Pesquisar por ID
                System.out.println("\nPESQUISAR CLIENTE POR ID:");
                if (!listaClientes.isEmpty()) {
                    // Pegamos o último da lista (size-1) para garantir que testamos o que acabamos de criar
                    long idCliente = listaClientes.get(listaClientes.size() - 1).getId();

                    Cliente clientePorId = clienteDao.pesquisar(idCliente);
                    System.out.println("Localizado -> Nome: " + clientePorId.getNome() + " | Idioma: " + clientePorId.getIdioma());

                    // TESTE 4 - CLIENTE: Update (Aninhado para evitar NullPointerException)
                    System.out.println("\nATUALIZAR CLIENTE POR ID:");
                    clientePorId.setNome("Patricia");
                    clientePorId.setEmail("patricia@gmail.com");
                    clientePorId.setTelefone("96876542");
                    clientePorId.setIdioma("Espanhol");

                    clienteDao.atualizar(clientePorId);
                    System.out.println("Sucesso! Cliente ID " + idCliente + " atualizado para Patricia.");

                } else {
                    System.out.println("Lista vazia. Pulando testes de Pesquisa e Update.");
                }

            // TESTE 5 - CLIENTE: Deletar (Comentado por segurança)
            /*
            System.out.println("\nDELETAR CLIENTE POR ID:");
            if(!listaClientes.isEmpty()){
                long idParaDeletar = listaClientes.get(0).getId();
                clienteDao.remover(idParaDeletar);
                System.out.println("Cliente ID: " + idParaDeletar + " removido.");
            }
            */

                // --- SEÇÃO INVESTIDOR ---
                System.out.println("\n--- [ TESTES INVESTIDOR ] ---");

                if (!listaClientes.isEmpty()) {
                    // 1. Pegamos o ID do cliente que acabamos de criar/listar
                    long idCli = listaClientes.get(0).getId();

                    // TESTE 1 - INVESTIDOR: Criar (Insert)
                    System.out.println("CRIAR INVESTIDOR:");
                    Investidor novoInv = new Investidor(idCli, "Holder", 45);
                    investidorDao.cadastrar(novoInv);
                    System.out.println("Investidor vinculado ao Cliente ID " + idCli + " cadastrado!");

                    // TESTE 2 - INVESTIDOR: Listar Todos (Select *)
                    System.out.println("\nLISTAR TODOS OS INVESTIDORES:");
                    List<Investidor> listaInvs = investidorDao.listarTodos();
                    for (Investidor i : listaInvs) {
                        System.out.println("ID Inv: " + i.getId() +
                                " | Cliente: " + i.getIdCliente() +
                                " | Persona: " + i.getPersona() +
                                " | Risco: " + i.getPontuacaoRisco());
                    }

                    // Verificamos se a lista de investidores não está vazia antes de prosseguir
                    if (!listaInvs.isEmpty()) {

                        // TESTE 3 - INVESTIDOR: Pesquisar por ID
                        // DICA: Pegamos o último da lista (size - 1) para garantir que é o que acabamos de criar
                        long idBusca = listaInvs.get(listaInvs.size() - 1).getId();
                        System.out.println("\nPESQUISAR INVESTIDOR POR ID: " + idBusca);

                        Investidor invEncontrado = investidorDao.buscarPorId(idBusca);

                        // --- BLOCO DE PROTEÇÃO ---
                        // Só entramos nos próximos testes se o objeto não for nulo
                        if (invEncontrado != null) {
                            System.out.println("Achado: " + invEncontrado.getPersona());
                            System.out.println("Achado: " + invEncontrado.getPontuacaoRisco());

                            // TESTE 4 - INVESTIDOR: Atualizar (Update)
                            System.out.println("\nATUALIZAR INVESTIDOR:");
                            invEncontrado.setPersona("Swing Trader");
                            invEncontrado.setPontuacaoRisco(70);

                            // Aqui chamamos o DAO. Se der erro de SQL, o catch lá debaixo resolve.
                            investidorDao.atualizar(invEncontrado);
                            System.out.println("Sucesso: Investidor ID " + idBusca + " atualizado para Swing Trader.");

                            // TESTE 5 - INVESTIDOR: Remover (Delete)
                            /* System.out.println("\nREMOVER INVESTIDOR:");
                            investidorDao.excluir(idBusca);
                            System.out.println("Investidor removido!");
                            */

                        } else {
                            System.err.println("ERRO: O investidor ID " + idBusca + " foi listado mas não pôde ser recuperado pelo buscarPorId.");
                        }
                    } else {
                        System.err.println("ERRO: A lista de investidores está vazia após o cadastro. Verifique o método cadastrar ou listarTodos.");
                    }

                } else {
                    System.out.println("Não há clientes. Impossível testar Investidor (FK obrigatória).");
                }
            }catch (SQLException e) {
                System.err.println("Erro de Banco de Dados: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Erro inesperado: " + e.getMessage());
            }


    }
}
