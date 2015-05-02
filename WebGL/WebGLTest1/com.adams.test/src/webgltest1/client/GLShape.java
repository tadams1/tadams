package webgltest1.client;

public class GLShape {
	private float[] vertices;
	private float[] colours;
	
	private int vertexBufferSize;
    private int vertexBufferItems;
    private double rotation;
    
    public GLShape(){
    	
    }
    
    public void initSquare() {
    	vertices = new float[] {
    			2.0f, 2.0f, -5.0f,
    			2.0f, 3.0f, -5.0f,
    			3.0f, 2.0f, -5.0f,
    			3.0f, 3.0f, -5.0f
    	};
    	
        colours = new float[] {
		        1.0f, 0.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 0.0f, 1.0f, 1.0f
		    };
        
        vertexBufferSize = 3;
        vertexBufferItems = 4;
    }
    public void initTriangle(){
   
        vertices = new float[]{
             0.0f,  1.0f,  -5.0f, // first vertex
            -1.0f, -1.0f,  -5.0f, // second vertex
             1.0f, -1.0f,  -5.0f  // third vertex
        };
        
        colours = new float[] {
		        1.0f, 0.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 0.0f, 1.0f, 1.0f
		    };
        
        vertexBufferSize = 3;
        vertexBufferItems = 3;
    }

	public float[] getVertices() {
		return vertices;
	}
	
	public float[] getColours() {
		return colours;
	}

	public int getVertexBufferSize() {
		return vertexBufferSize;
	}

	public int getVertexBufferItems() {
		return vertexBufferItems;
	}
	
	public double getRotationDegree() {
		  return rotation;
		}

		public double getRotationRad() {
		  return rotation * Math.PI / 180;
		}

		public void rotate(long elapsed) {
		  rotation += (90 * elapsed) / 1000.0;
		}

}
