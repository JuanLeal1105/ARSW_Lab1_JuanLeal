/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author hcadavid
 */
public class CountThreadsMain {
    public static void main(String a[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese start o run dependiendo del método que desee usar: ");
        String choice = sc.nextLine().toLowerCase();


        List<CountThread> threads = new ArrayList<>();

        threads.add(new CountThread(0, 99));
        threads.add(new CountThread(99, 199));
        threads.add(new CountThread(200, 299));

        threads.forEach(choice.equals("start") ? CountThread::start : CountThread::run);
    }
}
