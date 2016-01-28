var express = require('express');
var router = express.Router();
var cfg = require('../config')

/* GET users listing. */
router.get('/', function(req, res, next) {
	var layers = req.app.get('layers');
	var collection = layers.db.collection(cfg.mongodb.col);


	console.log(req.query.geotype)

    //collection.find({'geometry.type': req.query.geotype, 'properties.type': req.query.type},{'_id': false}).toArray(function(e,docs){
    var clause;

    if(req.query.type == "ALL") {
      clause = {};
    }else{
      clause={"properties.typeid": req.query.type};
    }
    collection.find(clause,{'_id': false}).toArray(function(e,docs){ 
   		var obj1 = {	
			"type": "FeatureCollection",
      "crs":{"type":"name","properties":{"name":"urn:ogc:def:crs:EPSG::3395"}},
  			"features": []

  		}
		obj1.features = docs;
		res.setHeader('Content-Type', 'application/json');
        res.json(obj1);
    });
});

module.exports = router;
