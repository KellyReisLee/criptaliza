package br.com.criptaliza.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Entidade que representa a Carteira de investimentos do usuário.
 * Ajustada para compatibilidade com banco de dados Oracle e precisão financeira.
 */
public class Carteira {

    // Identificadores e Associações
    private Long id;
    private Investidor investidor;
    private LocalDateTime dataCriacao;

    // Atributos de Negócio
    private String nome;
    private String faixaRisco;
    private BigDecimal totalInvestido;
    private BigDecimal valorAtual;

    // Coleções (Relacionamentos que serão carregados via DAO)
    private Map<String, BigDecimal> ativos = new HashMap<>();
    private List<Ordem> ordens = new ArrayList<>();
    private List<RecomendacaoCarteira> recomendacoes = new ArrayList<>();

    // --- CONSTRUTORES ---

    public Carteira() {
    }

     // Construtor para criação de nova carteira (Cadastro Inicial)
    public Carteira(Investidor investidor, String nome) {
        this.investidor = investidor;
        this.nome = nome;
        this.faixaRisco = "N/A";
        this.totalInvestido = BigDecimal.ZERO;
        this.valorAtual = BigDecimal.ZERO;
        this.dataCriacao = LocalDateTime.now();
    }


    // --- GETTERS E SETTERS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Investidor getInvestidor() {
        return investidor;
    }

    public void setInvestidor(Investidor investidor) {
        this.investidor = investidor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFaixaRisco() {
        return faixaRisco;
    }

    public void setFaixaRisco(String faixaRisco) {
        this.faixaRisco = faixaRisco;
    }

    public BigDecimal getTotalInvestido() {
        return totalInvestido;
    }

    public void setTotalInvestido(BigDecimal totalInvestido) {
        this.totalInvestido = totalInvestido;
    }

    public BigDecimal getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(BigDecimal valorAtual) {
        this.valorAtual = valorAtual;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Map<String, BigDecimal> getAtivos() {
        return ativos;
    }

    public List<Ordem> getOrdens() {
        return ordens;
    }

    public List<RecomendacaoCarteira> getRecomendacoes() {
        return recomendacoes;
    }

    // --- MÉTODOS DE GESTÃO DE ATIVOS (Lógica de Negócio) ---

    public void adicionarAtivo(String ticker, BigDecimal quantidade) {
        // Se o ativo já existe, soma a quantidade, se não, cria novo registro.
        this.ativos.merge(ticker, quantidade, BigDecimal::add);
    }

    public void removerAtivo(String ticker) {
        this.ativos.remove(ticker);
    }

    public BigDecimal calcularRentabilidade() {
        if (totalInvestido == null || totalInvestido.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        // Exemplo: ((Valor Atual - Total Investido) / Total Investido) * 100
        return valorAtual.subtract(totalInvestido)
                .divide(totalInvestido, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal("100"));
    }
}