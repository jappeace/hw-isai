package nl.jappieklooster.ISAI.behaviour.text

import nl.jappieklooster.ISAI.behaviour.AbstractBehaviour;
import nl.jappieklooster.ISAI.world.IHasNode

import com.jme3.font.BitmapFont
import com.jme3.font.BitmapText
import com.jme3.math.ColorRGBA
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
	public Node getNode() {
		return transformTarget;
	}
	@Override
	public void execute() {
		String text = source.getText()
		if(text.hashCode() == previousTextHash){
			// text does not need to be updated
			return
		}
		previousTextHash = text.hashCode()
		if(jmeText){
			transformTarget.detachChild(jmeText)
		}
		jmeText = new BitmapText(font, false)
		jmeText.text = text
		jmeText.color = color
		jmeText.size = font.charSet.renderedSize

		transformTarget.attachChild(jmeText)
	}

}
