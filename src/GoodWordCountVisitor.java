import java.text.DecimalFormat;
import java.util.*;

public class GoodWordCountVisitor implements Visitor {
	//change this
	List<String> goodWords;
	int goodWordCount;
	int totalWordCount;
	
	public GoodWordCountVisitor(List<String> gw){
		this.goodWords = gw;
		goodWordCount = 0;
		totalWordCount = 0;
	}
	
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
		DecimalFormat df = new DecimalFormat("#.0000"); 
		String temp = df.format(result);
		return Double.valueOf(temp);
	}
	
}
