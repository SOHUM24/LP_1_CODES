package Semaphore;

import java.util.concurrent.Semaphore;

public class ReaderWriter {

	static Semaphore readlock = new Semaphore(1);
	static Semaphore writelock = new Semaphore(1);
	static int readcount = 0;
	
	static class Read implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
				
			try {
				synchronized (this) {
				
				readlock.acquire();
				readcount++;
				
				if(readcount == 1)
					writelock.acquire();
				
				readlock.release();
				
				System.out.println(Thread.currentThread().getName()+" is reading");
				Thread.sleep(3000);
				System.out.println(Thread.currentThread().getName()+" has finnished reading");
				
				readlock.acquire();
				readcount--;
				if(readcount == 0)
					writelock.release();
				
				readlock.release();
				
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	static class Write implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			try {
				
				writelock.acquire();
				
				System.out.println(Thread.currentThread().getName()+" is writing");
				Thread.sleep(3000);
				System.out.println(Thread.currentThread().getName()+" has finnished writiing");
				
				writelock.release();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	public static void main(String[] args)throws Exception {
		
		Read read = new Read();
		Write write = new Write();
		
		Thread t1 = new Thread(read);
		t1.setName("ReadThread1");
		
		Thread t2 = new Thread(read);
		t2.setName("ReadThread2");
		
		Thread t3 = new Thread(write);
		t3.setName("WriteThread3");
		
		Thread t4 = new Thread(read);
		t4.setName("ReadThread4");
		
		Thread t5 = new Thread(write);
		t5.setName("WriteThread5");
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
	}
}