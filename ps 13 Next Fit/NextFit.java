import java.util.*;

public class NextFit {
    public static LinkedList<Integer> ProcessList = new LinkedList<Integer>();
    public static LinkedList<Integer> MemorySpace = new LinkedList<Integer>();

    public static HashMap<Integer, Integer> MemoryMap = new HashMap<Integer, Integer>();

    public static HashMap<Integer, Integer> ProcessMap = new HashMap<Integer, Integer>();

    // will store the next element to start with
    public static Integer cache_pointer = 0;

    public static void FindNextSpot(int id, int space) {
        int eleVisited = 0;
        while (eleVisited < MemorySpace.size()) {
            if (MemorySpace.get(cache_pointer) > space && MemoryMap.get(cache_pointer) < 0) {
                ProcessMap.put(id, cache_pointer);
                MemoryMap.put(cache_pointer, id);

                cache_pointer = (cache_pointer + 1) % MemoryMap.size();
                break;
            }
            // will search in cycle for space
            cache_pointer = (cache_pointer + 1) % MemoryMap.size();
            eleVisited++;
        }

    }

    public static void NextFitAlloc() {
        for (int i = 0; i < ProcessList.size(); i++) {
            FindNextSpot(i, ProcessList.get(i));
        }
    }

    public static void main(String[] args) {
        ProcessList.add(30);
        ProcessList.add(5);
        ProcessList.add(40);

        MemorySpace.add(10);
        MemorySpace.add(25);
        MemorySpace.add(35);
        MemorySpace.add(10);
        MemorySpace.add(25);
        MemorySpace.add(35);

        // will tell which process is allocated to which block
        ProcessMap.put(0, -1);
        ProcessMap.put(1, -1);
        ProcessMap.put(2, -1);

        // will tell if block is free or not
        MemoryMap.put(0, -1);
        MemoryMap.put(1, -1);
        MemoryMap.put(2, -1);
        MemoryMap.put(3, -1);
        MemoryMap.put(4, -1);
        MemoryMap.put(5, -1);
        NextFitAlloc();

        for (Map.Entry<Integer, Integer> mEntry : MemoryMap.entrySet()) {
            System.out.println("MID " + mEntry.getKey() + " - " + MemorySpace.get(mEntry.getKey()));
        }
        System.out.println('\n');
        for (Map.Entry<Integer, Integer> mEntry : ProcessMap.entrySet()) {
            System.out.println("Process ID - " + mEntry.getKey() + " Process Size - " + ProcessList.get(mEntry.getKey())
                    + " Memory Block - " + mEntry.getValue());
        }
        System.out.println('\n');

    }

}
