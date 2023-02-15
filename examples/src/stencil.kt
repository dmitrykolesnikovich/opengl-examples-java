package jopengl

import com.jogamp.opengl.GL.*
import com.jogamp.opengl.GL4
import jopengl.utils.Application
import jopengl.utils.createShaderProgram
import jopengl.utils.loadTexture
import jopengl.utils.runApplication
import utils.BufferUtils.createFloatBuffer
import utils.generateBuffer
import utils.loadBuffer
import java.nio.FloatBuffer

fun stencil() = runApplication(object : Application {

    var programId: Int = 0
    var textureId: Int = 0

    var bufferId1: Int = 0
    val buffer1: FloatBuffer = createFloatBuffer(100)
    var isDirtyBuffer1: Boolean = true

    var bufferId2: Int = 0
    val buffer2: FloatBuffer = createFloatBuffer(100)
    var isDirtyBuffer2: Boolean = true

    override fun reshape(gl: GL4, x: Int, y: Int, width: Int, height: Int) {
        gl.glViewport(x, y, width, height)
    }

    override fun init(gl: GL4) {
        // blend
        gl.glEnable(GL_BLEND)
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        programId = gl.createShaderProgram("shaders/stencil")
        textureId = gl.loadTexture("images/awesomeface.png")

        bufferId1 = gl.generateBuffer()
        buffer1.put(
            floatArrayOf(
                // positions        // uv
                0.5f, 0.5f, 0.0f, 1.0f, 1.0f, // top right
                0.5f, -0.5f, 0.0f, 1.0f, 0.0f, // bottom right
                -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, // bottom left
                0.5f, 0.5f, 0.0f, 1.0f, 1.0f, // top right
                -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, // bottom left
                -0.5f, 0.5f, 0.0f, 0.0f, 1.0f  // top left
            )
        )
        buffer1.rewind()

        bufferId2 = gl.generateBuffer()
        buffer2.put(
            floatArrayOf(
                // positions            // uv
                0.75f, 0.5f, -0.5f, 1.0f, 1.0f, // top right
                0.75f, -0.5f, -0.5f, 1.0f, 0.0f, // bottom right
                -0.25f, -0.5f, -0.5f, 0.0f, 0.0f, // bottom left
                0.75f, 0.5f, -0.5f, 1.0f, 1.0f, // top right
                -0.25f, -0.5f, -0.5f, 0.0f, 0.0f, // bottom left
                -0.25f, 0.5f, -0.5f, 0.0f, 1.0f  // top left
            )
        )
        buffer2.rewind()

        // stencil
        gl.glClearColor(0.2f, 0.3f, 0.3f, 1.0f)
        gl.glEnable(GL_STENCIL_TEST)
        gl.glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE)
    }

    override fun display(gl: GL4) {
        gl.glClear(GL_COLOR_BUFFER_BIT or GL_STENCIL_BUFFER_BIT)
        gl.glUseProgram(programId)

        // bufferId1
        gl.glStencilFunc(GL_ALWAYS, 1, 0xFF)
        gl.glStencilMask(0xFF)

        gl.glBindBuffer(GL_ARRAY_BUFFER, bufferId1)
        gl.glEnableVertexAttribArray(0)
        gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * 4, 0)
        gl.glEnableVertexAttribArray(1)
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * 4, 3 * 4)
        if (isDirtyBuffer1) {
            gl.loadBuffer(buffer1)
            isDirtyBuffer1 = false
        }
        gl.glActiveTexture(GL_TEXTURE0)
        gl.glBindTexture(GL_TEXTURE_2D, textureId)
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
        gl.glUniform1i(gl.glGetUniformLocation(programId, "texture1"), 0)
        gl.glDrawArrays(GL_TRIANGLES, 0, 6)

        // bufferId2
        gl.glStencilFunc(GL_NOTEQUAL, 1, 0xFF)
        gl.glStencilMask(0x00)

        gl.glBindBuffer(GL_ARRAY_BUFFER, bufferId2)
        gl.glEnableVertexAttribArray(0)
        gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * 4, 0)
        gl.glEnableVertexAttribArray(1)
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * 4, 3 * 4)
        if (isDirtyBuffer2) {
            gl.loadBuffer(buffer2)
            isDirtyBuffer2 = false
        }
        gl.glActiveTexture(GL_TEXTURE0)
        gl.glBindTexture(GL_TEXTURE_2D, textureId)
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
        gl.glUniform1i(gl.glGetUniformLocation(programId, "texture1"), 0)
        gl.glDrawArrays(GL_TRIANGLES, 0, 6)
    }

})
