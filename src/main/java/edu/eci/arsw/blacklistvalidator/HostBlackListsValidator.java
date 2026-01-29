/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import edu.eci.arsw.threads.BlackListControl;
import edu.eci.arsw.threads.BlackListSearchThread;
import edu.eci.arsw.threads.BlackListSyncThread;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress, int N) {
        HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();
        int totalServers = skds.getRegisteredServersCount();
        List<Integer> blackListOccurrences = new LinkedList<>();
        List<BlackListSearchThread> threads = new LinkedList<>();

        int blockSize = totalServers / N;
        int remainder = totalServers % N;
        int start = 0;
        int totalChecked = 0;

        for (int i = 0; i < N; i++) {
            int extra = (i < remainder) ? 1 : 0;
            int end = start + blockSize + extra;

            BlackListSearchThread thread = new BlackListSearchThread(start, end, ipaddress);
            threads.add(thread);
            thread.start();
            start = end;
        }

        for (BlackListSearchThread t : threads) {
            try {
                t.join();
                blackListOccurrences.addAll(t.getOccurrences());
                totalChecked += t.getCheckCount();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (blackListOccurrences.size() >= BLACK_LIST_ALARM_COUNT) {
            skds.reportAsNotTrustworthy(ipaddress);
        } else {
            skds.reportAsTrustworthy(ipaddress);
        }

        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{totalChecked, totalServers});
        return blackListOccurrences;
    }

    public List<Integer> checkHostWithSynchronization(String ipaddress, int N) {
        HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();
        int totalServers = skds.getRegisteredServersCount();
        int blockSize = totalServers / N;
        int remainder = totalServers % N;

        BlackListControl control = new BlackListControl();
        List<BlackListSyncThread> threads = new LinkedList<>();
        List<Integer> blackListOccurrences = new LinkedList<>();

        int start = 0;
        int totalChecked = 0;
        for (int i = 0; i < N; i++) {
            int extra = (i < remainder) ? 1 : 0;
            int end = start + blockSize + extra;

            BlackListSyncThread t = new BlackListSyncThread(start, end, ipaddress, control);
            threads.add(t);
            t.start();
            start = end;
        }

        for (BlackListSyncThread t : threads) {
            try {
                t.join();
                blackListOccurrences.addAll(t.getOccurrences());
                totalChecked += t.getCheckCount();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (blackListOccurrences.size() >= BLACK_LIST_ALARM_COUNT) {
            skds.reportAsNotTrustworthy(ipaddress);
        } else {
            skds.reportAsTrustworthy(ipaddress);
        }

        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{totalChecked, totalServers});
        return blackListOccurrences;
    }


    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
}
