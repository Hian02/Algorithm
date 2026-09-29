/*
문제 정의

원점 (0, 0)에서 시작하여 화살표에 저장된 방향대로 이동하면서 선을 그립니다.
방향은 0부터 7까지이며 각각 8방향을 나타냅니다.
0 : 위
1 : 오른쪽 위
2 : 오른쪽
3 : 오른쪽 아래
4 : 아래
5 : 왼쪽 아래
6 : 왼쪽
7 : 왼쪽 위
선을 그리는 과정에서 닫힌 공간이 만들어지면 하나의 방이 생깁니다.
화살표에 따라 모든 이동을 끝냈을 때 만들어지는 방의 개수를 구하는 문제입니다.
*/


/*
접근 방법

그래프로 생각해서 해결합니다.
현재 위치를 하나의 정점, 현재 위치에서 다음 위치까지 그은 선을 하나의 간선이라고 생각합니다.
방이 만들어지는 핵심 조건은 이미 방문한 정점에 처음 사용하는 간선으로 도착하는 경우입니다.
예를 들어 A -> B -> C -> D까지 이동한 뒤 D -> A로 이동한다고 생각해보겠습니다.
A는 이미 방문했던 정점이고 D -> A라는 간선은 처음 사용하는 간선이므로 하나의 사이클이 완성되고 방이 하나 만들어집니다.

따라서 다음 조건을 만족하면 answer를 1 증가시킵니다.
1. 다음 위치를 이전에 방문한 적이 있다.
2. 현재 위치와 다음 위치를 연결하는 간선은 지나간 적이 없다.
반대로 이미 지나간 간선을 다시 이동하는 경우에는 새로운 방이 만들어지지 않습니다.

하지만 한 가지 중요한 문제가 있습니다.
대각선이 서로 교차하는 경우입니다.
예를 들어
\ /
 X
/ \
처럼 두 대각선이 정점이 아닌 중간 지점에서 교차할 수 있습니다.
좌표를 한 칸씩만 이동하면 교차 지점이 정점으로 기록되지 않기 때문에 방을 제대로 셀 수 없습니다.
이를 해결하기 위해 arrows의 한 번의 이동을 2번의 작은 이동으로 나눕니다.

즉 원래(0, 0) -> (1, 1)로 이동하는 것을 (0, 0) -> (1, 1) -> (2, 2)처럼 처리합니다.
이렇게 좌표를 2배로 확장해서 이동하면 기존에는 선의 중간에서 만났던 교차점도 정수 좌표의 정점으로 표현할 수 있습니다.

방문한 정점은 HashSet<Long>에 저장하고, 방문한 간선도 HashSet<Long>에 저장합니다.
간선은 양방향이므로 A -> B와 B -> A를 같은 간선으로 취급해야 합니다.
이를 위해 항상 간선의 방향을 정규화해서 하나의 값으로 저장합니다.
*/


/*
문제 풀이
*/

import java.util.*;

class Solution {
    /*
    방향 순서
    0 : 위
    1 : 오른쪽 위
    2 : 오른쪽
    3 : 오른쪽 아래
    4 : 아래
    5 : 왼쪽 아래
    6 : 왼쪽
    7 : 왼쪽 위
    */
    static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
    static int[] dy = {1, 1, 0, -1, -1, -1, 0, 1};

    /*
    arrows의 길이는 최대 100,000이고 한 번의 이동을 2번으로 나누므로 좌표 범위는 최대 약 -200,000 ~ 200,000입니다.
    음수 좌표를 양수로 바꾸기 위해 OFFSET을 더합니다.
    */
    static final int OFFSET = 200_001;
    static final long WIDTH = 400_003L;


    /*
    하나의 좌표 (x, y)를 long 값 하나로 변환합니다.
    HashSet에서 좌표 방문 여부를 빠르게 확인하기 위해 사용합니다.
    */
    static long getPointKey(int x, int y) {
        return (long) (x + OFFSET) * WIDTH + (y + OFFSET);
    }


    /*
    현재 위치 (x, y)와 다음 위치 (nx, ny)를 연결하는 간선을 하나의 long 값으로 변환합니다.

    간선은 방향이 없으므로 A -> B와 B -> A를 같은 간선으로 만들어야 합니다.
    따라서 dx가 음수이거나, dx가 0이고 dy가 음수라면 두 정점의 위치를 서로 바꿉니다.

    정규화 후 가능한 간선 방향은 4가지입니다.

    0 : 위
    1 : 오른쪽 위
    2 : 오른쪽
    3 : 오른쪽 아래
    */
    static long getEdgeKey(int x, int y, int nx, int ny) {
        int moveX = nx - x;
        int moveY = ny - y;
        /*
        항상 오른쪽 또는 위쪽 방향을 기준으로 저장해서 역방향 이동도 같은 간선으로 처리합니다.
        */
        if (moveX < 0 || (moveX == 0 && moveY < 0)) {
            int tempX = x;
            int tempY = y;

            x = nx;
            y = ny;

            nx = tempX;
            ny = tempY;

            moveX = nx - x;
            moveY = ny - y;
        }

        int type;
        if (moveX == 0) {
            type = 0;        // 위
        } else if (moveY == 1) {
            type = 1;        // 오른쪽 위
        } else if (moveY == 0) {
            type = 2;        // 오른쪽
        } else {
            type = 3;        // 오른쪽 아래
        }
        return (getPointKey(x, y) * 4) + type;
    }
  
    public int solution(int[] arrows) {

        /*
        방문한 정점을 저장합니다.
        */
        HashSet<Long> visitedPoint = new HashSet<>();

        /*
        방문한 간선을 저장합니다.
        */
        HashSet<Long> visitedEdge = new HashSet<>();

        int x = 0;
        int y = 0;

        int answer = 0;

        /*
        시작점 (0, 0)을 방문 처리합니다.
        */
        visitedPoint.add(getPointKey(x, y));

        /*
        arrows에 저장된 모든 방향을 순서대로 확인합니다.
        */
        for (int dir : arrows) {

            /*
            대각선끼리 중간에서 교차하는 경우를 처리하기 위해 한 번의 이동을 2번으로 나눕니다.
            */
            for (int step = 0; step < 2; step++) {

                int nx = x + dx[dir];
                int ny = y + dy[dir];

                long nextPoint = getPointKey(nx, ny);
                long edge = getEdgeKey(x, y, nx, ny);
                /*
                다음 정점을 이미 방문한 적이 있으면서 현재 간선은 처음 지나가는 경우 새로운 사이클이 만들어지므로 방이 하나 생깁니다.
                */
                if (visitedPoint.contains(nextPoint) && !visitedEdge.contains(edge)) {
                    answer++;
                }
                /*
                다음 정점과 현재 간선을 방문 처리합니다.
                */
                visitedPoint.add(nextPoint);
                visitedEdge.add(edge);

                /*
                현재 위치를 다음 위치로 이동합니다.
                */
                x = nx;
                y = ny;
            }
        }
        return answer;
    }
}


/*
시간복잡도

arrows의 길이를 N이라고 하겠습니다.

각 방향마다 대각선 교차를 처리하기 위해 2번씩 이동하므로 총 이동 횟수는 2N입니다.
각 이동마다 HashSet의 contains와 add 연산을 수행하며 평균 시간복잡도는 O(1)입니다.

따라서 전체 시간복잡도는 O(N)입니다.

핵심 정리

1. 좌표를 정점, 이동한 선을 간선으로 생각합니다.
2. 이미 방문한 정점에 처음 사용하는 간선으로 도착하면 새로운 방이 하나 만들어집니다.
3. 이미 지나간 간선을 다시 지나가는 경우에는 방이 만들어지지 않습니다.
4. 대각선끼리 중간에서 교차하는 경우를 처리하기 위해 한 번의 이동을 2번으로 나누어 처리합니다.
5. 방문한 정점과 간선은 HashSet으로 관리합니다.
*/
