package nl.jappieklooster.ISAI.factory

/** lets a closure be executed as if it where a member method of the given object */
class DelegateClosure{

	def to
	def arguments
	
	void call( Closure callable ) {
		if ( !callable ){
			return
		}

		callable.delegate = to
		callable.resolveStrategy = Closure.DELEGATE_FIRST
		if(arguments){
            callable.call(arguments)
			return
		}
		callable.call()
	}


}
