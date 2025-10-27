package leetcode;

import java.util.Arrays;

public class Question_2_3ClosetSum {
    // Approach..
    // Sort array
    // fiexd the element
    // Apply two pointer Approach
    // return result
    public int twoSumCloset(int [] nums, int start,int target){
        int left = start;
        int right = nums.length-1;
        int closetSum = 100000;
        while(left<right){
            int currentSum = nums[left]+nums[right];
            if(Math.abs(currentSum-target)<Math.abs(closetSum-target)) closetSum = currentSum;
            if(currentSum<target) left++;
            else if(currentSum>target) right--;
            else return currentSum; // perfect match
        }
        return closetSum;
    }
    public int threeSumClosest(int[] nums, int target) {
        int n = nums.length;
        Arrays.sort(nums);
        int closetSum = 100000;
        for(int i = 0;i<n-2;i++){
            int fixedNum = nums[i];
            int twoSum = twoSumCloset(nums,i+1,target-fixedNum);
            int currentSum = twoSum+fixedNum;
            if(Math.abs(currentSum-target)<Math.abs(closetSum-target)){
                closetSum=currentSum;
            }
        }
        return closetSum;

    }
}
