package nl.jappieklooster.ISAI.init

import com.jme3.asset.AssetManager

import nl.jappieklooster.ISAI.Game
import nl.jappieklooster.ISAI.init.factory.dsl.WorldFactory;
import nl.jappieklooster.ISAI.world.World;
import nl.jappieklooster.ISAI.world.entity.tracking.ClickablesTracker

import org.codehaus.groovy.control.CompilerConfiguration

import groovy.util.DelegatingScript

import java.util.concurrent.ScheduledThreadPoolExecutor
class LevelLoader {
	private GroovyShell shell
	private ScheduledThreadPoolExecutor threadPoolExecuter
	private static final int threadCount = 1
	Game game
	private ClickablesTracker clickTracker


	LevelLoader(Game game){
		
		this.game = game

        threadPoolExecuter = new ScheduledThreadPoolExecutor(threadCount) // curently only neighbourtracker uses it
		clickTracker = new ClickablesTracker()

        CompilerConfiguration compilerConfig = new CompilerConfiguration();
        compilerConfig.setScriptBaseClass(DelegatingScript.class.name);
        shell = new GroovyShell(new Binding(),compilerConfig);
	}
	
	ClickablesTracker getClickTracker(){ clickTracker }

	World loadFromFile(String path){
		clickTracker.camera = game.camera
		// load the new world
		DelegatingScript script = (DelegatingScript)shell.parse(new File(path+".dsl"))
		WorldFactory factory = new WorldFactory(threadPoolExecuter, clickTracker)
		factory.game = game
		script.setDelegate(factory)
		script.run()
		factory.world.name = path
		return factory.world
	}
	
	void shutdownThreadPool(){
		threadPoolExecuter.shutdown()
	}
}
