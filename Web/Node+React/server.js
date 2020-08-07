const express = require("express");
const mongoose = require("mongoose");
const bodyParser = require("body-parser");
const passport = require("passport");
const path = require("path")

const users = require("./routes/api/users");
const events = require("./routes/api/event");
const solidity = require("./routes/api/solidity");
const qrcode = require("./routes/api/qrverification");

const app = express();
//Added on contacts
//routes(app);
//
// Bodyparser middleware
app.use(
  bodyParser.urlencoded({
    extended: false
  })
);
app.use(bodyParser.json());

// DB Config
const db = require("./config/keys").mongoURI;

// Connect to MongoDB
mongoose
  .connect(db, { useNewUrlParser: true })
  .then(() => console.log("MongoDB successfully connected"))
  .catch(err => console.log(err));

// Passport middleware
app.use(passport.initialize());

// Passport config
require("./config/passport")(passport);

// Routes
app.use("/api/users", users);
app.use("/api/event", events);
app.use("/api/solidity", solidity);
app.use("/api/qrverification", qrcode);

if(process.env.NODE_ENV === "production"){
  app.use(express.static("client/build"));
// Right before your app.listen(), add this:
  app.get("*", (req, res) => {
  res.sendFile(path.join(__dirname, "client", "build", "index.html"));
});
}


const port = process.env.PORT || 5000; // process.env.port is Heroku's port if you choose to deploy the app there
// app.get("/", (req, res) => res.send("Node and express server is running"));
app.listen(port, () => console.log(`Server up and running on port ${port} !`));
// app.listen(8080, "192.168.43.225");
// app.listen(5000, "192.168.0.112");
