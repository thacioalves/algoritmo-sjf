import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class SJF {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Algoritmo de Escalonamento SJF (Shortest Job First)");
        System.out.print("\nInforme a quantidade de processos a serem criados: ");
        int numProcesses = scanner.nextInt();

        List<Processo> processos = new ArrayList<>();
        for (int i = 1; i <= numProcesses; i++) {
            int pid = i;
            int arrivalTime = (int) (Math.random() * 16); // Intervalo de 0 a 15
            int burstTime = (int) (Math.random() * 25) + 1; // Intervalo de 1 a 25
            int priority = (int) (Math.random() * 5) + 1; // Intervalo de 1 a 5
            int quantum = (int) (Math.random() * 9) + 4; // Intervalo de 4 a 12

            processos.add(new Processo(pid, arrivalTime, burstTime, priority, quantum));
        }

        // Ordenar os processos por tempo de chegada (arrival time)
        Collections.sort(processos, Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        List<Processo> filaDeEspera = new ArrayList<>();
        List<Processo> processosConcluidos = new ArrayList<>();

        while (!processos.isEmpty() || !filaDeEspera.isEmpty()) {
            // Adicionar processos que chegaram no tempo atual à fila de espera
            while (!processos.isEmpty() && processos.get(0).arrivalTime <= currentTime) {
                filaDeEspera.add(processos.remove(0));
            }

            if (!filaDeEspera.isEmpty()) {
                // Ordenar a fila de espera pelo burst time (tempo de execução)
                filaDeEspera.sort(Comparator.comparingInt(p -> p.burstTime));

                Processo processoAtual = filaDeEspera.get(0);
                filaDeEspera.remove(0);

                int tempoExecutado = Math.min(processoAtual.remainingTime, processoAtual.quantum);
                currentTime += tempoExecutado;
                processoAtual.remainingTime -= tempoExecutado;

                if (processoAtual.remainingTime > 0) {
                    // O processo ainda não está concluído, volta para a fila de espera
                    filaDeEspera.add(processoAtual);
                } else {
                    // O processo está concluído
                    processoAtual.turnaroundTime = currentTime - processoAtual.arrivalTime;
                    processoAtual.waitingTime = processoAtual.turnaroundTime - processoAtual.burstTime;
                    processosConcluidos.add(processoAtual);
                }
            } else {
                // Não há processos na fila de espera, avança o tempo
                currentTime++;
            }
        }

        // Exibir a ordem correta de execução dos programas
        System.out.println("Ordem correta de execução dos programas (PID - Burst Time):");
        for (Processo processo : processosConcluidos) {
            System.out.println(processo.pid + " - " + processo.burstTime);
        }

        // Calcular o tempo médio de execução
        double tempoMedioExecucao = 0;
        for (Processo processo : processosConcluidos) {
            tempoMedioExecucao += processo.turnaroundTime;
        }
        tempoMedioExecucao /= numProcesses;

        System.out.println("\nNúmero de processos: " + numProcesses);
        System.out.println("Tempo médio de execução: " + tempoMedioExecucao);

        // Exibir o tempo de execução de cada processo
        System.out.println("\nTempo de execução de cada processo:");
        for (Processo processo : processosConcluidos) {
            System.out.println("PID " + processo.pid + ": " + processo.turnaroundTime);
        }
    }

}