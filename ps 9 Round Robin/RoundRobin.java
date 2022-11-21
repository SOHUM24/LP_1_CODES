
import java.util.*;

public class RoundRobin {

    public static TreeMap<Integer, Integer> PID = new TreeMap<Integer, Integer>();
    public static TreeMap<Integer, Integer> AT = new TreeMap<Integer, Integer>();
    public static TreeMap<Integer, Integer> BT = new TreeMap<Integer, Integer>();
    public static TreeMap<Integer, Integer> CT = new TreeMap<Integer, Integer>();
    public static TreeMap<Integer, Integer> TAT = new TreeMap<Integer, Integer>();
    public static TreeMap<Integer, Integer> ORGBT = new TreeMap<Integer, Integer>();
    public static TreeMap<Integer, Integer> WT = new TreeMap<Integer, Integer>();

    public static Queue<Integer> readyqueue = new LinkedList<Integer>();

    public static LinkedHashMap<Integer, Integer> SORTEDAT = new LinkedHashMap<Integer, Integer>();

    public static Boolean allProcessExecuted() {
        int count = 0;
        for (Map.Entry<Integer, Integer> process : PID.entrySet()) {
            if (process.getValue() == 1) {
                count++;
            }
        }
        return count == PID.size();
    }

    public static void GetReadyQueue(int time) {
        for (Map.Entry<Integer, Integer> at : AT.entrySet()) {
            int pid = at.getKey();
            int Arrivaltime = at.getValue();
            if (Arrivaltime <= time && readyqueue.contains(pid) == false && PID.get(pid) != 1) {
                // add it in readyQueue;
                readyqueue.add(pid);
            }

        }
    }

    public static int ExecuteProcess(int pid, int currentTime, int Quantam) {
        int BurstTime = BT.get(pid);
        System.out.println(BurstTime);
        //  chech for the burst time if it is greater than quantam
        if (BurstTime > Quantam) {
            BT.put(pid, BurstTime - Quantam);
            GetReadyQueue(currentTime + Quantam);
       
            // remove from first and add to last as it has burst time left
            readyqueue.remove();
            readyqueue.add(pid);
        } else {
            BT.put(pid, 0);
            PID.put(pid, 1);
            GetReadyQueue(currentTime + BurstTime);

            // calcualte CT
            CT.put(pid, currentTime + BurstTime);
            // calculate TAT
            TAT.put(pid, CT.get(pid) - AT.get(pid));
            // calculate WT
            WT.put(pid, TAT.get(pid) - ORGBT.get(pid));
            // remove the readyqueue as this process is completed
            readyqueue.remove();
            return currentTime + BurstTime;
        }

        return currentTime + Quantam;
    }

    public static void GanttChart(int Quantam) {

        int currentTime = 0;

        GetReadyQueue(currentTime);

        while (!readyqueue.isEmpty()) {
            // get the top process
            int PID = readyqueue.peek();
            // execute it
            currentTime = ExecuteProcess(PID, currentTime, Quantam);
        }

    }

    public static void PrintChart() {

        for (Map.Entry<Integer, Integer> entry : PID.entrySet()) {
            System.out.println(
                    "PID - " + entry.getKey() +
                            " AT - " + AT.get(entry.getKey()) +
                            " CT - " + CT.get(entry.getKey()) +
                            " TAT - " + TAT.get(entry.getKey()) +
                            " WT - " + WT.get(entry.getKey())

            );
        }

    }

    public static void main(String[] args) {

        int Quantam = 2;
        // status -1 means not complete
        PID.put(1, -1);
        PID.put(2, -1);
        PID.put(3, -1);
        PID.put(4, -1);

        // structure of hashmap - PID , AT
        AT.put(1, 0);
        AT.put(2, 1);
        AT.put(3, 2);
        AT.put(4, 4);

        // structure of hashmap - PID ,BT
        BT.put(1, 5);
        BT.put(2, 4);
        BT.put(3, 2);
        BT.put(4, 1);

        ORGBT.put(1, 5);
        ORGBT.put(2, 4);
        ORGBT.put(3, 2);
        ORGBT.put(4, 1);

        GanttChart(Quantam);
        PrintChart();
    }
}
