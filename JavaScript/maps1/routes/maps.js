var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/', function(req, res, next) {
	var layers = req.app.get('layers');
	var collection = layers.db.collection('test1');


	console.log(req.query.geotype)

    //collection.find({'geometry.type': req.query.geotype, 'properties.type': req.query.type},{'_id': false}).toArray(function(e,docs){
    var clause;

    if(req.query.geotype == "Point") {
    	clause={"geometry.type": req.query.geotype, "properties.typeid": req.query.type};
    	console.log(clause);
    } else {
    	clause={'geometry.type': req.query.geotype, 'properties.letter': req.query.type};
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
