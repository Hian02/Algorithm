class Solution {
    public String solution(String str) {
        String[] num_list = str.split(" ");

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (String num : num_list) {
            int n = Integer.parseInt(num);

            if (n < min) {
                min = n;
            }

            if (n > max) {
                max = n;
            }
        }

        return min + " " + max;
    }
}
