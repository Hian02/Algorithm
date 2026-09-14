/*
문제 정의

보물상자의 둘레에 N개의 16진수 숫자가 적혀 있습니다.
N은 항상 4의 배수이므로 각 변에는 N / 4개의 숫자가 존재합니다.

각 변의 숫자들은 하나의 16진수를 나타냅니다.
보물상자를 한 번 회전하면 모든 숫자가 시계 방향으로 한 칸 이동합니다.
각 회전 상태에서 4개의 숫자를 만들 수 있습니다.

이렇게 만들 수 있는 모든 숫자 중
1. 같은 숫자는 한 번만 사용하고
2. 큰 숫자부터 정렬한 뒤
3. K번째로 큰 숫자를 10진수로 출력하는 문제입니다.
*/


/*
접근 방법

한 변의 숫자 개수를 len = N / 4라고 하겠습니다.
예를 들어 N = 12라면 한 변에는 12 / 4 = 3개의 숫자가 있습니다.
초기 문자열이 1B3B3B81F75E라면 1B3, B3B, 81F, 75E 4개의 숫자를 얻을 수 있습니다.

보물상자를 한 칸 회전하면 E1B3B3B81F75가 되고
E1B, 3B3, B81, F75를 얻을 수 있습니다.

이 과정을 반복하면 됩니다.
중요한 점은 회전을 N번 할 필요가 없다는 것입니다.

한 변의 길이가 len이라면 len번 회전한 뒤에는 만들어지는 4개의 숫자 집합이 처음과 같아집니다.
따라서 0회전부터 len - 1회전까지만 확인하면 됩니다.

각 회전마다 4개의 문자열을 잘라서 Integer.parseInt(문자열, 16)을 사용하여 16진수를 10진수로 변환합니다.
그리고 TreeSet<Integer>를 Collections.reverseOrder()로 만들어 사용합니다.

TreeSet은
1. 중복 숫자를 자동으로 제거하고
2. 숫자를 자동으로 정렬합니다.

따라서 모든 숫자를 TreeSet에 넣은 뒤 앞에서부터 K번째 값을 찾으면 됩니다.
*/


/*
문제 풀이
*/

import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            /*
            보물상자의 전체 숫자 문자열
            */
            String str = br.readLine();

            /*
            한 변에 존재하는 숫자의 개수
            */
            int len = N / 4;


            /*
            TreeSet을 내림차순으로 생성합니다.
            같은 숫자가 들어오면 자동으로 중복 제거되고,큰 숫자부터 정렬됩니다.
            */
            TreeSet<Integer> numbers = new TreeSet<>(Collections.reverseOrder());

            /*
            len번만 회전하면 만들 수 있는 모든 경우를 확인할 수 있습니다.
            */
            for (int rotate = 0; rotate < len; rotate++) {
                /*
                현재 회전 상태에서 4개의 변을 확인합니다.
                */
                for (int side = 0; side < 4; side++) {
                    int start = side * len;
                    int end = start + len;

                    /*
                    현재 변의 16진수 문자열을 잘라냅니다.
                    예: "1B3"
                    */
                    String hex = str.substring(start, end);
                    /*
                    16진수 문자열을
                    10진수 정수로 변환합니다.
                    예:"1F7" -> 503
                    */
                    int value = Integer.parseInt( hex,16);
                    /*
                    TreeSet에 저장합니다.
                    중복된 숫자는 자동으로 제거됩니다.
                    */
                    numbers.add(value);
                }
                /*
                보물상자를 시계 방향으로 한 칸 회전합니다.
                마지막 문자가 가장 앞으로 이동합니다.
                예:ABCDEF->FABCDE
                */
                str = str.charAt(N - 1) + str.substring(0, N - 1);
            }
            /*
            TreeSet은 이미 내림차순으로 정렬되어 있으므로
            앞에서부터 K번째 숫자를 찾습니다.
            */
            int count = 0;
            int answer = 0;
            for (int number : numbers) {
                count++;
                if (count == K) {
                    answer = number;
                    break;
                }
            }
            System.out.println("#" + tc + " " + answer);
        }
    }
}


/*
시간복잡도

한 변의 길이는 N / 4입니다.
총 N / 4번 회전합니다.

각 회전에서 4개의 숫자를 만듭니다.
따라서 생성하는 숫자의 개수는 최대 4 * (N / 4 )= N개입니다.

각 숫자를 TreeSet에 넣을 때 O(log N) 의 시간이 필요합니다.
따라서 전체 시간복잡도는 O(N log N)
*/
