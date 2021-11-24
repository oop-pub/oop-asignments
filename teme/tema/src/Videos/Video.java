package Videos;

import java.util.ArrayList;

public class Video {
    private String titlu;
    private int AnLansare;
    private ArrayList<String> gen;

    public Video() {
    }

    public Video(String titlu, int anLansare, ArrayList<String> gen) {
        this.titlu = titlu;
        AnLansare = anLansare;
        this.gen = gen;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public int getAnLansare() {
        return AnLansare;
    }

    public void setAnLansare(int anLansare) {
        this.AnLansare = anLansare;
    }

    public void addGen(String gen)
    {
        this.gen.add(gen);
    }

    public ArrayList<String> getGen() {
        return gen;
    }
}
