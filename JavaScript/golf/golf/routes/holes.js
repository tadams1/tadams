var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {	
	var golfmongo = req.app.get('golfmongo');
	console.log('about to call loadRound');
 	golfmongo.loadHoles('holes', res)
});

router.post('/', function(req, res, next) {
	var golfmongo = req.app.get('golfmongo');
 	golfmongo.saveRound(req.body)
 	console.log('about to redic')
 	res.send(200, 'done')
});

module.exports = router;
