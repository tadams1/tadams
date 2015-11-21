var MongoClient = require('mongodb').MongoClient
  , assert = require('assert');


function golfmongo() {
	this.db = null;
	this.colHoles = "holestest";
	this.colRounds = "roundstest";
	this.colCourses = "coursetest";
	this.colPlayers = "playerstest";
	console.log("Object Created:")
}

golfmongo.prototype.init = function() {
	this.connectDB(this);
	this.testVal = "2";
}

golfmongo.prototype.connectDB = function(myobj) {
	var url = "mongodb://localhost:27017/myprojects"
	MongoClient.connect(url, function(err, db) {
		assert.equal(null, err);
		console.log("Connected correctly to server");
		console.log(err);
		myobj.db = db;
	});
}

golfmongo.prototype.loadHoles = function(viewname, res) {
	this.db.collection(this.colHoles).aggregate(
		[
			{ $match :{'properties.roundid': 19283}}, 
			{ $group: 
				{ _id: {
					roundid: '$properties.roundid',
					holenumber: '$properties.holenumber'
				}, 
				count: { $sum: 1}
			}
		}
		]).toArray(function (err, items) {
		console.log(items);
		var obj1 = {	
			"type": "Round",
			"RoundID": 19283,
  			"features": []
  		}

		obj1.features = items;
		var strJSONData = JSON.stringify(obj1);
		console.log('about to render');
		res.render(viewname, { roundData : strJSONData})

	});
}

golfmongo.prototype.saveRound = function (roundobj) {
	this.db.collection(this.colRounds).insert(roundobj);
}

golfmongo.prototype.saveShot = function (holeObj) {
	this.db.collection(this.colHoles).insert(holeObj);
}

module.exports = golfmongo