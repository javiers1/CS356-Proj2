/**
 * Implements the visitor pattern
 * @author Javi
 *
 */
public interface Visitor {
	public void visit(User user);
	public void visit(Group group);
}
