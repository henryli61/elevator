package elevator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileParser {
	private static final String INPUT_FILE = "c:\\tmp\\elevatorInput.txt";
	
	private static final int MIN = 1;
	private static final int MAX = 12;
	
	private File inputFile = new File(INPUT_FILE);
	private List<ElevatorEvent>	requests = null;
	
	public List<ElevatorEvent> buildRequests() throws IOException {
		BufferedReader br = null;
		
		InputStream is = null;
		
		try{
			is = new FileInputStream(inputFile);
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			requests = br.lines()
					.map(line->line.split(":"))
					.filter(a->a.length == 2 && a[0] != null && a[1] != null)
					.filter(a->isInteger(a[0]))
					.map(convertToAttributes)
					.collect(Collectors.toList());
					
		} catch (FileNotFoundException e){
			e.printStackTrace();
			throw new IOException(e);
		} finally {
			if(br != null) br.readLine();
		}
		
		return requests;
	}
	
	final private Function<String[],ElevatorEvent> convertToAttributes = (line) -> {
		List<Request> requests = Arrays.stream(line[1].split(","))
				.map(s->s.split("-"))
				.filter(a->a.length == 2 && a[0] != null && a[1] != null)
				.filter(a->isInteger(a[0]) && isInteger(a[1]))
				.map(s->new Integer[]{Integer.parseInt(s[0]),Integer.parseInt(s[1])})
				.filter(i->isInRange(i[0])&&isInRange(i[1]))
				.map(i->new Request(i[0],i[1]))
				.collect(Collectors.toList());
		
		return new ElevatorEvent(Integer.parseInt(line[0]),requests);
	};
	
	private boolean isInteger(String s){
		try{
			Integer.parseInt(s);
			return true;
		} catch (Exception e){
			return false;
		}
	}
	
	private boolean isInRange(int i) {
		return (i>=MIN && i<=MAX) ? true : false;
	}
}