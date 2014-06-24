package nl.jappieklooster.ISAI.init.factory.dsl.group

import java.util.concurrent.ScheduledThreadPoolExecutor;
import nl.jappieklooster.ISAI.init.DelegateClosure
import nl.jappieklooster.ISAI.world.Team

class TeamFactory extends GroupFactory {
	Team team
	TeamFactory(ScheduledThreadPoolExecutor exec){
		super(exec)
		init()
	}
	TeamFactory(TeamFactory factory){
		super(factory)
		init()
	}
	private void init(){
		team = new Team(group)
		group = team
	}
	
	Team rivalTeam(Closure commands){
		TeamFactory factory = new TeamFactory(this)
		new DelegateClosure(to: factory).call(commands)

		team.rivalTeams.add(factory.team)
		factory.team.rivalTeams.add(team)

		return factory.team
	}
	
}
