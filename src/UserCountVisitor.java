/**
 * Implements the Visitor pattern. Counts the number of Users, ignores Groups
 * @author Javi
 *
 */
public class UserCountVisitor implements Visitor {
	int count = 0;
	
	public void visit(User user) {
		count++;
	}

	public void visit(Group group) {
		//do nothing
	}
	
	public int getCount(){
		return count;
	}
	
}
