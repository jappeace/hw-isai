import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter
import nl.jappieklooster.ISAI.behaviour.state.StateMachine
import nl.jappieklooster.ISAI.behaviour.steer.Seek
import com.jme3.scene.shape.*

group{
    character{
    	location new Vector3(0,10,0)
        mesh new Sphere(10, 10, 1)	
		mass 0.01
        
        StateMachine machine = states{
        	float energy = 0
			
			float rate = 0.1
       		state{ 
       			float requiredEnergy = 0
       			name="farming"
       			enter{StateMachine stateMachine ->
       				println "I am farming"	
					stateMachine.target?.move(new Vector3(100, 0, 20))
					  
       			}
       			execute{ StateMachine stateMachine ->
       				if(energy < requiredEnergy){
       					stateMachine.changeState "resting"	
       					return
       				}	
       				energy -= rate
       			}
       		}

       		state{
       			float enoughEnergy = 100
       			name="resting"
       			active = true
       			enter{StateMachine stateMachine ->
       				println "I am resting"	
					stateMachine.target?.move(new Vector3(0, 0, 0))
       			}
				execute{ StateMachine stateMachine ->
					if(energy > enoughEnergy){
						stateMachine.changeState "farming"
						return
					}
					energy += rate
				}	
       		} 
        }
        
        write{
        	text {machine.currentStateName}
        	scale 0.5f
        	location 2f, 5f, 0f
            rotation new Vector3(0, 0,-0.25*Math.PI)
        }
    }
}
