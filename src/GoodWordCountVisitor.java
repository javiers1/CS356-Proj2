import java.text.DecimalFormat;
import java.util.*;


public class GoodWordCountVisitor implements Visitor {

	List<String> goodWords = Arrays.asList("like", "love", "good","nice","awesome","beast");
	int goodWordCount = 0;
	int totalWordCount = 0;
	
	public void visit(User user) {
		List<String> tweetsTemp = user.getMyTweets();
		for(String s: tweetsTemp){
			processTweet(s);
		}
	}

	public void visit(Group group) {
		//do nothing
	}
	
	public void processTweet(String tweet){
		String [] words = tweet.split(" ");
		for(String s: words){
			totalWordCount++;
			s = s.toLowerCase();
			for(String gw: goodWords){
				if(s.equals(gw)){
					goodWordCount++;
				}
			}
		}
	}
	
	public int getGoodWordCount(){
		return goodWordCount;
	}
	
	public double getGoodWordPercentage(){
		double result = (double) goodWordCount/totalWordCount;
		//DecimalFormat df = new DecimalFormat("#.###");      
		//result = Double.valueOf(df.format(result));
		
		return result;
	}
	
}
