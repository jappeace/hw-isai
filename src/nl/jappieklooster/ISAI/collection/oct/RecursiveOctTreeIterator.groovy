package nl.jappieklooster.ISAI.collection.oct

import java.util.NoSuchElementException;

import nl.jappieklooster.ISAI.world.IPositionable;


class RecursiveOctTreeIterator implements Iterator<IPositionable>{

	OctTree current
	int currentChild
	private static final int childInit = 0
	
	private RecursiveOctTreeIterator recursive

	RecursiveOctTreeIterator(OctTree target){
		current = target
		currentChild = childInit
		recursive = null
	}
	boolean hasNext(){
		if(current.isLeaf()){
			// use current child to see if the leaf is already read
			return currentChild == childInit && (!current.isEmpty())
		}
		for(int i = currentChild + 1; i < current.children.length; i++){
			if(!current.children[i].isEmpty()){
				return true
			}
		}
		return false
	}
	
    IPositionable next(){
		if(!hasNext()){
			return null
		}
		if(current.isLeaf()){

			// after current child is bumbed one, this leafnode hasnext will return false
            currentChild++
			return current.data
		}
		
		// it is not a leaf node so create an iterator for the children
		if(recursive == null){
			if(!createNextRecursive()){
				return null
			}
		}
		if(!recursive.hasNext()){
			if(!createNextRecursive()){
				return null
			}
		}
		
		// return the result of a child iterator
        return recursive.next()
    }
	
	private boolean createNextRecursive(){
		
		for(; currentChild < current.children.length; currentChild++){
            recursive = current.children[currentChild].iterator()
			if(recursive.hasNext()){
				return true
			}
		}
		return false
	}
	
    void remove(){
		if(current.isLeaf()){
			current.data = null
			return
		}
		recursive.remove()
    }
}
