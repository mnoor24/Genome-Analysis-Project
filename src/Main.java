import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner kbd = new Scanner(System.in);

        // Asks user for amino acid table file directory
        System.out.println("Please provide your Amino Acid Table File: ");
        String aminoAcidFile = kbd.next();

        // Asks user for sequence file directory
        System.out.println("Please provide your Sequence File: ");
        String sequenceFileName = kbd.next();

        File tableFile = new File(aminoAcidFile);
        Scanner scanner = new Scanner(tableFile);
        ArrayList<AminoAcid> aminoAcidList = new ArrayList<>();
        scanner.nextLine();

        while (scanner.hasNext()) {
            // turn amino acid table file content into array of strings
            String[] s = scanner.nextLine().split(",");

            // use amino acid table file content to create AminoAcid objects
            aminoAcidList.add(new AminoAcid(s[0], s[1], Arrays.copyOfRange(s, 3, s.length)));
        }

        // close amino acid table file
        scanner.close();

        // open sequence file
        File sequenceFile = new File(sequenceFileName);
        Scanner scanner1 = new Scanner(sequenceFile);

        int total, num;
        double percent;

        // get name of sequence file without file extension (not the best way to do it, but it works)
        int index = sequenceFile.getName().indexOf('.');
        String file = sequenceFile.getName().substring(0, index);

        // inside the src file create a file with the original sequence file name but add "_CodonBias.txt" at the end
        PrintWriter printWriter = new PrintWriter("src/" + file + "_CodonBias.txt");

        printWriter.println("*********** Complete Codon Bias Report **********");

        // create string array of the content inside sequence file
        String[] sequence = scanner1.nextLine().split(",");

        // loop through each amino acid in aminoAcidList(created on line 31)
        for (AminoAcid acid : aminoAcidList) {

            // counts the amount of times the codons of a specific amind acid are present in the sequence array
            total = countEqualTotal(sequence, acid.getCodons());

            // prints the name, code, and list of codons in the file
            printWriter.println(
                    "\nThe codons for " + acid.getName() + "(" + acid.getCode() + ") are: " + String.join(" ", acid.getCodons())
            );


            for (String codon : acid.getCodons()) {  // loops through the codons of a specific amino acid
                num = countEqual(sequence, codon); // gets the number of times a single codon is present in the sequence array
                percent = ((double) num / total) * 100.0; // formats the number correctly

                /*
                 adds name of codon,
                 amount of times it was present in the sequence
                 and how many times it was present (in percentage) compared to other codons of that amino acid
                 */
                printWriter.printf("%-1s: %4d %6.2f%%\n", codon, num, percent);

            }
        }

        scanner1.close();
        printWriter.close();

    }

    // returns total amount of times the codons of a specific amino acid are present in the sequence
    public static int countEqualTotal(String[] arr, String[] strs) { // arr is the sequence array and strs is the array of codons of the specific amino acid
        int count = 0;
        for (String str : strs) // loops through the codons
            /*
             sends sequence array(arr) and one codon(str) to count how many times the codon is present in
             the sequence and then adds that number to itself to get the total amount of times the codons of a
             specific amino acid are present in the sequence
             */
            count += countEqual(arr, str);

        return count;
    }

    // counts how many times a single codon is present in an array of codons
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