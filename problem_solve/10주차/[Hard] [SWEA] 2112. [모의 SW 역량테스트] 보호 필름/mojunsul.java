import java.util.*;
import java.io.*;

class Solution {

    static int D, W, K, min;
    static boolean[][] map;

    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            D = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            
            map = new boolean[D][W];
            
            for (int i = 0; i < D; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < W; j++) {
                    map[i][j] = st.nextToken().equals("1");
                }
            }
            
            min = K;
            
            if (K == 1 || check()) {
                min = 0;
            } else {
                dfs(0, 0);
            }

            sb.append("#")
                .append(tc)
                .append(" ")
                .append(min)
                .append("\n");
        }

        System.out.print(sb);
        br.close();
    }
    
    private static void dfs(int depth, int cnt) {
        if (cnt >= min) return;
        
        if (depth == D) {
            if (check()) {
                min = cnt;
            }
            return;
        }
        
        dfs(depth + 1, cnt);
        
        boolean[] origin = map[depth].clone();
        
        boolean[] aFilm = new boolean[W];
        map[depth] = aFilm;
        dfs(depth + 1, cnt + 1);
        
        boolean[] bFilm = new boolean[W];
        Arrays.fill(bFilm, true);
        map[depth] = bFilm;
        dfs(depth + 1, cnt + 1);
        
        map[depth] = origin;
    }
    
    private static boolean check() {
        for (int j = 0; j < W; j++) {
            int cnt = 1;
            boolean pass = false;
            
            if (K == 1) continue;

            for (int i = 1; i < D; i++) {
                if (map[i - 1][j] == map[i][j]) {
                    cnt++;
                } else {
                    cnt = 1;
                }
                
                if (cnt >= K) {
                    break;
                }
            }
            if (cnt < K) return false;
        }
        return true;
    }
}
