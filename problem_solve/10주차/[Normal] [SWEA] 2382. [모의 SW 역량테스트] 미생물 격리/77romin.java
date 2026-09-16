import java.util.*;
import java.io.*;

class Solution {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    private static int N, M, K;
    private static List<Cluster> clusters;
    private static int[][] area;
    
    private static final int[] dx = {0, 0, 0, -1, 1}; // 0, 상, 하, 좌, 우
    private static final int[] dy = {0, -1, 1, 0, 0}; // 0, 상, 하, 좌, 우
    
    static class Cluster {
        int y, x, n, d;
        Cluster(int y, int x, int n, int d) {
            this.y = y;
            this.x = x;
            this.n = n;
            this.d = d;
        }
    }
    
    private static int getMicorbes() {
        int cnt = 0;
        for(Cluster cluster : clusters)
            cnt += cluster.n;

        return cnt;
    }
    
    private static void spendTime() {
        // 1. 미생물 수 기준 내림차순 정렬
        Collections.sort(clusters, (a, b) -> Integer.compare(b.n, a.n));

        int[][] sumMap = new int[N][N]; // 합산 미생물 수
        int[][] dirMap = new int[N][N]; // 결정된 방향

        for (Cluster c : clusters) {
            if (c.n == 0) continue;

            // 이동
            c.y += dy[c.d];
            c.x += dx[c.d];

            // 가장자리(약품 구역) 처리
            if (c.y == 0 || c.y == N - 1 || c.x == 0 || c.x == N - 1) {
                c.n /= 2;
                if (c.d == 1) c.d = 2;
                else if (c.d == 2) c.d = 1;
                else if (c.d == 3) c.d = 4;
                else if (c.d == 4) c.d = 3;
            }

            if (c.n == 0) continue; // 소멸

            // 내림차순 정렬 상태이므로 빈 셀에 "가장 먼저 도달한 군집"이 무조건 원래 크기가 가장 컸던 군집!
            if (sumMap[c.y][c.x] == 0) {
                dirMap[c.y][c.x] = c.d; // 최대 군집 방향으로 확정
            }
            sumMap[c.y][c.x] += c.n;     // 미생물 수는 계속 누적 합산
        }

        // 2. 다음 시간을 위해 clusters 리스트 재구성
        clusters.clear();
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (sumMap[r][c] > 0) {
                    clusters.add(new Cluster(r, c, sumMap[r][c], dirMap[r][c]));
                }
            }
        }
    }
    
    private static void init() throws IOException {
        N=M=K=0;
        clusters = new ArrayList<>();
        
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N=Integer.parseInt(st.nextToken()); // The Length of Side
        M=Integer.parseInt(st.nextToken()); // Isolating Time
        K=Integer.parseInt(st.nextToken()); // The Num of Clusters
        
        // Initialize with -1
        area = new int[N][N];
        for(int i=0; i<N; i++)
            for(int j=0; j<N; j++)
                area[i][j] = -1;
        
        for(int i=0; i<K; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int n = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            clusters.add(new Cluster(y, x, n, d));
            area[y][x] = i; // area에 인덱스 채워놓기
        }
    }
    
	public static void main(String args[]) throws Exception {
        StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine().trim());

		for(int test_case = 1; test_case <= T; test_case++) {
			init();
            for(int i=0; i<M; i++) spendTime();
            sb.append("#").append(test_case).append(" ").append(getMicorbes()).append("\n");
		}
        System.out.print(sb);
	}
}
