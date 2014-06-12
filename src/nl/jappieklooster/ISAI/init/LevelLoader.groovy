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
	private ClickablesTracker clickTracker
	Game game

	static final String levelDir = "script"+ File.separator + "level" + File.separator
	static final String defaultFolder = "default"
	LevelLoader(Game game){
		this.game = game

        threadPoolExecuter = new ScheduledThreadPoolExecutor(threadCount) // curently only neighbourtracker uses it
		clickTracker = new ClickablesTracker()

        CompilerConfiguration compilerConfig = new CompilerConfiguration();
        compilerConfig.setScriptBaseClass(DelegatingScript.class.name);
        shell = new GroovyShell(new Binding(),compilerConfig);
	}
	
	ClickablesTracker getClickTracker(){ clickTracker }

	World loadFromFile(String levelName){
		clickTracker.camera = game.camera
		// load the new world
		WorldFactory factory = new WorldFactory(threadPoolExecuter, clickTracker)
		factory.game = game

		if(!new File(levelDir + levelName).isDirectory()){
			throw new Exception("Excpected to find a directory at " + levelDir + levelName)
		}
		
		new File(levelDir+defaultFolder).listFiles().each{

			File file = new File(levelDir + levelName + File.separator + it.getName())
			
			if(!file.exists()){
				file = it
			}

            DelegatingScript script = (DelegatingScript)shell.parse(file)
            script.setDelegate(factory)
            script.run()
		}
		factory.world.name = levelName
		return factory.world
	}
	
	void shutdownThreadPool(){
		threadPoolExecuter.shutdownNow()
	}
}
