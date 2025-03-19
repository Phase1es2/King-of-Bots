/*
 * @Author: Hao Yang
 * @Date: 2025-03-19 10:25:12
 * @LastEditTime: 2025-03-19 10:27:34
 * @LastEditors: MacBookPro
 * @Description: In User Settings Edit
 * @FilePath: /Java Final Project/web/src/assets/scripts/Cell.js
 */

export class Cell{
    constructor(r, c) {
        this.r = r;
        this.c = c;
        this.x = c + 0.5;
        this.y = r + 0.5;
    }
}