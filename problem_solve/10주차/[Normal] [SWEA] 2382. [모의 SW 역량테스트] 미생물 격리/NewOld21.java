import java.util.*;
import java.io.*;


class Solution
{
    static int N;
    static int K;
    static int M;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
	public static void main(String args[]) throws Exception
	{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
		

		for(int test_case = 1; test_case <= T; test_case++)
		{
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K =Integer.parseInt(st.nextToken());
            Queue<Node> micro = new ArrayDeque<>();

            for(int i=0; i<K; i++){
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken()) -1;

                micro.offer(new Node(x,y, d,c));
            }
            
            int ans = 0;
            for(int i=0; i<M; i++){
                int l = micro.size();
                int[][] sum = new int[N][N];
                int[][] max = new int[N][N];
                int[][] dir = new int[N][N];
                for(int j=0; j<l; j++){
                    Node node = micro.poll();
                    int nx = node.x + dx[node.dir];
                    int ny = node.y + dy[node.dir];

                    // 약품
                    if(checkConer(nx, ny)){
                        if(node.dir == 0 || node.dir == 2){
                            node.dir += 1;
                        }else{
                            node.dir -= 1;
                        }
                        sum[nx][ny] = node.cnt/2;
                        max[nx][ny] = node.cnt/2;
                        dir[nx][ny] = node.dir;
                    }
                    else{
                        // 미생물 합체
                        if(sum[nx][ny] > 0){
                            if(max[nx][ny] < node.cnt){
                                max[nx][ny] = node.cnt;
                                dir[nx][ny] = node.dir;
                            }
                            sum[nx][ny] += node.cnt;
                        }else{
                            sum[nx][ny] = node.cnt;
                            max[nx][ny] = node.cnt;
                            dir[nx][ny] = node.dir;
                        }
                    }
                }
                ans = 0;
                // 합친 미생물로 갱신
                for(int h=0; h<N; h++){
                    for(int w=0; w<N; w++){
                        if(sum[h][w] == 0)
                            continue;
                        ans += sum[h][w];
                        micro.offer(new Node(h, w, dir[h][w], sum[h][w]));
                    }
                }
            }

            System.out.println("#" + test_case + " " + ans);

		}
	}

    private static boolean checkConer(int x, int y){
        if(x==0 || x==N-1 || y==0 || y==N-1){
            return true;
        }
        return false;
    }
}

class Node{
    int x;
    int y;
    int dir;
    int cnt;

    Node(int x, int y, int dir, int cnt){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.cnt = cnt;
    }
}