


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
public class Spinning3D implements EntryPoint {
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
    private WebGLBuffer cubeVertexIndexBuffer;
    
    private WebGLUniformLocation pMatrixUniform;
    private WebGLUniformLocation mvMatrixUniform;
    
    private GLMatrixMat4 mvMatrix;
    private GLMatrixMat4 pMatrix;
    private Stack<GLMatrixMat4> mvMatrixStack;
    
    private GLShape3D t;
    private GLShape3D s;
    
    private float xrot, yrot, zrot;
    
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
        
        
        FocusPanel fp = new FocusPanel();
        RootPanel.get("gwtGL").add(fp);
        fp.setFocus(true);
        
        fp.addKeyUpHandler(new KeyUpHandler() {
        	
        	public void onKeyUp(KeyUpEvent event) {
        		
        		if(event.getNativeKeyCode() == KeyCodes.KEY_X) {
        			xrot += 0.1;
                  }
        		
        		if(event.getNativeKeyCode() == KeyCodes.KEY_Y) {
        			yrot += 0.1;
                  }
        		
        		if(event.getNativeKeyCode() == KeyCodes.KEY_Z) {
        			zrot += 0.1;
                  }

        		
        		if(event.getNativeKeyCode() == KeyCodes.KEY_A) {
        			xrot -= 0.1;
                  }
        		
        		if(event.getNativeKeyCode() == KeyCodes.KEY_B) {
        			yrot -= 0.1;
                  }
        		
        		if(event.getNativeKeyCode() == KeyCodes.KEY_C) {
        			zrot -= 0.1;
                  }
			}
        	
        });
	}
	
	private void start() {
		initGL();
		initMatrices();
        initShaders();
        initBuffers();
        lastTime = 0;
        xrot = 0.1f;
        yrot = 0.1f;
        zrot = 0.1f;

        loopTimer = new Timer() {
          @Override
          public void run() {
            loop();
          }
        }; 
        loopTimer.scheduleRepeating(5);
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
		
	    t = new GLShape3D();
	    t.initTriangle();
	    checkErrors();
	    s = new GLShape3D();
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
	    
	    cubeVertexIndexBuffer = glContext.createBuffer();
	    glContext.bindBuffer(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER, cubeVertexIndexBuffer);
	    float cubeVertexIndices[] = new float [] {
	      0, 1, 2,      0, 2, 3,    // Front face
	      4, 5, 6,      4, 6, 7,    // Back face
	      8, 9, 10,     8, 10, 11,  // Top face
	      12, 13, 14,   12, 14, 15, // Bottom face
	      16, 17, 18,   16, 18, 19, // Right face
	      20, 21, 22,   20, 22, 23  // Left face
	    };
	    glContext.bufferData(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER, Float32Array.create(cubeVertexIndices), WebGLRenderingContext.STATIC_DRAW);
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
	    
	    String logmsg = "xrot: " + xrot + " yrot: " + yrot + "zrot: " + zrot;
	    GWT.log(logmsg, null); 
	    GLMatrix.mat4Rotate(mvMatrix, t.getRotationRad(), xrot, yrot, zrot);
	    
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
	    glContext.drawArrays(WebGLRenderingContext.TRIANGLES, 0, 18);
	    
	    glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexSquareBuffer);
	    glContext.vertexAttribPointer(vertexPositionAttribute, 3, WebGLRenderingContext.FLOAT, false, 0, 0);
	    glContext.enableVertexAttribArray(vertexPositionAttribute);

	    glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexSquareColourBuffer);
	    checkErrors();
	    glContext.vertexAttribPointer(vertexColourAttribute, 4, WebGLRenderingContext.FLOAT, false, 0, 0);
	    checkErrors();
	    glContext.enableVertexAttribArray(vertexColourAttribute);
	    
	    glContext.bindBuffer(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER, cubeVertexIndexBuffer);
		 
	      
	    
	    glContext.uniformMatrix4fv(pMatrixUniform, false, pMatrix.getMat4());
	    glContext.uniformMatrix4fv(mvMatrixUniform, false, mvMatrix.getMat4());
	    
	    checkErrors();

	    //glContext.bindBuffer(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER, cubeVertexIndexBuffer);
	    checkErrors();
	    // Draw the polygon
	    glContext.drawElements(WebGLRenderingContext.TRIANGLES, 24, WebGLRenderingContext.UNSIGNED_SHORT, 0);
	    checkErrors();
	    
	    

	    //glContext.drawElements(WebGLRenderingContext.TRIANGLES, 36,0, 0);
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
