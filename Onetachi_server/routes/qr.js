const express = require("express");
const router = express.Router();

module.exports = router;

// scan Req
router.post("/scan", (req, res) => {
  const id = req.body.id;
  console.log(id);
  res.json({
    id: "ok",
  });
});
