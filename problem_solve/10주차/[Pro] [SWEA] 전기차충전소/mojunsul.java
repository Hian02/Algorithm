import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class UserSolution {

    static class Edge {
        int id, from, to, dist;

        public Edge(int id, int from, int to, int dist) {
            this.id = id;
            this.from = from;
            this.to = to;
            this.dist = dist;
        }
    }

    static class Node implements Comparable<Node> {
        int city, costSum, minCost;

        public Node(int city, int costSum, int minCost) {
            this.city = city;
            this.costSum = costSum;
            this.minCost = minCost;
        }

        @Override
        public int compareTo(Node o) {
            if(this.costSum != o.costSum) return Integer.compare(this.costSum, o.costSum);
            return Integer.compare(this.minCost, o.minCost);
        }
    }

    final int MAX_EDGE = 5000;
    final int MAX_NODE = 305;
    final int MAX_COST = 2000;
    final int INF = 1_500_000_000;

    int N;
    int[] cost;
    Edge[] edges = new Edge[MAX_EDGE];
    int edgeCnt = 0;
    Map<Integer, Integer> idToIdx = new HashMap<>();
    int[][] adj = new int[MAX_NODE][MAX_NODE];

    PriorityQueue<Node> pq = new PriorityQueue<>();
    int[][] totalCost = new int[MAX_NODE][MAX_COST];

    public void init(int N, int mCost[], int K, int mId[], int sCity[], int eCity[], int mDistance[]) {
        edgeCnt = 0;
        idToIdx.clear();
        for (int i = 0; i < MAX_NODE; i++) {
            Arrays.fill(adj[i], 0);
        }
        this.N = N;
        cost = mCost;
        for (int i = 0; i < K; i++) {
            add(mId[i], sCity[i], eCity[i], mDistance[i]);
        }
    }

    public void add(int mId, int sCity, int eCity, int mDistance) {
        edgeCnt++;
        edges[edgeCnt] = new Edge(mId, sCity, eCity, mDistance);
        idToIdx.put(mId, edgeCnt);
        adj[sCity][eCity] = edgeCnt;
    }

    public void remove(int mId) {
        Integer idx = idToIdx.remove(mId);
        Edge edge = edges[idx];
        adj[edge.from][edge.to] = 0;
    }

    public int cost(int sCity, int eCity) {
        pq.clear();
        for (int i = 0; i < MAX_NODE; i++) {
            Arrays.fill(totalCost[i], INF);
        }

        pq.offer(new Node(sCity, 0, cost[sCity]));
        totalCost[sCity][cost[sCity]] = 0;

        while(!pq.isEmpty()) {
            Node cur = pq.poll();

            if(cur.city == eCity) return totalCost[eCity][cur.minCost];

            if(cur.costSum > totalCost[cur.city][cur.minCost]) continue;

            int minCost = Math.min(cur.minCost, cost[cur.city]);
            for (int edgeIdx : adj[cur.city]) {
                if(edgeIdx == 0) continue;
                Edge edge = edges[edgeIdx];
                int nextCostSum = cur.costSum + minCost * edge.dist;

                if(totalCost[edge.to][minCost] > nextCostSum) {
                    totalCost[edge.to][minCost] = nextCostSum;
                    pq.offer(new Node(edge.to, nextCostSum, minCost));
                }
            }
        }

        return -1;
    }
}
