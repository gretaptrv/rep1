package attempt1;

import java.util.Arrays;
import java.util.Scanner;

public class demo {
    public static void main(String[] args) {
        Scanner scan= new Scanner(System.in);
        int[] input = Arrays.stream(scan.nextLine().split(""))
                .mapToInt(Integer::parseInt)
                .map(e -> e + 1)
                .toArray();
        for (int i : input) {
            System.out.printf("%d", i);
        }

    }
}
