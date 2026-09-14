import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Solution {

    static class Micro {
        int y, x, num, d;
        int maxCount;

        public Micro(int y, int x, int num, int d) {
            this.y = y;
            this.x = x;
            this.num = num;
            this.d = d;
            this.maxCount = num;
        }

        public void addMicro(int newNum, int d) {
            if (newNum > maxCount) {
                maxCount = newNum;
                this.d = d;
            }
            this.num += newNum;
        }
    }

    static int N, M;
    static List<Micro> micro;

    // 1: 상, 2: 하, 3: 좌, 4: 우
    static final int[] dy = {0, -1, 1, 0, 0};
    static final int[] dx = {0, 0, 0, -1, 1};

    public static void main(String args[]) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            sb.append('#').append(test_case).append(' ');

            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            micro = new ArrayList<>();

            for (int i = 0; i < K; i++) {

                st = new StringTokenizer(br.readLine());

                int y = Integer.parseInt(st.nextToken());
                int x = Integer.parseInt(st.nextToken());
                int num = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());

                micro.add(new Micro(y, x, num, d));
            }

            for (int i = 0; i < M; i++) {
                move();
            }

            int answer = 0;

            for (Micro m : micro) {
                answer += m.num;
            }

            sb.append(answer).append('\n');
        }

        System.out.print(sb);
    }

    private static void move() {
        Micro[][] map = new Micro[N][N];

        for (Micro m : micro) {

            int ny = m.y + dy[m.d];
            int nx = m.x + dx[m.d];

            int num = m.num;
            int d = m.d;

            if (!isIn(ny, nx)) {
                num /= 2;

                d = reverse(d);
            }

            if (num == 0) {
                continue;
            }

            if (map[ny][nx] == null) {
                map[ny][nx] = new Micro(ny, nx, num, d);
            } else {
                map[ny][nx].addMicro(num, d);
            }
        }

        micro = new ArrayList<>();

        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {

                if (map[y][x] != null) {
                    micro.add(map[y][x]);
                }
            }
        }
    }

    private static int reverse(int d) {
        if (d == 1) return 2;
        if (d == 2) return 1;
        if (d == 3) return 4;

        return 3;
    }

    private static boolean isIn(int y, int x) {
        return y > 0 && y < N - 1 && x > 0 && x < N - 1;
    }
}


/*
별도의 자료구조를 만들어서 편하게 처리했다.
매 시간마다 새 배열을 만들어서 해당 배열에 이미 Micro가
존재하면 둘을 합친다.
 */