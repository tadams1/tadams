var config = module.exports = {};
 
config.env = 'development';
config.hostname = 'dev.example.com';
 
//mongo database
config.mongodb = {};
config.mongodb.url = 'mongodb://localhost:27017/BirdMap';
config.mongodb.col = 'MapData';
config.listenport = 3001;