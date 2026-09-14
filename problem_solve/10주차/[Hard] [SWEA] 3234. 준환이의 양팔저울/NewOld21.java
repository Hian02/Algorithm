import java.util.*;
import java.io.*;

class Solution
{
    static int N;
    static int[] chu;
    static int ans;
	public static void main(String args[]) throws Exception
	{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
		

		for(int test_case = 1; test_case <= T; test_case++)
		{
            N = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            chu = new int[N];
            for(int i=0; i<N; i++){
                chu[i] = Integer.parseInt(st.nextToken());
            }

            ans = 0;
            boolean[] used = new boolean[N];
            dfs(0, 0, 0, used);
            
			System.out.println("#" + test_case + " " + ans);
		}
	}

    private static void dfs(int cnt, int left, int right, boolean[] used){
        if(left<right){
            return;
        }
        int sum = 0;
        for(int i=0; i<N; i++){
            if(!used[i]){
                sum += chu[i];
            }
        }
        if(left>= right+sum){
            ans += Math.pow(2, N-cnt) - 1;
        }

        if(cnt==N){
            ans++;
            return;
        }

        for(int i=0; i<N; i++){
            if(!used[i]){
                used[i] = true;
                dfs(cnt+1, left+chu[i], right, used);
                dfs(cnt+1, left, right+chu[i], used);
                used[i] = false;
            }
        }
    }
}