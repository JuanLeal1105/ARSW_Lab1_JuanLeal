/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author hcadavid
 */
public class Main {
    public static void main(String[] args) {
        HostBlackListsValidator hostValidator = new HostBlackListsValidator();
        String ipToCheck = "200.24.34.55";
        int numberOfThreads = 5;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select search mode:");
        System.out.println("1. Normal");
        System.out.println("2. Synchronized");
        System.out.print("Option: ");

        int option = scanner.nextInt();
        List<Integer> blackListOccurrences;

        if (option == 2) {
            blackListOccurrences = hostValidator.checkHostWithSynchronization(ipToCheck, numberOfThreads);
        } else {
            blackListOccurrences = hostValidator.checkHost(ipToCheck, numberOfThreads);
        }

        if (blackListOccurrences.isEmpty()) {
            System.out.println("The host " + ipToCheck + " was not found in any blacklist");
        } else {
            System.out.println("The host " + ipToCheck + " was found in the following blacklists: " + blackListOccurrences);
        }
        scanner.close();
    }
    
}
