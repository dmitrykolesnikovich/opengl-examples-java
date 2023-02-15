package jopengl.utils

import com.jogamp.opengl.GL4

fun GL4.createShaderProgram(shader: String): Int {
    val vertexShaderFilePath: String = "$shader.vertex.shader"
    val fragmentShaderFilePath: String = "$shader.fragment.shader"
    val vShaderSource: String = ClassLoader.getSystemResource(vertexShaderFilePath).readText()
    val fShaderSource: String = ClassLoader.getSystemResource(fragmentShaderFilePath).readText()
    val vShaderObj: Int = glCreateShader(GL4.GL_VERTEX_SHADER)
    glShaderSource(vShaderObj, 1, arrayOf(vShaderSource), null, 0)
    glCompileShader(vShaderObj)
    val fShaderObj: Int = glCreateShader(GL4.GL_FRAGMENT_SHADER)
    glShaderSource(fShaderObj, 1, arrayOf(fShaderSource), null, 0)
    glCompileShader(fShaderObj)
    val shaderProgram: Int = glCreateProgram()
    glAttachShader(shaderProgram, vShaderObj)
    glAttachShader(shaderProgram, fShaderObj)
    glLinkProgram(shaderProgram)
    glDeleteShader(vShaderObj)
    glDeleteShader(fShaderObj)
    return shaderProgram
}
