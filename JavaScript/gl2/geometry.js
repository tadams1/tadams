var Cube=function() {
	var size;
	var vData;
	var indices;
	var bBuild;

	Cube.prototype.init = function(x,y,z,s) {
		this.size = s;
		this.x = x;
		this.y = y;
		this.z = z;
		this.bBuild = false;
	}

	Cube.prototype.getVerticesCount = function () {
		 return 24;
	}

	Cube.prototype.getIndicesCount = function() {
    	return 36;
    }

	Cube.prototype.getVectices = function() {
    if(this.bBuild) {
      return this.vData;
    }
    vert = new Float32Array(72);
  	normals = new Float32Array(72);

    var vertinit = [
      // Front face
      -1.0*(this.size+this.x), -1.0*(this.size+this.y),  1.0*(this.size+this.z),
       1.0*(this.size+this.x), -1.0*(this.size+this.y),  1.0*(this.size+this.z),
       1.0*(this.size+this.x),  1.0*(this.size+this.y),  1.0*(this.size+this.z),
      -1.0*(this.size+this.x),  1.0*(this.size+this.y),  1.0*(this.size+this.z),

      // Back face
      -1.0*(this.size+this.x), -1.0*(this.size+this.y), -1.0*(this.size+this.z),
      -1.0*(this.size+this.x),  1.0*(this.size+this.y), -1.0*(this.size+this.z),
       1.0*(this.size+this.x),  1.0*(this.size+this.y), -1.0*(this.size+this.z),
       1.0*(this.size+this.x), -1.0*(this.size+this.y), -1.0*(this.size+this.z),

      // Top face
      -1.0*(this.size+this.x),  1.0*(this.size+this.y), -1.0*(this.size+this.z),
      -1.0*(this.size+this.x),  1.0*(this.size+this.y),  1.0*(this.size+this.z),
       1.0*(this.size+this.x),  1.0*(this.size+this.y),  1.0*(this.size+this.z),
       1.0*(this.size+this.x),  1.0*(this.size+this.y), -1.0*(this.size+this.z),

      // Bottom face
      -1.0*(this.size+this.x), -1.0*(this.size+this.y), -1.0*(this.size+this.z),
       1.0*(this.size+this.x), -1.0*(this.size+this.y), -1.0*(this.size+this.z),
       1.0*(this.size+this.x), -1.0*(this.size+this.y),  1.0*(this.size+this.z),
      -1.0*(this.size+this.x), -1.0*(this.size+this.y),  1.0*(this.size+this.z),

      // Right face
       1.0*(this.size+this.x), -1.0*(this.size+this.y), -1.0*(this.size+this.z),
       1.0*(this.size+this.x),  1.0*(this.size+this.y), -1.0*(this.size+this.z),
       1.0*(this.size+this.x),  1.0*(this.size+this.y),  1.0*(this.size+this.z),
       1.0*(this.size+this.x), -1.0*(this.size+this.y),  1.0*(this.size+this.z),

      // Left face
      -1.0*(this.size+this.x), -1.0*(this.size+this.y), -1.0*(this.size+this.z),
      -1.0*(this.size+this.x), -1.0*(this.size+this.y),  1.0*(this.size+this.z),
      -1.0*(this.size+this.x),  1.0*(this.size+this.y),  1.0*(this.size+this.z),
      -1.0*(this.size+this.x),  1.0*(this.size+this.y), -1.0*(this.size+this.z),
    ];

    var normalsinit = [
      // Front face
       0.0,  0.0,  1.0,
       0.0,  0.0,  1.0,
       0.0,  0.0,  1.0,
       0.0,  0.0,  1.0,

      // Back face
       0.0,  0.0, -1.0,
       0.0,  0.0, -1.0,
       0.0,  0.0, -1.0,
       0.0,  0.0, -1.0,

      // Top face
       0.0,  1.0,  0.0,
       0.0,  1.0,  0.0,
       0.0,  1.0,  0.0,
       0.0,  1.0,  0.0,

      // Bottom face
       0.0, -1.0,  0.0,
       0.0, -1.0,  0.0,
       0.0, -1.0,  0.0,
       0.0, -1.0,  0.0,

      // Right face
       1.0,  0.0,  0.0,
       1.0,  0.0,  0.0,
       1.0,  0.0,  0.0,
       1.0,  0.0,  0.0,

      // Left face
      -1.0,  0.0,  0.0,
      -1.0,  0.0,  0.0,
      -1.0,  0.0,  0.0,
      -1.0,  0.0,  0.0,
    ];


    for(var x = 0; x< 72;x++) {
    		vert[x] = vertinit[x];
    		normals[x] = normalsinit[x];
    }

    this.bBuild=true;
    this.vData = {
        vertices: vert,
        normals: normals
    } 
    return this.vData;
   		
	}

	Cube.prototype.getIndices = function() {
	var idx = new Uint16Array(36);
     var cubeVertexIndices = [
      0, 1, 2,      0, 2, 3,    // Front face
      4, 5, 6,      4, 6, 7,    // Back face
      8, 9, 10,     8, 10, 11,  // Top face
      12, 13, 14,   12, 14, 15, // Bottom face
      16, 17, 18,   16, 18, 19, // Right face
      20, 21, 22,   20, 22, 23  // Left face
    ];
    for(var x= 0; x<36; x++) {
    	idx[x] = cubeVertexIndices[x];
    }

    return idx;
  }
}

	  