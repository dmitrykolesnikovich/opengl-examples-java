package jopengl.utils

import com.jogamp.opengl.GL4
import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLEventListener
import com.jogamp.opengl.awt.GLJPanel
import javax.swing.JFrame

interface Application {
    fun init(gl: GL4) {}
    fun dispose(gl: GL4) {}
    fun display(gl: GL4) {}
    fun reshape(gl: GL4, x: Int, y: Int, width: Int, height: Int) {}
}

fun runApplication(app: Application) {
    lateinit var gl: GL4

    val frame: JFrame = JFrame()
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.title = "OpenGL Examples in Java"
    frame.setSize(800, 800)
    val panel: GLJPanel = GLJPanel()
    panel.addGLEventListener(object : GLEventListener {

        override fun init(drawable: GLAutoDrawable) {
            gl = drawable.gl as GL4
            app.init(gl)
        }

        override fun dispose(drawable: GLAutoDrawable) {
            app.dispose(gl)
        }

        override fun display(drawable: GLAutoDrawable) {
            app.display(gl)
        }

        override fun reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
            app.reshape(gl, x, y, width, height)
        }

    })
    frame.add(panel)
    frame.isVisible = true
}
