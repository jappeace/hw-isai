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
	Camera cam
	float rotationSpeed = 1f;
	float moveSpeed = 3f;
	float zoomSpeed = 1f;

	MotionAllowedListener motionAllowed = null;
	void rotateCamera(float value, Vector3f axis){
		Matrix3f matatrix = new Matrix3f()
		matatrix.fromAngleNormalAxis(rotationSpeed * value, axis)

		Vector3f up = cam.getUp()
		Vector3f left = cam.getLeft()
		Vector3f dir = cam.getDirection()

		matatrix.mult(up, up)
		matatrix.mult(left, left)
		matatrix.mult(dir, dir)

		Quaternion qauternion = new Quaternion()
		qauternion.fromAxes(left, up, dir)
		qauternion.normalizeLocal()

		cam.setAxes(qauternion)
	}

	void zoomCamera(float value){
		// derive fovY value
		float frustTop = cam.getFrustumTop()
		float frustRight = cam.getFrustumRight()
		float aspect = frustRight / frustTop

		float near = cam.getFrustumNear()

		float fovY = FastMath.atan(frustTop / near) / (FastMath.DEG_TO_RAD * 0.5f)
		float newFovY = fovY + value * 0.1f * zoomSpeed
		if (newFovY > 0f) {
			fovY = newFovY
		}

		frustTop = FastMath.tan( fovY * FastMath.DEG_TO_RAD * 0.5f) * near
		frustRight = frustTop * aspect

		cam.setFrustumTop(frustTop)
		cam.setFrustumBottom(-frustTop)
		cam.setFrustumLeft(-frustRight)
		cam.setFrustumRight(frustRight)
	}

	void riseCamera(float value){
		Vector3f vel = new Vector3f(0, value * moveSpeed, 0)
		Vector3f pos = cam.getLocation().clone()

		if (motionAllowed != null){
			motionAllowed.checkMotionAllowed(pos, vel)
		}else{
			pos.addLocal(vel)
		}

		cam.setLocation(pos)
	}

	void moveCamera(float value, boolean sideways){
		Vector3f vel = new Vector3f()
		Vector3f pos = cam.getLocation().clone()

		if (sideways){
			cam.getLeft(vel)
		}else{
			cam.getDirection(vel)
		}
		vel.multLocal(value * moveSpeed)

		if (motionAllowed != null){
			motionAllowed.checkMotionAllowed(pos, vel)
		}else{
			pos.addLocal(vel)
		}

		cam.setLocation(pos)
	}

}
