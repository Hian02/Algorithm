import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            String s = br.readLine();
            int len = N / 4;
            // 만들어진 숫자 저장
            int[] nums = new int[N];
            int size = 0;

            String ss = s + s;  // 원형 문자열 처리

            for (int r = 0; r < len; r++) {
                int start = (N - r) % N;

                for (int side = 0; side < 4; side++) {
                    int from = start + side * len;
                    String hex = ss.substring(from, from + len);
                    int num = Integer.parseInt(hex, 16);

                    boolean exist = false;

                    for (int i = 0; i < size; i++) {
                        if (nums[i] == num) {
                            exist = true;
                            break;
                        }
                    }

                    if (!exist) {
                        nums[size++] = num;
                    }
                }
            }

            Arrays.sort(nums, 0, size);
            int answer = nums[size - K];

            out.append("#").append(tc).append(" ").append(answer).append("\n");
        }

        System.out.print(out);
    }
}
