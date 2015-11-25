var TriGenerator = function() {
	var width;
	var height;
  var step;

	TriGenerator.prototype.init = function(w,h,s) {
		this.width = w;
		this.height = h;
    this.step =s;
	}

	TriGenerator.prototype.getVerticesCount = function () {
		 return (this.width+1) * (this.height+1)*3;
	}

	 TriGenerator.prototype.getIndicesCount = function() {
      var numIndPerRow = this.width * 2 + 2;
      var numIndDegensReq = (this.height - 1) * 2;
      return numIndPerRow * this.height + numIndDegensReq;
    	//return (this.width*2+2) * this.height + ((this.height-1)*2);
  	}

	TriGenerator.prototype.getVectices = function() {
    var w = this.width+1;
    var h = this.height+1;
		var total_vertices = w * h *3;
    	vert = new Float32Array(total_vertices);
    	normals = new Float32Array(total_vertices);

    	for(var y = 0; y < h; y++) {
      		var base = y * w;
      		for(var x = 0; x < w; x++) {
        		var index = (base + x)*3;
        		vert[index]=x/this.step;
        		vert[index+1]=y/this.step;
        		vert[index+2] = 0;
            normals[index]=0;
            normals[index+1]=0;
            normals[index+2]=1;
          }
		  }

   		return {
    		vertices: vert,
      	normals: normals
    	}
	}

	TriGenerator.prototype.getIndices = function() {
    var i = 0;
 		indices = new Uint16Array(this.getIndicesCount(this.width,this.height));
    for(var y = 0; y < this.height; y++) { 
      var base = y * (this.width+1);
			for(var x = 0; x < this.width+1; x++) {
        indices[i++] = ((base + x));
        indices[i++] = ((base + this.width + x))+1;
      }

	    if(y < this.height-1) {
        indices[i++] = ((y + 1) * (this.width+1) + (this.width));
	      indices[i++] = ((y + 1) * (this.width+1));
      }
    }
    return indices;
  }
}

function SphereBumpGenerator() {
  TriGenerator.call(this);
}

SphereBumpGenerator.prototype = Object.create(TriGenerator.prototype); 
SphereBumpGenerator.prototype.constructor = SphereBumpGenerator;

SphereBumpGenerator.prototype.init = function(w,h,s,r,x1,y1) {
  TriGenerator.prototype.init(w,h);
  this.radius = r;
  this.step = s;
  this.x = x1;
  this.y = y1;
}

SphereBumpGenerator.prototype.getVectices = function() {
  var w = this.width+1;
  var h = this.height+1;
  console.log(w);
  console.log(h);
  var total_vertices = w * h *3;
  vert = new Float32Array(total_vertices);
  normals = new Float32Array(total_vertices);

  for(var y = 0; y < h; y++) {
    var base = y * w;
    for(var x = 0; x < w; x++) {
      var index = (base + x)*3;
      var c = ((x-this.x)*(x-this.x))+((y-this.y)*(y-this.y));
      vert[index]=x/this.step;
      vert[index+1]=y/this.step;
      console.log("c + "+ c + " " + this.r)
      if(c<Math.pow(this.radius,2)) {
        vert[index+2]=-1*(this.radius-Math.sqrt(c))/this.step;
        normals[index]=(x-this.x)/this.radius;
        normals[index+1]=(y-this.y)/this.radius;
        normals[index+2]=(this.radius-Math.sqrt(c))/this.radius;
      } else {
        vert[index+2] = 0;
        normals[index] = 0;
        normals[index+1] = 0;
        normals[index+2]= 1;
      }
    }
  }
  return {
    vertices: vert,
    normals: normals  
  } 
}


function MandleBumpGenerator() {
  TriGenerator.call(this);
}
MandleBumpGenerator.prototype = Object.create(TriGenerator.prototype); 
MandleBumpGenerator.prototype.constructor = MandleBumpGenerator;

MandleBumpGenerator.prototype.init = function(w,h,s) {
  TriGenerator.prototype.init(w,h,s);
}

MandleBumpGenerator.prototype.getVectices = function() {
  var w = this.width+1;
  var h = this.height+1;
  console.log(w);
  console.log(h);
  var total_vertices = w * h *3;
  vert = new Float32Array(total_vertices);
  normals = new Float32Array(total_vertices);

  var xmin = -2.0; var xmax = 1.0;
  var ymin = -1.5; var ymax = 1.5;

  var maxIt = 10;
  var x1 = 0.0; var y1 = 0.0;
  var zx = 0.0; var zx0 = 0.0; var zy = 0.0;
  var zx2 = 0.0; var zy2 = 0.0;

  for(var y = 0; y < h; y++) {
    var base = y * w;
    var y1 = ymin + (ymax - ymin) * y / this.height;

    for(var x = 0; x < w; x++) {
      x1 = xmin + (xmax - xmin) * x / this.width;
      zx = x1; 
      zy = y1;
      var index = (base + x)*3;
      vert[index]=x/this.step;
      vert[index+1]=y/this.step;
      for(var i = 0; i < maxIt; i++) {
        var zx2 = zx * zx; 
        var zy2 = zy * zy;
        if(zx2 + zy2 > 4.0) 
          break;
          
          zx0 = zx2 - zy2 + x1;
          zy = 2.0 * zx * zy + y1;
          zx = zx0;
      }
    
      vert[index+2]=10-i;
      normals[index] = 0;
      normals[index+1] = 0;
      normals[index+2]= 1;
      
    }
  }
  return {
    vertices: vert,
    normals: normals  
  } 
}


