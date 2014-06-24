package nl.jappieklooster.ISAI.world


/**
 * the team functionality extends groups in that it has a list of rival teams
 * this meta data allows group members to interact with their enemies
 * 
 */
class Team extends Group{

	Collection<Team> rivalTeams
	
	Team(){
		super()
		init()
	}

	/**
	 * copy constructor
	 */
	Team(Team source){
		super(source)
		rivalTeams = new LinkedList<>(source.rivalTeams)
	}
	
	Team(Group source){
		super(source)
		init()
	}
	
	private void init(){
		rivalTeams = new LinkedList<>()
	}
}
