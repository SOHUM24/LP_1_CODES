
import java.util.*;

public class SJF {

    // create a ready queue with all the process not executed and need to be
    // executed
    public static ArrayList<Integer> getProcessBefore(int time, HashMap<Integer, Integer> PID,
            HashMap<Integer, Integer> AT,
            HashMap<Integer, Integer> BT) {
        // gets all process before the given time ;
        ArrayList<Integer> ReadyQueue = new ArrayList<Integer>();

        for (Map.Entry<Integer, Integer> pid : PID.entrySet()) {
            if (AT.get(pid.getKey()) <= time && pid.getValue() < 1) {
                ReadyQueue.add(pid.getKey());
            }
        }
        return ReadyQueue;
    }

    public static int getShortestProcess(ArrayList<Integer> ReadyQueue, HashMap<Integer, Integer> BurstTime,
            HashMap<Integer, Integer> PID) {
        if (ReadyQueue.size() == 0) {
            return -1;
        }
        int minPid = ReadyQueue.get(0);
        int minBurstTime = BurstTime.get(ReadyQueue.get(0));
        // get the PID with less BurstTime;

        for (int i : ReadyQueue) {

            if (BurstTime.get(i) < minBurstTime) {
                minBurstTime = BurstTime.get(i);
                minPid = i;
            }

        }
        // as this shortest burst time process will be executed update its status;
        return minPid;
    }

    // ExecuteForOneUnit(pid, PID, BT, CT, AT, TAT, WT, start);

    public static void ExecuteForOneUnit(
            int pid,
            HashMap<Integer, Integer> PID,
            HashMap<Integer, Integer> BT,
            HashMap<Integer, Integer> CT,

            HashMap<Integer, Integer> AT,
            HashMap<Integer, Integer> TAT,
            HashMap<Integer, Integer> WT,
            HashMap<Integer, Integer> ORGBT,
            int currTime) {
        if (BT.containsKey(pid)) {
            // if burst time is not 0 then process is not completed and decrement it by 1
            BT.put(pid, BT.get(pid) - 1);
            // we have got the completion time of the process

        }
        if (BT.get(pid) == 0) {
            // if burst time is 0 then process is completed and mark it 1
            PID.put(pid, 1);
            // as process is completed
            CT.put(pid, currTime + 1);
            int turnaroundtime = CT.get(pid) - AT.get(pid);
            TAT.put(pid, turnaroundtime);
            int waitingtime = TAT.get(pid) - ORGBT.get(pid);
            WT.put(pid, waitingtime);

        }
    }

    public static boolean isAllProcessExecuted(HashMap<Integer, Integer> PID) {
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : PID.entrySet()) {
            if (entry.getValue() == 1) {
                count++;
            }
        }

        return count == PID.size();
    }

    // PID, AT, BT, CT, TAT, WT
    public static void GannttChart(
            HashMap<Integer, Integer> PID,
            HashMap<Integer, Integer> AT,
            HashMap<Integer, Integer> BT,
            HashMap<Integer, Integer> CT,
            HashMap<Integer, Integer> TAT,
            HashMap<Integer, Integer> WT,
            HashMap<Integer, Integer> ORGBT) {
        int start = 0;

        // this is a simulation of gannt chart where we execute it till all process are
        // completed
        // the 3 steps are
        // 1 getting current ready queue
        // 2 getting current shortest pid
        // 3 execute it for one unit
        // repeat the process untill all process are executed

        while (isAllProcessExecuted(PID) != true) {
            ArrayList<Integer> ReadyQueue = getProcessBefore(start, PID, AT, BT);
            int pid = getShortestProcess(ReadyQueue, BT, PID);
            // edge case for the idle cpu time
            if (pid != -1) {

                ExecuteForOneUnit(pid, PID, BT, CT, AT, TAT, WT, ORGBT, start);
            }
            start++;
        }
    }

    public static void main(String[] args) {

        // PID with key value pair with status 1 finished and 0 not finished
        HashMap<Integer, Integer> PID = new HashMap<Integer, Integer>();

        // WILL RETURN AT ON KEY PID
        HashMap<Integer, Integer> AT = new HashMap<Integer, Integer>();
        // WILL RETURN BT ON KEY PID
        HashMap<Integer, Integer> BT = new HashMap<Integer, Integer>();

        // WILL RETURN COMPLETION TIME ON KEY PID
        HashMap<Integer, Integer> CT = new HashMap<Integer, Integer>();
        // WILL RETURN TAT ON KEY PID
        HashMap<Integer, Integer> TAT = new HashMap<Integer, Integer>();
        // WILL RETURN WT ON KEY PID
        HashMap<Integer, Integer> WT = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> ORGBT = new HashMap<Integer, Integer>();

        // put all the arrival time values

        PID.put(1, 0);
        PID.put(2, 0);
        PID.put(3, 0);
        PID.put(4, 0);

        // put values of arrival time along with key pid

        AT.put(1, 0);
        AT.put(2, 1);
        AT.put(3, 2);
        AT.put(4, 4);

        // put values of burst time along with key pid
        BT.put(1, 5);
        BT.put(2, 3);
        BT.put(3, 4);
        BT.put(4, 1);

        ORGBT.put(1, 5);
        ORGBT.put(2, 3);
        ORGBT.put(3, 4);
        ORGBT.put(4, 1);

        GannttChart(PID, AT, BT, CT, TAT, WT, ORGBT);

        for (Map.Entry<Integer, Integer> entry : PID.entrySet()) {
            System.out.println(
                    "PID - " + entry.getKey() +
                            " AT - " + AT.get(entry.getKey()) +
                            " BT - " + BT.get(entry.getKey()) +
                            " CT - " + CT.get(entry.getKey()) +
                            " TAT - " + TAT.get(entry.getKey()) +
                            " WT - " + WT.get(entry.getKey()));
        }

    }

}
