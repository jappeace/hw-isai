package nl.jappieklooster.ISAI.relation

import nl.jappieklooster.ISAI.world.IPositionable

class TeamTracker {

	SortedSet<Team> teams
	
	TeamTracker(){
		teams = new TreeSet<>()
	}
	
	Team findTeam(String name){
		return teams.tailSet(new Team(name: name)).first()
	}
}
