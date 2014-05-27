package nl.jappieklooster.ISAI.thread

import com.jme3.app.Application
import java.util.concurrent.Callable

/**
 * allows objects to syncrhonize task without knowing about an entire applicaition
 * the task is just a closure
 * @author jappie
 *
 */
class TaskSynchronizer {

	private Application application
	void setApplication(Application to){
		application = to
	}
	
	void execute(Runnable task){
		application.enqueue(new Callable<Integer>() {
			public Integer call() throws Exception {
				task.run()
				return 1 // it has to return somthing
			}
		})
	}
}
