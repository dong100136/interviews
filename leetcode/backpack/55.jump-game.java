/*
 * @lc app=leetcode id=55 lang=java
 *
 * [55] Jump Game
 *
 * https://leetcode.com/problems/jump-game/description/
 *
 * algorithms
 * Medium (30.64%)
 * Total Accepted:    214.8K
 * Total Submissions: 701K
 * Testcase Example:  '[2,3,1,1,4]'
 *
 * Given an array of non-negative integers, you are initially positioned at the
 * first index of the array.
 * 
 * Each element in the array represents your maximum jump length at that
 * position.
 * 
 * Determine if you are able to reach the last index.
 * 
 * Example 1:
 * 
 * 
 * Input: [2,3,1,1,4]
 * Output: true
 * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last
 * index.
 * 
 * 
 * Example 2:
 * 
 * 
 * Input: [3,2,1,0,4]
 * Output: false
 * Explanation: You will always arrive at index 3 no matter what. Its
 * maximum
 * jump length is 0, which makes it impossible to reach the last index.
 * 
 * 
 */
class Solution {
    public boolean canJump(int[] nums) {
        int n = nums.length;
        boolean[] marks = new boolean[n];

        marks[0] = true;
        for (int i = 0;i<n;i++){
            if (marks[i]==false){
                continue;
            }

            for (int j = 1;j<=nums[i]&&j+i<n;j++){
                marks[j+i]=true;
            }
        }

        // for (int i =0;i<n;i++){
        //     System.out.print(marks[i]+",");
        // }
        // System.out.println("");

        return marks[n-1];
    }

    public static void main(String[] args) {
        Solution solution= new Solution();
        System.out.println(solution.canJump(new int[]{2,3,1,1,4}));
        System.out.println(solution.canJump(new int[]{3,2,1,0,4}));
    }
}
