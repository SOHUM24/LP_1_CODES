import java.util.*;

public class FIFO {
    public static Integer Max_Framesize = 3;
    public static ArrayList<Integer> Frame = new ArrayList<Integer>();
    public static Integer cachePointer = 0;
    public static Integer pageFault = 0;
    public static Integer pageHit = 0;

    public static void VirtualPaging(int refString) {
        
        if (Frame.contains(refString)) {
            // increment the page Hit
            pageHit++;
            return;
        }
        // increment the page Fault
        pageFault++;
        int size = Frame.size();
        // if there is space in frame then add refstring to the frame
        if (size < Max_Framesize) {
            Frame.add(refString);

            cachePointer = (cachePointer + 1) % Max_Framesize;
            return;
        } else {
            Frame.set(cachePointer, refString);
            cachePointer = (cachePointer + 1) % Max_Framesize;
            return;
        }

    }

    public static void main(String[] args) {
        int refString[] = new int[] { 0, 2, 1, 6, 4, 0, 1, 0, 3, 1, 2 };
        System.out.println(refString.length);

        // iterate over refstring array
        for (Integer i : refString) {
//   0 
            VirtualPaging(i);

        }

        System.out.println("Page Fault - " + pageFault);
        System.out.println("Page HIT - " + pageHit);
        System.out.println("Page Fault Ratio - " + ((pageFault / 15) * 100));
        System.out.println("Page HIT Ratio - " + (pageHit / 15) * 100);

    }

}
