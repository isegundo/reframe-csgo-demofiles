const fs = require("fs");
const demofile = require("demofile");

const demoFile = new demofile.DemoFile();

// demoFile.gameEvents.on("player_death", e => {
//   const victim = demoFile.entities.getByUserId(e.userid);
//   const victimName = victim ? victim.name : "unnamed";

//   // Attacker may have disconnected so be aware.
//   // e.g. attacker could have thrown a grenade, disconnected, then that grenade
//   // killed another player.
//   const attacker = demoFile.entities.getByUserId(e.attacker);
//   const attackerName = attacker ? attacker.name : "unnamed";

//   const headshotText = e.headshot ? " HS" : "";

//   console.log(`${attackerName} [${e.weapon}${headshotText}] ${victimName}`);
// });

demoFile.on("tickend", tickNumber => {
    
    if (tickNumber < 10000){
      
        console.log("Players: " + demoFile.players.map(p=> p.position.))
    }
})

// demoFile.on("tickend", tickNumber => {
    
//     if (tickNumber % 5000 == 0){
//         console.log("AIIIII")
//         //console.log("Players: " + demoFile.players.map(p=> p.userInfo.name))
//     }
// })


// demoFile.gameEvents.on("round_start", e => {
//     console.log("Round start tick: " + demoFile.currentTick + ", seconds: " + demoFile.currentTime)
    
// })

// demoFile.gameEvents.on("player_spawn", e => {
//     console.log(e)
// })

demoFile.on("end", e =>{
    
    console.log("cabou")
})

demoFile.parseStream(fs.createReadStream("./test-demos/01.dem"));