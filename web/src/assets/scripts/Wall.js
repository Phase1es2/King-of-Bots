/*
 * @Author: Hao Yang
 * @Date: 2025-03-18 13:55:54
 * @LastEditTime: 2025-03-18 14:05:46
 * @LastEditors: MacBookPro
 * @Description: In User Settings Edit
 * @FilePath: /Java Final Project/web/src/assets/scripts/Wall.js
 */
import { GameObject } from "./GameObject";

export class Wall extends GameObject {
    constructor(r, c, gamemap) {
        super()
        this.r = r;
        this.c = c;
        this.gamemap = gamemap;
        this.color = "#B36226";

        this.walls = [];
    }

    update() {
        this.render();
    }

    render() {
        const L = this.gamemap.L;
        const ctx = this.gamemap.ctx;

        ctx.fillStyle = this.color;
        ctx.fillRect(this.c * L, this.r * L, L, L);
    }

}