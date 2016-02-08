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
	
    if(req.query.format == 'CSV') {
      var s = "type,typeid,typetext,comment,icon,link,name,email,date,longitute,latitude\n";
      for(var i = 0; i<docs.length; i++){
        s += docs[i].properties.type + ',' + docs[i].properties.typeid + ',' + docs[i].properties.typetext + ',';
        s += docs[i].properties.comment + docs[i].properties.icon + ',' + docs[i].properties.link + ',';
        s += docs[i].properties.yourname + docs[i].properties.youremail  + ',' + docs[i].properties.datesave + ',';
        s += docs[i].geometry.coordinates[0] + ',' + docs[i].geometry.coordinates[1] + '\n';
      }
      res.contentType('csv');
      res.send(s);

    } else {
      obj1.features = docs;
      res.setHeader('Content-Type', 'application/json');
      res.json(obj1);

    }
  });


});

module.exports = router;
