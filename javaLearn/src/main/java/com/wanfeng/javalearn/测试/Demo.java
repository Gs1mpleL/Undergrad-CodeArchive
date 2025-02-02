package com.wanfeng.javalearn.测试;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        rob(new int[]{2,3,2});
    }
    public static int rob(int[] nums) {
        if (nums.length == 2){
            return Math.max(nums[0],nums[1]);
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = nums[1];
        boolean isOne = false;
        boolean isTwo = false;
        for (int i = 2; i < nums.length; i++) {
            if (i == 2){
                if (dp[i-2] + nums[i] > dp[i-1]){
                    isOne = true;
                }else if (dp[i-2] + nums[i] < dp[i-1]){
                    isTwo = true;
                }else {
                    isOne = true;
                    isTwo = true;
                }
                dp[i] = Math.max(dp[i-2] + nums[i],dp[i-1]);
                continue;
            }
            if (i == nums.length-1){
                if (isOne && !isTwo){
                    dp[i] = dp[i-1];
                    break;
                }
            }
            dp[i] = Math.max(dp[i-2] + nums[i],dp[i-1]);
        }
        return dp[dp.length-1];
    }
}
