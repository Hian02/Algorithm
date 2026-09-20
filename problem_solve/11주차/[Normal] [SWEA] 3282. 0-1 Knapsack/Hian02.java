/*
문제 정의

N개의 물건이 주어집니다.
각 물건은 Vi : 물건의 부피, Ci : 물건의 가치들을 가지고 있습니다.

가방에는 최대 K의 부피만큼 물건을 넣을 수 있습니다.
각 물건은 최대 한 번만 선택할 수 있으며, 선택한 물건들의 부피 합이 K 이하여야 합니다.

이 조건을 만족하면서 선택한 물건들의 가치 합을 최대화하는 문제입니다.
*/

/*
접근 방법

0/1 Knapsack(배낭 문제)을 DP로 해결합니다.

각 물건은 1. 선택한다. 2. 선택하지 않는다. 두 가지 경우가 존재합니다.
dp[c] = 가방의 최대 부피가 c일 때 얻을 수 있는 최대 가치

현재 물건의 부피가 volume, 가치가 value라고 하겠습니다.
현재 물건을 선택하지 않는 경우에는 dp[c]를 그대로 사용합니다.
현재 물건을 선택하는 경우에는 dp[c - volume] + value를 사용합니다.
따라서 점화식은 dp[c] = Math.max(dp[c], dp[c - volume] + value)입니다.

중요한 점은 부피를 K부터 volume까지 내림차순으로 탐색해야 한다는 것입니다.
오름차순으로 탐색하면 현재 물건을 사용해서 갱신한 dp값을 같은 물건을 처리하는 도중 다시 사용할 수 있습니다.
그러면 하나의 물건을 여러 번 선택한 것처럼 계산됩니다.

따라서 각 물건을 한 번만 사용할 수 있도록 for (int c = K; c >= volume; c--)형태로 역순 갱신합니다.
*/


/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            /*
            dp[c]: 가방의 최대 부피가 c일 때 얻을 수 있는 최대 가치입니다.
            처음에는 어떤 물건도 선택하지 않았으므로 모든 값은 0으로 초기화됩니다.
            */
            int[] dp = new int[K + 1];
            /*
            N개의 물건을 하나씩 확인합니다.
            */
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                int volume = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());
                /*
                현재 물건을 선택할 수 있는 모든 가방 부피에 대해 DP를 갱신합니다.
                물건을 중복 선택하지 않도록 부피를 반드시 내림차순으로 확인합니다.
                */
                for (int c = K; c >= volume; c--) {
                    /*
                    현재 물건을 선택하지 않는 경우 dp[c]
                    현재 물건을 선택하는 경우 dp[c - volume] + value
                    두 경우 중 더 큰 가치를 선택합니다.
                    */
                    dp[c] = Math.max(dp[c],dp[c - volume] + value);
                }
            }
            /*
            최대 K의 부피를 사용할 수 있을 때 얻을 수 있는 최대 가치를 출력합니다.
            */
            System.out.println("#" + tc + " " + dp[K]);
        }
    }
}


/*
시간복잡도

N개의 물건을 하나씩 확인합니다.
각 물건마다 가방의 부피 K부터 현재 물건의 부피까지 확인합니다.
따라서 최악의 경우 O(N * K)입니다.
*/
