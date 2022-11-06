
public class RunnableClass implements Runnable {

	private String name;
	private Integer currentMessage = 0;
	private int mapKey;
	
	public RunnableClass(String n, int key) {
		name = n;
		mapKey = key;
	}
	
	public Integer getMessage() {
		return currentMessage;
	}
	
	public String getName() {
		return name;
	}
	
	
	@Override
	public void run() {
		
		synchronized (currentMessage) {
			currentMessage++;
			System.out.println("THREAD NAME: " + name);
			System.out.println("THREAD MAP KEY: " + mapKey);
			System.out.println("THREAD " + name + " CURRENT MESSAGE: " + currentMessage);	
		}
	}
}
