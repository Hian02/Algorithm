import java.io.*;
import java.util.*;

public class Solution {
    static int N, M, K;
    static int[][] d = {{-1, 0},{1, 0},{0, -1},{0, 1}};

    static class Group {
        int cnt;
        int dir;
        int max;

        Group(int cnt, int dir) {
            this.cnt = cnt;
            this.dir = dir;
            this.max = cnt;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            Group[][] map = new Group[N][N];

            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine());

                int r = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                int cnt = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken()) - 1;

                map[r][c] = new Group(cnt, dir);
            }

            for (int time = 0; time < M; time++) {
                Group[][] next = new Group[N][N];

                for (int r = 0; r < N; r++) {
                    for (int c = 0; c < N; c++) {

                        if (map[r][c] == null) continue;

                        Group g = map[r][c];

                        // 현재 방향으로 한 칸 이동
                        int nr = r + d[g.dir][0];
                        int nc = c + d[g.dir][1];

                        int cnt = g.cnt;
                        int dir = g.dir;

                        if (nr == 0 || nr == N - 1 ||
                            nc == 0 || nc == N - 1) {
                            cnt /= 2;
                            dir ^= 1;
                        }
                        
                        if (cnt == 0) continue; // 미생물이 모두 죽은 경우
                        if (next[nr][nc] == null) { // 아직 아무 군집도 없는 칸
                            next[nr][nc] = new Group(cnt, dir);
                        } else {
                            // 같은 위치로 이동한 군집 합치기
                            Group merged = next[nr][nc];
                            merged.cnt += cnt;
                            
                            if (cnt > merged.max) { // 가장 큰 개별 군집의 방향 유지
                                merged.max = cnt;
                                merged.dir = dir;
                            }
                        }
                    }
                }

                map = next;
            }

            int answer = 0;

            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    if (map[r][c] != null) answer += map[r][c].cnt;
                }
            }

            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }

        System.out.print(sb);
    }
}
