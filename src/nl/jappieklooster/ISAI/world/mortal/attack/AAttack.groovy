package nl.jappieklooster.ISAI.world.mortal.attack
import com.jme3.bounding.BoundingSphere
import com.jme3.bounding.BoundingVolume
import com.jme3.collision.CollisionResults
import com.jme3.scene.Spatial
import nl.jappieklooster.ISAI.world.Environment
import nl.jappieklooster.ISAI.world.IHasNode
import nl.jappieklooster.ISAI.world.entity.MovingEntity
import nl.jappieklooster.ISAI.world.mortal.IAttack
import nl.jappieklooster.ISAI.world.mortal.IMortal
import nl.jappieklooster.ISAI.world.mortal.Team;
import nl.jappieklooster.math.vector.Vector3

/**
 * common base for most attacks
 * @author jappie
 *
 */
abstract class AAttack implements IAttack {

	/**
	 * the thing that represents the attack (if any)
	 */
	MovingEntity body = null

	/**
	 * the team teh attack is meant for, this should probalby be more than one, but for now I will just say there are just 2 teams fighting
	 */
	Team target = null
	
	/**
	 * can also collide with environment
	 */
	IHasNode environment = null
	
	
	@Override
	Vector3 getPosition(){
		return body.position
	}
	/**
	 * determins if the attack is colliding with the target team
	 * @return
	 */
	IMortal findCollidingMortal(){
		
		if(body == null){
			return null
		}
		if(target == null){
			return null
		}
		CollisionResults collisions = new CollisionResults()
		
		for(IMortal mortal : target.members){

			body.collideWith(mortal.spatial, collisions)

			if(collisions.size() > 0){
				return mortal
			}
		}
		
		body.collideWith(environment.node,collisions)

		if(collisions.size() > 0){
			return new IMortal(){
				Vector3 getPosition(){
					return environment.position
				}
				void setHealth(float to){
					// don't kill the environment
				}
				Spatial getSpatial(){
					return environment.node
				}
			}

		}
		return null
	}
	
	@Override
	public Spatial getSpatial() {
		return body.spatial
	}
}
