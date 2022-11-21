
import java.util.Scanner;


public class Paging {
	
	int stack_mem[],hit,fault,number,i,input[],j;
	static Scanner s1;
	
	Paging()
	{
		s1 = new Scanner(System.in);
		hit=0;
		fault=0;
		stack_mem = new int[3];	
	}
	
	void get()
	{
		System.out.print("\n\tEnter number of frames : ");
		number = s1.nextInt();
		
		input = new int[number];
		
		System.out.print("\n\tEnter frames : ");
		for(i=0;i<number;i++)
		{
			input[i] = s1.nextInt();
			
		}
		
		for(i=0;i<3;i++)
		{
			stack_mem[i] = -1;
			
		}
		
		
	}
	
	int check_hf(int m)
	{
		int flag=-1;
		
		for(int k=0;k<3;k++)
		{
			if(stack_mem[k]==input[m])
			{	flag=0;
				break;
			}
		}
		
		if(flag==0)
			return 1;
		
		else 
			return 0;
		
	}
	
	void fifo()
	{
		i=0;
		int j=0;
		int replace=0;
		while(i<number)
		{
			
			if(i<3)
			{
				stack_mem[j] = input[i];
				j++;
				i++;
				for(int l=0;l<3;l++)
					System.out.print("\t "+stack_mem[l]);
				System.out.print("\n");
				
			}
			
			else
			{
				
				int check = check_hf(i);
				
				if(check == 1){
					hit++;
					for(int l=0;l<3;l++)
						System.out.print("\t "+stack_mem[l]+"");
					System.out.print("\n");
					
				}
				else{
					stack_mem[replace] = input[i];
					replace =  (replace+1)%3;
					for(int l=0;l<3;l++)
						System.out.print("\t "+stack_mem[l]);
					System.out.print("\n");
					
				}
				i++;	
			}
			
			
		}
		
		System.out.print("\n\thit = "+hit);
		fault = number-hit;
		System.out.print("\n\tfault = "+fault);
		
	}
		
	void optimal()
	{
		i=0;
		j=0;
		while(i<number)
		{
			
			if(i<3)
			{
				stack_mem[j] = input[i];
				j++;
				i++;	
				for(int l=0;l<3;l++)
					System.out.print("\t "+stack_mem[l]);
				System.out.print("\n");
			}
			else
			{
				int check = check_hf(i);	
				if(check == 1){
					hit++;
					System.out.println("hits = "+hit);
					for(int l=0;l<3;l++)
						System.out.print("\t "+stack_mem[l]);
					System.out.print("\n");
				}
					
				else
				{
					
					int index = checkoptimal(i);
					stack_mem[index] = input[i];
		
					for(int l=0;l<3;l++)
						System.out.print("\t "+stack_mem[l]);
					System.out.print("\n");
				}	
				i++;
				
			}
		}
		System.out.print("\n\thit = "+hit);
		fault = number-hit;
		System.out.print("\n\tfault = "+fault);	
	}
	
	int checkoptimal(int m){
		int ind1=0,ind2=0,ind3=0;
		int flag1=-1;
		int flag2=-1;
		int flag3=-1;
		
			for(int u=m;u<number;u++){
				if(stack_mem[0] == input[u])
				{	ind1 = u;
					flag1=0;
					break;
				}
		
			}
			if(flag1 != 0)
				ind1 = 9999;

			for(int u=m;u<number;u++){
				if(stack_mem[1] == input[u])
				{	ind2 = u;
					flag2=0;
					break;
				}
			}
			
			if(flag2 != 0)
				ind2 = 9999;

		
			for(int u=m;u<number;u++){
				if(stack_mem[2] == input[u])
				{	ind3 = u;
					flag3=0;
					break;
				}
		
			}
		
			if(flag3 != 0)
				ind3 = 9999;

			
			if(ind1>ind2)
			{
				if(ind1>ind3)
					return 0;
				else 
					return 2;	
			}
			
			else if(ind2>ind3){
				return 1;
			}
			
			else 
				return 2;	
	}
	
	void lru()
	{
		int i=0;
		while(i<number)
		{
			i=0;
			j=0;
			while(i<number)
			{
				
				if(i<3)
				{
					stack_mem[j] = input[i];
					j++;
					i++;	
					for(int l=0;l<3;l++)
						System.out.print("\t "+stack_mem[l]);
					System.out.print("\n");
				}
				else
				{
					int check = check_hf(i);	
					if(check == 1){
						hit++;
						System.out.println("hits = "+hit);
						for(int l=0;l<3;l++)
							System.out.print("\t "+stack_mem[l]);
						System.out.print("\n");
					}
						
					else
					{
						
						int index = checkoptimal2(i);
						stack_mem[index] = input[i];
			
						for(int l=0;l<3;l++)
							System.out.print("\t "+stack_mem[l]);
						System.out.print("\n");
					}	
					i++;
					
				}
			}
			
		}
		System.out.print("\n\thit = "+hit);
		fault = number-hit;
		System.out.print("\n\tfault = "+fault);	
		
	}
	
	int checkoptimal2(int m)
		{
		
			int ind1=0,ind2=0,ind3=0;
			
			for(int l=m-1;l>=0;l--){
				if(stack_mem[0]==input[l])
				{
					ind1 = l;
					break;
				}
			}
			
			
			
			for(int l=m-1;l>=0;l--){
				if(stack_mem[1]==input[l])
				{
					ind2 = l;
					break;
				}
				
			}

			for(int l=m-1;l>=0;l--){
				if(stack_mem[2]==input[l])
				{
					ind3 = l;					
					break;
				}
	
			}
			
			if(ind1<ind2)
			{
				if(ind1<ind3)
					return 0;
				else
					return 2;
			}
			
			else if(ind2<ind3)
				return 1;
			
			else
				return 2;
				

		}
	
	
	public static void main(String args[])
	{
	
		int choice;
		
		do
		{
			Paging p1 = new Paging();
			System.out.print("\n\t0.Exit\n\t1.FIFO\n\t2.Optimal\n\t3.LRU");
			choice = s1.nextInt();
			
			switch(choice)
			{
			
			case 1:p1.get();
				p1.fifo();
				break;
				
			case 2:
				p1.get();
				p1.optimal();
				break;
				
			case 3:
				p1.get();
				p1.lru();
				break;
				
			
				
			}
		}while(choice!=0);
		
	}

}


/*OUTPUT : 

	0.Exit
	1.FIFO
	2.Optimal
	3.LRU1

	Enter number of frames : 5

	Enter frames : 1
2
3
2
5
	 1	 -1	 -1
	 1	 2	 -1
	 1	 2	 3
	 1	 2	 3
	 5	 2	 3

	hit = 1
	fault = 4
	0.Exit
	1.FIFO
	2.Optimal
	3.LRU2

	Enter number of frames : 5

	Enter frames : 1
2
4
2
1
	 1	 -1	 -1
	 1	 2	 -1
	 1	 2	 4
hits = 1
	 1	 2	 4
hits = 2
	 1	 2	 4

	hit = 2
	fault = 3
	0.Exit
	1.FIFO
	2.Optimal
	3.LRU3

	Enter number of frames : 5

	Enter frames : 4
5
6
5
2
	 4	 -1	 -1
	 4	 5	 -1
	 4	 5	 6
hits = 1
	 4	 5	 6
	 2	 5	 6

	hit = 1
	fault = 4
	0.Exit
	1.FIFO
	2.Optimal
	3.LRU0*/

