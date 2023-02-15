package utils

import com.jogamp.opengl.GL.GL_ARRAY_BUFFER
import com.jogamp.opengl.GL.GL_STATIC_DRAW
import com.jogamp.opengl.GL4
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer

object BufferUtils {

    fun createByteBuffer(size: Int): ByteBuffer {
        return createByteBuffer(ByteArray(size))
    }

    fun createByteBuffer(array: ByteArray): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(array.size).order(ByteOrder.nativeOrder())
        byteBuffer.put(array).flip()
        return byteBuffer
    }

    fun createFloatBuffer(size: Int): FloatBuffer {
        return createFloatBuffer(FloatArray(size))
    }

    fun createFloatBuffer(array: FloatArray): FloatBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(array.size shl 2).order(ByteOrder.nativeOrder()).asFloatBuffer()
        byteBuffer.put(array).flip()
        return byteBuffer
    }

    fun createIntBuffer(size: Int): IntBuffer {
        return createIntBuffer(IntArray(size))
    }

    fun createIntBuffer(array: IntArray): IntBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(array.size shl 2).order(ByteOrder.nativeOrder()).asIntBuffer()
        byteBuffer.put(array).flip()
        return byteBuffer
    }

}

fun GL4.generateBuffer(): Int {
    val intBuffer: IntBuffer = BufferUtils.createIntBuffer(1)
    glGenBuffers(1, intBuffer)
    return intBuffer[0]
}

fun GL4.loadBuffer(buffer: FloatBuffer) {
    val size: Int = buffer.capacity() * Float.SIZE_BYTES
    glBufferData(GL_ARRAY_BUFFER, size.toLong(), buffer, GL_STATIC_DRAW)
}