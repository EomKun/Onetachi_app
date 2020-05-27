const express = require("express");
const router = express.Router();
const Users = require("../server").Users;

// Login Req
router.post("/login", (req, res) => {
    const result = Users.filter((val) => {
        return val.id === req.body.id;
    });

    if(result.length){
        res.json({ id : req.body.id });
    } else {
        res.json({  });
    }
});


// Sign Up Req
router.post("/signup", (req, res) => {
    const id = req.body.id;
    const residentNum = req.body.residentNum;

    try{
        Users.push({ id, residentNum });
        console.log(req.body)
        res.json({
            result: true
        });
    } catch(err){
        console.error(err);
        res.json({
            result: false
        });
    }
});

module.exports = router;