var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var common = require('common');
var layers = require('./layers');

var routes = require('./routes/index');
var viewrecords = require('./routes/viewrecords');
var users = require('./routes/users');
var maps = require('./routes/maps');

var app = express();

var layers = new layers();

console.log("TestVal" + layers.testVal);
app.set('layers', layers)
layers.init();
// view engine setup
app.set('views', path.join(__dirname, 'views'));
//app.set('view engine', 'jade');
app.set('view engine', 'ejs');


// uncomment after placing your favicon in /public
//app.use(favicon(__dirname + '/public/favicon.ico'));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', routes);
app.use('/users', users);
app.use('/maps', maps);
app.use('/viewrecords', viewrecords);
module.exports = app;
