package nl.jappieklooster.ISAI.collection.oct

import java.util.Collection;
import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.math.vector.Vector3

/**
 * this class contains the basic functionality of an octree
 * @author jappie
 *
 */
class OctTree implements Collection<IPositionable>{

	// for bitwise magic
	private final int Xax = 4, Yax = 2, Zax = 1

	private Vector3 center
	private Vector3 halfDimension
	
	private OctTree<T>[] children
	IPositionable data

	
	OctTree(Vector3 center, Vector3 halfDimension){
		this.center = center
		this.halfDimension = halfDimension
		clear()
	}

	OctTree[] getChildren(){
		return children
	}
	
	/**
	 * this is the recursive add call
	 * @param element
	 */
	void insert(IPositionable element){
		
		// this gaurd prevents an eternal trying to get to a point which is out of reach
		// I conteplated moving this to the add, but putting it here is less bug prone
		if(!hasPoint(element.position)){
			throw new IndexOutOfBoundsException("this octree does not contain: " + element.position + "\n"
				+ " the octree is " + center + " \n with a halfdimension of" + halfDimension +"\n\n"
				+ "the bounding box min: " + (center - halfDimension) + "\n"
				+ "the bounding box max: " + (center + halfDimension) + "\n")
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
                    center + 
                    new Vector3(
						halfDimension.x,
						halfDimension.y,
						halfDimension.z
					) 
					* 
					new Vector3(
						it & Xax ? 0.5 : -0.5, 
						it & Yax ? 0.5 : -0.5, 
						it & Zax ? 0.5 : -0.5
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
	IPositionable getAt(Vector3 location){
		IPositionable result
		searchLeafTree(location,{ OctTree tree ->
			result = tree.data
		})
		return result
	}
	/**
	 * calls the action with as parameter the tree containing the data at the location
	 * @param location
	 * @param action
	 */
	private void searchLeafTree(Vector3 location, IReceiveOctTree handler){
		if(isLeaf()){
			handler.receive(this)
		}
		children[getChildIndex(location)].searchLeafTree(location, handler)
	}
	/**
	 * returns a collection of things inside the location radius
	 * @param location
	 * @param radius
	 * @return
	 */
	Collection<IPositionable> find(Vector3 location, float radius){
        Collection<IPositionable> results = new LinkedList<>()
		search(location, radius){ IPositionable result ->
			results.add(result)
		}
		return results
		
	}
	
	IPositionable findClosest(Vector3 to, float considerRadius = 1){
		IPositionable start = null
		search(to, considerRadius){ IPositionable current ->
			if(start == null){
				start = current
			}
			if((start.position - to).lengthSq > (current.position - to).lengthSq){
				start = current
			}
		}
	}
	/**
	 * calls the action for each element it finds
	 * @param location
	 * @param radius
	 * @param action
	 */
	void search(Vector3 location, float radius, IReceivePositionable handler){
		if(isLeaf()){
			if(data == null){
                return
			}
			if((location - data.position).lengthSq < radius * radius){
				handler.receive(data)
			}
			return
		}
		eachChild{ OctTree child ->
            if(child.hasPoint(location, new Vector3(radius))){
                child.search(location, radius, handler)
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
	void traverse(IReceivePositionable handler){
		if(isLeaf()){
			if(data == null){
                return
			}
			handler.receive(data)
			return
		}
		eachChild{ OctTree child ->
			child.traverse(handler)
		}
	}
	boolean hasPoint(Vector3 point){
		return hasPoint(point, new Vector3(0))
	}
	boolean hasPoint(Vector3 point, Vector3 offset){
        Vector3 min = center - halfDimension - offset
        Vector3 max = center + halfDimension + offset
		for(int dim : 0..2){
			if(point[dim] < min[dim]){
                return false
            }		
			if(point[dim] > max[dim]){
				return false
			}
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
	private void eachChild(IReceiveOctTree handler){
		(0..7).each{
			handler.receive(children[it])
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
		eachChild{ OctTree child ->
			result += child.size()
		}
		return result
	}
	@Override
    boolean isEmpty(){
		return data == null && children == null
	}
	@Override
    boolean contains(Object obj){
		if(!(obj instanceof IPositionable)){
			return false
		}
		IPositionable target = (IPositionable) obj
		return getAt(target) == obj
	}

    Iterator<IPositionable> iterator(){
		return new RecursiveOctTreeIterator(this)

	}
	@Override
    Object[] toArray(){
		List<IPositionable> result = new LinkedList<>()
		traverse{
			result.add(it)
		}
		return result.toArray()
	}
	@Override
    @SuppressWarnings("unchecked")
	<X> X[] toArray(X[] a){
		List<IPositionable> result = new LinkedList<>()
		traverse{
			result.add(it)
		}
		return result.toArray(a)
	}
	@Override
    boolean add(IPositionable element){
		if(element == null){
			throw new NullPointerException("Null is not allowed in this collection")
		}
		insert(element)
		return true
	}
	@Override
    boolean remove(Object obj){

		if(!(obj instanceof IPositionable)){
			return false
		}

		boolean changed = false
		searchLeafTree(((IPositionable) obj).position,
			{ OctTree tree ->
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
    boolean addAll(Collection<? extends IPositionable> collection){

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
		if(!(obj instanceof OctTree)){
			return false
		}
		OctTree target = (OctTree) obj
		return target.data == data && target.children == children
	}

	@Override
    int hashCode(){
		return data?.hashCode() * children?.hashCode() * 3
	}
}
