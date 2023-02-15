package jopengl.utils

import com.jogamp.opengl.GL
import com.jogamp.opengl.GL4
import de.matthiasmann.twl.utils.PNGDecoder
import utils.BufferUtils
import utils.BufferUtils.createIntBuffer
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.IntBuffer

fun GL4.loadImage(filePath: String): Int {
    val intBuffer: IntBuffer = createIntBuffer(1)
    glGenTextures(1, intBuffer)
    val textureId: Int = intBuffer[0]
    glBindTexture(GL.GL_TEXTURE_2D, textureId)
    glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR)
    glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR)
    glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP_TO_EDGE)
    glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP_TO_EDGE)
    val inputStream: InputStream? = ClassLoader.getSystemResourceAsStream(filePath)
    if (inputStream == null) {
        error("image not loaded")
    }
    val pngDecoder: PNGDecoder = PNGDecoder(inputStream)
    val width: Int = pngDecoder.width
    val height: Int = pngDecoder.height
    val alignment: Int = 1
    val stride: Int = 4 * alignment * width
    val buffer: ByteBuffer = BufferUtils.createByteBuffer(stride * height)
    pngDecoder.decodeFlipped(buffer, stride, PNGDecoder.Format.RGBA) // IMPORTANT flip
    buffer.flip()
    glPixelStorei(GL.GL_UNPACK_ALIGNMENT, alignment)
    glPixelStorei(GL.GL_PACK_ALIGNMENT, 1)
    glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, width, height, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, buffer)
    inputStream.close()
    glGenerateMipmap(GL.GL_TEXTURE_2D)
    glBindTexture(GL.GL_TEXTURE_2D, 0)
    return textureId
}