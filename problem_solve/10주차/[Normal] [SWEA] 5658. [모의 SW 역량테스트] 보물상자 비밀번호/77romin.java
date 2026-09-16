import java.util.*;
import java.io.*;

class Solution {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int N;
    private static int K;
    
    private static int answer;
    private static Deque<String> queue;
    private static HashSet<Integer> set;
    private static int[] arr;
    
	public static void main(String args[]) throws Exception {
		int T = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

		for(int test_case = 1; test_case <= T; test_case++) {
            answer = 0;
            queue = new ArrayDeque<>();
            set = new HashSet<>();
            
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            
            String[] bits = br.readLine().trim().split("");

            for(int i=0; i<N; i++) queue.offerLast(bits[i]);
            
            for(int i=0; i<N/4; i++) rotateBox();
            
            arr = new int[set.size()];
            
            int cnt = 0;
            for(int n : set) arr[cnt++]=n;
            
            Arrays.sort(arr);
            
            answer = arr[set.size()-K];
            
            sb.append("#").append(test_case).append(" ")
                .append(answer)
                .append("\n");
		}
        System.out.print(sb);
	}
    
    private static void rotateBox() {
        for(int i=0; i<4; i++) {
            String snum = "";
            for(int j=0; j<N/4; j++) {
                String bit = queue.pollFirst();
                snum += bit;
                queue.offerLast(bit);
            }
            set.add(Integer.parseInt(snum, 16));
        }
        queue.offerLast(queue.pollFirst());
    }
}
