import java.util.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Admin Singleton Class. Creates a single instance of Admin control panel and Admin UI
 * Handles the insertion of Users and Groups
 * Returns basic information about users and groups
 * Also returns percentage and counts using Visitors
 * @author Javi
 *
 */

public class Admin {
	
	private static Admin instance = null;
	private static List<NodeComponent> nodes;
	private static Group root = null;
	 
	
	private Admin(){
		//nodes = new HashMap<String,NodeComponent>();
		root = new Group("root");
		nodes = new ArrayList<NodeComponent>();
		AdminGUI.main(null);
	}
	
	/**
	 * Called Once from driver just to initialize some pre-loaded values
	 * NOTE: Adding Groups and Users from the GUI works as well!
	 */
	protected void run(){
		//pre-loaded items
		addGroup("root", "cs356");
		addUser("cs356","Steve");
		addGroup("root","cs445");
		addGroup("cs445","students");
		addUser("cs445","Mack");
		addUser("students","John");
		addUser("students","Cecy");
		addUser("root","Yu");
		addUser("root","Javi");
		
		
		//some pre-loaded follows
		follow("Javi","John");
		follow("Javi","Steve");
		follow("Javi","Cecy");
		follow("Cecy","Javi");
		follow("Javi","Yu");
		follow("Yu","Javi");
				
		//pre-loaded tweets to demonstrate
		tweet("John","TWITTER is so cool");
		tweet("John","Its awesome");
		tweet("Javi","I like Turtles");
		tweet("Steve","steve is a beast");
		tweet("Cecy","I love javi");
		tweet("Javi","I LOVE cecy");
		tweet("Yu","I like teaching cs356");
		tweet("Javi","I hope I get a good score");
		
	}
	
	/**
	 * following Singleton Pattern
	 * @return
	 */
	protected static Admin getInstance(){
		if(instance == null){
			instance = new Admin();
		}
			return instance;
	}
	
	/**
	 * returns a Default tree model built upon the root, made of users and groups
	 * @return DefaultTreeModel 
	 */
	protected DefaultTreeModel getTreeModel(){
		DefaultTreeModel aTree = getTreeModel(root);
		return aTree;
	}
	
	//helper function
	protected DefaultTreeModel getTreeModel(NodeComponent startNode){
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		recursiveBuildTree(root, ((Group) startNode).getChildren());
		DefaultTreeModel tree = new DefaultTreeModel(root);
		return tree;
	}
	
	//another helper function, to recurse across tree
	protected void recursiveBuildTree(DefaultMutableTreeNode parent, List<NodeComponent> children){
		for(NodeComponent aNode: children){
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(((NodeComponent) aNode).getName());
			parent.add(node);
			if(aNode instanceof Group){
				recursiveBuildTree(node, ((Group) aNode).getChildren());
			}
		}	
	}
	
	/**
	 * utilizes the Visitor pattern to count Groups using GroupCountVisitor
	 * @return Integer number of groups
	 */
	protected int getGroupCount(){
		GroupCountVisitor countVisitor = new GroupCountVisitor();
		for(Visitable node: nodes){
			node.accept(countVisitor);
		}
		return countVisitor.getCount();
		
	}
	
	/**
	 * utilizes the Visitor pattern to count Users using UserCountVisitor
	 * @return Integer number of Users
	 */
	protected int getUserCount(){
		UserCountVisitor countVisitor = new UserCountVisitor();
		for(Visitable node: nodes){
			node.accept(countVisitor);
		}
		return countVisitor.getCount();
		
	}
	
	/**
	 * utilizes the Visitor pattern to count Tweets using MessageCountVisitor
	 * @return Integer number of Tweets
	 */
	protected int getMessageCount(){
		MessageCountVisitor countVisitor = new MessageCountVisitor();
		for(Visitable node: nodes){
			node.accept(countVisitor);
		}
		return countVisitor.getCount();
		
	}
	
	/**
	 * utilizes the Visitor pattern to calculate GW percentage using 
	 * GoodWordPercentageVisitor
	 * @return double percentage of good words, in format 23.45 = 24.35%
	 */
	protected double getGoodWordPercentage(){
		GoodWordCountVisitor countVisitor = new GoodWordCountVisitor(Arrays.asList("like", "love", "good","nice","awesome","beast"));
		for(Visitable node: nodes){
			node.accept(countVisitor);
		}
		
		return countVisitor.getGoodWordPercentage();
	}
	
	/**
	 * Checks to see if the name passed to it is a Group in the tree.
	 * @param String node
	 * @return boolean 
	 */
	protected boolean isGroup(String node){
		boolean result = false;
		if(root.getGroup(node) instanceof Group){
			result = true;
		}
		return result;
	}
	
	/**
	 * Checks to see if the name passed to it is a User in the tree.
	 * @param String node
	 * @return boolean 
	 */
	protected boolean isUser(String node){
		boolean result = false;
		if(root.getUser(node) instanceof User){
			result = true;
		}
		return result;
	}
	
	/**
	 * Adds a new User to the Tree. Passes the Parent node in string and User name
	 * @param groupname as a String to be the parent node
	 * @param name of new User child to be added to parent node
	 */
	protected void addUser(String group,String name){
		Group tmp = root.getGroup(group);
		if(tmp != null){
			User aUser = new User(name);
			tmp.addComponent(aUser);
			nodes.add(aUser);
		}
	}
	
	/**
	 * Adds a new Group to the Tree.
	 * @param Parent group name as a String 
	 * @param name of new Group child to be added to parent node
	 */
	protected void addGroup(String group,String groupName){
		Group tmp = root.getGroup(group);
		if(tmp != null){
			Group aGroup = new Group(groupName);
			tmp.addComponent(aGroup);
			nodes.add(aGroup);
		}
	}	
	
	/**
	 * Gets the User.
	 * @param name - String of User name
	 * @return NodeComponent - The actual User
	 */
	protected NodeComponent getUser(String name){
		NodeComponent user = root.getUser(name);
		if(user != null){
			return user;
		}
		return null;
	}
	
	/**
	 * Validates the users
	 * @return true if all users/groups are valid, false if any one user/group is invalid
	 */
	protected Boolean validateUsers(){
		Boolean result = true;
		for(NodeComponent node: nodes){
			if(node.getName().contains(" ")){
				result = false;
			} else if(isDuplicate(node)){
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * Check to see if there is a duplicate user or group
	 * @param name
	 * @return true if duplicate, false if not
	 */
	protected Boolean isDuplicate(NodeComponent checkNode){
		Boolean result = false;
		for(NodeComponent node: nodes){
			if(!checkNode.equals(node)){
				if(node.getName().equals(checkNode.getName())){
					result = true;
				}
			}
		}
		return result;
	}
	
	/**
	 * Returns a String of the Last Updated User Name and Time
	 * @return String Latest Updated User + Time Updated
	 */
	protected String getLastUpdatedUser(){
		String result;
		long temp = (long) 0.0;
		User tempUser = null;
		for(NodeComponent node : nodes){
			if(node instanceof User){
				if(((User) node).getLastUpdatedTime() > temp){
					temp = ((User) node).getLastUpdatedTime();
					tempUser = (User) node;
				}
			}
		}
		result = tempUser.getName() + " : " + temp;
		return result;
	}
	
	//just a temporary method to pre-load follows before running UI
	public void follow(String n1, String n2){
		User user = root.getUser(n1);
		user.follow(n2);
	}
	
	//temporary method to pre-tweet before running UI
	public void tweet(String user, String message){
		User temp = root.getUser(user);
		temp.tweet(message);
	}
	
}
