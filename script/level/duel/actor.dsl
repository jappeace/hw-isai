import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter
import nl.jappieklooster.ISAI.behaviour.state.StateMachine
import nl.jappieklooster.ISAI.behaviour.steer.Seek
import nl.jappieklooster.ISAI.world.Character
import nl.jappieklooster.ISAI.world.mortal.Team

import com.jme3.scene.shape.*

Team[] teams = [new Team("red"), new Team("blue")]

group {
    (0..2).each { int number ->
        
        // declaring the variable here allows acces inside the states because scoping
        Character person = null

        // if I where to declare and initilize on method call it would be to late
        // in groovy the decleration is made after the init call (tried it and it failed so must be true)
        person = character{

            location new Vector3(0,10,25*number)
            mesh new Sphere(10, 10, 1)
            mass 0.01

            StateMachine machine = states{
                float energy = 0

                float rate = 0.1
                state{
                    float requiredEnergy = 0
                    name="farming"
                    enter{ println "I am farming"	 }
                    execute{ StateMachine stateMachine ->

                        if(energy < requiredEnergy){
                            stateMachine.changeState "moveToHome"
                            return
                        }
                        energy -= rate
                    }
                }

                state{
                    name="moveToHome"
                    enter{
                        println "going home"
                        person?.move(new Vector3(0, 0, 0))
                    }

                    execute{StateMachine stateMachine ->
                        if(person.isDone()){
                            stateMachine.changeState "resting"
                        }
                    }
                }
                state{

                    float enoughEnergy = 100
                    name="resting"
                    active = true
                    enter{ println "I am resting" }

                    execute{ StateMachine stateMachine ->
                        if(energy > enoughEnergy){
                            stateMachine.changeState "moveToFarm"
                            return
                        }
                        energy += rate
                    }
                }
                state{
                    name="moveToFarm"
                    enter{
                        println "going to work, while shooting at: " + teams[number % 2].findClosest(person).position
                        person?.move(new Vector3(100, 0, 20))
                    }
                    execute{StateMachine stateMachine ->
						person.primary.attack(teams[number % 2].findClosest(person))
                        if(person.isDone()){
                            stateMachine.changeState "farming"
                        }
                    }
                }
            }

            write{
                text {machine.currentStateName}
                scale 0.5f
                location 2f, 5f, 0f
            }
        }
        teams[(number + 1) % 2].members.add(person)
		
		weapons{
			targetTeam = teams[number%2]
			person.primary = pistol(person)
		}
    }
}