import java.util.*;
import java.io.*;
public class pageReplacement {
    int size,input[],frames,pageHit;
    Scanner sc;
    int position[];
    ArrayList<Integer> list;
    pageReplacement()
    {
        sc=new Scanner(System.in);
    }
    public void getInput()
    {
        System.out.print("Enter the size of String:-");
        size=sc.nextInt();
        input=new int[size];
        System.out.print("Enter the String:-");
        for(int i=0;i<size;i++)
        {
            input[i]=sc.nextInt();
        }
        System.out.print("Enter the Number of Frames:-");
        frames=sc.nextInt();
        list= new ArrayList<Integer>(frames);
    }
    public int checkHit(int a)
    {
        for(int s:list)
        {  
            if(a==s)
            {
                pageHit++;
                return -1;
            }
        }
        return 0;
    }
    public int getMinimum()
    {
        int min=0;
        for(int i=1;i<frames;i++)
        {
            if(position[min]>position[i])
               min=i;
        }
        return min;
    }
    public void calculateLRU()
    {
        pageHit=0;
        position=new int[frames];
        int j=0;
        for(int i=0;i<frames;i++)
        {
            position[i]=j;
            list.add(input[j++]);
            System.out.println("        "+list);
        }
        for(;j<size;j++)
        {
            int a=checkHit(input[j]);
            int b=getMinimum();
            if(a!=-1)
            {
                list.remove(b);
                list.add(b,input[j]);
                position[b]=j;
                System.out.println("        "+list);
            }
            else
            {
                int s=list.lastIndexOf(input[j]);
                position[s]=j;
                System.out.println("Page Hit Occurred");
            }
        }
        System.out.println("Page Hit==>     "+pageHit);
        System.out.println("Page Fault==>   "+(size-pageHit));
    }
    public int getMaximum()
    {
        int max=0;
        for(int i=1;i<frames;i++)
        {
            if(position[max]<position[i])
               max=i;
        }
        return max;
    }
    public int getNextIndex(int element,int start)
    {
        int a=-1;
        int i;
        for(i=start+1;i<size;i++)
        {
            if(element==input[i])
            {
                a=i;
                break;
            }
        }
        if(a==-1&&i==size)
            a=999;
        return a;
    }
    public void calculateOptimal()
    {
        pageHit=0;
        position=new int[frames];
        int j=0;
        for(int i=0;i<frames;i++)
        {
            list.add(input[j]);
            position[j]=getNextIndex(input[j],j);
            j++;
            System.out.println("        "+list);
        }
        for(;j<size;j++)
        {
            int a=checkHit(input[j]);
            int b=getMaximum();
            if(a!=-1)
            {
                list.remove(b);
                list.add(b,input[j]);
                position[b]=getNextIndex(input[j],j);
                System.out.println("        "+list);
            }
            else
            {
                int s=list.lastIndexOf(input[j]);
                position[s]=getNextIndex(input[j],j);
                System.out.println("Page Hit Occurred");
            }
        }
        System.out.println("Page Hit==>     "+pageHit);
        System.out.println("Page Fault==>   "+(size-pageHit));
    }
    public static void main(String[] args) 
    {
        pageReplacement obj=new pageReplacement();
        Scanner sc=new Scanner(System.in);
        int choice,check=0;
        do
        {
            System.out.println("***MENU ***");
            System.out.println("1.) Least Recently Used");
            System.out.println("2.) Optimal Page Replacement");
            System.out.println("3.) Exit");
            System.out.print("Enter Your Choice--->   ");
            choice=sc.nextInt();
            switch(choice)
            {
                case 1:
                        System.out.println("Least Recently Used");
                        obj.getInput();
                        obj.calculateLRU();
                        break;
                case 2:
                        System.out.println("Optimal Page Replacement");
                        obj.getInput();
                        obj.calculateOptimal();
                        break;
                case 3:
                        System.out.println("Exiting...");
                        check=1;
                        break;
                default:
                        System.out.print("Enter Correct Input...");
                        break;
            }
        }while(check!=1);
    }
}
/*
run:
***MENU ***
1.) Least Recently Used
2.) Optimal Page Replacement
3.) Exit
Enter Your Choice--->   1
Least Recently Used
Enter the size of String:-20
Enter the String:-1 2 3 4 2 1 5 6 2 1 2 3 7 6 3 2 1 2 3 6
Enter the Number of Frames:-3
        [1]
        [1, 2]
        [1, 2, 3]
        [4, 2, 3]
Page Hit Occurred
        [4, 2, 1]
        [5, 2, 1]
        [5, 6, 1]
        [5, 6, 2]
        [1, 6, 2]
Page Hit Occurred
        [1, 3, 2]
        [7, 3, 2]
        [7, 3, 6]
Page Hit Occurred
        [2, 3, 6]
        [2, 3, 1]
Page Hit Occurred
Page Hit Occurred
        [2, 3, 6]
Page Hit==>     5
Page Fault==>   15
***MENU ***
1.) Least Recently Used
2.) Optimal Page Replacement
3.) Exit
Enter Your Choice--->   2
Optimal Page Replacement
Enter the size of String:-20
Enter the String:-1 2 3 4 2 1 5 6 2 1 2 3 7 6 3 2 1 2 3 6
Enter the Number of Frames:-3
        [1]
        [1, 2]
        [1, 2, 3]
        [1, 2, 4]
Page Hit Occurred
Page Hit Occurred
        [1, 2, 5]
        [1, 2, 6]
Page Hit Occurred
Page Hit Occurred
Page Hit Occurred
        [3, 2, 6]
        [3, 7, 6]
Page Hit Occurred
Page Hit Occurred
        [3, 2, 6]
        [3, 2, 1]
Page Hit Occurred
Page Hit Occurred
        [6, 2, 1]
Page Hit==>     9
Page Fault==>   11
***MENU ***
1.) Least Recently Used
2.) Optimal Page Replacement
3.) Exit
Enter Your Choice--->   3
Exiting...
*/