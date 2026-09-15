import java.util.*;
import java.io.*;

class Solution {

    static int D, W, K, min;
    static boolean[][] map;
    static int[] drug;

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
            drug = new int[D];
            
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

        // 굳이 직접 map 바꿔주는게 아닌 약품 투입 여부 정보 저장해서 체크
        // 이로 인해 377ms -> 284ms 로 최적화됨
        drug[depth] = 0;
        dfs(depth + 1, cnt);
        
        drug[depth] = 1;
        dfs(depth + 1, cnt + 1);
        
        drug[depth] = 2;
        dfs(depth + 1, cnt + 1);
        
        drug[depth] = 0;
    }
    
    private static boolean check() {
        for (int j = 0; j < W; j++) {
            int cnt = 1;
            
            boolean prev = (drug[0] == 0) ? map[0][j] : (drug[0] == 2);
            
            if (K == 1) continue;

            for (int i = 1; i < D; i++) {
                boolean cur = (drug[i] == 0) ? map[i][j] : (drug[i] == 2);
                
                if (prev == cur) {
                    cnt++;
                } else {
                    cnt = 1;
                    prev = cur;
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
