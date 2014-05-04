package nl.jappieklooster.ISAI.collection.oct

import java.util.Collection;
import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.math.vector.Vector3

/**
 * this class contains the basic functionality of an octree
 * @author jappie
 *
 */
class OctTree<T extends IPositionable> implements Collection<T>{

	// for bitwise magic
	private final int Xax = 4, Yax = 2, Zax = 1

	private Vector3 center
	private Vector3 halfDimension
	
	private OctTree<T>[] children
	T data
	
	OctTree<T>[] getChildren(){
		return children
	}
	OctTree(Vector3 center, Vector3 halfDimension){
		this.center = center
		this.halfDimension = halfDimension
		clear()
	}
	
	/**
	 * this is the recursive add call
	 * @param element
	 */
	void insert(T element){
		
		// this gaurd prevents an eternal trying to get to a point which is out of reach
		// I conteplated moving this to the add, but putting it here is less bug prone
		if(!hasPoint(element.position)){
			throw new IndexOutOfBoundsException("This is where a proprer octree should grow in the desired direction")
		}
		if(isLeaf()){
			
			// im a leaf and there is no data here
			if(data == null){
				data = element
				return
			}
			
			// Can only hold one data so tiem to construct children and let them do work
			children = new OctTree<>[8]
			(0..7).each{
				children[it] = new OctTree<>(
                    center + new Vector3(
                        halfDimension.x * (it & Xax ? 0.5 : -0.5),
                        halfDimension.y * (it & Yax ? 0.5 : -0.5),
                        halfDimension.z * (it & Zax ? 0.5 : -0.5)
                    ), 
                    halfDimension * new Vector3(0.5)
				)
			}

			children[getChildIndex(element.getPosition())].insert(element)
			children[getChildIndex(data.getPosition())].insert(data)
			
			// child now contains data, truncate own reference to prevent leaking
			data = null
			return
		}

		children[getChildIndex(element.getPosition())].insert(element)
	}
	
	/**
	 * navigates the graph and tries to get the data at the bottom, will try to return a result, but it might not be at the exact coordinate,
	 * if there is somthing at the coordinate in this tree, this method will find that
	 */
	T getAt(Vector3 location){
		T result
		searchLeafTree(location,{ OctTree<T> tree ->
			result = tree.data
		})
		return result
	}
	/**
	 * calls the action with as parameter the tree containing the data at the location
	 * @param location
	 * @param action
	 */
	private void searchLeafTree(Vector3 location, Closure action){
		if(isLeaf()){
			action(this)
		}
		children[getChildIndex(location)].searchLeafTree(location, action)
	}
	/**
	 * returns a collection of things inside the location radius
	 * @param location
	 * @param radius
	 * @return
	 */
	Collection<T> find(Vector3 location, float radius){
        Collection<T> results = new LinkedList<>()
		search(location, radius, { T result ->
			results.add(result)
		})
		return results
		
	}
	/**
	 * calls the action for each element it finds
	 * @param location
	 * @param radius
	 * @param action
	 */
	void search(Vector3 location, float radius, Closure action){
		if(isLeaf()){
			if(data == null){
                return
			}
			if((location - data.position).lengthSq < radius * radius){
				action(data)
			}
			return
		}
		eachChild{ OctTree<T> child ->
            if(child.hasPoint(location, new Vector3(radius))){
                child.search(location, radius, action)
            }
		}
	}
	
	/**
	 * just executes the action for each data what is not null
	 * if you use this one a lot, its better to use an ordinairy list, 
	 * this method gives no optimizations what so ever, and the extra method calls
	 * make it slower then a list
	 * O(N) speed
	 * @param action
	 */
	void traverse(Closure action){
		if(isLeaf()){
			if(data == null){
                return
			}
			action(data)
			return
		}
		eachChild{ OctTree<T> child ->
			child.traverse(action)
		}
	}
	boolean hasPoint(Vector3 point){
		return hasPoint(point, new Vector3(0))
	}
	boolean hasPoint(Vector3 point, Vector3 offset){
        Vector3 min = center - halfDimension - offset
        Vector3 max = center + halfDimension + offset
        
        if(min.x < point.x || min.y < point.y || min.z < point.z){
            return false
        }
        
        if(max.x > point.x || max.y > point.y || max.z > point.z){
            return false
        }

		return true
	}
	
	private boolean isLeaf(){ children == null }
	/**
	 * figures out which child has the point
	 * @param point
	 * @return
	 */
	private int getChildIndex(Vector3 point) {
		int oct = 0;
		if(point.x >= center.x) oct |= Xax;
		if(point.y >= center.y) oct |= Yax;
		if(point.z >= center.z) oct |= Zax;
		return oct;
	}
	
	/**
	 * traverse all the children and for evrey child execute the action, giving the child as argument
	 * @param action
	 */
	private void eachChild(Closure action){
		(0..7).each{
			action(children[it])
		}
	}

    int size(){
        if(isEmpty()){
            return 0
        }
		// not MT but leafnode, means there is one thing inside here
		if(isLeaf()){
			return 1
		}
		
		// not a leafnode, go trough all children and see wat there sisze is
		int result = 0
		eachChild{ OctTree<T> child ->
			result += child.size()
		}
		return result
	}
	@Override
    boolean isEmpty(){
		return size() == 0
	}
	@Override
    boolean contains(Object obj){
		if(!(obj instanceof T)){
			return false
		}
		T target = (T) obj
		return get(target) == obj
	}

    Iterator<T> iterator(){
		return new RecursiveOctTreeIterator(this)
	}
	@Override
    Object[] toArray(){
		List<T> result = new LinkedList<>()
		traverse{
			result.add(it)
		}
		return result.toArray()
	}
	@Override
    @SuppressWarnings("unchecked")
	<X> X[] toArray(X[] a){
		List<T> result = new LinkedList<>()
		traverse{
			result.add(it)
		}
		return result.toArray(a)
	}
	@Override
    boolean add(T element){
		if(element == null){
			throw new NullPointerException("Null is not allowed in this collection")
		}
		insert(element)
		return true
	}
	@Override
    boolean remove(Object obj){

		if(!(obj instanceof T)){
			return false
		}

		boolean changed = false
		searchLeafTree(((T) obj).position,
			{ OctTree<T> tree ->
                if(tree.data == obj){
                    tree.data = null
					changed = true
                }
            }
        )
		return changed
	}
	@Override
    boolean containsAll(Collection<?> collection){
		boolean result = true
		for(Object item : collection){
			if(!contains(item)){
				result = false
				break
			}
		}
		return result
	}
	@Override
    boolean addAll(Collection<? extends T> collection){
		boolean changed = false
		collection.each{
			changed = add(it) || changed
		}
		return changed
	}
	@Override
    boolean removeAll(Collection<?> collection){

		boolean changed = false
		collection.each{
			changed = remove(it) || changed
		}
		return changed
	}
	@Override
    boolean retainAll(Collection<?> collection){
		clear()
		collection.each{
			add(it)
		}
		return true
	}
	@Override
    void clear(){
		// since it is kind of alinked list, removing refernces from the root will basicly MT this collection
		// gc will handle the rest
		children = null
		data = null
	}

	@Override
    boolean equals(Object obj){
		if(obj.is(this)){
			return true
		}
		if(!(obj instanceof OctTree<T>)){
			return false
		}
		OctTree<T> target = (OctTree<T>) obj
		return target.data == data && target.children == children
	}

	@Override
    int hashCode(){
		return data?.hashCode() * children?.hashCode() * 3
	}
}
