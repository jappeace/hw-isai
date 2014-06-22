package nl.jappieklooster.ISAI.world


class Team {
	/**
	 * the state of a team equals its name
	 * two teams with the same name are the same team
	 */
	String name

	Collection<IPositionable> members
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
