package webgltest1.client;

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
import com.google.gwt.user.client.Window;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Com_adams_test implements EntryPoint {
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
    private WebGLUniformLocation uniformLocation1;
    private WebGLUniformLocation uniformLocation2; 
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
        final Canvas webGLCanvas = Canvas.createIfSupported();
        webGLCanvas.setCoordinateSpaceHeight(500);
        webGLCanvas.setCoordinateSpaceWidth(500);
        glContext = (WebGLRenderingContext) webGLCanvas.getContext("experimental-webgl");
        checkErrors("first");
        if(glContext == null) {
                Window.alert("Sorry, Your Browser doesn't support WebGL!");
        }
        glContext.viewport(0, 0, 500, 500);
        checkErrors("second");
        RootPanel.get("gwtGL").add(webGLCanvas);
        start();
  }
	
	private void start() {
        initShaders();
        glContext.clearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glContext.clearDepth(1.0f);
        glContext.enable(WebGLRenderingContext.DEPTH_TEST);
        glContext.depthFunc(WebGLRenderingContext.LEQUAL);
        initBuffers();

        drawScene();
	}
	
	public void initShaders() {
        WebGLShader fragmentShader = getShader(WebGLRenderingContext.FRAGMENT_SHADER, Shaders.INSTANCE.fragmentShader().getText());
        WebGLShader vertexShader = getShader(WebGLRenderingContext.VERTEX_SHADER, Shaders.INSTANCE.vertexShader().getText());

        shaderProgram = glContext.createProgram();
        checkErrors("third");
        glContext.attachShader(shaderProgram, vertexShader);
        glContext.attachShader(shaderProgram, fragmentShader);
        glContext.linkProgram(shaderProgram);
        checkErrors("forth");
        if (!glContext.getProgramParameterb(shaderProgram, WebGLRenderingContext.LINK_STATUS)) {
                throw new RuntimeException("Could not initialise shaders");
        }

        glContext.useProgram(shaderProgram);

        vertexPositionAttribute = glContext.getAttribLocation(shaderProgram, "vertexPosition");
        glContext.enableVertexAttribArray(vertexPositionAttribute);
        checkErrors("five");
        vertexColourAttribute = glContext.getAttribLocation(shaderProgram, "aVertexColor");
        checkErrors("five11");
        glContext.enableVertexAttribArray(vertexColourAttribute);
        checkErrors("six");
	}
	
	private WebGLShader getShader(int type, String source) {
        WebGLShader shader = glContext.createShader(type);

        glContext.shaderSource(shader, source);
        glContext.compileShader(shader);
        checkErrors("eight");
        if (!glContext.getShaderParameterb(shader, WebGLRenderingContext.COMPILE_STATUS)) {
                throw new RuntimeException(glContext.getShaderInfoLog(shader));
        }
        checkErrors("nine");
        return shader;
}
	
	private void initBuffers() {
		
		vertexBuffer = glContext.createBuffer();
	    glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);
	    float[] vertices = new float[]{
	    		0.0f,  1.0f,  -5.0f, // first vertex
	    		-1.0f, -1.0f,  -5.0f, // second vertex
	    		1.0f, -1.0f,  -5.0f  // third vertex
	    };
	    checkErrors("ten-1");
	    glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER, Float32Array.create(vertices), WebGLRenderingContext.STATIC_DRAW);
	    checkErrors("ten");
	    vertexColourBuffer = glContext.createBuffer();
	    checkErrors("elevent");
	    glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexColourBuffer);
	    checkErrors("12");
	    float[] colors = new float[] {
	        1.0f, 0.0f, 0.0f, 1.0f,
	        0.0f, 1.0f, 0.0f, 1.0f,
	        0.0f, 0.0f, 1.0f, 1.0f
	    };
	    glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER ,Float32Array.create(colors),  WebGLRenderingContext.STATIC_DRAW);

	    checkErrors("n`qweine");
	    }
	
	private void drawScene() {
	    glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT | WebGLRenderingContext.DEPTH_BUFFER_BIT);

	    glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);
	    glContext.vertexAttribPointer(vertexPositionAttribute, 3, WebGLRenderingContext.FLOAT, false, 0, 0);
	    glContext.enableVertexAttribArray(vertexPositionAttribute);

	    glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexColourBuffer);
	    checkErrors("final123");
	    glContext.vertexAttribPointer(vertexColourAttribute, 4, WebGLRenderingContext.FLOAT, false, 0, 0);
	    checkErrors("final23");
	    glContext.enableVertexAttribArray(vertexColourAttribute);
	    checkErrors("final31");
	    WebGLUniformLocation uniformLocation = glContext.getUniformLocation(shaderProgram, "perspectiveMatrix");
	    checkErrors("final2"); 
	    float[] perspectiveMatrix = createPerspectiveMatrix(45, 1, 0.1f, 1000);
	    glContext.uniformMatrix4fv(uniformLocation, false, perspectiveMatrix);
	    checkErrors("final3");

	    // Draw the polygon
	    //glContext.drawElements(WebGLRenderingContext.TRIANGLES, 3, WebGLRenderingContext.UNSIGNED_SHORT, 0);
	    checkErrors("final1");
	    glContext.drawArrays(WebGLRenderingContext.TRIANGLES, 0, 3);
	    checkErrors("finalxx");
	    glContext.flush();
	    checkErrors("final");

	}
	
    private void checkErrors(String x) {
        int error = glContext.getError();
        String message = "WebGL Error: " + x + " :" + error;
        GWT.log(message, null);
    }	
	
	private float[] createPerspectiveMatrix(int fieldOfViewVertical, float aspectRatio, float minimumClearance, float maximumClearance) {
        float top    = minimumClearance * (float)Math.tan(fieldOfViewVertical * Math.PI / 360.0);
        float bottom = -top;
        float left   = bottom * aspectRatio;
        float right  = top * aspectRatio;

        float X = 2*minimumClearance/(right-left);
        float Y = 2*minimumClearance/(top-bottom);
        float A = (right+left)/(right-left);
        float B = (top+bottom)/(top-bottom);
        float C = -(maximumClearance+minimumClearance)/(maximumClearance-minimumClearance);
        float D = -2*maximumClearance*minimumClearance/(maximumClearance-minimumClearance);

        return new float[]{     X, 0.0f, A, 0.0f,
                                                0.0f, Y, B, 0.0f,
                                                0.0f, 0.0f, C, -1.0f,
                                                0.0f, 0.0f, D, 0.0f};
	}

}
