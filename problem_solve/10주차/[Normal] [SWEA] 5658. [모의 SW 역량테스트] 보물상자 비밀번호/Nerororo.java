
import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

class Solution
{
    public static void main(String args[]) throws Exception
    {
//        System.setIn(new FileInputStream("res/sample_input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            sb.append('#').append(test_case).append(' ');

            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int range = N / 4;

            char[] chest = br.readLine().toCharArray();

            sb.append(chestPass(N, K, range, chest)).append('\n');
        }
        System.out.println(sb.toString());
    }

    private static int chestPass(int N, int K, int range, char[] chest) {

        List<Integer> list = new ArrayList<>();

        for (int startIndex = 0; startIndex < range; startIndex++) {
            int len = 0;
            int result = 0;

            for (int i = startIndex; i < N + startIndex; i++) {
                int idx = i;
                if (i >= N) idx %= N;

                if (chest[idx] >= '0' && chest[idx] <= '9') {
                    result = result * 16 + chest[idx] - '0';
                } else {
                    result = result * 16 + chest[idx] - 'A' + 10;
                }

                len++;

                if (len >= range) {
                    if (!list.contains(result)) list.add(result);

                    len = 0;
                    result = 0;
                }
            }
        }

        list.sort(Collections.reverseOrder());
//
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
        return list.get(K - 1);
    }
}

/*
전체 순회(O(n^2)) * contains(O(n))
결과적으로 시간복잡도 n^3을 가진다.

검색해보니 Set 썼다가 List로 변환하면 시간 줄일 수 있다는데
자료구조 바꿔가면서까지 구..ㄷ..이?
 */