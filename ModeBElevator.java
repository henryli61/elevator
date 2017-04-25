package elevator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class ModeBElevator extends Elevator {
	
	private List<Integer> processList = new ArrayList<>();

	public ModeBElevator(List<ElevatorEvent> request, CountDownLatch latch) {
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
			ElevatorMovingDirection direction = getDirction(request.getStartFloor());
			int processedRequest = 0;
			if(direction == ElevatorMovingDirection.UP){
				if(request.isUp()){
					processedRequest = buildAsendingOrderList(requests,false);
				} else {
					processedRequest = buildDesendingOrderList(requests,true);
				}
			} else if(direction == ElevatorMovingDirection.DOWN){
				if(request.isUp()){
					processedRequest = buildAsendingOrderList(requests,true);
				} else {
					processedRequest = buildDesendingOrderList(requests,false);
				}				
			} else {
				moveTo(request.getEndFloor(),sb);
				continue;
			}
			
			for(Integer v : processList){
				moveTo(v,sb);
			}
			
			if(processedRequest > 0){
				for(int i=0;i<processedRequest - 1; i++){
					it.next();
				}
			} else {
				moveTo(request.getStartFloor(),sb);
				moveTo(request.getEndFloor(),sb);
			}
			
			
		}
		addTotal(sb);
		return sb.toString();
	}
	
	private int buildAsendingOrderList(List<Request> requests,boolean isOpsitDirection){
		processList.clear();
		Map<Integer,Integer> pricessMapToAvoidDuplication = new HashMap<>();
		int numberOfProcessed = 0;
		for(Request request : requests){
			if(request.isProcessed()) continue;
			if(request.isProcessed() != true && request.isUp()){
				addToTheOrderedList(request.getStartFloor(),pricessMapToAvoidDuplication,isOpsitDirection);
				addToTheOrderedList(request.getEndFloor(),pricessMapToAvoidDuplication,isOpsitDirection);
				numberOfProcessed++;
				request.setProcessed(true);
			}else {
				break;
			}
		}
		
		processList.sort((a,b)->Integer.compareUnsigned(a, b));
		return numberOfProcessed;
	}
	
	private int buildDesendingOrderList(List<Request> requests,boolean isOpsitDirection){
		processList.clear();
		Map<Integer,Integer> pricessMapToAvoidDuplication = new HashMap<>();
		int numberOfProcessed = 0;
		for(Request request : requests){
			if(request.isProcessed()) continue;
			if(request.isProcessed() != true && request.isUp() != true){
				addToTheOrderedList(request.getStartFloor(),pricessMapToAvoidDuplication,isOpsitDirection);
				addToTheOrderedList(request.getEndFloor(),pricessMapToAvoidDuplication,isOpsitDirection);
				numberOfProcessed++;
				request.setProcessed(true);
			}else {
				break;
			}
		}
		
		processList.sort((a,b)->Integer.compareUnsigned(b, a));
		return numberOfProcessed;
	}	

	private void addToTheOrderedList(int v,Map map, boolean isOpsitDirection){
		if(this.currentFloor == v && isOpsitDirection == false) return;
		
		if(map.get(v) == null){
			map.put(v, v);
			processList.add(v);
		}
	}

}
