import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter
import nl.jappieklooster.ISAI.behaviour.state.StateMachine
import nl.jappieklooster.ISAI.behaviour.steer.Seek
import com.jme3.scene.shape.*

group{
    vehicle{
    	location new Vector3(0,10,0)
        mesh new Sphere(10, 10, 1)	
        
        rotation new Vector3(0, 0,-0.25*Math.PI)
        StateMachine machine = states{
        	int energy = 0
			
			float rate = 0.01
       		state{ 
       			int requiredEnergy = 0
       			int decrease = 1
       			name="farming"
       			enter{
       				println "I am farming"	
       			}
       			execute{ StateMachine stateMachine ->
       				if(energy < requiredEnergy){
       					stateMachine.changeState "resting"	
       					return
       				}	
       				energy -= decrease
       				stateMachine.target.spatial.localScale.multLocal(Converter.toJME(new Vector3(1-rate,1-rate,1-rate)))
       			}
       		}

       		state{
       			int enoughEnergy = 100
       			int increase = 1
       			name="resting"
       			active = true
       			enter{
       				println "I am resting"	
       			}
				execute{ StateMachine stateMachine ->
					if(energy > enoughEnergy){
						stateMachine.changeState "farming"
						return
					}
					energy += increase
       				stateMachine.target.spatial.localScale.multLocal(Converter.toJME(new Vector3(1+rate,1+rate,1+rate)))
				}	
       		} 
        }
        
        write{
        	text {machine.currentStateName}
        	scale 0.5f
        	location 2f, 5f, 0f
        }
    }
}
