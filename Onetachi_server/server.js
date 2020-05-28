const express = require("express");
const https = require("https");
const fs = require("fs");
const app = express();

const path = require("path");
module.exports = { Users: [] };

app.use(express.static(path.join(__dirname, "public")));
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

const options = {
  key: fs.readFileSync("./keys/private.pem"),
  cert: fs.readFileSync("./keys/public.pem"),
};

app.use("/user", require("./routes/user"));
app.use("/qr", require("./routes/qr"));

https.createServer(options, app).listen(3000, () => {
  console.info("server ready");
});
