package com.wanfeng.javalearn.排序;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuickSort extends BaseData{
    public static void main(String[] args) {
        print();
        quick(arr, 0, arr.length - 1);
        print();
    }

    static void quick(int[] arr, int left, int right){
        if (right-left <= 0){
            return;
        }
        int baseNum = arr[right];
        int lp = left;
        int rp = right;
        while (lp!=rp){
            while (lp!=rp && arr[lp] < baseNum){
                lp++;
            }
            arr[rp] = arr[lp];
            while (lp!=rp && arr[rp] >= baseNum){
                rp--;
            }
            arr[lp] = arr[rp];
        }
        arr[lp] = baseNum;
        quick(arr,left,lp-1);
        quick(arr,lp+1,right);

    }

}
