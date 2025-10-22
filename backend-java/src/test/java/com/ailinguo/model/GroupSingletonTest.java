package com.ailinguo.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GroupSingletonTest {

    @Test
    public void testSingletonPattern() {
        System.out.println("\n=== TESTANDO PADRÃO SINGLETON PARA GROUP ===");
        
        // Obter primeira instância
        Group group1 = Group.getInstance();
        System.out.println("Primeira instância obtida. HashCode: " + group1.hashCode());
        
        // Obter segunda instância
        Group group2 = Group.getInstance();
        System.out.println("Segunda instância obtida. HashCode: " + group2.hashCode());
        
        // Obter terceira instância
        Group group3 = Group.getInstance();
        System.out.println("Terceira instância obtida. HashCode: " + group3.hashCode());
        
        // Verificar se todas as instâncias são a mesma
        assertSame(group1, group2, "As duas primeiras instâncias devem ser a mesma");
        assertSame(group2, group3, "As duas últimas instâncias devem ser a mesma");
        assertSame(group1, group3, "A primeira e terceira instâncias devem ser a mesma");
        
        System.out.println("✅ Confirmado: Todas as instâncias são a MESMA! (Singleton funcionando)");
        
        // Testar funcionalidade básica
        assertNotNull(group1.getName(), "Nome do grupo não deve ser nulo");
        assertNotNull(group1.getDescription(), "Descrição do grupo não deve ser nula");
        assertTrue(group1.getOpen(), "Grupo deve estar aberto por padrão");
        assertEquals(0, group1.getMembers(), "Grupo deve começar com 0 membros");
        
        System.out.println("✅ Funcionalidade básica do Group testada com sucesso!");
        System.out.println("Nome do grupo: " + group1.getName());
        System.out.println("Descrição: " + group1.getDescription());
        System.out.println("===============================================\n");
    }
    
    @Test
    public void testGroupManagement() {
        System.out.println("=== TESTANDO GERENCIAMENTO DE GRUPOS ===");
        
        Group grupoPrincipal = Group.getInstance();
        
        // Criar usuários de teste
        User usuario1 = User.builder()
                .id(1L)
                .name("João Silva")
                .email("joao@example.com")
                .password("senha123")
                .cefrLevel(User.CefrLevel.A2)
                .build();
        
        User usuario2 = User.builder()
                .id(2L)
                .name("Maria Santos")
                .email("maria@example.com")
                .password("senha456")
                .cefrLevel(User.CefrLevel.B1)
                .build();
        
        // Testar adição de membros
        grupoPrincipal.adicionarMembro(usuario1);
        assertEquals(1, grupoPrincipal.getMembers(), "Grupo deve ter 1 membro após adicionar usuário1");
        
        grupoPrincipal.adicionarMembro(usuario2);
        assertEquals(2, grupoPrincipal.getMembers(), "Grupo deve ter 2 membros após adicionar usuário2");
        
        // Testar criação de subgrupos
        grupoPrincipal.criarSubgrupo("Grupo Iniciantes", "Grupo para usuários iniciantes");
        grupoPrincipal.criarSubgrupo("Grupo Avançados", "Grupo para usuários avançados");
        
        List<Group> subgrupos = grupoPrincipal.listarSubgrupos();
        assertEquals(2, subgrupos.size(), "Devem existir 2 subgrupos criados");
        
        // Testar remoção de membro
        grupoPrincipal.removerMembro(usuario1);
        assertEquals(1, grupoPrincipal.getMembers(), "Grupo deve ter 1 membro após remover usuário1");
        
        System.out.println("✅ Gerenciamento de grupos testado com sucesso!");
        grupoPrincipal.exibirDetalhes();
        System.out.println("===============================================\n");
    }
    
    @Test
    public void testMultipleThreadsSingleton() {
        System.out.println("=== TESTANDO SINGLETON COM MÚLTIPLAS THREADS ===");
        
        final int NUM_THREADS = 10;
        final Group[] instances = new Group[NUM_THREADS];
        final Thread[] threads = new Thread[NUM_THREADS];
        
        // Criar múltiplas threads que tentam obter a instância
        for (int i = 0; i < NUM_THREADS; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                instances[index] = Group.getInstance();
                System.out.println("Thread " + index + " obteve instância. HashCode: " + instances[index].hashCode());
            });
        }
        
        // Iniciar todas as threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Aguardar todas as threads terminarem
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        // Verificar se todas as instâncias são a mesma
        Group firstInstance = instances[0];
        for (int i = 1; i < NUM_THREADS; i++) {
            assertSame(firstInstance, instances[i], 
                "Todas as instâncias obtidas em threads diferentes devem ser a mesma");
        }
        
        System.out.println("✅ Singleton thread-safe funcionando corretamente!");
        System.out.println("===============================================\n");
    }
    
    @Test
    public void testGroupOperations() {
        System.out.println("=== TESTANDO OPERAÇÕES DO GRUPO ===");
        
        Group grupo = Group.getInstance();
        
        // Testar criação de múltiplos subgrupos
        grupo.criarSubgrupo("Grupo A1", "Iniciantes absolutos");
        grupo.criarSubgrupo("Grupo A2", "Iniciantes com conhecimento básico");
        grupo.criarSubgrupo("Grupo B1", "Intermediários");
        grupo.criarSubgrupo("Grupo B2", "Intermediários avançados");
        grupo.criarSubgrupo("Grupo C1", "Avançados");
        
        List<Group> subgrupos = grupo.listarSubgrupos();
        assertEquals(5, subgrupos.size(), "Devem existir 5 subgrupos");
        
        // Verificar se os subgrupos têm IDs únicos
        List<Long> ids = subgrupos.stream().map(Group::getId).toList();
        assertEquals(5, ids.stream().distinct().count(), "Todos os IDs devem ser únicos");
        
        // Testar adição de múltiplos usuários
        for (int i = 1; i <= 5; i++) {
            User usuario = User.builder()
                    .id((long) i)
                    .name("Usuário " + i)
                    .email("usuario" + i + "@example.com")
                    .password("senha" + i)
                    .cefrLevel(User.CefrLevel.A1)
                    .build();
            grupo.adicionarMembro(usuario);
        }
        
        assertEquals(5, grupo.getMembers(), "Grupo deve ter 5 membros");
        assertEquals(5, grupo.getMembersList().size(), "Lista de membros deve ter 5 usuários");
        
        System.out.println("✅ Operações do grupo testadas com sucesso!");
        grupo.exibirDetalhes();
        System.out.println("===============================================\n");
    }
}
