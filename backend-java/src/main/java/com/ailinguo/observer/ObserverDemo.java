package com.ailinguo.observer;

/**
 * Exemplo simples demonstrando o padrão Observer
 * Para testar: execute este main e veja as notificações nos logs
 */
public class ObserverDemo {
    
    public static void main(String[] args) {
        // Criar o subject (observável)
        UserProgressSubject subject = new UserProgressSubject();
        
        // Criar os observers
        AchievementObserver achievementObserver = new AchievementObserver();
        NotificationObserver notificationObserver = new NotificationObserver();
        
        // Registrar os observers
        subject.attach(achievementObserver);
        subject.attach(notificationObserver);
        
        System.out.println("=== Simulando progressão do usuário ===\n");
        
        // Simular ganho de XP
        System.out.println("1. Usuário ganha 10 XP:");
        subject.userGainedXp(1L, 10, 10);
        
        System.out.println("\n2. Usuário ganha mais 90 XP (total: 100):");
        subject.userGainedXp(1L, 90, 100);
        
        System.out.println("\n3. Usuário sobe para nível 5:");
        subject.userLeveledUp(1L, 5);
        
        System.out.println("\n4. Usuário completa uma tarefa:");
        subject.userCompletedTask(1L, "VOCABULARY_REVIEW");
        
        System.out.println("\n=== Removendo um observer ===");
        subject.detach(achievementObserver);
        
        System.out.println("\n5. Usuário ganha mais XP (só NotificationObserver notificado):");
        subject.userGainedXp(1L, 15, 115);
    }
}
