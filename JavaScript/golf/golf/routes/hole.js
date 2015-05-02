var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {	
	var golfmongo = req.app.get('golfmongo');
	req.
	res.render('hole', {'roundid': req.query.roundid, 'holenumber': req.query.holenumber });
});

router.post('/', function(req, res, next) {
	var golfmongo = req.app.get('golfmongo');
 	golfmongo.saveShot(req.body)
 	res.send(200, 'done')
});

module.exports = router;
