var config = module.exports = {};
 
config.env = 'development';
config.hostname = 'dev.example.com';
config.mapprefix = 'http://192.168.1.113:3000'
 
//mongo database
config.mongodb = {};
config.mongodb.url = 'mongodb://localhost:27017/BirdMap';
config.mongodb.col = 'MapData';
config.listenport = 3001;