package edu.eci.arsw.threads;

import edu.eci.arsw.blacklistvalidator.HostBlackListsValidator;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.LinkedList;
import java.util.List;

public class BlackListSearchThread extends Thread{
    private int start;
    private int end;
    private String IpAddress;
    private List<Integer> occurrences = new LinkedList<>();
    private int checkCount = 0;

    private HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();

    public BlackListSearchThread(int start, int end, String ipAddress) {
        this.start = start;
        this.end = end;
        IpAddress = ipAddress;
    }

    @Override
    public void run (){
        for (int i = start; i < end; i++) {
            checkCount++;
            if (skds.isInBlackListServer(i, IpAddress)) {
                occurrences.add(i);
            }
        }
    }

    public List<Integer> getOccurrences() {
        return occurrences;
    }
    public int getCheckCount() {
        return checkCount;
    }
}
