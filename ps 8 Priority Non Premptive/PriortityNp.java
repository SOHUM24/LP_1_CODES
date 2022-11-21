import java.util.*;

public class PriortityNp {
    public static HashMap<Integer, Integer> PID = new HashMap<Integer, Integer>();
    public static HashMap<Integer, Integer> AT = new HashMap<Integer, Integer>();
    public static HashMap<Integer, Integer> BT = new HashMap<Integer, Integer>();
    public static HashMap<Integer, Integer> Priority = new HashMap<Integer, Integer>();
    public static HashMap<Integer, Integer> WT = new HashMap<Integer, Integer>();
    public static HashMap<Integer, Integer> CT = new HashMap<Integer, Integer>();
    public static HashMap<Integer, Integer> TAT = new HashMap<Integer, Integer>();

    public static boolean areAllProcessExecuted() {
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : PID.entrySet()) {
            if (entry.getValue() == 1) {
                count++;
            }
        }
        return count == PID.size();
    }

    public static ArrayList<Integer> getReadyQueue(int time) {
        ArrayList<Integer> ReadyQueue = new ArrayList<Integer>();

        // iterate arrival time which have not been executed

        for (Map.Entry<Integer, Integer> entry : AT.entrySet()) {
            // if arrival time is less than time and PID is not executed
            if (entry.getValue() <= time && PID.get(entry.getKey()) < 1) {
                ReadyQueue.add(entry.getKey());
            }
        }
        return ReadyQueue;
    }

    public static int getHighestPriority(ArrayList<Integer> ReadyQueue) {
        int highestPID = ReadyQueue.get(0);
        int highestPriority = Priority.get(ReadyQueue.get(0));
        for (Integer processID : ReadyQueue) {
            if (highestPriority > Priority.get(processID)) {
                highestPriority = Priority.get(processID);
                highestPID = processID;
            }

        }

        return highestPID;
    }

    public static int ExecuteTask(int currtime, int pid) {
        int EndTime = currtime + BT.get(pid);
        // update the completion time table
        CT.put(pid, EndTime);
        // update the TAT time table
        int turnaround = CT.get(pid) - AT.get(pid);
        TAT.put(pid, turnaround);

        int waiting = TAT.get(pid) - BT.get(pid);
        WT.put(pid, waiting);

        PID.put(pid, 1);
        return EndTime;
    }

    public static void GannttChart() {
        // get the ready queue
        // get the highest priority pid
        // execute it and calculate the WT , TAT

        int currentTime = 0;

        while (areAllProcessExecuted() != true) {
            // step - 1 -> get all the process which are to be executed
            ArrayList<Integer> ReadyQueue = getReadyQueue(currentTime);
            if (!ReadyQueue.isEmpty()) {
                int pid = getHighestPriority(ReadyQueue);
                currentTime = ExecuteTask(currentTime, pid);
            } else {
                currentTime = currentTime + 1;
            }

        }

    }

    public static void PrintChart() {

        for (Map.Entry<Integer, Integer> entry : PID.entrySet()) {
            System.out.println(
                    "PID - " + entry.getKey() +
                            " AT - " + AT.get(entry.getKey()) +
                            " BT - " + BT.get(entry.getKey()) +

                            " PT - " + Priority.get(entry.getKey()) +
                            " CT - " + CT.get(entry.getKey()) +
                            " TAT - " + TAT.get(entry.getKey()) +
                            " WT - " + WT.get(entry.getKey()));
        }

    }

    public static void main(String[] args) {
        // FILL ALL THE PID WITH STATUS = 0 I.E NOT COMPLETED
        // STATUS = 1 MEANS PROCESS IS COMPLETED
        PID.put(1, 0);
        PID.put(2, 0);
        PID.put(3, 0);
        PID.put(4, 0);
        PID.put(5, 0);
        PID.put(6, 0);
        PID.put(7, 0);
        // FILL ALL THE AT ALONG WITH KEY PID
        AT.put(1, 0);
        AT.put(2, 1);
        AT.put(3, 3);
        AT.put(4, 4);
        AT.put(5, 5);
        AT.put(6, 6);
        AT.put(7, 10);

        // FILL ALL THE BT ALONG WITH KEY PID

        BT.put(1, 8);
        BT.put(2, 2);
        BT.put(3, 4);
        BT.put(4, 1);
        BT.put(5, 6);
        BT.put(6, 5);
        BT.put(7, 1);

        // CALL THE GANNT CHART FUNCTION TO
        Priority.put(1, 3);
        Priority.put(2, 4);
        Priority.put(3, 4);
        Priority.put(4, 5);
        Priority.put(5, 2);
        Priority.put(6, 6);
        Priority.put(7, 1);

        // the criteria for highest priority is higher the number greater the priority

        GannttChart();
        PrintChart();
    }
}
