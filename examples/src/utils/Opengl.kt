package jopengl.utils

import com.jogamp.opengl.GL
import com.jogamp.opengl.GL4
import java.nio.FloatBuffer
import java.nio.IntBuffer

fun GL4.glGenBuffer(): Int {
    val intBuffer: IntBuffer = BufferUtils.createIntBuffer(1)
    glGenBuffers(1, intBuffer)
    return intBuffer[0]
}

fun GL4.glGenVertexArray(): Int {
    val intBuffer: IntBuffer = BufferUtils.createIntBuffer(1)
    glGenVertexArrays(1, intBuffer)
    return intBuffer[0]
}

fun GL4.loadBuffer(array: FloatArray) {
    loadBuffer(BufferUtils.createFloatBuffer(array))
}

fun GL4.loadBuffer(buffer: FloatBuffer) {
    val size: Int = buffer.capacity() * Float.SIZE_BYTES
    glBufferData(GL.GL_ARRAY_BUFFER, size.toLong(), buffer, GL.GL_STATIC_DRAW)
}
