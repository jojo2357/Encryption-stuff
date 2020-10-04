import java.util.regex.*;

//Test out regex's here!
class RegexTester{
	public static void main(String args[]){
		Pattern pattern = Pattern.compile("(.ll)");
		Matcher matcher = pattern.matcher("Hello World!");
		while (matcher.find()){
			System.out.println(matcher.end());
		}
	}
}