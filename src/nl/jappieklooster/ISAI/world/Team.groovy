package nl.jappieklooster.ISAI.world


/**
 * the team functionality extends groups in that it has a list of rival teams
 * this meta data allows group members to interact with their enemies
 * 
 * it also overrides the equals method, teams or equals to eachother when they share
 * the same name
 */
class Team extends Group{

	Collection<Team> rivalTeams

	@Override
	boolean equals(Object to){
		if(!to instanceof Team){
			return false
		}
		Team target = (Team) to
		return target.name == name
	}
	
	@Override
	int hashCode(){
		return name.hashCode()
	}
	
}
