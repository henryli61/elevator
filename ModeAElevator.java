package elevator;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ModeAElevator extends Elevator {

	public ModeAElevator(List<ElevatorEvent> request, CountDownLatch latch) {
		super(request, latch);
	}

	@Override
	protected String process(ElevatorEvent e) {
		this.currentFloor = e.getValue();
		StringBuilder sb = new StringBuilder();
		sb.append(this.currentFloor);
		sb.append(" ");
		
		List<Request> requests = e.getList();
		
		Iterator<Request> it = requests.iterator();
		
		Request request = null;
		
		while(it.hasNext()){
			request = it.next();
			if(currentFloor != request.getStartFloor()){
				moveTo(request.getStartFloor(),sb);
			}
			moveTo(request.getEndFloor(),sb);
		}
		
		addTotal(sb);
		
		return sb.toString();
	}

}
