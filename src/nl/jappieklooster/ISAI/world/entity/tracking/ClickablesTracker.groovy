package nl.jappieklooster.ISAI.world.entity.tracking

import com.jme3.collision.CollisionResult
import com.jme3.collision.CollisionResults
import com.jme3.math.Ray
import com.jme3.math.Vector2f
import com.jme3.math.Vector3f
import com.jme3.renderer.Camera
import nl.jappieklooster.ISAI.world.entity.Entity
import nl.jappieklooster.math.vector.Vector2

import com.jme3.scene.Node

class ClickablesTracker{

	private Camera camera
	private List<Entity> trackingItems
    ClickablesTracker(){
		super()
		trackingItems = new ArrayList<>() // need indeci
	}
	void setCamera(Camera cam){
		camera = cam
	}
	void track(Entity clickable){
		// jme only allows the saving of primetives in their spatials for some reason
		// I work around this by adding an index to the spatial and add the entity to the list
		clickable.geometry.setUserData("clickableIndex", trackingItems.size())
		trackingItems.add(clickable)
		
	}
	
	Entity click(Node candidates, Vector2f position){
		Vector3f origin = camera.getWorldCoordinates(position, 0)
		Vector3f direction = camera.getWorldCoordinates(position, 1f).subtractLocal(origin).normalizeLocal()
		Ray ray = new Ray(origin, direction)
		
		CollisionResults rayCollisions = new CollisionResults();
		candidates.collideWith(ray, rayCollisions)
		
		// C if there is a result in the tracking items
		Entity result = null
		
		// collision results sorts itself if the iterator is asked, (foreach asks a iterator)
		for(CollisionResult candidate : rayCollisions){
			
			// try to get theuserdata
			Integer index = candidate.geometry.getUserData("clickableIndex")
			if(index == null){
				// if it failed the geometry is certainly not in here
				continue	
			}
			
			// there was somthing in there, check if its in range of the arraylist
			if(index >= trackingItems.size() || index < 0){
				continue
			}
			
			// we found what we where looking for
			result = trackingItems[index]
			
			// this function only gives one result, because if 2 clickables line up
			// the user expects the one in front to be selected not both
			break
		}
		return result
	}
}
