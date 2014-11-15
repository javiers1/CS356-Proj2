import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;

import java.awt.SystemColor;

import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.border.LineBorder;

/**
 * AdminGUI. This was generated using the Auto Generated Code by the Eclipse Window
 * builder, so sorry for the sloppy code.
 * 
 * This AdminGUI is called once from the Admin Singleton Class at Instantiation, so
 * it is also a Singleton
 * @author Javi
 *
 */
public class AdminGUI extends JFrame {

	private JPanel contentPane;
	private JTree tree;
	private JTextField addUserTextField;
	private JTextField addGroupTextField;
	private String selectedNodeComponent;

	/**
	 * Launch the application. 
	 * NOTE: by default, the window builder designed the class this way so,
	 * I went with it.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGUI frame = new AdminGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame. Add Components. Add Appropriate Listeners
	 * 
	 * NOTE: Any time a Group is added with no children, it is displayed
	 * as a leaf until the Children (Users) are added.
	 */
	public AdminGUI() {
		setTitle("Javi's Mini Twitter - Admin Control");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		addUserTextField = new JTextField();
		addUserTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addUserTextField.setText("");
			}
		});
		addUserTextField.setText("Enter New User Name");
		addUserTextField.setColumns(10);
		
		JButton addUserButton = new JButton("Add User");
		addUserButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String newUser = addUserTextField.getText();
					if(Admin.getInstance().isGroup(selectedNodeComponent)){
						Admin.getInstance().addUser(selectedNodeComponent, newUser);
						tree.setModel(Admin.getInstance().getTreeModel());
						addUserTextField.setText("Enter New User Name");
					}
			}
		});
		addUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		addGroupTextField = new JTextField();
		addGroupTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addGroupTextField.setText("");
			}
		});
		addGroupTextField.setText("Enter New Group Name");
		addGroupTextField.setColumns(10);
		
		JButton addGroupButton = new JButton("Add Group");
		addGroupButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String newGroup = addGroupTextField.getText();
				if(Admin.getInstance().isGroup(selectedNodeComponent)){
					Admin.getInstance().addGroup(selectedNodeComponent, newGroup);
					tree.setModel(Admin.getInstance().getTreeModel());
					addGroupTextField.setText("Enter New Group Name");
				}
			}
		});
		
		JButton userViewButton = new JButton("Open User View");
		userViewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(Admin.getInstance().isUser(selectedNodeComponent)){
					UserUI.newUserUI((User) Admin.getInstance().getUser(selectedNodeComponent));
				}
			}
		});
		
		JButton userCountButton = new JButton("User Count");
		userCountButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "User Count is : " + Admin.getInstance().getUserCount());
			}
		});
		
		JButton groupCountButton = new JButton("Group Count");
		groupCountButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Group Count is : " + Admin.getInstance().getGroupCount());
			}
		});
		
		JButton messageCountButton = new JButton("Tweet Count");
		messageCountButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Total Tweet Count is : " + Admin.getInstance().getMessageCount());
			}
		});
		
		JButton gwPercentageButton = new JButton("Good Word %");
		gwPercentageButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Good Word percentage is : " + Admin.getInstance().getGoodWordPercentage() + "%" );
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(addUserTextField, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
								.addComponent(addGroupTextField, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(6)
									.addComponent(addGroupButton, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
								.addComponent(addUserButton)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(groupCountButton, Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
								.addComponent(userCountButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(gwPercentageButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(messageCountButton, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))
						.addComponent(userViewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(204, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(addUserTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(addUserButton))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(addGroupTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(addGroupButton))
							.addGap(18)
							.addComponent(userViewButton)
							.addGap(117)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(userCountButton)
								.addComponent(messageCountButton))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(groupCountButton)
								.addComponent(gwPercentageButton))))
					.addGap(34))
		);
		
		tree = new JTree();
		scrollPane.setViewportView(tree);
		tree.setForeground(Color.WHITE);
		tree.setBorder(null);
		tree.setBackground(Color.WHITE);
		tree.getSelectionModel().setSelectionMode
        (TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
		public void valueChanged(TreeSelectionEvent e) {
			selectedNodeComponent = e.getPath().getLastPathComponent().toString();
		}
		});
		
		tree.setModel(Admin.getInstance().getTreeModel());
		
		contentPane.setLayout(gl_contentPane);
	}
}
