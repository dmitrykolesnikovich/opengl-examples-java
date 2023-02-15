package jopengl.utils

import kotlin.math.sqrt

typealias Vector = Vector3

data class Vector3(var x: Float = 0f, var y: Float = 0f, var z: Float = 0f) {

    fun assign(original: Vector3): Vector3 {
        this.x = original.x
        this.y = original.y
        this.z = original.z
        return this
    }

    fun assign(x: Float, y: Float, z: Float): Vector3 {
        this.x = x
        this.y = y
        this.z = z
        return this
    }

    fun dot(vector: Vector): Float = x * vector.x + y * vector.y + z * vector.z

    fun len2(): Float = x * x + y * y + z * z

    fun len(): Float = sqrt(x * x + y * y + z * z)

    operator fun timesAssign(scalar: Float) {
        assign(x * scalar, y * scalar, z * scalar)
    }

    operator fun minusAssign(vector: Vector) {
        assign(x - vector.x, y - vector.y, z - vector.z)
    }

    fun normalize(): Vector3 {
        val len2: Float = len2()
        if (len2 != 0f && len2 != 1f) {
            this *= 1f / sqrt(len2)
        }
        return this
    }

    fun assignCross(vector: Vector3): Vector3 =
        assignCross(this, vector)

    fun assignCross(p1: Vector3, p2: Vector): Vector3 {
        return assign(p1.y * p2.z - p1.z * p2.y, p1.z * p2.x - p1.x * p2.z, p1.x * p2.y - p1.y * p2.x)
    }

    fun distanceSquared(vector: Vector3): Float {
        val dx: Float = x - vector.x
        val dy: Float = y - vector.y
        val dz: Float = z - vector.z
        return dx * dx + dy * dy + dz * dz
    }

    companion object {
        fun dot(x1: Float, y1: Float, z1: Float, x2: Float, y2: Float, z2: Float): Float = x1 * x2 + y1 * y2 + z1 * z2
        fun len(x: Float, y: Float, z: Float): Float = sqrt(x * x + y * y + z * z)
        fun cross(v1: Vector3, v2: Vector3): Vector3 =
            Vector3(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x)
    }

    // >> just for try todo delete this
    operator fun plus(vector: Vector): Vector = Vector(x + vector.x, y + vector.y, z + vector.z)
    operator fun times(float: Float): Vector = Vector(x * float, y * float, z * float)
    // <<

}

fun String.toVector(): Vector = Vector().assign(this)

fun Vector.assign(value: String): Vector = apply {
    val (x, y, z) = value.split(",").map { it.trim().toFloat() }
    this.x = x
    this.y = y
    this.z = z
}

fun Vector.array(): List<Float> = listOf(x, y, z)