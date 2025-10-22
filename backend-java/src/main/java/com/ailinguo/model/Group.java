package com.ailinguo.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "groups")
@EntityListeners(AuditingEntityListener.class)
public class Group {
    
    // Atributo estático privado para guardar a ÚNICA instância da classe
    private static Group instancia;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    private Integer members;
    private Boolean open;
    
    // Atributos para gerenciamento centralizado
    private static AtomicInteger proximoGrupoId = new AtomicInteger(1);
    private static List<Group> gruposCriados = new ArrayList<>();
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Construtor PRIVADO: impede a criação de instâncias com 'new Group()' de fora da classe
    private Group() {
        this.id = 1L;
        this.name = "Grupo Principal AILinguo";
        this.description = "O grupo principal para todos os usuários da plataforma";
        this.members = 0;
        this.open = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.membersList = new ArrayList<>();
        System.out.println("👥 Instância ÚNICA do Grupo Principal criada! (Singleton) 👥");
    }
    
    // Método estático público para obter a instância única
    public static synchronized Group getInstance() {
        if (instancia == null) {
            instancia = new Group();
        }
        return instancia;
    }
    
    // Relacionamento many-to-many com User
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "group_members",
        joinColumns = @JoinColumn(name = "group_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> membersList;
    
    // Getters e Setters manuais
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Integer getMembers() { return members; }
    public void setMembers(Integer members) { this.members = members; }
    
    public Boolean getOpen() { return open; }
    public void setOpen(Boolean open) { this.open = open; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public List<User> getMembersList() { return membersList; }
    public void setMembersList(List<User> membersList) { this.membersList = membersList; }
    
    // Métodos de negócio para gerenciamento de grupos
    public void adicionarMembro(User usuario) {
        if (usuario != null && !this.membersList.contains(usuario)) {
            this.membersList.add(usuario);
            this.members = this.membersList.size();
            this.updatedAt = LocalDateTime.now();
            System.out.println("[Group Singleton] Usuário '" + usuario.getName() + "' adicionado ao grupo principal. Total de membros: " + this.members);
        }
    }
    
    public void removerMembro(User usuario) {
        if (usuario != null && this.membersList.contains(usuario)) {
            this.membersList.remove(usuario);
            this.members = this.membersList.size();
            this.updatedAt = LocalDateTime.now();
            System.out.println("[Group Singleton] Usuário '" + usuario.getName() + "' removido do grupo principal. Total de membros: " + this.members);
        }
    }
    
    public void criarSubgrupo(String nome, String descricao) {
        int novoId = proximoGrupoId.getAndIncrement();
        Group subgrupo = new Group();
        subgrupo.setId((long) novoId);
        subgrupo.setName(nome);
        subgrupo.setDescription(descricao);
        subgrupo.setMembers(0);
        subgrupo.setOpen(true);
        subgrupo.setCreatedAt(LocalDateTime.now());
        subgrupo.setUpdatedAt(LocalDateTime.now());
        subgrupo.setMembersList(new ArrayList<>());
        
        gruposCriados.add(subgrupo);
        System.out.println("[Group Singleton] Subgrupo '" + nome + "' (ID: " + novoId + ") criado pelo grupo principal.");
    }
    
    public List<Group> listarSubgrupos() {
        System.out.println("[Group Singleton] Listando " + gruposCriados.size() + " subgrupo(s) criado(s)...");
        return new ArrayList<>(gruposCriados);
    }
    
    public void exibirDetalhes() {
        System.out.println("\n--- Detalhes do Grupo Principal (Singleton) ---");
        System.out.println("ID: " + id);
        System.out.println("Nome: " + name);
        System.out.println("Descrição: " + description);
        System.out.println("Membros: " + members);
        System.out.println("Aberto: " + (open ? "Sim" : "Não"));
        System.out.println("Criado em: " + createdAt);
        System.out.println("Atualizado em: " + updatedAt);
        if (membersList.isEmpty()) {
            System.out.println("Lista de Membros: Nenhum membro adicionado ainda.");
        } else {
            System.out.println("Lista de Membros (" + membersList.size() + "):");
            for (User member : membersList) {
                System.out.println("  - " + member.getName() + " (ID: " + member.getId() + ", Email: " + member.getEmail() + ")");
            }
        }
        System.out.println("Subgrupos criados: " + gruposCriados.size());
        System.out.println("HashCode da Instância: " + this.hashCode());
        System.out.println("----------------------------------------");
    }
}

