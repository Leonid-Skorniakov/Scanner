import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Wspp{
    public static void main(String[] args){
		String fileInputName = "input.txt";
		String fileOutputName = "output.txt";
		try {
			fileInputName = args[0];
        	fileOutputName = args[1];

			Scanner reader = new Scanner(new FileInputStream(fileInputName), StandardCharsets.UTF_8.name());
			List<String> list = new ArrayList<String>();
			Map<String, IntList> map = new HashMap<>();
			try {
				int count = 0;
				while(true){
					String line = reader.nextLine();
					if (line == null){
						break;
					}
					int ender;
					for (int i = 0; i < line.length(); i++) {
						for (ender = i; ender < line.length(); ender++) {
							if (!Character.isLetter(line.charAt(ender)) &&
								!(line.charAt(ender) == '\'') && 
								!(Character.getType(line.charAt(ender)) == Character.DASH_PUNCTUATION)) {
								break;
							}
						}
						if (i != ender) {
							count++;
							String word = line.substring(i,ender).toLowerCase();
							if (map.get(word) == null) {
								map.put(word, new IntList());
								list.add(word);
							}
							map.get(word).add(count);
							i = ender;
						}
					} 
				}
				
				try {
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter
						(new FileOutputStream(fileOutputName),
						StandardCharsets.UTF_8.name())
					);
					try{
						for (String key : list){
							writer.write(key + " " + map.get(key).size());
							for (int value : map.get(key).get()){
								writer.write(" " + value);
							}
							writer.newLine();
						}
					} finally {
						writer.close();
					}
				} catch (IOException e) {
					System.err.println("IOException: problem with opening ouput file. Error: " + e.getMessage());
				}
			} finally {
				reader.close();
			}
		} catch (IOException e) {
			System.err.println("IOException: problem with opening input file. Error: " + e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("ArrayIndexOutOfBoundsException: incorrect count of arguments. Error: " + e.getMessage());
		}
	}
}
