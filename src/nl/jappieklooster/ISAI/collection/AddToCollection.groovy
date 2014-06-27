package nl.jappieklooster.ISAI.collection

import java.util.List;

import nl.jappieklooster.ISAI.behaviour.IBehaviour;

class AddToCollection<T> extends AListEditor<T> {
	AddToCollection(T target){
		super(target)
	}

	@Override
	public void edit(Collection<T> what) {
		what.add(target)
	}

}
