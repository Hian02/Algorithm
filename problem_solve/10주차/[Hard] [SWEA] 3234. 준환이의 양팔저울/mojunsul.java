import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {

    static int N, ans, total;
    static int[] weight;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            weight = new int[N];
            total = 0;
            for (int i = 0; i < N; i++) {
                weight[i] = Integer.parseInt(st.nextToken());
                total += weight[i];
            }
            ans = 0;
            total /= 2;
            dfs(0, 0, 0, 0);

            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.println(sb);
        br.close();
    }

    private static void dfs(int cnt, int left, int right, int mask) {
        if (right > left) {
            return;
        }

        if (cnt == N) {
            ans++;
            return;
        }

        for (int i = 0; i < N; i++) {
            if (((1 << i) & mask) != 0) continue;
            int nextMask = mask | (1 << i);
            dfs(cnt + 1, left + weight[i], right, nextMask);
            dfs(cnt + 1, left, right + weight[i], nextMask);
        }

    }
}
