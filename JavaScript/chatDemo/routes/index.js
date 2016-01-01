var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  //res.render('index', { title: 'Express' });
  res.sendfile(__dirname + '/index.html')
});

router.put('/', function(req,res,next) {
	res.send(200);
});

module.exports = router;
