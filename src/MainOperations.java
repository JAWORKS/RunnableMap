import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class MainOperations {
	
	private static Map<Integer, RunnableClass> myMap = new HashMap<>();
	private ScheduledThreadPoolExecutor scheduledExecutor = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(4, new ThreadFactory(){
        public Thread newThread(Runnable r) {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        }
    });
	
	public MainOperations() {
		
		RunnableClass first = new RunnableClass("FIRST", 0);
		RunnableClass second = new RunnableClass("SECOND", 1);
		RunnableClass third = new RunnableClass("THIRD", 2);
		RunnableClass fourth = new RunnableClass("FOURTH", 3);
		
		scheduledExecutor.scheduleAtFixedRate(first, 10, 10, TimeUnit.SECONDS);
		scheduledExecutor.scheduleAtFixedRate(second, 10, 10, TimeUnit.SECONDS);
		scheduledExecutor.scheduleAtFixedRate(third, 10, 10, TimeUnit.SECONDS);
		scheduledExecutor.scheduleAtFixedRate(fourth, 10, 10, TimeUnit.SECONDS);
		
		myMap.put(0, first);
		myMap.put(1, second);
		myMap.put(2, third);
		myMap.put(3, fourth);	
	}
	
	public RunnableClass getRunnable(Integer i) {
		synchronized (myMap) {
			return myMap.get(i);
		}
	}

	//Main Method Comment
	public static void main(String[] args) {
		MainOperations starterClass = new MainOperations();
		while(true) {
			try {
				    //Print from MainOperations map every 50 seconds.
					//Expect to see 5 MainOp thread outputs before single main thread loop 
					Thread.sleep(51000L);
				
					for(int index=0; index<4; index++) {
					    //Get runnable from map in MainOperations class
						RunnableClass x = starterClass.getRunnable(index);
						//Make sure only one thread can access current runnable
						//NOTE: main thread and MainOp thread from thread pool would possibly access each runnable at any given time
						//main thread only gets from the runnable, MainOp thread updates runnable
						synchronized (x) {
							System.out.println("MAIN GET -- NAME: " + x.getName());
							System.out.println("MAIN GET -- CURRENTMESSAGE: " + x.getMessage());
						}
					}
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}	
		}
	}



