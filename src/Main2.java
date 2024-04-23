import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main2 {

    public static void main(String[] args) throws FileNotFoundException {

        String sequenceFileName = "src/measlesSequenceRF1.csv";
        File sequenceFile = new File(sequenceFileName);
        Scanner scanner1 = new Scanner(sequenceFile);
        String[] sequence = scanner1.nextLine().split(",");

        String[] frame1 = {"TAA", "GCT", "ATG"};
        int frameIdx = 0;
        int startIdx = 0;

        for (String item : sequence)
        {
            if (frameIdx < frame1.length) {
                if (item.equals(frame1[frameIdx])) {
                    frameIdx++;
                }
                else
                    frameIdx = 0;
            }
            else if (frameIdx == frame1.length)
                break;
            startIdx++;
        }

        int startIndex = -1;
        int endIndex = 0;
        String sequenceString = "";
        for (int i = startIdx; i < sequence.length; i++)
        {
            String codon = sequence[i];
            if (codon.equals("ATG"))
            {
                startIndex = i;
                sequenceString = "";
            }
            else if (startIndex != -1 && isEnd(codon) && sequenceString.length() > 50)
            {
                endIndex = i;
                System.out.printf("something: %d-%d | len: %d\n", startIndex + sequenceString.length(), endIndex, sequenceString.length());
                startIndex = -1;
            }
            else
            {
                sequenceString += codon;
            }
        }

    }

    public static boolean isEnd(String codon)
    {
        String[] endCodons = {"TAG", "TAA", "TGA"};
        for (String stuff : endCodons)
        {
            if (stuff.equals(codon))
                return true;
        }
        return false;
    }
}
