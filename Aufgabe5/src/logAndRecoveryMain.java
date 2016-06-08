/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author LJ
 */
public class logAndRecoveryMain {

    private static final int MYTHREADS = 5;

    public static void main(String args[]) throws Exception {
        
        PersistenceManager mgmt = new PersistenceManager();
        
        ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
        String[] hostList = {"Ada", "Mark", "Helge", "Henning", "Lara"};

        for (String name : hostList) {
            //Client client = new Client(name,mgmt);
            //executor.execute(client);
        }
        executor.shutdown();
        // Wait until all threads are finished
        while (!executor.isTerminated()) {

        }
        System.out.println("\nFinished all threads");
    }
}
