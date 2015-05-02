var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
	console.log("This is the body" + JSON.stringify(req.body));
	res.render('round', { thedata: req.body });
});

module.exports = router;