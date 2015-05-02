


package webgltest1.client;

import java.util.EmptyStackException;
import java.util.Stack;

import webgltest1.shared.FieldVerifier;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.gwtgl.binding.WebGLBuffer;
import com.googlecode.gwtgl.binding.WebGLProgram;
import com.googlecode.gwtgl.array.Float32Array;
import com.googlecode.gwtgl.binding.WebGLShader;
import com.googlecode.gwtgl.binding.WebGLUniformLocation;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SpinningTriangle implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

    private WebGLRenderingContext glContext;
    private WebGLProgram shaderProgram;
    private int vertexPositionAttribute;
    private int vertexColourAttribute;
    private WebGLBuffer vertexBuffer;
    private WebGLBuffer vertexColourBuffer;
    private WebGLBuffer vertexSquareBuffer;
    private WebGLBuffer vertexSquareColourBuffer;
    
    
    private WebGLUniformLocation pMatrixUniform;
    private WebGLUniformLocation mvMatrixUniform;
    
    private GLMatrixMat4 mvMatrix;
    private GLMatrixMat4 pMatrix;
    private Stack<GLMatrixMat4> mvMatrixStack;
    
    private GLShape t;
    private GLShape s;
    
    private Integer errorCount;
    
    private Timer loopTimer;
    private long lastTime, timeNow;
    
    
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
         start();
  }
	
	private void initGL() {
		errorCount = 0;
		final Canvas webGLCanvas = Canvas.createIfSupported();
		webGLCanvas.setCoordinateSpaceHeight(500);
		webGLCanvas.setCoordinateSpaceWidth(500);
		
		glContext = (WebGLRenderingContext) webGLCanvas.getContext("experimental-webgl");
		
		checkErrors();
		if(glContext == null) {
		        Window.alert("Sorry, Your Browser doesn't support WebGL!");
		}
		glContext.viewport(0, 0, 500, 500);
		checkErrors();
		RootPanel.get("gwtGL").add(webGLCanvas);

		glContext.clearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glContext.clearDepth(1.0f);
        glContext.enable(WebGLRenderingContext.DEPTH_TEST);
        glContext.depthFunc(WebGLRenderingContext.LEQUAL);
        

 	
	}
	
	private void start() {
		initGL();
		initMatrices();
        initShaders();
        initBuffers();
        lastTime = 0;

        loopTimer = new Timer() {
          @Override
          public void run() {
            loop();
          }
        }; 
        loopTimer.scheduleRepeating(10);
	}
	
	public void loop() {
		drawScene();
		animate();
	}
	
	private void animate() {
		  timeNow = System.currentTimeMillis();
		  if (lastTime != 0) {
		    long elapsed = timeNow - lastTime;
		    t.rotate(elapsed);
		  }
		  lastTime = timeNow; 
		}
	
	public void initMatrices() {
		mvMatrix = new GLMatrixMat4("mvMatrix");        
		pMatrix = new GLMatrixMat4("pMatrix");
	    
		mvMatrixStack = new Stack<GLMatrixMat4>(); 
	}
	
	private void mvPushMatrix() {	
		GLMatrixMat4 copy = new GLMatrixMat4("mvMatrix");
		copy.clone(mvMatrix);
		mvMatrixStack.push(copy);
	}

	private void mvPopMatrix() {
		if (mvMatrixStack.empty()) {
			throw new EmptyStackException();
		}
		mvMatrix = mvMatrixStack.pop();
	}
		
	public void initShaders() {
        WebGLShader fragmentShader = getShader(WebGLRenderingContext.FRAGMENT_SHADER, Shaders.INSTANCE.fragmentShader().getText());
        WebGLShader vertexShader = getShader(WebGLRenderingContext.VERTEX_SHADER, Shaders.INSTANCE.vertexShader().getText());

        shaderProgram = glContext.createProgram();
        checkErrors();
        glContext.attachShader(shaderProgram, vertexShader);
        checkErrors();
        glContext.attachShader(shaderProgram, fragmentShader);
        checkErrors();
        glContext.linkProgram(shaderProgram);
        checkErrors();
        if (!glContext.getProgramParameterb(shaderProgram, WebGLRenderingContext.LINK_STATUS)) {
                throw new RuntimeException("Could not initialise shaders");
        }

        glContext.useProgram(shaderProgram);
        vertexPositionAttribute = glContext.getAttribLocation(shaderProgram, "vertexPosition");
        glContext.enableVertexAttribArray(vertexPositionAttribute);
        checkErrors();
        vertexColourAttribute = glContext.getAttribLocation(shaderProgram, "aVertexColor");
        checkErrors();
        glContext.enableVertexAttribArray(vertexColourAttribute);
        checkErrors();
	}
	
	private WebGLShader getShader(int type, String source) {
        WebGLShader shader = glContext.createShader(type);

        glContext.shaderSource(shader, source);
        checkErrors();
        glContext.compileShader(shader);
        checkErrors();
        if (!glContext.getShaderParameterb(shader, WebGLRenderingContext.COMPILE_STATUS)) {
                throw new RuntimeException(glContext.getShaderInfoLog(shader));
        }
        checkErrors();
        return shader;
	}
	
	private void initBuffers() {
		
	    t = new GLShape();
	    t.initTriangle();
	    checkErrors();
	    s = new GLShape();
	    s.initSquare();
		vertexBuffer = glContext.createBuffer();
		checkErrors();
	    glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);
	    checkErrors();
	    glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER, Float32Array.create(t.getVertices()), WebGLRenderingContext.STATIC_DRAW);
	    checkErrors();
	    vertexColourBuffer = glContext.createBuffer();
	    checkErrors();
	    glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexColourBuffer);
	    checkErrors();
	    glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER ,Float32Array.create(t.getColours()),  WebGLRenderingContext.STATIC_DRAW);
	    checkErrors();
	    
	    vertexSquareBuffer = glContext.createBuffer();
		checkErrors();
	    glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexSquareBuffer);
	    checkErrors();
	    glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER, Float32Array.create(s.getVertices()), WebGLRenderingContext.STATIC_DRAW);
	    checkErrors();
	    vertexSquareColourBuffer = glContext.createBuffer();
	    checkErrors();
	    glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexSquareColourBuffer);
	    checkErrors();

	    glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER ,Float32Array.create(s.getColours()),  WebGLRenderingContext.STATIC_DRAW);
	    checkErrors();
	    
	    pMatrixUniform = glContext.getUniformLocation(shaderProgram, "uPMatrix");
	    checkErrors(); 

	    mvMatrixUniform = glContext.getUniformLocation(shaderProgram, "uMVMatrix");
	    checkErrors(); 
	}
	
	private void drawScene() {
	    glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT | WebGLRenderingContext.DEPTH_BUFFER_BIT);

	    GLMatrix.mat4Perspective(45, 500, 500, 0.1, 100.0, pMatrix);
	    GLMatrix.mat4Identity(mvMatrix);
	    mvPushMatrix();
	    GLMatrix.mat4Translate(mvMatrix, 0f, 0.0f, -7.0f);
	    GLMatrix.mat4Rotate(mvMatrix, t.getRotationRad(), 0f, 0f, 1f);
	    
	    glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);
	    glContext.vertexAttribPointer(vertexPositionAttribute, 3, WebGLRenderingContext.FLOAT, false, 0, 0);
	    glContext.enableVertexAttribArray(vertexPositionAttribute);

	    glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexColourBuffer);
	    checkErrors();
	    glContext.vertexAttribPointer(vertexColourAttribute, 4, WebGLRenderingContext.FLOAT, false, 0, 0);
	    checkErrors();
	    glContext.enableVertexAttribArray(vertexColourAttribute);
	
	    
	    glContext.uniformMatrix4fv(pMatrixUniform, false, pMatrix.getMat4());
	    glContext.uniformMatrix4fv(mvMatrixUniform, false, mvMatrix.getMat4());
	    
	    checkErrors();

	    // Draw the polygon
	    //glContext.drawElements(WebGLRenderingContext.TRIANGLES, 3, WebGLRenderingContext.UNSIGNED_SHORT, 0);
	    checkErrors();
	    glContext.drawArrays(WebGLRenderingContext.TRIANGLES, 0, 3);
	    
	    glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexSquareBuffer);
	    glContext.vertexAttribPointer(vertexPositionAttribute, 3, WebGLRenderingContext.FLOAT, false, 0, 0);
	    glContext.enableVertexAttribArray(vertexPositionAttribute);

	    glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexSquareColourBuffer);
	    checkErrors();
	    glContext.vertexAttribPointer(vertexColourAttribute, 4, WebGLRenderingContext.FLOAT, false, 0, 0);
	    checkErrors();
	    glContext.enableVertexAttribArray(vertexColourAttribute);
	    
	    
	    glContext.uniformMatrix4fv(pMatrixUniform, false, pMatrix.getMat4());
	    glContext.uniformMatrix4fv(mvMatrixUniform, false, mvMatrix.getMat4());
	    
	    checkErrors();

	    // Draw the polygon
	    //glContext.drawElements(WebGLRenderingContext.TRIANGLES, 3, WebGLRenderingContext.UNSIGNED_SHORT, 0);
	    checkErrors();
	    glContext.drawArrays(WebGLRenderingContext.TRIANGLE_STRIP, 0, 4);
	    checkErrors();
	    mvPopMatrix();
	    glContext.flush();
	    checkErrors();
	    

	}
	
    private void checkErrors() {
        int error = glContext.getError();
        String message = "WebGL Error: " + errorCount + " : : " + error;
        errorCount++;
        GWT.log(message, null);
    }	

	
}




