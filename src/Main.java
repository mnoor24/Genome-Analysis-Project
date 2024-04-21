import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner kbd = new Scanner(System.in);

        System.out.println("Please provide your Amino Acid Table File: ");
        String aminoAcidFile = kbd.next();

        System.out.println("Please provide your Sequence File: ");
        String sequenceFileName = kbd.next();

        File tableFile = new File(aminoAcidFile);
        Scanner scanner = new Scanner(tableFile);
        ArrayList<AminoAcid> aminoAcidList = new ArrayList<>();
        scanner.next();

        while (scanner.hasNext()) {
            String[] s = scanner.nextLine().split(",");

            if (s.length > 1) {
                aminoAcidList.add(new AminoAcid(s[0], s[1], Arrays.copyOfRange(s, 3, s.length)));
            }
        }

        scanner.close();

        File sequenceFile = new File(sequenceFileName);
        Scanner scanner1 = new Scanner(sequenceFile);

        int total, num;
        double percent;
        int index = sequenceFile.getName().indexOf('.');
        String file = sequenceFile.getName().substring(0, index);

        PrintWriter printWriter = new PrintWriter("src/" + file + "_CodonBias.txt");
        printWriter.println("*********** Complete Codon Bias Report **********");

        String[] sequence = scanner1.nextLine().split(",");
        for (AminoAcid acid : aminoAcidList) {
            total = countEqualTotal(sequence, acid.getCodons());

            printWriter.println(
                    "\nThe codons for " + acid.getName() + "(" + acid.getCode() + ") are: " + String.join(" ", acid.getCodons())
            );

            for (String codon : acid.getCodons()) {
                num = countEqual(sequence, codon);
                percent = ((double) num / total) * 100.0;

                printWriter.printf("%-1s: %4d %6.2f%%\n", codon, num, percent);

            }
        }

        scanner1.close();
        printWriter.close();

    }

    public static int countEqualTotal(String[] arr, String[] strs) {
        int count = 0;
        for (String str : strs)
            count += countEqual(arr, str);
        return count;
    }

    public static int countEqual(String[] arr, String s) {
        int count = 0;

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i].equals(s)) {
                count++;
            }
        }
        return count;
    }
}