package nl.jappieklooster.ISAI.world.mortal.attack
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
            body.node.collideWith(mortal.body, collisions)
			if(collisions.size() > 0){
				return mortal
			}
		}
		
		body.node.collideWith(environment.node, collisions)
		if(collisions.size() > 0){
			return new IMortal(){
				Vector3 getPosition(){
					return environment.position
				}
				void setHealth(float to){
					// don't kill the environment
				}
				Spatial getBody(){
					return environment.node
				}
			}

		}
		return null
	}
	
}
