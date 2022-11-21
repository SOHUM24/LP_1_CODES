import java.util.*;

public class SJF_NonPreemptive {
    public static HashMap<Integer, Integer> PID = new HashMap<Integer, Integer>();
    public static HashMap<Integer, Integer> AT = new HashMap<Integer, Integer>();
    public static HashMap<Integer, Integer> BT = new HashMap<Integer, Integer>();
    public static HashMap<Integer, Integer> WT = new HashMap<Integer, Integer>();
    public static HashMap<Integer, Integer> CT = new HashMap<Integer, Integer>();
    public static HashMap<Integer, Integer> TAT = new HashMap<Integer, Integer>();

    // ready queue which will hold all the process which need to be executed;

    public static ArrayList<Integer> ReadyQueue = new ArrayList<Integer>();

    public static boolean AreAllProcessExecuted() {
        int count = 0;
        for (Map.Entry<Integer, Integer> processID : PID.entrySet()) {
            if (processID.getValue() == 1) {
                count++;
            }
        }
        // we will return true if every entry is 1
        // if every entry is 1 then all process are executed
        // else some process are still pending

        return count == PID.size();
    }

    public static void getReadyQueue(int currentTime) {
        // we will select only those process which has arrival time less than or equal
        // to current time
        for (Map.Entry<Integer, Integer> ArrivalTime : AT.entrySet()) {
            int ProcessID = ArrivalTime.getKey();
            int ProcessArrivalTime = ArrivalTime.getValue();

            if (
            // check if arrival time of process is less than equal to current time
            ProcessArrivalTime <= currentTime
                    // check if current process is already executed by checking the status
                    && PID.get(ProcessID) == -1
                    && ReadyQueue.contains(ProcessID) == false) {
                // add that ID in the ready queue as it is ready to be executed
                ReadyQueue.add(ProcessID);
            }

        }
        System.out.println("Ready Queue is - " + ReadyQueue);

    }

    public static int getShortestFromReadyQueue() {
        // to make sure initial value over-rides the minprocess
        int minProcessID = 0;
        int minBurstTime = Integer.MAX_VALUE;

        for (Integer ProcessID : ReadyQueue) {

            // get the burst time of every process and compare with min Burst time
            int BurstTime = BT.get(ProcessID);
            // update min burst time if is less than previous burst time
            if (BurstTime < minBurstTime) {
                minBurstTime = BurstTime;
                // will also need to update ID accordingly
                minProcessID = ProcessID;
            }
        }

        // now return the minimum process ID to execute it
        return minProcessID;
    }

    public static int ExecuteProcess(int curentTime, int ProcessID) {

        int completionTime = curentTime + BT.get(ProcessID);
        CT.put(ProcessID, completionTime);

        // now calculate TAT i.e Completion Time - Arrival Time
        int turnAround = completionTime - AT.get(ProcessID);
        TAT.put(ProcessID, turnAround);

        // now calculate WT i.e Turn Around Time - Burst Time
        int waiting = turnAround - BT.get(ProcessID);
        WT.put(ProcessID, waiting);

        // mark the process as completed in PID Hashtable
        PID.put(ProcessID, 1);

        // get index of process in readyqueue
        int index = ReadyQueue.indexOf(ProcessID);
        // remove that element from readyqueue
        ReadyQueue.remove(index);
        System.out.println("Process - " + ProcessID + " Executed At " + curentTime);
        return completionTime;
    }

    public static void GanntChart() {
        // initially our time is 0
        int currentTime = 0;

        while (AreAllProcessExecuted() == false) {

            getReadyQueue(currentTime);

            // this is to check if readyqueue is empty
            // ready queue will always be empty if no process is available at particular
            // arrival time
            // check for this example readyqueue is empty for current = 0;

            if (!ReadyQueue.isEmpty()) {
                // now get the shortest process according to burst time
                int ProcessID = getShortestFromReadyQueue();
                // we got the process ID which has minimum Burst Time

                // now execute this process and increment the currentTime
                currentTime = ExecuteProcess(currentTime, ProcessID);

            } else {
                // if cpu is idle move to next unit of time
                currentTime++;
            }
        }

    }

    public static void PrintChart() {

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

    public static void main(String[] args) {

        // PID contains ID and then status of completion
        // if value = -1 it is in not completed;
        // if value = 1 it is completed;
        PID.put(1, -1);
        PID.put(2, -1);
        PID.put(3, -1);
        PID.put(4, -1);
        PID.put(5, -1);
        // arrival time with keys pointing to our PID hashmap
        AT.put(1, 0);
        AT.put(2, 2);
        AT.put(3, 4);
        AT.put(4, 6);
        AT.put(5, 6);
        // Burst Time with keys pointing to our PID Hashmap

        BT.put(1, 5);
        BT.put(2, 3);
        BT.put(3, 2);
        BT.put(4, 4);
        BT.put(5, 1);

        // lets create a gannt chart to keep track of progress
        GanntChart();

        PrintChart();
    }
}
