import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
 
class Solution {
 
    static int N;
    static int[] weight;
    static int[][] dp;
 
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
 
        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            weight = new int[N];
            int sum = 0;
            for (int i = 0; i < N; i++) {
                weight[i] = Integer.parseInt(st.nextToken());
                sum += weight[i];
            }
 
            // dp[mask][diff] mask로 뭐 사용했는지 보니까 diff만 넣어도 됨
            // 메모이제이션으로 최적화, 1,889 ms -> 85 ms
            dp = new int[1 << N][sum + 1];
            for (int i = 0; i < (1 << N); i++) {
                Arrays.fill(dp[i], -1);
            }
 
            int ans = dfs(0, 0);
 
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.println(sb);
        br.close();
    }
 
    private static int dfs(int mask, int diff) {
        if (diff < 0) {
            return 0;
        }
 
        if (mask == (1 << N) - 1) {
            return 1;
        }
 
        if (dp[mask][diff] != -1) {
            return dp[mask][diff];
        }
 
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            if ((mask & (1 << i)) != 0) continue;
 
            int nextMask = mask | (1 << i);
            cnt += dfs(nextMask, diff + weight[i]);
            cnt += dfs(nextMask, diff - weight[i]);
        }
 
        return dp[mask][diff] = cnt;
    }
}
