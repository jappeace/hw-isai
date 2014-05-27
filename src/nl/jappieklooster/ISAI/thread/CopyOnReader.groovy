package nl.jappieklooster.ISAI.thread

class CopyOnReader <ReturnType extends ICopyable, Source extends IReadable<ReturnType>>{

	boolean hasRead = false
	Source source
	
	
}
