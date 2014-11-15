import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ListSelectionModel;

import java.awt.SystemColor;

/**
 * UserUI gets instantiated from the AdminGUI class. The UserUI must be passed in a
 * User object to function.
 * 
 * This also implements the Observer pattern. This class observes the Users that are
 * being followed by its User. Everytime a User that is being followed tweets, 
 * the UserUI refreshed the NewsFeed
 * 
 * Again, sorry for the sloppy Auto-Generated Code
 * @author Javi
 *
 */
public class UserUI extends JFrame implements Observer{
	private JTextField txtEnterUserName;
	private JTextField txtEnterTweet;
	private JList newsFeedList;
	private User globalUser;

	/**
	 * Launch the application. 
	 * NOTE: This was the way the WindowBuilder tool created the class
	 * so I went with it. Also, the class must be instantiated with a User
	 * object passed in.
	 */
	public static void newUserUI(final User user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserUI frame = new UserUI(user);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame. Add Components.
	 */
	public UserUI(final User user) {
		setTitle(user.getName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 567, 458);
		
		globalUser = user;
		globalUser.setUI(this);
		globalUser.addObserver(this);
		globalUser.addNewObservers();
		
		JScrollPane followingScrollPane = new JScrollPane();
		
		final JList followingList = new JList(user.getFollowing().toArray());
		followingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		followingList.setSelectionBackground(SystemColor.textHighlight);
		followingScrollPane.setViewportView(followingList);
		
		JScrollPane newsFeedScrollPane = new JScrollPane();
		
		JLabel followingLabel = new JLabel("Currently Following:");
		
		JLabel lblNewLabel = new JLabel("News Feed:");
		
		txtEnterUserName = new JTextField();
		txtEnterUserName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtEnterUserName.setText("");
			}
		});
		txtEnterUserName.setText("Enter User Name");
		txtEnterUserName.setColumns(10);
		
		JButton btnFollowUser = new JButton("Follow User");
		btnFollowUser.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void mouseClicked(MouseEvent e) {
				String userName = txtEnterUserName.getText();
				if(Admin.getInstance().isUser(userName)){
					user.follow(userName);
					globalUser.addNewObservers();
					txtEnterUserName.setText("Enter User Name");
					followingList.setListData(user.getFollowing().toArray());
				}
			}
		});
		
		txtEnterTweet = new JTextField();
		txtEnterTweet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtEnterTweet.setText("");
			}
		});
		txtEnterTweet.setText("Tweet Something!");
		txtEnterTweet.setColumns(10);
		
		JButton btnNewButton = new JButton("Post Tweet");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String tweet = txtEnterTweet.getText();
				user.tweet(tweet);
				txtEnterTweet.setText("Tweet Something!");
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(newsFeedScrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
						.addComponent(followingScrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
						.addComponent(lblNewLabel)
						.addComponent(followingLabel, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(txtEnterUserName, GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnFollowUser))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(txtEnterTweet, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnNewButton)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtEnterUserName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnFollowUser))
					.addGap(4)
					.addComponent(followingLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(followingScrollPane, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtEnterTweet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton))
					.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(newsFeedScrollPane, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		newsFeedList = new JList(user.getNewsFeed().toArray());
		newsFeedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		newsFeedList.setSelectionBackground(SystemColor.textHighlight);
		newsFeedScrollPane.setViewportView(newsFeedList);
		getContentPane().setLayout(groupLayout);
	}

	@Override
	/**
	 * Update the Newsfeed any time a User that is being followed tweets
	 */
	public void update(Observable o, Object arg) {
		if(arg instanceof List){
			newsFeedList.setListData(globalUser.getNewsFeed().toArray());
		}
	}
}
