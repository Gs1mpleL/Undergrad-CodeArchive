package com.wanfeng.javalearn.排序;


public class MaoPaoSort extends BaseData{
    public static void main(String[] args) {
        print();
        // 记录本次冒泡的终点
        int prePos = arr.length;
        for (int i = 0;i<arr.length;i++){
            // 记录下次冒泡的终点
            int nextPos = 0;
            for (int j = 0;j<prePos-1;j++){
                if (arr[j+1]<arr[j]){
                    swap(j+1,j);
                    nextPos = j+1;
                }
            }
            // 本次没有移动数据，说明已经有序了
            if (nextPos == 0){
                return;
            }
            prePos = nextPos;
        }
        print();
    }
}
