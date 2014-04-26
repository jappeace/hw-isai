package nl.jappieklooster.ISAI.init

import com.jme3.asset.AssetManager
import nl.jappieklooster.ISAI.init.factory.WorldFactory
import org.codehaus.groovy.control.CompilerConfiguration
import groovy.util.DelegatingScript
import java.util.concurrent.ScheduledThreadPoolExecutor
class LevelLoader {
	private GroovyShell shell
	private Random random // store the randomness
	AssetManager manager
	private ScheduledThreadPoolExecutor threadPoolExecuter
	private static final int threadCount = 1
	LevelLoader(AssetManager manager){
		
		this.manager = manager
		random = new Random()
        threadPoolExecuter = new ScheduledThreadPoolExecutor(threadCount) // curently only neighbourtracker uses it

        CompilerConfiguration compilerConfig = new CompilerConfiguration();
        compilerConfig.setScriptBaseClass(DelegatingScript.class);
        GroovyShell shell = new GroovyShell(new Binding(),compilerConfig);
	}
	
	void loadFile(String path){
		if(!random && manager){
			throw new Exception("please specify the random and AssetManager before loading a user script")
		}
		// destruct previous world
		// TODO: destruct previous world
		// load the new world
		DelegatingScript script = (DelegatingScript)shell.parse(new File(path+".dsl"))
		script.setDelegate(new WorldFactory(manager, random))
		script.run()
	}
}
