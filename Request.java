package elevator;

public class Request {
	private int startFloor;
	private int endFloor;
	private boolean isProcessed;
	
	public boolean isProcessed() {
		return isProcessed;
	}
	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}
	public int getStartFloor() {
		return startFloor;
	}
	public int getEndFloor() {
		return endFloor;
	}
	public Request(int startFloor, int endFloor) {
		this.startFloor = startFloor;
		this.endFloor = endFloor;
	}
	
	public boolean isUp() {return this.startFloor - this.endFloor < 0;}
}
