package elevator;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Driver {
	private final static FileParser fileParser = new FileParser();
	
	public static void main(String[] args) throws InterruptedException {
		try{
			List<ElevatorEvent> requests = fileParser.buildRequests();
			CountDownLatch latch = new CountDownLatch(2);
			
			Elevator modeA = new ModeAElevator(requests,latch);
			Thread t1 = new Thread(modeA);
			t1.start();
			
			Elevator modeB = new ModeBElevator(requests,latch);
			Thread t2 = new Thread(modeB);
			t2.start();
			
			latch.await();
			System.out.println("Mode A outputs");
			modeA.printOuts();
			
			System.out.println("Mode B outputs");
			modeB.printOuts();
		} catch(IOException e){
			System.out.println("IO exception...");
		}
	}

}
