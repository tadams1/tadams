var express = require('express');
var router = express.Router();
var cfg = require('../config')

/* GET users listing. */
router.get('/', function(req, res, next) {
	var layers = req.app.get('layers');
	var collection = layers.db.collection(cfg.mongodb.col);

/*"properties.validated": false*/
  collection.find().toArray(function(e,docs){ 

	 
    console.log(JSON.stringify(docs));
    res.render('validator', {records: JSON.stringify(docs)});
  });
});

module.exports = router;