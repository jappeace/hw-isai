package nl.jappieklooster.ISAI.world
import com.jme3.math.Vector3f
import com.jme3.scene.shape.Sphere;

import nl.jappieklooster.ISAI.world.entity.Entity;
import nl.jappieklooster.math.vector.Vector3

/**
 * the world class is the link between static envoironments and updatable stuff, like groups
 * Before I did some refactoring a group was called a world, which made no sense because worlds could contain worlds.
 * 
 * in this setup a world could be in a world, but I made it hard to do because there are no standart dsl functions that support that
 * 
 * @author jappie
 *
 */
class World extends AHasNode implements IUpdatable{

	List<IUpdatable> actors
	Environment environment
	
	World(){
		super()
		actors = new LinkedList<>()
	}
	@Override
	public void update(float tpf) {
		actors.each{
			it.update(tpf)
		}
	}

}
