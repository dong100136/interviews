class Solution:
    def candy(self, nums: 'List[int]') -> 'int':
        BIGINT = 100003
        minv, mini = BIGINT, 0
        for i, v in enumerate(nums):
            if v < minv:
                minv = v
                mini = i
        nums = nums[mini:] + nums[:mini + 1]
        forward = [1]
        for i in range(1, len(nums)):
            if nums[i] > nums[i - 1]:
                forward.append(forward[-1] + 1)
            else:
                forward.append(1)
        
        backward = [1]
        for i in reversed(range(len(nums) - 1)):
            if nums[i] > nums[i + 1]:
                backward.append(backward[-1] + 1)
            else:
                backward.append(1)
        
        backward = reversed(backward)
        return sum(max(i, j) for i, j in zip(forward, backward)) - 1