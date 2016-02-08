var express = require('express');
var router = express.Router();
var cfg = require('../config')

/* GET users listing. */
router.get('/', function(req, res, next) {
	var layers = req.app.get('layers');
	var collection = layers.db.collection(cfg.mongodb.col);

  collection.find({"properties.validated": false},{'_id': false}).toArray(function(e,docs){ 
    var obj1 = {	
      "type": "FeatureCollection",
   	  "features": []
    }
	 
    obj1.features = docs;
    res.rendor('validator', {records: obj1});
  });
});

module.exports = router;