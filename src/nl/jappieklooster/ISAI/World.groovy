package nl.jappieklooster.ISAI
import com.jme3.scene.Node
import nl.jappieklooster.ISAI.entity.Entity
class World {
	Node node
	List<Entity> entities
	void update(float tpf){
		entities.each{
			it.update(tpf)
		}
	}

}
