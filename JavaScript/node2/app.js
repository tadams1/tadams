var express = require('express');
var path = require('path');
var bodyParser = require('body-parser');
var app = express();

var listOfItems =  [			{ id: 1, desc: 'i1' },
			{ id: 2, desc: 'i2' },
			{ id: 3, desc: 'i3' } 

		];

app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));


app.use(bodyParser());

app.get('/', function(req, res) {
	res.render('index',{
		title: 'my app',
		items: listOfItems
	});

});

app.post('/add', function(req,res) {
	var newItem = req.body.newItem;
	console.log(newItem);
	listOfItems.push( {
		id: listOfItems.length+1,
		desc: newItem
	});
	res.redirect('/');

});

app.listen(1338, function() {
	console.log('listen on 1338');
});