package webgltest1.client;

public class GLShape3D {
	private float[] vertices;
	private float[] colours;
	
	private int vertexBufferSize;
    private int vertexBufferItems;
    private double rotation;
    
    public GLShape3D(){
    	
    }
    
    public void initSquare() {
    	vertices = new float[] {
    			2.0f, 2.0f, -5.0f,
    			2.0f, 3.0f, -5.0f,
    			3.0f, 2.0f, -5.0f,
    			3.0f, 3.0f, -5.0f,
    			
    			2.0f, 2.0f, -5.0f,
    			2.0f, 3.0f, -5.0f,
    			2.0f, 2.0f, -4.0f,
    			2.0f, 3.0f, -4.0f,
    			
    			2.0f, 2.0f,  -5.0f,
    			3.0f, 2.0f,  -5.0f,
    			2.0f, 2.0f,  -4.0f,
    			3.0f, 2.0f,  -4.0f,
    			
    			2.0f, 2.0f, -4.0f,
    			2.0f, 3.0f, -4.0f,
    			3.0f, 2.0f, -4.0f,
    			3.0f, 3.0f, -4.0f,
    			
    			3.0f, 2.0f, -5.0f,
    			3.0f, 3.0f, -5.0f,
    			3.0f, 2.0f, -4.0f,
    			3.0f, 3.0f, -4.0f,
    			
    			2.0f, 3.0f,  -5.0f,
    			3.0f, 3.0f,  -5.0f,
    			2.0f, 3.0f,  -4.0f,
    			3.0f, 3.0f,  -4.0f,
    	};
    	
        colours = new float[] {
		        1.0f, 0.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 0.0f, 1.0f, 1.0f,
		        
		        1.0f, 0.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 0.0f, 1.0f, 1.0f,
		        
		        1.0f, 0.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 0.0f, 1.0f, 1.0f,
		        
		        1.0f, 0.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 0.0f, 1.0f, 1.0f,
		        
		        1.0f, 0.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 0.0f, 1.0f, 1.0f,
		        
		        1.0f, 0.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 0.0f, 1.0f, 1.0f
		    };
        
        vertexBufferSize = 3;
        vertexBufferItems =24;
    }
    public void initTriangle(){
   
        vertices = new float[]{
                // Front face
                0.0f,  1.0f,  0.0f,
               -1.0f, -1.0f,  1.0f,
                1.0f, -1.0f,  1.0f,
               // Right face
                0.0f,  1.0f,  0.0f,
                1.0f, -1.0f,  1.0f,
                1.0f, -1.0f, -1.0f,
               // Back face
                0.0f,  1.0f,  0.0f,
                1.0f, -1.0f, -1.0f,
               -1.0f, -1.0f, -1.0f,
               // Left face
                0.0f,  1.0f,  0.0f,
               -1.0f, -1.0f, -1.0f,
               -1.0f, -1.0f,  1.0f,
               
               // Back face
               -1.0f,  -1.0f,  1.0f,
               1.0f, -1.0f, -1.0f,
              -1.0f, -1.0f, -1.0f,
              // Left face
               1.0f,  -1.0f,  -1.0f,
              -1.0f, -1.0f, 1.0f,
              1.0f, -1.0f,  1.0f,
        };
        
        colours = new float[] {
		        1.0f, 0.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 0.0f, 1.0f, 1.0f,
		        1.0f, 0.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 0.0f, 1.0f, 1.0f,
		        1.0f, 0.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 0.0f, 1.0f, 1.0f,
		        1.0f, 0.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 0.0f, 1.0f, 1.0f,
		        1.0f, 0.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 0.0f, 1.0f, 1.0f,
		        1.0f, 0.0f, 0.0f, 1.0f,
		        0.0f, 1.0f, 0.0f, 1.0f,
		        0.0f, 0.0f, 1.0f, 1.0f
		    };
        
        vertexBufferSize = 3;
        vertexBufferItems = 12;
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
