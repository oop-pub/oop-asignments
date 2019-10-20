package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

public final class InputReader {
    private String filename;
    private BufferedReader bufferedReader;

    /**
     * Input reader constructor.
     *
     * @param filename the file name
     */
    public InputReader(String filename) {
        this.filename = filename;
        bufferedReader = new BufferedReader(getReaderForFile(filename));
    }

    /**
     * Reads a line from the file.
     *
     * @return the line
     */
    public String readLine() {
        String line = null;

        try {
            line = this.bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }


    private Reader getReaderForFile(String file) {
        if (file == null || file.isEmpty()) {
            return new InputStreamReader(System.in);
        } else {
            try {
                return new FileReader(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
