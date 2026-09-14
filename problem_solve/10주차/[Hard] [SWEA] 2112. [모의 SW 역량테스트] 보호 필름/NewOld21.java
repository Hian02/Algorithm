import java.util.*;
import java.io.*;

class Solution
{
    static int D;
    static int W;
    static int K;
    static int[][] film;
    static int ans;
	public static void main(String args[]) throws Exception
	{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
        StringTokenizer st;

		for(int test_case = 1; test_case <= T; test_case++)
		{
            st = new StringTokenizer(br.readLine());
            D = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
			film = new int[D][W];

            for(int i=0; i<D; i++){
                st = new StringTokenizer(br.readLine());
                for(int j=0; j<W; j++){
                    film[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            ans = Integer.MAX_VALUE;
            dfs(0, 0);

            System.out.println("#" + test_case + " " + ans);
		}
        br.close();
	}


    private static boolean checkFilm(){
        int i=0;
        for(; i<W; i++){
            int cnt = 1;
            int preNum = film[0][i];
            for(int j=1; j<D; j++){
                if(preNum != film[j][i]){
                    preNum = film[j][i];
                    cnt = 1;
                }else{
                    cnt++;
                }
                
                if(cnt>=K){
                    break;
                }
            }
            if(cnt<K)
                break;
        }

        if(i==W)
            return true;
        return false;
    }

    private static void dfs(int idx, int drug){
        if(ans<=drug){
            return;
        }
        if(checkFilm()){
            ans = drug;
            return;
        }
        if(idx==D){
            return;
        }
        int[] pre_film = new int[W];
        for(int i=0; i<W; i++){
            pre_film[i] = film[idx][i];
            film[idx][i] = 1;
        }
        dfs(idx+1, drug+1);
        for(int i=0; i<W; i++){
            film[idx][i] = 0;
        }
        dfs(idx+1, drug+1);
        for(int i=0; i<W; i++){
            film[idx][i] = pre_film[i];
        }
        dfs(idx+1, drug);
    }
}

// DFS 경우의 수	O(3^D)
// checkFilm()	O(D*W)
// 전체 시간복잡도	O(3^D * D * W)