package nl.jappieklooster.ISAI.init.factory.dsl

import com.jme3.asset.AssetManager
import com.jme3.scene.Spatial;

import nl.jappieklooster.ISAI.behaviour.text.ITextSource
import nl.jappieklooster.ISAI.behaviour.text.TextWriter

/**
 * allows creation of text in 3d space
 * @author jappie
 *
 */
class TextFactory extends ASpatialFactory {

	TextWriter result
	AssetManager manager
	TextFactory(AssetManager manager){
		this.manager = manager
		result = new TextWriter()
	}

	void setToDefault(){
		font "Interface/Fonts/Default.fnt"
		text "No text specified"
	}
	
	void font(String path){
		result.font = manager.loadFont(path)
	}
	
	void text(ITextSource source){
		result.source = source
	}
	
	void text(String text){
		result.source = {text}
	}

	@Override
	protected Spatial getSpatial() {
		return result.node
	}
}
