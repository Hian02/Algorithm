/*
문제 정의

N개의 서로 다른 무게추가 주어집니다.
모든 무게추를 하나씩 순서대로 양팔저울에 올려야 합니다.

각 무게추를 올릴 때마다
1. 어떤 무게추를 먼저 올릴지 선택하고
2. 그 무게추를 왼쪽 또는 오른쪽에 올릴지 선택합니다.

단, 무게추를 올리는 모든 순간에 오른쪽 무게의 합 <= 왼쪽 무게의 합 조건을 만족해야 합니다.
모든 무게추를 올렸을 때 조건을 만족하는 모든 경우의 수를 구하는 문제입니다.
*/


/*
접근 방법

DFS + 백트래킹을 사용합니다.
DFS에서는 다음 정보를 관리합니다.
depth = 지금까지 올린 무게추의 개수
left = 현재 왼쪽 저울의 무게 합
right = 현재 오른쪽 저울의 무게 합
remain = 아직 사용하지 않은 무게추들의 총합

각 단계에서는 아직 사용하지 않은 무게추 하나를 선택합니다.

선택한 무게추 weight에 대해
1. 왼쪽에 놓기
왼쪽은 무게가 증가하므로 오른쪽이 왼쪽보다 커질 일이 없습니다.
따라서 항상 가능합니다.

2. 오른쪽에 놓기
right + weight <= left 일 때만 가능합니다.

모든 무게추를 사용했다면 하나의 올바른 경우를 완성한 것이므로 answer를 증가시킵니다.

추가 가지치기
현재 left >= right + remain이라면 남은 모든 무게추를 오른쪽에 전부 올려도 오른쪽이 왼쪽보다 커지지 않습니다.
따라서 이후에는 어떤 순서로 무게추를 선택하고, 각 무게추를 왼쪽/오른쪽 어디에 놓더라도 모두 가능합니다.

남은 무게추가 remainCount개라면
순서 선택 : remainCount!
좌우 선택 : 2^remainCount
이므로 remainCount! * 2^remainCount개의 경우를 한 번에 더할 수 있습니다.
*/


/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Solution {

    static int N;

    static int[] weight;
    static boolean[] visited;

    static long answer;

    static long[] factorial;
    static long[] powerOfTwo;

    static void dfs(int depth, int left, int right, int remain) {

        /*
        모든 무게추를 사용한 경우입니다.
        */
        if (depth == N) {
            answer++;
            return;
        }

        /*
        남아 있는 무게추를 전부 오른쪽에 올려도 right + remain <= left 라면 앞으로는 어떤 선택을 해도 오른쪽이 왼쪽보다 무거워질 수 없습니다.
        */
        if (left >= right + remain) {
            int remainCount = N - depth;
            /*
            남은 무게추의 순서
            remainCount!
            각 무게추의 좌우 선택
            2^remainCount
            */
            answer += factorial[remainCount] * powerOfTwo[remainCount];
            return;
        }

        /*
        아직 사용하지 않은 무게추를 하나씩 선택합니다.
        */
        for (int i = 0; i < N; i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            /*
            1. 현재 무게추를 왼쪽에 놓는 경우 왼쪽이 더 무거워지므로 항상 조건을 만족합니다.
            */
            dfs(depth + 1,left + weight[i],right,remain - weight[i]);
            /*
            2. 현재 무게추를 오른쪽에 놓는 경우 오른쪽에 올린 이후에도 오른쪽 <= 왼쪽이어야 합니다.
            */
            if (right + weight[i] <= left) {
                dfs(depth + 1,left,right + weight[i],remain - weight[i]);
            }

            /*
            현재 무게추를 사용하지 않은 상태로 복구합니다.
            다음 무게추를 선택하기 위한 백트래킹입니다.
            */
            visited[i] = false;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        /*
        N <= 9이므로
        0! ~ 9!
        2^0 ~ 2^9
        를 미리 계산합니다.
        */
        factorial = new long[10];
        powerOfTwo = new long[10];

        factorial[0] = 1;
        powerOfTwo[0] = 1;

        for (int i = 1; i <= 9; i++) {
            factorial[i] = factorial[i - 1] * i;
            powerOfTwo[i] = powerOfTwo[i - 1] * 2;
        }

        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            weight = new int[N];
            visited = new boolean[N];
            st =  new StringTokenizer(br.readLine());
          
            int total = 0;
            for (int i = 0; i < N; i++) {
                weight[i] = Integer.parseInt(st.nextToken());
                total += weight[i];
            }
            answer = 0;

            /*
            아직 아무 무게추도 올리지 않은 상태에서 시작합니다.
            depth = 0
            left = 0
            right = 0
            remain = 모든 무게추의 합
            */
            dfs(0,0,0,total);
            System.out.println("#" + tc + " " + answer);
        }
    }
}


/*
시간복잡도

가지치기가 전혀 없다고 생각하면 무게추를 올리는 순서는 N!가지이고
각 무게추마다 왼쪽/오른쪽 두 가지 선택이 있으므로 2^N가지가 있습니다.
따라서 단순한 최대 경우의 수는 O(N! * 2^N)

하지만 실제로는 right + weight > left인 경우 오른쪽에 놓는 탐색을 하지 않습니다.
또한 left >= right + remain이 되는 순간 남은 경우를 직접 탐색하지 않고 remainCount! * 2^remainCount를 한 번에 계산합니다.

따라서 실제 탐색량은 크게 줄어듭니다.
*/
