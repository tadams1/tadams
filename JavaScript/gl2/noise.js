var p = [151,160,137,91,90,15,131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,190, 
		6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,88,237,149,56,87,174,20,125,136,
		171,168, 68,175,74,165,71,134,139,48,27,166,77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,
		55,46,245,40,244,102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196,135,130,116,
		188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,5,202,38,147,118,126,255,82,85,212,207,206,
		59,227,47,16,58,17,182,189,28,42,223,183,170,213,119,248,152, 2,44,154,163, 70,221,153,101,155,167, 43,172,9,
    	129,22,39,253, 19,98,108,110,79,113,224,232,178,185, 112,104,218,246,97,228,251,34,242,193,238,210,144,12,191,
    	179,162,241, 81,51,145,235,249,14,239,107,49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,127, 4,150,254,
   		138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180];

var perm = new Array(512);

function popPerm() {
	for(var x=0;x<512;x++) {
		perm[x]=p[x%256];
	}
}

function fade(n) {
	return (n*n*n*(n*6-15)+10);
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
	console.log("Floor'd:" + x1 + " " +  y1 + " " + z1);
	var x2 = x - Math.floor(x);
	var y2 = y - Math.floor(y);
	var z2 = z - Math.floor(z);

	console.log("vert'd:" + x2 + " " +  y2 + " " + z2);
	var u = fade(x2);
	var v = fade(y2);
	var w = fade(z2);

	console.log("Floor'd:" + u + " " +  w + " " + v);
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