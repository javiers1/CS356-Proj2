import java.util.*;
/**
 * Group class. Implements the Visitor pattern and Composite pattern named NodeComponent
 * @author Javi
 *
 */

public class Group implements Visitable, NodeComponent {
	private String groupID;
	private List<NodeComponent> children;
	
	public Group(String name){
		children = new ArrayList<NodeComponent>();
		groupID = name;
	}
	
	public void addComponent(NodeComponent comp){
		children.add(comp);
	}
	
	public String getName(){
		return groupID;
	}

	public List<NodeComponent> getChildren(){
		return children;
	}
	
	public void print(){
		System.out.println("Group Name is :" + groupID);
	}
	
	/**
	 * Recursively search for a Group and return it
	 * @param group - String of the Group Name
	 * @return Group instance
	 */
	public Group getGroup(String group){
		if(groupID.equals(group)){
			return this;
		}else{
			for(NodeComponent tmp: children){
				if(tmp instanceof Group){
					if(((Group) tmp).getGroup(group) != null){
						return ((Group) tmp).getGroup(group);
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Recursively search for a User and return it
	 * @param group - String of the User Name
	 * @return User instance
	 */
	public User getUser(String user) {
		User result = null;
		for(NodeComponent tmp: children){
			if(tmp instanceof User){
				if(tmp.getUser(user) != null){
					result = tmp.getUser(user);
				}
			}else if(tmp instanceof Group){
				if(tmp.getUser(user) != null){
					result = tmp.getUser(user);
				}
			}
		}
		return result;
	}
	
	public String toString(){
		String result;
		result = groupID + ": " + children.toString();
		return result;
	}
	
	/**
	 * Implement the Visitor pattern.
	 */
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
