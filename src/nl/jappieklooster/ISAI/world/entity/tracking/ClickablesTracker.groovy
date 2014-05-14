package nl.jappieklooster.ISAI.world.entity.tracking

import com.jme3.collision.CollisionResult
import com.jme3.collision.CollisionResults
import com.jme3.math.Ray
import com.jme3.math.Vector2f
import com.jme3.math.Vector3f
import com.jme3.renderer.Camera
import nl.jappieklooster.ISAI.world.entity.Entity
import nl.jappieklooster.math.vector.Vector2
import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter

import com.jme3.scene.Node
import com.jme3.scene.Spatial

class ClickablesTracker{

	private Camera camera
	/** allows the retrieval of the entity from the spatial */
	private Map<Spatial, Entity> trackingItems
    private Node candidates
    ClickablesTracker(){
		super()
		trackingItems = new HashMap<>()
	}
	void setCamera(Camera cam){
		camera = cam
	}
	void setCandidates(Node to){
		candidates = to
	}
	void track(Entity clickable){
		trackingItems[clickable.geometry] = clickable
	}
	
	Entity clickOnEntity(Vector2f mousePosition){


		CollisionResults rayCollisions = findCollisionResults(mousePosition)

		Entity result = null
		
		// collision results sorts itself if the iterator is asked, (foreach asks a iterator)
		for(CollisionResult candidate : rayCollisions){
			
			result = trackingItems[candidate.geometry]
			if(result){
				// if its in the map
				break
			}
		}
		return result
	}
	Vector3 clickOnSurface(Vector2f mousePosition){
		CollisionResults rayCollisions = findCollisionResults(mousePosition)
		return Converter.fromJME(rayCollisions.closestCollision.contactPoint)
	}
	private CollisionResults findCollisionResults(Vector2f mousePosition){
		Vector3f origin = camera.getWorldCoordinates(mousePosition, 0)
		Vector3f direction = camera.getWorldCoordinates(mousePosition, 1f).subtractLocal(origin).normalizeLocal()
		Ray ray = new Ray(origin, direction)
		
		CollisionResults result = new CollisionResults();
		candidates.collideWith(ray, result)	
		
		return result
	}
}
