package nl.jappieklooster.ISAI.state

import com.jme3.app.Application
import com.jme3.app.SimpleApplication
import com.jme3.app.state.AbstractAppState
import com.jme3.app.state.AppStateManager
import com.jme3.asset.AssetManager
import com.jme3.input.InputManager
import com.jme3.renderer.ViewPort
import com.jme3.scene.Node
import nl.jappieklooster.ISAI.Game

/**
 * set the stuff most states need on init
 * @author jappie
 *
 */
abstract class ACommenState extends AbstractAppState{
	protected Node              rootNode
	protected AssetManager      assetManager
	protected InputManager      inputManager
	protected ViewPort          viewPort
	protected AppStateManager   stateManager

	@Override
	public void initialize(AppStateManager stateManager, Application givenApp) {
		super.initialize(stateManager, givenApp)
		Game app = (Game) givenApp;
		rootNode     = app.getRootNode();
		assetManager = app.getAssetManager();
		inputManager = app.getInputManager();
		viewPort     = app.getViewPort();
		this.stateManager = stateManager
		init(app)
	}
	
	abstract void init(Game app);

}
