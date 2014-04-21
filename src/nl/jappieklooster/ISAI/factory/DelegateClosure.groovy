package nl.jappieklooster.ISAI.factory

/** lets a closure be executed as if it where a member method of the given object */
class DelegateClosure{

	def to
	
	void call( Closure callable ) {
		if ( !callable ){
			return
		}

		callable.resolveStrategy = Closure.DELEGATE_FIRST
		callable.delegate = to
		callable.call()
	}

}
