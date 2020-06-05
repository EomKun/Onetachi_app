const express = require("express");
const router = express.Router();

// 일단 이 사람의 서류라고 가정하고 보내자
const papers = [
    ["발급 가능 서류 1", "발급기관1"],
    ["발급 가능 서류 2", "발급기관2"],
    ["발급 가능 서류 3", "발급기관3"],
    ["발급 가능 서류 4", "발급기관4"],
    ["발급 가능 서류 5", "발급기관5"],
    ["발급 가능 서류 6", "발급기관6"],
    ["발급 가능 서류 7", "발급기관7"],
    ["발급 가능 서류 8", "발급기관8"],
    ["발급 가능 서류 9", "발급기관9"],
    ["발급 가능 서류 10", "발급기관10"],
    ["발급 가능 서류 11", "발급기관11"],
    ["발급 가능 서류 12", "발급기관12"],
];

// Paper list req
router.post("/list", (req, res) => {
    console.info(req.body);
    console.info("Req Paper from id : ", req.body.id);
    res.json({ papers });
});

module.exports = router;