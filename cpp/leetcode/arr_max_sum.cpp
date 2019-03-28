class Solution {
public:
  /**
   * @param s1: A string
   * @param s2: A string
   * @param s3: A string
   * @return: Determine whether s3 is formed by interleaving of s1 and s2
   */
  bool isInterleave(string &s1, string &s2, string &s3) {
    // write your code here
    int n1 = s1.size(), n2 = s2.size(), n3 = s3.size();
    if (n3 != n1 + n2)
      return false;

    vector<vector<bool>> dp(n1 + 1, vector<bool>(n2 + 1));

    dp[0][0] = true;
    for (int i = 1; i <= n1; i++) {
      dp[i][0] = s1[i - 1] == s3[i - 1] && dp[i - 1][0];
    }

    for (int i = 1; i <= n2; i++) {
      dp[0][i] = s2[i - 1] == s3[i - 1] && dp[0][i - 1];
    }

    for (int i = 1; i <= n1; i++) {
      for (int j = 0; j <= n2; j++) {
        int k = i + j - 1;

        dp[i][j] = (s3[k] == s1[i - 1] && dp[i - 1][j]) ||
                   (s3[k] == s2[j - 1] && dp[i][j - 1]);
      }
    }

    return dp[n1][n2];
  }
};