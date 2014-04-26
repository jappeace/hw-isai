package nl.jappieklooster.ISAI.init

import com.jme3.asset.AssetManager
import nl.jappieklooster.ISAI.World
import nl.jappieklooster.ISAI.init.factory.WorldFactory
import org.codehaus.groovy.control.CompilerConfiguration
import groovy.util.DelegatingScript
import java.util.concurrent.ScheduledThreadPoolExecutor
class LevelLoader {
	private GroovyShell shell
	private Random random // store the randomness
	private ScheduledThreadPoolExecutor threadPoolExecuter
	private static final int threadCount = 1
	AssetManager manager

	LevelLoader(AssetManager manager){
		
		this.manager = manager
		random = new Random()
        threadPoolExecuter = new ScheduledThreadPoolExecutor(threadCount) // curently only neighbourtracker uses it

        CompilerConfiguration compilerConfig = new CompilerConfiguration();
        compilerConfig.setScriptBaseClass(DelegatingScript.class);
        GroovyShell shell = new GroovyShell(new Binding(),compilerConfig);
	}
	
	World loadFile(String path){
		// load the new world
		DelegatingScript script = (DelegatingScript)shell.parse(new File(path+".dsl"))
		WorldFactory factory = new WorldFactory()
		factory.neighTracker.threadPoolExecuter = threadPoolExecuter
		script.setDelegate(factory)
		script.run()

		return factory.world
	}
	
	void releaseThreadPool(){
		threadPoolExecuter.shutdown()
	}
}
