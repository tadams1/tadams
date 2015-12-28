var perm = [151,160,137,91,90,15,131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,190, 
    6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,88,237,149,56,87,174,20,125,136,
    171,168, 68,175,74,165,71,134,139,48,27,166,77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,
    55,46,245,40,244,102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196,135,130,116,
    188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,5,202,38,147,118,126,255,82,85,212,207,206,
    59,227,47,16,58,17,182,189,28,42,223,183,170,213,119,248,152, 2,44,154,163, 70,221,153,101,155,167, 43,172,9,
      129,22,39,253, 19,98,108,110,79,113,224,232,178,185, 112,104,218,246,97,228,251,34,242,193,238,210,144,12,191,
      179,162,241, 81,51,145,235,249,14,239,107,49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,127, 4,150,254,
      138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180];

var p = new Array(512);

function popPerm() {
  for(var x=0;x<512;x++) {
    p[x]=perm[x%256];
  }
 }

function fade(t) {
  return (t * t * t * (t * (t * 6 - 15) + 10));
}

function lerp(t, a, b) { 
  return a + t * (b - a); 
}

function grad(hash, x, y, z) {
      var h = hash & 15; 
      var u = h<8 ? x : y;
      var v = h<4 ? y : h==12||h==14 ? x : z;
      return ((h&1) == 0 ? u : -u) + ((h&2) == 0 ? v : -v);
}

function makeNoise(x,y,z) {

  var x1 = Math.floor(x) % 255;
  var y1 = Math.floor(y) % 255;
  var z1 = Math.floor(z) % 255;

  var x2 = x - Math.floor(x);
  var y2 = y - Math.floor(y);
  var z2 = z - Math.floor(z);

  var u = fade(x2);
  var v = fade(y2);
  var w = fade(z2);


  var A = p[x1]+y1;
  var AA = p[A]+z1;
  var AB = p[A+1]+z1;
  var B = p[x1+1]+y1;
  var BA = p[B]+z1;
  var BB = p[B+1]+z1;      

  return lerp(w, lerp(v, lerp(u, grad(p[AA], x2, y2, z2),grad(p[BA], x2-1, y2, z2)),
           lerp(u, grad(p[AB], x2, y2-1, z2), grad(p[BB], x2-1, y2-1, z2))),
             lerp(v, lerp(u, grad(p[AA+1], x2, y2, z2-1), grad(p[BA+1], x2-1, y2, z2-1)),
             lerp(u, grad(p[AB+1], x2, y2-1, z2-1),grad(p[BB+1], x2-1, y2-1, z2-1))));

}


var TriGenerator = function() {
	var width;
	var height;
  var step;
  var vData;
  var bBuild;

	TriGenerator.prototype.init = function(w,h,s) {
		this.width = w;
		this.height = h;
    this.step =s;
    this.bBuild = false;
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
    if(this.bBuild) {
      return this.vData;
    }

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

      this.bBuild=true;
      this.vData = {
        vertices: vert,
        normals: normals
      } 
      return this.vData;
   		
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
  if(this.bBuild) {
      return this.vData;
  }
  var w = this.width+1;
  var h = this.height+1;
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
  this.bBuild=true;
  this.vData = {
        vertices: vert,
        normals: normals
  } 
  return this.vData;
      
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
  if(this.bBuild) {
      return this.vData;
  }

  var w = this.width+1;
  var h = this.height+1;
  
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
    }
  }

  var idx = TriGenerator.prototype.getIndices();
  var idxcnt = TriGenerator.prototype.getIndicesCount();
  console.log("idx:" + idx.length);
  for(var n=2; n<idxcnt;n++){
    var verta=idx[n-2];
    var vertb=idx[n-1];
    var vertc=idx[n]

    var bcx = vert[(vertb*3)]-vert[(vertc*3)];
    var bcy = vert[(vertb*3)+1]-vert[(vertc*3)+1];
    var bcz = vert[(vertb*3)+2]-vert[(vertc*3)+2];
    var acx = vert[(verta*3)]-vert[(vertc*3)];
    var acy = vert[(verta*3)+1]-vert[(vertc*3)+1];
    var acz = vert[(verta*3)+2]-vert[(vertc*3)+2];

    var nx = (acy * bcz) - (acz * bcy);
    var ny = (acz * bcx) - (acx * bcz);
    var nz = (acx * bcy) - (acy * bcx);
    var l = Math.sqrt(nx*nx + ny*ny + nz*nz);
    normals[vertb*3]=nx/l;
    normals[vertb*3+1]=ny/l;
    normals[vertb*3+2]=nz/l;
     
  }


  this.bBuild=true;
  this.vData = {
        vertices: vert,
        normals: normals
      } 
  return this.vData;
      
}


function NoiseBumpGenerator() {
  TriGenerator.call(this);
}
NoiseBumpGenerator.prototype = Object.create(TriGenerator.prototype); 
NoiseBumpGenerator.prototype.constructor = SphereBumpGenerator;

NoiseBumpGenerator.prototype.init = function(w,h,s,r,x1,y1) {
  TriGenerator.prototype.init(w,h);
  this.radius = r;
  this.step = s;
  this.x = x1;
  this.y = y1;
}

NoiseBumpGenerator.prototype.getVectices = function() {
  if(this.bBuild) {
      return this.vData;
  }

  popPerm();
  var w = this.width+1;
  var h = this.height+1;
  var total_vertices = w * h *3;
  vert = new Float32Array(total_vertices);
  normals = new Float32Array(total_vertices);

  for(var y = 0; y < h; y++) {
    var base = y * w;
    for(var x = 0; x < w; x++) {
      var index = (base + x)*3;
      var c = ((x-this.x)*(x-this.x))+((y-this.y)*(y-this.y));
      vert[index]=x/this.step;
      vert[index+1]=(y/this.step)+makeNoise(x*1.23 , y * .137 , 0 );
      var z = makeNoise(x*1.23 , y * .137 , 0 );
      vert[index+2]=Math.pow(z-0.5,4);

  
    }
  }


  var idx = TriGenerator.prototype.getIndices();
  var idxcnt = TriGenerator.prototype.getIndicesCount();
  for(var n=2; n<idxcnt;n++){
    var verta=idx[n-2];
    var vertb=idx[n-1];
    var vertc=idx[n]

    var bcx = vert[(vertb*3)]-vert[(vertc*3)];
    var bcy = vert[(vertb*3)+1]-vert[(vertc*3)+1];
    var bcz = vert[(vertb*3)+2]-vert[(vertc*3)+2];
    var acx = vert[(verta*3)]-vert[(vertc*3)];
    var acy = vert[(verta*3)+1]-vert[(vertc*3)+1];
    var acz = vert[(verta*3)+2]-vert[(vertc*3)+2];

    var nx = (acy * bcz) - (acz * bcy);
    var ny = (acz * bcx) - (acx * bcz);
    var nz = (acx * bcy) - (acy * bcx);
    var l = Math.sqrt(nx*nx + ny*ny + nz*nz);
    normals[vertb*3]=nx/l;
    normals[vertb*3+1]=ny/l;
    normals[vertb*3+2]=nz/l;
     
  }
  this.bBuild=true;
  this.vData = {
        vertices: vert,
        normals: normals
  } 
  return this.vData;
      
}

