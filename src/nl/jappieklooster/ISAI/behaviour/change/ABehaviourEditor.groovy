package nl.jappieklooster.ISAI.behaviour.change

import java.util.List;

import nl.jappieklooster.ISAI.behaviour.IBehaviour
import nl.jappieklooster.ISAI.world.entity.IBehaviourEditor

abstract class ABehaviourEditor implements IBehaviourEditor{
	ABehaviourEditor(IBehaviour trgt){
		target = trgt
	}
	IBehaviour target
}
