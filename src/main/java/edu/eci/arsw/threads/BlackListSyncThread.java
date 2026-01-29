package edu.eci.arsw.threads;

import java.util.LinkedList;
import java.util.List;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

public class BlackListSyncThread extends Thread{
    private int start;
    private int end;
    private String IpAddress;
    private BlackListControl control;

    private List<Integer> occurrences = new LinkedList<>();
    private int checkCount = 0;
    private HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();

    public BlackListSyncThread(int start, int end, String ipAddress, BlackListControl control) {
        this.start = start;
        this.end = end;
        IpAddress = ipAddress;
        this.control = control;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            if (control.stopSearch()) {
                break;
            }

            checkCount++;
            if (skds.isInBlackListServer(i, IpAddress)) {
                occurrences.add(i);
                control.reportOccurrence();
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
