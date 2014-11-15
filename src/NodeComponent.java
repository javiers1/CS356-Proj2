/**
 * Composite Component Interface. Users are Leaf, Groups are Composites
 * Also can extends Visitable interface so that Each component can be visited,
 * both Users and Groups
 */
public interface NodeComponent extends Visitable{
	public String getName();
	public User	getUser(String user);
}
