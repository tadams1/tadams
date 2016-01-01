var express = require('express');
var mongoose = require('mongoose');

var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {

mongoose.connect("mongodb://localhost/users")
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function (callback) {
    var kittySchema = mongoose.Schema({
        name:  {
        	first: String,
        	last: String
        },
        age: Number
    });
    kittySchema.virtual('name.full').get(function() {
    	return this.name.first + " " + this.name.last;
    });

    kittySchema.methods.speak = function() {
    	var greeting = this.name ? "Meow my name is" + this.name.full : "I dont have a name";
    	greeting += " i am this age" + this.age;
    	console.log(greeting)
    }

    var kitten = mongoose.model("kitten", kittySchema);
    //var monty = new kitten({name: "monty"});
    //monty.save();

    kitten.findOne({"name.first": "monty"} , function(err, mont) {
 		if(err) 
    	{
    		return console.error(err);
    	}

    	console.log(mont.name.full);
    	mont.speak();
    });
	//var monty = new kitten({name: {first: "monty", last: "Daisy"}, age: 10});
	//monty.save();
    /*var monty = kitten.findOne({name: "monty"}, function(err, themont) {
    	if(err) 
    	{
    		return console.error(err);
    	}

    	console.log(themont.name);
    	themont.name = "Daisy";
    	themont.save(function (err) {
    		if(err) 
    		{
    			console.error(err);
    		}
    	});
    });*/

  // yay!
  console.log("test");
});

  res.render('index', { title: 'Express' });
});

module.exports = router;
