package nl.jappieklooster

class Timer {

	long measure(Closure action){
		final long startTime = System.currentTimeMillis()
		action.call()
		final long endTime = System.currentTimeMillis()
		return startTime - endTime
	}
}
