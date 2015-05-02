var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {


	// Connection URL
	// Use connect method to connect to the Server

      var layers = req.app.get('layers');
      console.log('in request')

      console.log("Req:" + layers.strPoints);

      res.render('index', { points: layers.strPoints, polygons: layers.strPolygons});

	  	//var myj2 = db.collection('test1').find();



	 //console.log('asdf' + JSON.stringify(myj2));

});


router.post('/', function(req, res, next) {


    var layers = req.app.get('layers');
 	layers.savePointer(req.body)
	console.log(req.body);


	 //console.log('asdf' + JSON.stringify(myj2));

});

module.exports = router;
