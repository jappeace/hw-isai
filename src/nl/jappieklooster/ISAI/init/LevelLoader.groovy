package nl.jappieklooster.ISAI.init

import com.jme3.asset.AssetManager

import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.init.factory.dsl.WorldFactory;
import nl.jappieklooster.ISAI.world.World;

import org.codehaus.groovy.control.CompilerConfiguration

import groovy.util.DelegatingScript

import java.util.concurrent.ScheduledThreadPoolExecutor
class LevelLoader {
	private GroovyShell shell
	private Random random // store the randomness
	private ScheduledThreadPoolExecutor threadPoolExecuter
	private static final int threadCount = 1
	Game game

	LevelLoader(Game game){
		
		this.game = game

		random = new Random()
        threadPoolExecuter = new ScheduledThreadPoolExecutor(threadCount) // curently only neighbourtracker uses it

        CompilerConfiguration compilerConfig = new CompilerConfiguration();
        compilerConfig.setScriptBaseClass(DelegatingScript.class.name);
        shell = new GroovyShell(new Binding(),compilerConfig);
	}
	
	World loadFromFile(String path){
		// load the new world
		DelegatingScript script = (DelegatingScript)shell.parse(new File(path+".dsl"))
		WorldFactory factory = new WorldFactory(threadPoolExecuter)
		factory.game = game
		factory.random = random
		script.setDelegate(factory)
		script.run()
		factory.world.name = path
		return factory.world
	}
	
	void shutdownThreadPool(){
		threadPoolExecuter.shutdown()
	}
}
