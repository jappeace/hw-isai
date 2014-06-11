package nl.jappieklooster.ISAI.behaviour.change

import java.util.List;

import nl.jappieklooster.ISAI.behaviour.IBehaviour;

class AddBehaviour extends ABehaviourEditor {

	AddBehaviour(IBehaviour target){
		super(target)
	}

	@Override
	public void edit(List<IBehaviour> what) {
		what.add(target)
	}

}
