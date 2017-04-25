package elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public abstract class Elevator implements Runnable {
	protected int currentFloor;
	protected ElevatorMovingDirection direction;
	protected int totalTraveled = 0;
	private CountDownLatch latch;
	private List<ElevatorEvent> request;
	
	private List<String> outputs = new ArrayList<>();
	
	public Elevator(List<ElevatorEvent> request,CountDownLatch latch) {
		this.latch = latch;
		this.request = request;
	}
	
	public void run(){
		movement();
		latch.countDown();
	}
	
	public void printOuts(){
		for(String s : outputs){
			System.out.println(s);
		}
	}
	
	private List<String> movement(){
		for(ElevatorEvent e : request){
			String output = process(e);
			if(output != null){
				outputs.add(output);
			}
			totalTraveled = 0;
		}
		return outputs;
	}
	
	abstract protected String process(ElevatorEvent e);
	
	protected ElevatorMovingDirection getDirction(int targetFloor){
		direction = (targetFloor - currentFloor > 0) ? ElevatorMovingDirection.UP
				: (targetFloor - currentFloor < 0) ? ElevatorMovingDirection.DOWN
				: ElevatorMovingDirection.NOTMOVE;
		
		return direction;
	}
	
	protected void moveTo(int targetFloor,StringBuilder sb){
		totalTraveled += Math.abs(targetFloor - currentFloor);
		currentFloor = targetFloor;
		sb.append(targetFloor);
		sb.append(" ");
	}
	
	protected void addTotal(StringBuilder sb){
		sb.append("(");
		sb.append(totalTraveled);
		sb.append(")");
	}
	
	enum ElevatorMovingDirection {
		UP,DOWN,NOTMOVE;
	}
}
