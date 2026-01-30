package edu.eci.arsw.threads;

import edu.eci.arsw.blacklistvalidator.HostBlackListsValidator;

import java.util.List;
import java.util.Scanner;

public class PerformanceExperiments {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HostBlackListsValidator validator = new HostBlackListsValidator();
        String ip = "202.24.34.55";

        int cores = Runtime.getRuntime().availableProcessors();

        System.out.println("Seleccione el experimento a ejecutar:");
        System.out.println("1. Un solo hilo");
        System.out.println("2. Tantos hilos como núcleos del procesador");
        System.out.println("3. Tantos hilos como el doble de núcleos del procesador");
        System.out.println("4. 50 hilos");
        System.out.println("5. 100 hilos");

        int option = sc.nextInt();
        int threads = 0;

        switch (option) {
            case 1:
                threads = 1;
                break;
            case 2:
                threads = cores;
                break;
            case 3:
                threads = cores * 2;
                break;
            case 4:
                threads = 50;
                break;
            case 5:
                threads = 100;
                break;
            default:
                System.out.println("Opción inválida");
                System.exit(0);
        }

        System.out.println("Ejecutando con " + threads + " hilos...");

        long startTime = System.currentTimeMillis();
        List<Integer> results = validator.checkHost(ip, threads);
        long endTime = System.currentTimeMillis();

        System.out.println("Tiempo de ejecución: " + (endTime - startTime) + " ms");
        System.out.println("Ocurrencias encontradas: " + results.size());

        sc.close();
    }
}
