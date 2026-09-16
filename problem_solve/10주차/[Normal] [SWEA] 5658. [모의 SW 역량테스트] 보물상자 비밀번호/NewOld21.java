import java.util.*;
import java.io.*;


class Solution
{
    static int N;
    static int K;
	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
        StringTokenizer st;


		for(int test_case = 1; test_case <= T; test_case++)
		{
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            String[] str = br.readLine().split("");

            List<Integer> arr = new ArrayList<>();

            for(int i=0; i<N; i++){
                String s = "";
                for(int j=0; j<N/4; j++){
                    s += str[(i+j)%N];
                }
                
                int num = Integer.parseInt(s, 16); 
                if(!arr.contains(num))
                    arr.add(num);
            }
            Collections.sort(arr, Collections.reverseOrder());

            System.out.println("#"+ test_case + " " + arr.get(K-1) );
		}
	}
}

// 순차 탐색 O(N^2)​
// 정렬 O(NlogN)
// 시간 복잡도 O(N^2+NlogN) => O(N^2)​