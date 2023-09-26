import java.util.Scanner;

class Processo {
    int pid;
    int arrivalTime;
    int burstTime;
    int priority;
    int quantum;
    int remainingTime;
    int turnaroundTime; // Tempo de retorno
    int waitingTime; // Tempo de espera

    public Processo(int pid, int arrivalTime, int burstTime, int priority, int quantum) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.quantum = quantum;
        this.remainingTime = burstTime;
        this.turnaroundTime = 0;
        this.waitingTime = 0;
    }
}
