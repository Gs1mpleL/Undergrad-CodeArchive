package com.wanfeng.javalearn.other;


import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            str.append(sc.next());
            str.append("\n");
        }
        String[] strArr = str.toString().split("\n");
        int lineSize = strArr[0].split(",").length;
        int shuSize = strArr.length;
        int[][] daoyu = new int[shuSize][lineSize];
        for(int i = 0;i<shuSize;i++){
            String[] onePack = strArr[i].split(",");
            for(int j = 0;j<lineSize;j++){
                daoyu[i][j] = Integer.parseInt(onePack[j]);
            }
        }

        boolean[][] isVisit = new boolean[shuSize][lineSize];
        int ans = 0;
        for(int i = 0;i<shuSize;i++){
            for(int j = 0;j<lineSize;j++){
                // 发现岛屿
                if(daoyu[i][j] == 1 && !isVisit[i][j]){
                    ans = Math.max(ans,checkDaoyu(daoyu,i,j,isVisit));
                }
            }
        }
        System.out.println(ans);
    }

    private static int checkDaoyu(int[][] daoyu, int i, int j, boolean[][] isVisit) {
        isVisit[i][j] = true;
        int sum = 1;
        LinkedList<String> dirQueue = new LinkedList<>();
        dirQueue.addFirst(i +","+j);
        while(!dirQueue.isEmpty()){
            String dirStr = dirQueue.removeLast();
            String[] dirArr = dirStr.split(",");
            int curI= Integer.parseInt(dirArr[0]);
            int curJ = Integer.parseInt(dirArr[1]);

            // 右边
            int rightCurI = curI;
            int rightCurJ = curJ + 1;
            if(rightCurJ < daoyu[0].length && rightCurI<daoyu.length && daoyu[rightCurI][rightCurJ] == 1 && !isVisit[rightCurI][rightCurI]){
                isVisit[rightCurI][rightCurJ] = true;
                sum ++;
                dirQueue.addFirst(rightCurI + "," + rightCurJ);
            }

            // 下边
            int nextCurI = curI +1;
            int nextCurJ = curJ;
            if(nextCurJ < daoyu[0].length && nextCurI<daoyu.length && daoyu[nextCurI][nextCurJ] == 1 && !isVisit[nextCurI][nextCurJ]){
                isVisit[nextCurI][nextCurJ] = true;
                sum ++;
                dirQueue.addFirst(nextCurI + "," + nextCurJ);
            }

            // 左边
            int leftCurI = curI;
            int leftCurJ = curJ - 1;
            if(leftCurJ >= 0  && leftCurI >= 0 && daoyu[leftCurI][leftCurJ] == 1 && !isVisit[leftCurI][leftCurJ]){
                isVisit[leftCurI][leftCurJ] = true;
                sum ++;
                dirQueue.addFirst(leftCurI + "," + leftCurJ);
            }

            // 左边
            int upCurI = curI - 1 ;
            int upCurJ = curJ;
            if(upCurJ >= 0  && upCurI >= 0 && daoyu[upCurI][upCurJ] == 1 && !isVisit[upCurI][upCurJ]){
                isVisit[upCurI][upCurJ] = true;
                sum ++;
                dirQueue.addFirst(upCurI + "," + upCurJ);
            }

        }
        return sum;
    }


    public int[] findBuilding (int[] heights) {
        // 看题目是使用单调栈
        // 但是题意不清晰，不知道在干什么，30m高的楼，前后都比它高，为什么还能看见3个楼顶？？？
        // 题目还有错别字
        // 排成一排，高楼挡不住后边的楼么？？？
        LinkedList<Integer> stack  = new LinkedList<>();
        HashMap<Integer, Integer> ans = new HashMap<>();
        for (int a : heights) {
            ans.put(a,1);
        }
        stack.push(heights[0]);
        for (int i = 1; i < heights.length; i++) {
            while (stack.peek() >= heights[i]){
                Integer pop = stack.pop();
                continue;
            }
            stack.push(heights[i]);
        }
        int[] ints = new int[ans.size()];
        ArrayList<Integer> values = (ArrayList<Integer>) ans.values();
        for (int i = 0; i < values.size(); i++) {
            ints[i] =  values.get(i);
        }
        return ints;
    }



}
