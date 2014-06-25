package nl.jappieklooster.ISAI

import nl.jappieklooster.ISAI.world.IPositionable
import nl.jappieklooster.math.vector.Vector3

class Team implements Comparable<Team>{
	String name
	Collection<IPositionable> members
	
	Team(){
		this("")
	}
	Team(String name){
		members = new LinkedList<>()
		this.name = name
	}
	@Override
	int compareTo(Team team) {
		return name.compareTo(team.name);
	}
	
	boolean equals(Object to){
		if(!(to instanceof Team)) {
			return false
		}
		Team target = (Team) to
		return name == target.name
		
	}
	int hashCode(){
		return name.hashCode()
	}
	
	
	IPositionable findClosest(IPositionable to){
		Vector3 closest = Vector3.CreateBiggestFloat()
		IPositionable result = null
		members.each{
			if((to.position - it.position).lengthSq < (to.position - closest).lengthSq){
				closest = it.position
				result = it
			}
		}
		return result
	}
}

