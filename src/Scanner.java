import java.io.*;

public class Scanner {
    private final int BLOCK_SIZE = 256;
    
    private char[] buffer = new char[BLOCK_SIZE];
    private int bufferSize = 0;
    private int bufferIndex = 0;

    private final Reader reader;

    //-------------------------------------------------------------------------------------
    Scanner(String inputArg) {
        buffer = inputArg.toCharArray();
        bufferSize = buffer.length;
        reader = new BufferedReader(new InputStreamReader(InputStream.nullInputStream()));
    }
    
    Scanner(InputStream in) throws IOException {
        reader = new BufferedReader(new InputStreamReader(in));
    }
    
    Scanner(FileInputStream in, String code) throws IOException {
        reader = new BufferedReader(new InputStreamReader(in, code));
    }
    //-------------------------------------------------------------------------------------
    private int bufferReadStream() throws IOException {
        bufferSize = reader.read(buffer);
        return bufferSize;
    }
    //-------------------------------------------------------------------------------------
    public String next() throws IOException {
        StringBuilder tempWord = new StringBuilder();
        boolean wordFinded = false;
        for(int index = bufferIndex; true; index++) {
            if (index >= bufferSize) {
                if (bufferReadStream() <= 0) {
                    if (tempWord.length() == 0) {
                        return null;
                    }
                    bufferIndex = index;
                    break;
                }
                index = 0;
            }
            if (Character.isWhitespace(buffer[index])) {
                if (wordFinded) {
                    break;
                } else {
                    continue;
                }
            } else {
                tempWord.append(buffer[index]);
                wordFinded = true;
            }
        }
        return tempWord.toString(); 
    }
    //------------------------------------------------------------------------------------- 
    private boolean wasR = false;

    public String nextLine() throws IOException {
        StringBuilder tempWord = new StringBuilder();
        wasR = false;
        for(int index = bufferIndex; true; index++) {
            if (index >= bufferSize) {
                if (bufferReadStream() <= 0) {
                    if (tempWord.length() == 0) {
                        return null;
                    }
                    bufferIndex = index;
                    break;
                }
                index = 0;
            }
            if (wasR) {
                if (isLineSeparator(buffer[index])) {
                    bufferIndex = index + 1;
                } else {
                    bufferIndex = index;
                }
                tempWord.setLength(tempWord.length() - 1);
                break;
            } else if (isLineSeparator(buffer[index])) {
                bufferIndex = index + 1;
                break;
            } else {
                tempWord.append(buffer[index]);
            }
        }
        return tempWord.toString(); 
    }
    //------------------------------------------------------------------------------------- 
    private boolean isLineSeparator(char literal) {
        return switch(literal) {
            case '\n' -> true;
            case '\r' -> {
                wasR = true;
                yield false;
            }
            default -> false;
        };
    }
    //------------------------------------------------------------------------------------- 
    public void close() throws IOException {
        reader.close();
    }
}
