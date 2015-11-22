
function poparray(v) {
	for(var i = 0; i< 10; i++) {
		v[i]=i;
	}
}
console.log("hello");
var v = new Float32Array(10);
poparray(v);
for(var i = 0; i< 10; i++) {
	console.log(v[i]);
}

