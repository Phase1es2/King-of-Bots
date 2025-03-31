package com.kob.backend.consumer.utils;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.mapper.RecordMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.Record;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Game extends Thread{
    final private Integer rows;
    final private  Integer cols;
    final private  Integer inner_walls_count;
    final private int[][] g;

    final private static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    private final Player playerA, playerB;
    private Integer nextStepA = null;
    private Integer nextStepB = null;
    private String st = "playing"; // playing -> finished
    private String loser = ""; // all, A, B
    private ReentrantLock lock = new ReentrantLock();

    public Game(Integer rows, Integer cols, Integer inner_walls_count, Integer idA, Integer idB) {
        this.cols = cols;
        this.rows = rows;
        this.inner_walls_count = inner_walls_count;
        this.g = new int[rows][cols];
        playerA = new Player(idA, rows - 2, 1, new ArrayList<>());
        playerB = new Player(idB, 1, cols - 2, new ArrayList<>());
    }

    public int[][] getG() {
        return g;
    }

    public Player getPlayerA() {
        return playerA;
    }

    public Player getPlayerB() {
        return playerB;
    }

    private boolean draw() {
        for (int i = 0; i < this.rows; i ++)
            for (int j = 0; j < this.cols; j ++)
                g[i][j] = 0;

        for (int r = 0; r < this.rows; r ++)
            g[r][0] = g[r][this.cols - 1] = 1;

        for (int c = 0; c < this.cols; c ++)
            g[0][c] = g[this.rows - 1][c] = 1;

        Random random = new Random();
        for (int i = 0; i < this.inner_walls_count / 2; i ++) {
            for (int j = 0; j < 1000; j ++) {
                int r = random.nextInt(this.rows);
                int c = random.nextInt(this.cols);

                if (g[r][c] == 1 || g[this.rows - 1 - r][this.rows - 1 - c] == 1) continue;
                if (r == this.rows - 2 && c == 1 || r == 1 && c == this.cols - 2) continue;

                g[r][c] = g[this.rows - 1 - r][this.cols - 1 - c] = 1;
                break;
            }
        }
        return check_connected(this.rows - 2, 1, 1, this.cols - 2);
    }

    private boolean check_connected(int sx, int sy, int tx, int ty) {
        if (sx == tx && sy == ty) return true;
        g[sx][sy] = 1;
        for (int i = 0; i < 4; i ++) {
            int x = sx + dx[i], y = sy + dy[i];
            if (x >= 0 && x < this.rows && y >= 0 && y < this.cols && g[x][y] == 0) {
                if (check_connected(x, y, tx, ty)) {
                    g[sx][sy] = 0;
                    return true;
                }
            }
        }
        return false;
    }

    public void setNextStepA(Integer nextStepA) {
        lock.lock();
            try {
                this.nextStepA = nextStepA;
            }   finally {
                lock.unlock();
            }
    }

    public void setNextStepB(Integer nextStepB) {
        lock.lock();
        try {
            this.nextStepB = nextStepB;
        }   finally {
            lock.unlock();
        }
    }

    public void generateMap(){
        for (int i = 0; i < 1000; i ++)
            if (draw())
                break;
    }

    // waiting for next move
    private boolean nextStep() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < 50; i ++) {
            try {
                Thread.sleep(100);
                lock.lock();
                try {
                    if(nextStepA != null && nextStepB != null) {
                        playerA.getSteps().add(nextStepA);
                        playerB.getSteps().add(nextStepB);
                        return true;
                    }
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    // boardcast
    private void broadcastResult() {
        JSONObject resp = new JSONObject();
        resp.put("event", "result");
        resp.put("loser", loser);
        saveToDatabase();
        broadcastToPlayer(resp.toJSONString());
    }

    private void broadcastToPlayer(String message) {
        WebSocketServer.users.get(playerA.getId()).sendMessage(message);
        WebSocketServer.users.get(playerB.getId()).sendMessage(message);
    }

    private void broadcastMove() {
        // need to read setp a and b
        lock.lock();
        try {
            JSONObject resp = new JSONObject();
            resp.put("event", "move");
            resp.put("a_direction", nextStepA);
            resp.put("b_direction", nextStepB);
            broadcastToPlayer(resp.toJSONString());
            nextStepA = nextStepB = null;
            // broadcastToPlayer(resp.toJSONString());
        } finally {
            lock.unlock();
        }
    }


    private String mapToString(){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < rows; i ++)
            for (int j = 0; j < cols; j ++)
                res.append(g[i][j]);
        return res.toString();
    }

    private void saveToDatabase() {
        Record record = new Record(
                null,
                playerA.getId(),
                playerA.getSx(),
                playerA.getSy(),
                playerB.getId(),
                playerB.getSx(),
                playerB.getSy(),
                playerA.getStepsString(),
                playerB.getStepsString(),
                mapToString(),
                loser,
                new Date()

        );
        WebSocketServer.recordMapper.insert(record);

    }

    private boolean check_overlap(List<Cell> A, List<Cell> B) {
        int n = A.size();
        Cell cell = A.get(n - 1);
        if (g[cell.x][cell.y] == 1) return false;

        for (int i = 0; i < n - 1; i ++)
            if (A.get(i).x == cell.x && A.get(i).y == cell.y)
                return false;

        for (int i = 0; i < n - 1;i ++)
            if (B.get(i).x == cell.x && B.get(i).y == cell.y)
                return false;
        return true;
    }


    private void check() {
        List<Cell> A = playerA.getCells();
        List<Cell> B = playerB.getCells();


        boolean vA = check_overlap(A, B);
        boolean vB = check_overlap(B, A);
        if (!vA || !vB) {
            st = "finished";

            if (!vA && !vB) loser = "all";
            else if (!vA) loser = "A";
            else loser = "B";
        }


    }


    @Override
    public void run(){
        // 13 * 14 =
        for (int i = 0; i < 1000; i ++) {
            if (nextStep()) {
                check();
                if (st.equals("playing")) {
                    broadcastMove();
                } else {
                    broadcastResult();
                    break;
                }
            } else {
                st = "finished";
                lock.lock();
                try {
                    if (nextStepA == null && nextStepB == null) loser = "all";
                    else if (nextStepA == null) loser = "A";
                    else loser = "B";
                } finally {
                    lock.unlock();
                }
                broadcastResult();
                break;
            }
        }
    }
}
