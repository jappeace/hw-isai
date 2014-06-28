package nl.jappieklooster

/**
 * a simple time to measure blocks of code
 * @author jappie
 *
 */
class Timer {

	private long beginTime
	
	/**
	 * store the current time in milliseconds as a local atribute
	 */
	void start(){
		beginTime = System.currentTimeMillis()
	}
	
	long calulateTimeSinceStart(){
		return System.currentTimeMillis() - beginTime
	}
	
	/**
	 * 
	 * @param the block of code that needs to be timed
	 * @return how long it took in milliseconds
	 */
	long measure(Closure action){
		start()
		action.call()
		return calulateTimeSinceStart()
	}
}
