package nl.jappieklooster.ISAI.collection

import java.util.List;

class RemoveFromCollection<T> extends AListEditor<T> {
	RemoveFromCollection(T target){
		super(target)
	}

	@Override
	public void edit(Collection<T> what) {
		what.remove(target)
	}

}
