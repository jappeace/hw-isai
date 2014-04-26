package nl.jappieklooster.ISAI.init

import com.jme3.asset.AssetManager
import nl.jappieklooster.ISAI.init.factory.WorldFactory
import org.codehaus.groovy.control.CompilerConfiguration
import groovy.util.DelegatingScript
class LevelLoader {
	private Script script
	LevelLoader(AssetManager manager){
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class);
        GroovyShell sh = new GroovyShell(ClassLoader.getRootLoader(),new Binding(),cc);
	}
}
