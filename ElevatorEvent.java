package elevator;

import java.util.ArrayList;
import java.util.List;

public class ElevatorEvent {
	public ElevatorEvent(int value, List<Request> list) {
	super();
		this.value = value;
		this.list = list;
	}
	private int value;
	private List<Request> list;
	
	public int getValue() {
		return value;
	}
	public List<Request> getList() {
		return new ArrayList<Request>(this.list);
	}
	
	
}
