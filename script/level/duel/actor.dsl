import nl.jappieklooster.math.vector.Vector3
import nl.jappieklooster.math.vector.Converter
import nl.jappieklooster.ISAI.behaviour.state.StateMachine
import nl.jappieklooster.ISAI.behaviour.steer.Seek
import nl.jappieklooster.ISAI.world.Character
import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.ISAI.world.mortal.Team

import org.bakkes.fuzzy.*
import org.bakkes.fuzzy.sets.*
import org.bakkes.fuzzy.operators.*
import org.bakkes.fuzzy.hedges.*
import com.jme3.scene.shape.*

Team[] teams = [new Team("red"), new Team("blue")]

FuzzyVariable distance = new FuzzyVariable()

IFuzzySet close = new LeftShoulder(10,0,40)
IFuzzySet medium = new Triangle(40,10,300)
IFuzzySet far = new RightShoulder(300,20,1000)
distance.addSet("close", close)
distance.addSet("medium", medium)
distance.addSet("far", far)

FuzzyVariable desirability = new FuzzyVariable()

IFuzzySet verydesirable = new LeftShoulder(75,50,100)
IFuzzySet desirable= new Triangle(50,25,75)
IFuzzySet undesirable = new RightShoulder(25,0,50)
desirability.addSet("verydesirable", verydesirable)
desirability.addSet("desirable", desirable)
desirability.addSet("undesirable", undesirable)

FuzzyVariable ammo = new FuzzyVariable()

IFuzzySet loads = new LeftShoulder(80,50,100)
IFuzzySet okey = new Triangle(50,0,80)
IFuzzySet low = new RightShoulder(0,0,50)
ammo.addSet("loads", loads)
ammo.addSet("okey", okey)
ammo.addSet("low", low)

def module = [
	"shotgun":new FuzzyModule(),
	"pistol": new FuzzyModule()
]
module["shotgun"].addFLV("ammo", ammo)
module["shotgun"].addFLV("desirability", desirability)
module["shotgun"].addFLV("distance", distance)
module["pistol"].addFLV("ammo", ammo)
module["pistol"].addFLV("desirability", desirability)
module["pistol"].addFLV("distance", distance)

module["shotgun"].addRule(new FuzzyAnd(close, loads), verydesirable)
module["shotgun"].addRule(new FuzzyAnd(close, okey), verydesirable)
module["shotgun"].addRule(new FuzzyAnd(close, low), desirable)

module["shotgun"].addRule(new FuzzyAnd(medium, loads), desirable)
module["shotgun"].addRule(new FuzzyAnd(medium, okey), desirable)
module["shotgun"].addRule(new FuzzyAnd(medium, low), undesirable)

module["shotgun"].addRule(new FuzzyAnd(far, loads), undesirable)
module["shotgun"].addRule(new FuzzyAnd(far, okey), undesirable)
module["shotgun"].addRule(new FuzzyAnd(far, low), undesirable)


module["pistol"].addRule(new FuzzyAnd(close, loads), desirable)
module["pistol"].addRule(new FuzzyAnd(close, okey), desirable)
module["pistol"].addRule(new FuzzyAnd(close, low), undesirable)

module["pistol"].addRule(new FuzzyAnd(medium, loads), verydesirable)
module["pistol"].addRule(new FuzzyAnd(medium, okey), verydesirable)
module["pistol"].addRule(new FuzzyAnd(medium, low), desirable)

module["pistol"].addRule(new FuzzyAnd(far, loads), verydesirable)
module["pistol"].addRule(new FuzzyAnd(far, okey), verydesirable)
module["pistol"].addRule(new FuzzyAnd(far, low), verydesirable)

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

				int shells = 50
				int rounds = 50
				
				// put the fuzzy stuff in its own 'function'
				def attackAI = { StateMachine stateMachine, String nextState ->
                    IPositionable target = teams[number % 2].findClosest(person)
                    float closestDist = (target.position - person.position).length

                    module["shotgun"].fuzzify("distance", closestDist)
                    module["shotgun"].fuzzify("ammo", shells)
                    float shotgunDesire = module["shotgun"].deFuzzify("desirability", DefuzzifyType.MAX_AV)

                    module["pistol"].fuzzify("distance", closestDist)
                    module["pistol"].fuzzify("ammo", rounds)
                    float pistolDesire = module["pistol"].deFuzzify("desirability", DefuzzifyType.MAX_AV)

                    if(pistolDesire > shotgunDesire){
                        def attack = person.pistol.attack(target)
						if(attack != null){
							println "pistoldesire: " + pistolDesire + " -- " + "shotgundesire: " + shotgunDesire
                            shells++
                            rounds--
						}
                    }else{
                       	def attack = person.shotgun.attack(target)
						if(attack != null){
							println "pistoldesire: " + pistolDesire + " -- " + "shotgundesire: " + shotgunDesire
                            shells--
                            rounds++
						}
                    }

                    if(person.isDone()){
                        stateMachine.changeState nextState
                    }
				}
                state{
                    name="moveUp"
                    enter{
                        println "going home"
                        person?.move(new Vector3(100, 0, 10*number))
                    }

                    execute{StateMachine stateMachine ->
						attackAI(stateMachine, "moveDown")
                    }
                }
                state{
                    name="moveDown"
                    enter{
                        println "going to work, while shooting at: " + teams[number % 2].findClosest(person).position
                        person?.move(new Vector3(-100, 0, 50*number))
                    }
                    execute{StateMachine stateMachine ->
						attackAI(stateMachine, "moveUp")
                    }
                }
            }
        }
        teams[(number + 1) % 2].members.add(person)
		
		weapons{
			targetTeam = teams[number%2]
			person.shotgun =  shotgun(person) 
			person.pistol = pistol(person)
		}
    }
}