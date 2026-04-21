package br.com.criptaliza.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    // Mude de UUID para Long para casar com o banco (cd_cliente)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String idioma;
    private final List<Investidor> investidores;

    // Construtor para criar novos clientes (o banco gera o ID)
    public Cliente(String nome, String email, String telefone, String idioma) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.idioma = idioma;
        this.investidores = new ArrayList<>();
    }

    // NOVO: Construtor para quando os dados vêm do banco (já com ID)
    public Cliente(Long id, String nome, String email, String telefone, String idioma) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.idioma = idioma;
        this.investidores = new ArrayList<>();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // Adicione este para o DAO parar de reclamar ou mude no DAO para getId()
    public Long getCodigo() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    // ... restante dos métodos (adicionarInvestidor, etc)
}