import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter
import nl.jappieklooster.ISAI.behaviour.state.StateMachine
import nl.jappieklooster.ISAI.behaviour.steer.Seek
import nl.jappieklooster.ISAI.world.Character
import nl.jappieklooster.ISAI.world.mortal.Team

import com.jme3.scene.shape.*

Team[] teams = [new Team("red"), new Team("blue")]

group {
    (0..1).each { int number ->
        
        // declaring the variable here allows acces inside the states because scoping
        Character person = null

        // if I where to declare and initilize on method call it would be to late
        // in groovy the decleration is made after the init call (tried it and it failed so must be true)
        person = character{

            location new Vector3(0,10,25*number)
            mesh new Sphere(10, 10, 1)
            mass 0.01

            StateMachine machine = states{

                state{
                    name="moveUp"
                    enter{
                        println "going home"
                        person?.move(new Vector3(100*number, 0, 0))
                    }

                    execute{StateMachine stateMachine ->
						person.primary.attack(teams[number % 2].findClosest(person))
                        if(person.isDone()){
                            stateMachine.changeState "moveDown"
                        }
                    }
                }
                state{
                    name="moveDown"
                    enter{
                        println "going to work, while shooting at: " + teams[number % 2].findClosest(person).position
                        person?.move(new Vector3(100 * (number-1), 0, 0))
                    }
                    execute{StateMachine stateMachine ->
						person.primary.attack(teams[number % 2].findClosest(person))
                        if(person.isDone()){
                            stateMachine.changeState "moveUp"
                        }
                    }
                }
            }
        }
        teams[(number + 1) % 2].members.add(person)
		
		weapons{
			targetTeam = teams[number%2]
			person.primary =  shotgun(person) 
		}
    }
}