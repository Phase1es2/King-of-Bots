/*
 * @Author: Hao Yang
 * @Date: 2025-03-19 10:35:59
 * @LastEditTime: 2025-03-31 17:58:37
 * @LastEditors: MacBookPro
 * @Description: In User Settings Edit
 * @FilePath: /Java Final Project/web/src/assets/scripts/snake.js
 */
import { GameObject } from "./GameObject";
import { Cell } from "./Cell";

export class Snake extends GameObject {
    constructor(info, gamemap) {
        super();

        this.id = info.id; // give each snake an id
        this.color = info.color;
        this.gamemap = gamemap;

        this.cells = [new Cell(info.r, info.c)]; // use Cell to store snake, and cells[0] is snake's head;
        this.next_cell = null; // dist for next step
        
        this.speed = 5; // snake can move 5 blocks/sec

        /* direction -1 means no input
            0:   up
            1:   right
            3:   down
            4:   left
        */
        this.direction = -1;
        /*
        each snake have 3 status:
            idle
            move
            die
        */
        this.status = "idle";
        
        /*
                up (-1, 0)
        left(0, -1)     right(0, 1)
                down (1, 0)
        */

        this.dr = [-1, 0, 1, 0];
        this.dc = [0, 1, 0, -1];
        
        
        this.step = 0;

        this.eps = 1e-2; // error range, if two points are with eps, means match

        this.facing_direction = 0;
        
        if (this.id === 1) this.facing_direction = 2; 

        /**
         * -------------------------->x
         *|
         *|
         *|
         *|y
         */
        this.facing_dx = [
            [-1, 1],
            [1, 1],
            [1, -1],
            [-1, -1],

        ];

        this.facing_dy = [
            [-1, -1],
            [-1, 1],
            [1, 1],
            [1, -1],
        ];
    }

    start() {

    }

    set_direction(d) {
        this.direction = d;
    }

    /**
     * in the first 10 step, we will increase the tail
     * after 10 steps, every other 3 steps, we increase the tail.
     */
    
    check_tail_increasing() {
        if (this.step <= 10) return true;
        if (this.step % 3 === 1) return true;
        return false; 
    } 

    // update status of snake, move to next direction
    next_step() {
        const d = this.direction;
        this.next_cell = new Cell(this.cells[0].r + this.dr[d], this.cells[0].c + this.dc[d]);
        this.facing_direction = d;
        this.direction = -1;
        this.status = "move";
        this.step ++ ;


        const k = this.cells.length;
        for (let i = k; i > 0; i -- ) {
            this.cells[i] = JSON.parse(JSON.stringify(this.cells[i - 1]));
        }

        //invalid move
        /* move to backend
        if (!this.gamemap.check_valid(this.next_cell)) {
            this.status = "die";
        } */
    }


    update_move() {
        // velocity * time
        // this.cells[0].x += this.speed * this.timedelta / 1000;

        const dx = this.next_cell.x - this.cells[0].x;
        const dy = this.next_cell.y - this.cells[0].y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < this.eps) { // reach to dest
            this.cells[0] = this.next_cell;
            this.next_cell = null;
            this.status = "idle";
            
            /*
            since we are moving the snake to next pos
            if the snake did not increase the size
            we do not need the last part
            */
            if (!this.check_tail_increasing()) { 
                this.cells.pop();
            }

        } else {
            // distance between two frame
            const move_distance = this.speed * this.timedelta / 1000; 
            this.cells[0].x += move_distance * dx / distance;
            this.cells[0].y += move_distance * dy / distance;

            if (!this.check_tail_increasing()) {
                const k = this.cells.length;
                const tail = this.cells[k - 1];
                const tail_pos = this.cells[k - 2];
                // move tail to tail pos;
                const tail_dx = tail_pos.x - tail.x;
                const tail_dy = tail_pos.y - tail.y;
                tail.x += move_distance * tail_dx / distance;
                tail.y += move_distance * tail_dy / distance;
            }

        }
    }

    update() {
        if (this.status === 'move') {
            this.update_move();
        }

        this.render();
    }


    render() {
        const L = this.gamemap.L;
        const ctx = this.gamemap.ctx;

        ctx.fillStyle = this.color;

        if (this.status === "die") {
            ctx.fillStyle = "white";
        }

        for (const cell of this.cells) {
            ctx.beginPath();
            // ctx.arc(cell.x * L, cell.y * L, L / 2, 0, Math.Pi * 2);
            // each part of snake body is cycle
            ctx.arc(cell.x * L, cell.y * L, L / 2 * 0.8, 0, Math.PI * 2);
            ctx.fill();
        }

        // Using rectangle to fill out the body.
        for (let i = 1; i < this.cells.length; i ++) {
            const a = this.cells[i - 1], b = this.cells[i];
            if (Math.abs(a.x - b.x) < this.eps && Math.abs(a.y - b.y) < this.eps)
                continue;
            if (Math.abs(a.x - b.x) < this.eps) {
                ctx.fillRect((a.x - 0.5 + 0.1) * L, Math.min(a.y, b.y) * L, L * 0.8, Math.abs(a.y - b.y) * L);
            } else {
                ctx.fillRect(Math.min(a.x, b.x) * L, (a.y - 0.5 + 0.1) * L, Math.abs(a.x - b.x) * L, L * 0.8);
            }
        }

        ctx.fillStyle = "black";
        for (let i = 0; i < 2; i ++) {
            const facing_x = (this.cells[0].x + this.facing_dx[this.facing_direction][i] * 0.15) * L;
            const facing_y = (this.cells[0].y + this.facing_dy[this.facing_direction][i] * 0.15) * L;
            ctx.beginPath();
            ctx.arc(facing_x, facing_y, L * 0.05, 0, Math.PI * 2);
            ctx.fill();
        }
    }

}
