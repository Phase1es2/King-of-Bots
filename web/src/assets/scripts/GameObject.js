/*
 * @Author: Hao Yang
 * @Date: 2025-03-18 12:16:39
 * @LastEditTime: 2025-03-19 10:49:11
 * @LastEditors: MacBookPro
 * @Description: In User Settings Edit
 * @FilePath: /Java Final Project/web/src/assets/scripts/GameObject.js
 */
// store all the game obejcts 
const GAME_OBJECTS = [];

//
export class GameObject {
    constructor() {
        GAME_OBJECTS.push(this);
        this.timedelta = 0; //use to track time between to frame
        this.has_called_start = false;
    }

    // only execute when game start
    start() {
        
    }
    
    // execpt the 1st frame, update calls every frame
    update() {

    }

    // execute before delete
    on_destroy() {

    }

    destroy() {
        this.on_destroy();
        for (let i in GAME_OBJECTS) {
            const obj = GAME_OBJECTS[i];
            if (obj === this) {
                // delete that object
                GAME_OBJECTS.splice(i);
                break;
            }
        }
    }
}

// call it every frame use recursion

// last execute time
let last_timestamp;

const step = timestamp => {
    for (let obj of GAME_OBJECTS) {
        if (!obj.has_called_start) {
            obj.has_called_start = true;
            obj.start();
        } else {
            obj.timedelta = timestamp - last_timestamp;
            obj.update();
        }
    }

    last_timestamp = timestamp;
    requestAnimationFrame(step)
}

// in the next frame execute step function;
requestAnimationFrame(step)