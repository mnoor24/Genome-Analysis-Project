public class Gene {

    private String[] acids;
    private int startPosition, endPosition, geneLength;

    public Gene (String[] acids, int startPosition, int endPosition, int geneLength) {
        this.acids = acids;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public String[] getAcids() {
        return acids;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public int getGeneLength() {
        return geneLength;
    }

    @Override
    public String toString() {
        return "Gene";
    }
}
