package Shape;
import Print.*;
import java.io.*;
public class Point implements Print {
    public void draw(PrintStream out,String content) {
		out.println(content);
    }
    public String shape(char mark,int size) {
		String arr = "";
		arr += mark;
 	    return arr;
    }
}
