package jopengl

import com.jogamp.opengl.GL.*
import com.jogamp.opengl.GL4
import jopengl.utils.*

fun stencil2() = runApplication(object : Application {
    var stencil2Program: Int = 0
    var borderProgram: Int = 0
    var textureId: Int = 0
    val camera: Camera = Camera()

    override fun reshape(gl: GL4, x: Int, y: Int, width: Int, height: Int) {
        gl.glViewport(x, y, width, height)
        camera.resize(width, height)
    }

    override fun init(gl: GL4) {
        gl.glEnable(GL_DEPTH_TEST)
        gl.glDepthFunc(GL_LESS)
        gl.glEnable(GL_STENCIL_TEST)
        gl.glStencilFunc(GL_NOTEQUAL, 1, 0xFF)
        gl.glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE)

        stencil2Program = gl.createShaderProgram("shaders/stencil2")
        borderProgram = gl.createShaderProgram("shaders/border")

        val cubeVertices: FloatArray = floatArrayOf(
            // positions         // uv
            -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,
            0.5f, -0.5f, -0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
            -0.5f, 0.5f, 0.5f, 0.0f, 1.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            -0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            -0.5f, 0.5f, 0.5f, 0.0f, 0.0f,
            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
        )
        val planeVertices: FloatArray = floatArrayOf(
            // positions         // uv
            5.0f, -0.5f, 5.0f, 2.0f, 0.0f,
            -5.0f, -0.5f, 5.0f, 0.0f, 0.0f,
            -5.0f, -0.5f, -5.0f, 0.0f, 2.0f,
            5.0f, -0.5f, 5.0f, 2.0f, 0.0f,
            -5.0f, -0.5f, -5.0f, 0.0f, 2.0f,
            5.0f, -0.5f, -5.0f, 2.0f, 2.0f
        )

        // cube VAO
        val cubeVAO: Int = gl.glGenVertexArray()
        val cubeVBO: Int = gl.glGenBuffer()
        gl.glBindVertexArray(cubeVAO)
        gl.glBindBuffer(GL_ARRAY_BUFFER, cubeVBO)
        gl.loadBuffer(cubeVertices)
        gl.glEnableVertexAttribArray(0);
        gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * Float.SIZE_BYTES, 0L)
        gl.glEnableVertexAttribArray(1);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * Float.SIZE_BYTES, 3L * Float.SIZE_BYTES)
        gl.glBindVertexArray(0)

        // plane VAO
        val planeVAO: Int = gl.glGenVertexArray()
        val planeVBO: Int = gl.glGenBuffer()
        gl.glBindVertexArray(planeVAO)
        gl.glBindBuffer(GL_ARRAY_BUFFER, planeVBO)
        gl.loadBuffer(planeVertices)
        gl.glEnableVertexAttribArray(0)
        gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * Float.SIZE_BYTES, 0L)
        gl.glEnableVertexAttribArray(1)
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * Float.SIZE_BYTES, 3L * Float.SIZE_BYTES)
        gl.glBindVertexArray(0)

        textureId = gl.loadTexture("images/metal.png")
    }

    override fun display(gl: GL4) {
        gl.glClearColor(0.1f, 0.1f, 0.1f, 1.0f)
        gl.glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT or GL_STENCIL_BUFFER_BIT)

        gl.glUseProgram(stencil2Program)
    }

})
