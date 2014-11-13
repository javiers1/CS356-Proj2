import java.util.*;

/*
 * 	Every User observes other users, Leaf of the NodeComponent
 */

public class User extends Observable implements Observer, NodeComponent, Visitable{
	private String userName;
	private int tweetCount;
	private List<String> followers;
	private List<String> following;
	private List<String> myTweets;
	private List<String> newsFeed;
	private UserUI myUI;
	
	public User(String userName){
		this.userName = userName;
		this.tweetCount = 0;
		this.followers = new ArrayList<String>();
		this.following = new ArrayList<String>();
		this.newsFeed = new ArrayList<String>();
		this.myTweets = new ArrayList<String>();
		this.myUI = null;
	}
	
	public void setUI(UserUI userUI){
		this.myUI = userUI;
	}
	
	public void follow(String user){
		User newUser = (User) Admin.getInstance().getUser(user);
		if(newUser == null){
			//user not found, error printed
		}
		else{
			following.add(newUser.getName());
			newUser.addFollower(this.userName);
			newUser.addObserver(this);
		}
	}
	
	public void tweet(String tweetMsg){
		myTweets.add(tweetMsg);
		tweetCount++;
		newsFeed.add(userName + ": " + (String) tweetMsg);
		setChanged();
		notifyObservers(tweetMsg);
		clearChanged();
		setChanged();
		notifyObservers(getNewsFeed());
		clearChanged();
	}
	
	public void addFollower(String uName){
		followers.add(uName);		
	}
	
	public List<String> getFollowing(){
		return following;
	}

	public String getName(){
		return userName;
	}
	
	public List<String>	getNewsFeed(){
		return newsFeed;
	}
	
	public List<String> getMyTweets() {
		return myTweets;
	}
	
	public int getTweetCount(){
		return tweetCount;
	}
	
	public void update(Observable o,Object arg){
		if(arg instanceof String){
			newsFeed.add(((User) o).getName() + ": " + (String) arg);
		}
	}

	public User getUser(String user) {
		if(userName.equals(user)){
			return this;
		}else{
			return null;
		}
	}
	
	public String toString(){
		return userName;
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void addNewObservers() {
		for(String user: following){
			User temp = (User) Admin.getInstance().getUser(user);
			temp.addObserver(myUI);
		}
	}
}
