package jopengl.utils

import kotlin.math.cos
import kotlin.math.sin

class Camera {

    var position: Vector = Vector()
    val view: Matrix4 = Matrix4()
    val projection: Matrix4 = Matrix4()
    var yaw: Float = -90f
    var pitch: Float = 0f
    var fov: Float = 45f
    val cameraFront: Vector = Vector()
    fun cameraRight(): Vector = Vector.cross(cameraFront, Vector(0.0f, 1.0f, 0.0f)).normalize()
    val cameraUp: Vector = Vector(0.0f, 1.0f, 0.0f)
    fun cameraSpeed(): Float = 35f / 1000f * 4f
    val mouseSensitivity: Float = 0.35f

    fun updateCamera() {
        cameraFront.x = cos(yaw.toRadians()) * cos(pitch.toRadians())
        cameraFront.y = sin(pitch.toRadians())
        cameraFront.z = sin(yaw.toRadians()) * cos(pitch.toRadians())
        cameraFront.normalize()
        cameraUp.assignCross(cameraRight(), cameraFront).normalize()
        view.lookAt(position, position + cameraFront, cameraUp)
    }

    fun resize(width: Int, height: Int) {
        projection.assignPerspective(0.1f, 100f, fov, width.toFloat() / height.toFloat())
    }

}