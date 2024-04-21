import java.util.ArrayList;
import java.util.Arrays;

public class AminoAcid {

    String name, code;
    String[] codons;

    public AminoAcid (String name, String code, String[] codons) {
        this.name = name;
        this.code = code;
        this.codons = codons;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String[] getCodons() {
        return codons;
    }

    public String toString()  {
        return name + " " + " " + code + " " +  Arrays.toString(codons);
    }
}
