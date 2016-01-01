var mongoose = requires('mongoose')

mongoose.connect('mongodb:/localhost/users');
db.on('error', console.error.bind(console, "connection error:"));

