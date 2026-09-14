/*
문제 정의

N x N 크기의 구역에 K개의 미생물 군집이 있습니다.
각 군집은
- 세로 위치
- 가로 위치
- 미생물 수
- 이동 방향
을 가지고 있습니다.

매 시간 다음 과정이 진행됩니다.
1. 모든 군집이 자신의 방향으로 한 칸 이동합니다.
2. 가장자리 약품 셀에 도착한 경우
   미생물 수가 절반으로 감소하고
   이동 방향이 반대로 바뀝니다.
3. 이동이 모두 끝난 후 같은 셀에 여러 군집이 있다면 하나로 합쳐집니다.
   미생물 수는 모두 더하고,
   방향은 합쳐지기 전 가장 많은 미생물을 가지고 있던 군집의 방향을 따릅니다.
이 과정을 M시간 반복한 후
남아 있는 모든 미생물 수의 합을 구하는 문제입니다.
*/


/*
접근 방법

현재 존재하는 군집들을 ArrayList에 저장합니다.
매 시간마다 새로운 N x N 배열을 하나 만듭니다.
Group[][] map
모든 군집에 대해 다음 작업을 합니다.

1. 이동
현재 방향에 따라 한 칸 이동합니다.

2. 약품 처리
이동한 위치가 가장자리라면 count /= 2를 수행하고 방향을 반대로 변경합니다.
미생물 수가 0이 되었다면 해당 군집은 사라집니다.

3. 병합
이동한 위치 map[r][c]가 비어 있다면현재 군집을 그대로 저장합니다.
이미 다른 군집이 있다면 기존 미생물 수 += 현재 미생물 수로 합칩니다.
하지만 여기서 방향을 정하기 위해서는 합쳐지기 전 가장 큰 군집의 미생물 수를 따로 기억해야 합니다.
따라서 Group 클래스에 maxCount를 둡니다.

예를 들어
8마리 -> 오른쪽
14마리 -> 위쪽
3마리 -> 왼쪽
한 곳에 모였다면 전체 미생물 수는 8 + 14 + 3 = 25가 되지만 방향은 가장 많았던 14마리 군집의 위쪽 방향을 사용해야 합니다.
모든 군집 이동이 끝나면 map에 존재하는 군집들을 다시 ArrayList에 저장합니다.
이 작업을 M번 반복합니다.
*/


/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Solution {
    static int N;
    static int M;
    static int K;
    /*
    방향
    1 : 상
    2 : 하
    3 : 좌
    4 : 우
    */
    static int[] dr = {0, -1, 1, 0, 0};
    static int[] dc = {0, 0, 0, -1, 1};

    static class Group {
        int r;
        int c;
        int count;
        int dir;
        /*
        같은 셀에서 여러 군집이 합쳐질 때 어느 군집의 방향을 사용할지 판단하기 위해 사용합니다.
        count는 이미 여러 군집의 합이 될 수 있기 때문에 방향을 결정할 때 count를 직접 사용하면 안 됩니다.
        */
        int maxCount;
        Group(int r, int c, int count, int dir) {
            this.r = r;
            this.c = c;
            this.count = count;
            this.dir = dir;
            this.maxCount = count;
        }
    }

    /*
    이동 방향을 반대로 변경합니다.
    상 <-> 하
    좌 <-> 우
    */
    static int reverse(int dir) {
        if (dir == 1) return 2;
        if (dir == 2) return 1;
        if (dir == 3) return 4;
        return 3;
    }

    /*
    현재 위치가 약품이 칠해진 가장자리인지 확인합니다.
    */
    static boolean isEdge(int r, int c) {
        return r == 0 || r == N - 1 || c == 0 || c == N - 1;
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            /*
            현재 존재하는 미생물 군집들을 저장합니다.
            */
            ArrayList<Group> groups = new ArrayList<>();
            /*
            최초 K개의 군집 정보 입력
            */
            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine());
                int r = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                int count = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken());

                groups.add(new Group(r,c,count,dir));
            }

            /*
            M시간 동안 시뮬레이션을 진행합니다.
            */
            for (int time = 0; time < M; time++) {
                /*
                이번 시간에 군집들이 이동할 위치를 저장합니다.
                같은 위치에 여러 군집이 들어오면 여기에서 바로 합칩니다.
                */
                Group[][] map = new Group[N][N];

                /*
                현재 존재하는 모든 군집을 이동시킵니다.
                */
                for (Group group : groups) {
                    /*
                    1. 현재 방향으로 한 칸 이동
                    */
                    int nr = group.r + dr[group.dir];
                    int nc = group.c + dc[group.dir];
                    int count = group.count;
                    int dir = group.dir;

                    /*
                    2. 약품이 있는 가장자리에 도착한 경우 미생물 수를 절반으로 줄이고  이동 방향을 반대로 변경합니다.
                    */
                    if (isEdge(nr, nc)) {
                        count /= 2;
                        dir = reverse(dir);
                    }
                    /*
                    미생물 수가 0이 되면 해당 군집은 사라집니다.
                    */
                    if (count == 0) {
                        continue;
                    }
                    /*
                    3. 현재 위치에 다른 군집이 없는 경우
                    */
                    if (map[nr][nc] == null) {
                        map[nr][nc] = new Group( nr, nc, count, dir);
                    }
                    /*
                    현재 위치에 이미 다른 군집이 있는 경우
                    두 군집을 합칩니다.
                    */
                    else {
                        Group merged = map[nr][nc];
                        /*
                        전체 미생물 수를 더합니다.
                        */
                        merged.count += count;
                        /*
                        현재 들어온 군집의 미생물 수가 기존에 가장 컸던 군집보다 많다면
                        합쳐진 군집의 이동 방향을 현재 군집의 방향으로 변경합니다.
                        */
                        if (count > merged.maxCount) {
                            merged.maxCount = count;

                            merged.dir = dir;
                        }
                    }
                }


                /*
                모든 군집의 이동과 병합이 끝났습니다.
                다음 시간에 사용할 groups를 다시 만듭니다.
                */
                groups.clear();

                for (int r = 0; r < N; r++) {
                    for (int c = 0; c < N; c++) {
                        if (map[r][c] != null) {
                            groups.add(map[r][c]);
                        }
                    }
                }
            }

            /*
            M시간이 지난 후 모든 군집의 미생물 수를 더합니다.
            */
            int answer = 0;
            for (Group group : groups) {
                answer += group.count;
            }

            System.out.println("#" + tc + " " + answer);
        }
    }
}


/*
시간복잡도

M시간 동안 시뮬레이션합니다.
각 시간마다 현재 존재하는 군집들을 한 번씩 이동시키므 O(K) 정도가 필요합니다.
또한 새로운 군집 목록을 만들기 위해 N x N 크기의 map을 확인합니다.
O(N^2)
따라서 전체 시간복잡도는 O(M * (K + N^2))

*/
