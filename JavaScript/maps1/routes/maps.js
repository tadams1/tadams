var express = require('express');
var router = express.Router();
var cfg = require('../config')

/* GET users listing. */
router.get('/', function(req, res, next) {
	var layers = req.app.get('layers');
	var collection = layers.db.collection(cfg.mongodb.col);

    var clause;
    if(req.query.type == "ALL") {
      clause = {};
    }else if(req.query.type == "ALLTYPE") {
      clause={"properties.type": req.query.types}
    } else {
      clause={"properties.typeid": req.query.type};
    }
    collection.find(clause,{'_id': false}).toArray(function(e,docs){ 
   		var obj1 = {	
			"type": "FeatureCollection",
   			"features": []

  		}
		obj1.features = docs;
		res.setHeader('Content-Type', 'application/json');
        res.json(obj1);
    });
});

module.exports = router;
