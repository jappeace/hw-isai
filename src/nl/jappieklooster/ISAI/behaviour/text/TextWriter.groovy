package nl.jappieklooster.ISAI.behaviour.text

import nl.jappieklooster.ISAI.behaviour.AbstractBehaviour;
import nl.jappieklooster.ISAI.world.IHasNode
import nl.jappieklooster.math.vector.Vector3;
import nl.jappieklooster.math.vector.Converter

import com.jme3.font.BitmapFont
import com.jme3.font.BitmapText
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f
import com.jme3.scene.Node

/**
 * attaches text to a node
 * 
 * it is a behaviour because it allows text to be changed on runtime,
 * if its only necisary to render it once, just call execute once.
 * @author jappie
 *
 */
class TextWriter extends AbstractBehaviour implements IHasNode{

	private int previousTextHash
	private BitmapText jmeText
	private Node transformTarget

	IHasNode nodeContainer
	ITextSource source
	BitmapFont font
	ColorRGBA color
	
	TextWriter(){
		color = new ColorRGBA(1,1,1,1)
		transformTarget = new Node("textNode")
	}
	
	void setNodeContainer(IHasNode container){
		nodeContainer = container
		nodeContainer.node.attachChild(transformTarget)
	}
	@Override
	Node getNode() {
		return transformTarget;
	}
	@Override
	void execute() {
		String text = source.getText()
		if(text.hashCode() == previousTextHash){
			// text does not need to be updated
			return
		}
		previousTextHash = text.hashCode()
		if(jmeText){
			transformTarget.detachAllChildren()
		}
		jmeText = createText(text)
	
		BitmapText behind = createText(text)
		behind.rotate((float)Math.PI,0,0)
		transformTarget.attachChild(jmeText)
		transformTarget.attachChild(behind)
	}

	private BitmapText createText(String text){
		BitmapText result = new BitmapText(font, false)
		result.text = text
		result.color = color
		result.size = font.charSet.renderedSize
		return result
	}

	@Override
	public Vector3 getPosition() {
		return Converter.fromJME(transformTarget.localTransform)
	}
}
