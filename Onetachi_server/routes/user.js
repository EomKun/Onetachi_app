const express = require("express");
const router = express.Router();
const Users = require("../server").Users;

// Login Req
router.post("/login", (req, res) => {
    const result = Users.filter((val) => {
        return val.id === req.body.id;
    });

    if(result.length){
        console.info("Login success! Logined ID : " + req.body.id)
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
        const users_filter = Users.filter((user) => {
            return user.id === id;
        });

        if(users_filter.length){
            res.json({
                result: false
            });
        }
        
        Users.push({ id, residentNum });
        console.info("Signup Complete! info [id : ", 
            req.body.id,
            "regNum1 : ",
            req.body.regNum1);
            
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