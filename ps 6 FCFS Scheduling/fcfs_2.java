// FCFS
import java.util.Scanner;

public class fcfs_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //Entering the number of processes
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        //Initializing an array for storing Burst Time
        int bt[] = new int[20];
        //Initializing an array for storing Arrival Time
        int at[] = new int[20];

        System.out.println("\nEnter the Burst Time for each process.");
        for (int i = 0; i < n; i++) {
            System.out.print("\nFor Process " + (i + 1) + ":");
            bt[i] = sc.nextInt();
        }

        System.out.println("\nEnter the arrival time for each process.");
        for (int j = 0; j < n; j++) {
            System.out.print("\nFor Process " + (j + 1) + ":");
            at[j] = sc.nextInt();
        }

        //Invoking the function to calculate the average waiting and turnaround times.
        avg_wt_tt(n, bt, at);
        sc.close();

    }

    //function to calculate Waiting Time for each process
    private static void waiting_time(int n, int[] bt, int[] wt, int[] at) {
        //To store the burst time of previous process.
        int temp[] = new int[20];
        temp[0] = 0;

        for (int i = 0; i < n; i++) {
            wt[i] = 0;
            temp[i + 1] = temp[i] + bt[i];

            //Calculating waiting time
            wt[i] = temp[i] - at[i];
        }
    }

    //function to calculate Turn Around Time
    private static void turnaround_time(int n, int[] bt, int[] wt, int[] tt) {
        for (int i = 0; i < n; i++) {
            //Calculating turn around time
            tt[i] = bt[i] + wt[i];
        }
    }

    //function to calculate average waiting time and average turn around time.
    private static void avg_wt_tt(int n, int[] bt, int[] at) {
        int wt[] = new int[n];
        int tt[] = new int[n];

        // Invoking the function for waiting time
        waiting_time(n, bt, wt, at);

        // Invoking the function for turn around time
        turnaround_time(n, bt, wt, tt);

        //Displaying the headings
        System.out.println("\nProcesses ||" + " Burst Time ||" + " Arrival Time ||" + " Waiting Time ||" + " Turn-Around Time ");

        float awt = 0;
        float att = 0;

        for (int i = 0; i < n; i++) {
            //Calculating the total waiting time for all processes
            awt = awt + wt[i];

            //Calculating the total turn around time for all processes
            att = att + tt[i];

            //Displaying all the details
            System.out.println(i + 1 + "\t  ||\t" + bt[i] + "\t||\t" + at[i] + "\t||\t" + wt[i] + "\t||\t " + tt[i]);
        }

        //Calculating average waiting time
        awt = awt / n;
        //Calculating average turn around time
        att = att / n;

        System.out.println("\nAverage waiting time = " + awt);
        System.out.println("\nAverage turn around time = " + att);
    }
}