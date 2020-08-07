const mongoose = require("mongoose");
const Schema = mongoose.Schema;

// Create Schema
const EventSchma = new Schema({
  eventName: {
    type: String,
    required: true
  },
  eventDesc: {
    type: String,
    required: true
  },
  reward: {
    type: Number,
    required: true
  },
  eventLocation: {
    type: String,
    required: true
  },
  eventManager: {
    type: String,
    required: true
  },
  requiredVolunteer: {
    type: Number,
    required: true
  },
  eventImageSrc: {
    type: String,
    required: true
  },
  eventDate: {
    type: Date,
    required: true,
    default: Date.now
  },
  eventEndDate: {
    type: Date,
    required: true
  },
  eventQrCode: {
    type: String,
    required: true
  },
  eventType: {
    type: String
  }
});

module.exports = Event = mongoose.model("events", EventSchma);
