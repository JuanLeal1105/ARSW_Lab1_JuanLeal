/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.util.List;

/**
 *
 * @author hcadavid
 */
public class Main {

    public static void main(String[] args) {
        HostBlackListsValidator hostValidator = new HostBlackListsValidator();

        String ipToCheck = "212.24.34.55";
        int numberOfThreads = 5;

        List<Integer> blackListOcurrences = hostValidator.checkHost(ipToCheck, numberOfThreads);
        if(blackListOcurrences.isEmpty()){
            System.out.println("The host " + ipToCheck + " was not fout in any blacklist");
        }else{
            System.out.println("The host " + ipToCheck + " was found in the following blacklists: " + blackListOcurrences);
        }
    }
    
}
