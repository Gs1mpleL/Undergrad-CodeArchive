package com.wanfeng.javalearn.排序;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class MergeSort extends BaseData {
    public static void main(String[] args) {
        print();
        merge(arr,0,arr.length-1);
        print();
    }

    public static void merge(int[] arr,int left,int right){
        if (right-left <=0){
            return;
        }
        int mid = (left+right)/2;
        merge(arr,left,mid);
        merge(arr,mid+1,right);
        sort(arr,left,mid,right);
    }

    public static void sort(int[] arr, int start, int mid, int end) {
        //左边分组的起点i_start，终点i_end，也就是第一个有序序列
        int i_start = start;
        int i_end = mid;
        //右边分组的起点j_start，终点j_end，也就是第二个有序序列
        int j_start = mid + 1;
        int j_end = end;
        //额外空间初始化，数组长度为end-start+1
        int[] temp=new int[end-start+1];
        int len = 0;
        //合并两个有序序列
        while (i_start <= i_end && j_start <= j_end) {
            //当arr[i_start]<arr[j_start]值时，将较小元素放入额外空间，反之一样
            if (arr[i_start] < arr[j_start]) {
                temp[len] = arr[i_start];
                len++;
                i_start++;
            } else {
                temp[len] = arr[j_start];
                len++;
                j_start++;
            }
            //temp[len++]=arr[i_start]<arr[j_start]?arr[i_start++]:arr[j_start++];
        }

        //i这个序列还有剩余元素
        while(i_start<=i_end){
            temp[len] = arr[i_start];
            len++;
            i_start++;
        }
        //j这个序列还有剩余元素
        while(j_start<=j_end){
            temp[len] = arr[j_start];
            len++;
            j_start++;
        }
        //辅助空间数据覆盖到原空间
        System.arraycopy(temp, 0, arr, start, temp.length);
    }

}
