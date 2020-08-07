const express = require("express");
const router = express.Router();

// Load User model
const User = require("../../models/user");
const Event = require("../../models/event");

router.post("/verification", (req, res) => {
  const user = req.body.username;
  const eventqrcode = req.body.qrcode;

  Event.findOne({ eventQrCode: eventqrcode }).then(event => {
    if (!event) {
      return res.status(404).json({ eventError: "Event Not Found Error!" });
    }
    User.findOne({ email: user }).then(user => {
      if (!user) {
        return res.status(400).json({ userError: "User Not Found Error!" });
      }
      return res.status(200).json({
        userKey: user.walletAddress,
        eventRewards: event.reward
      });
    });
  });
});

module.exports = router;
