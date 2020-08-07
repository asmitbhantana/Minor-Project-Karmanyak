const express = require("express");
const router = express.Router();
const keys = require("../../config/keys");
const bcrypt = require("bcryptjs");

// Load User model
const Event = require("../../models/event");

router.post("/addevent", (req, res) => {
  //const { errors, isValid } = validateEventInput(req.body);
  // if (!isValid) {
  //   return res.status(400).json(errors);
  // }
  //
  let newEvent = new Event(req.body);

  // Hash password before saving in database
  let seconds = Math.floor(Date.now() / 1000); // current time in seconds
  bcrypt.genSalt(10, (err, salt) => {
    bcrypt.hash(seconds.toString(), salt, (err, hash) => {
      if (err) {
        throw err;
      }
      newEvent.eventQrCode = hash;
      newEvent.save((err, contact) => {
        if (err) {
          res.send(err);
        }
        res.json(contact);
      });
    });
  });
});

//route GET api/crm/event
router.get("/getevent", (req, res) => {
  Event.find({}, (err, event) => {
    if (err) {
      res.send(err);
    }
    res.json(event);
  });
});

//route get event by id
router.get("/getevent/:eventid", (req, res) => {
  Event.findById(req.params.eventid, (err, event) => {
    if (err) {
      res.send(err);
    }
    res.json(event);
  });
});

//router get for update
router.put("/editevent/:_id", (req, res) => {
  Event.findOneAndUpdate(
    { _id: req.params._id },
    req.body,
    { new: true },
    (err, event) => {
      if (err) {
        res.send(err);
      }
      res.json(event);
    }
  );
});

//router get for update
router.delete("/deleteevent/:_id", (req, res) => {
  Event.findByIdAndDelete(req.params._id, (err, event) => {
    if (err) {
      res.send(err);
    }
    res.json(event);
  });
});

module.exports = router;

// Hash c before saving in database
// bcrypt.genSalt(10, (err, salt) => {
//   bcrypt.hash(c, salt, (err, hash) => {
//     if (err) throw err;

//     // Console.log(hash);

//     newEvent
//       .save()
//       .then(newEvent => res.json(newEvent))
//       .catch(err => console.log(err));
//   });
// });
