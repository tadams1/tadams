var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

var routes = require('./routes/index');
var users = require('./routes/users');
var addround = require('./routes/addround');
var round = require('./routes/round');
var golfmongo = require('./golfmongo');
var holes = require('./routes/holes');
var hole = require('./routes/hole');
var app = express();


var io = require('socket.io').listen(app.listen(3001));
var golfmongo = new golfmongo();

app.set('golfmongo', golfmongo)
golfmongo.init();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

// uncomment after placing your favicon in /public
//app.use(favicon(__dirname + '/public/favicon.ico'));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', routes);
app.use('/addround', addround);
app.use('/round', round);
app.use('/holes', holes);
app.use('/hole', hole);

io.on('connection',function(socket){
  console.log('connection..')
})


module.exports = app;
