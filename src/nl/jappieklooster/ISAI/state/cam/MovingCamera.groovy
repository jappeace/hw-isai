package nl.jappieklooster.ISAI.state.cam

import com.jme3.math.Vector3f
import com.jme3.renderer.Camera

import com.jme3.collision.MotionAllowedListener
import com.jme3.math.FastMath
import com.jme3.math.Matrix3f
import com.jme3.math.Quaternion
import com.jme3.math.Vector3f


/**
 * a rip of flybycamara, except this one does not include keymappings, which should be handled by a state
 * @author jappie
 *
 */
class MovingCamera {
	private Camera camera
	float rotationSpeed = 1f;
	float moveSpeed = 50f;
	float zoomSpeed = 1f;
	MotionAllowedListener motionAllowed = null;
	
	MovingCamera(Camera cam){
		camera = cam
	}

	Camera getCamera(){
		return camera
	}

	void rotate(float value, Vector3f axis){
		Matrix3f matatrix = new Matrix3f()
		matatrix.fromAngleNormalAxis((float)rotationSpeed * value, axis)

		Vector3f up = camera.getUp()
		Vector3f left = camera.getLeft()
		Vector3f dir = camera.getDirection()

		matatrix.mult(up, up)
		matatrix.mult(left, left)
		matatrix.mult(dir, dir)

		Quaternion qauternion = new Quaternion()
		qauternion.fromAxes(left, up, dir)
		qauternion.normalizeLocal()

		camera.setAxes(qauternion)
	}

	void zoom(float value){
		// derive fovY value
		float frustTop = camera.getFrustumTop()
		float frustRight = camera.getFrustumRight()
		float aspect = frustRight / frustTop

		float near = camera.getFrustumNear()

		float fovY = FastMath.atan(frustTop / near) / (FastMath.DEG_TO_RAD * 0.5f)
		float newFovY = fovY + value * 0.1f * zoomSpeed
		if (newFovY > 0f) {
			fovY = newFovY
		}

		frustTop = FastMath.tan( fovY * FastMath.DEG_TO_RAD * 0.5f) * near
		frustRight = frustTop * aspect

		camera.setFrustumTop(frustTop)
		camera.setFrustumBottom(-frustTop)
		camera.setFrustumLeft(-frustRight)
		camera.setFrustumRight(frustRight)
	}

	void rise(float value){
		Vector3f vel = new Vector3f(0, value * moveSpeed, 0)
		Vector3f pos = camera.getLocation().clone()

		if (motionAllowed != null){
			motionAllowed.checkMotionAllowed(pos, vel)
		}else{
			pos.addLocal(vel)
		}

		camera.setLocation(pos)
	}

	void move(float value, boolean sideways){
		Vector3f vel = new Vector3f()
		Vector3f pos = camera.getLocation().clone()

		if (sideways){
			camera.getLeft(vel)
		}else{
			camera.getDirection(vel)
		}
		vel.multLocal((float)value * moveSpeed)

		if (motionAllowed != null){
			motionAllowed.checkMotionAllowed(pos, vel)
		}else{
			pos.addLocal(vel)
		}

		camera.setLocation(pos)
	}

}
