package nl.jappieklooster.ISAI.collection

import java.util.List;

import nl.jappieklooster.ISAI.behaviour.IBehaviour

abstract class AListEditor<T> implements ICollectionEditor<T>{
	AListEditor(T trgt){
		target = trgt
	}
	T target
}
