package miniProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class SignupPage {

	public JFrame frame;
	private JTextField idField;
	private JTextField nicknameField;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignupPage window = new SignupPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SignupPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel Signup_ID = new JLabel("아이디");
		Signup_ID.setBounds(68, 82, 57, 15);
		frame.getContentPane().add(Signup_ID);
		
		JLabel Signup_PW = new JLabel("비밀번호");
		Signup_PW.setBounds(68, 107, 57, 15);
		frame.getContentPane().add(Signup_PW);
		
		JLabel Signup_PW_chk = new JLabel("비밀번호 확인");
		Signup_PW_chk.setBounds(68, 132, 86, 15);
		frame.getContentPane().add(Signup_PW_chk);
		
		JLabel Signup_NickName = new JLabel("닉네임");
		Signup_NickName.setBounds(68, 157, 57, 15);
		frame.getContentPane().add(Signup_NickName);
		
		idField = new JTextField();
		idField.getDocument().addDocumentListener(new DocumentListener() {		// 중복확인 이후 아이디가 변했을 시 다시 중복확인을 하도록 비밀번호필드를 비활성화

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				passwordField_1.setEnabled(false);
				passwordField_2.setEnabled(false);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				passwordField_1.setEnabled(false);
				passwordField_2.setEnabled(false);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				passwordField_1.setEnabled(false);
				passwordField_2.setEnabled(false);
			}
		});
		idField.setBounds(155, 79, 116, 21);
		frame.getContentPane().add(idField);
		idField.setColumns(10);
		
		nicknameField = new JTextField();
		nicknameField.setColumns(10);
		nicknameField.setBounds(155, 154, 116, 21);
		frame.getContentPane().add(nicknameField);
		
		JButton overlapCheckBtn = new JButton("중복 확인");
		overlapCheckBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = idField.getText();
				boolean checkID = CheckOverlap(id);
				System.out.println(checkID);
				if(checkID == true) {
					ShowWindow(0);		// id overlapped
					passwordField_1.setEnabled(false);
					passwordField_2.setEnabled(false);
				}
				else {
					ShowWindow(1);		// id not overlapped
					passwordField_1.setEnabled(true);
					passwordField_2.setEnabled(true);
				}
			}
		});
		overlapCheckBtn.setHorizontalAlignment(SwingConstants.LEFT);
		overlapCheckBtn.setBounds(283, 78, 93, 23);
		frame.getContentPane().add(overlapCheckBtn);

		
		JButton Signup_Reg = new JButton("확인");
		Signup_Reg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String o1 = String.valueOf(passwordField_1.getPassword());
				String o2 = String.valueOf(passwordField_2.getPassword());
				if(!o1.equals(o2)){
					ShowWindow(2);
				} 
				else if(CheckOverlap(idField.getText()) == true){
					ShowWindow(3);
				}
				else if(passwordField_1.isEnabled() == false) {
					ShowWindow(3);
				}
				else if(nicknameField.getText().length() == 0) {
					ShowWindow(6);
				}
				else {
					// Sign up, Insert Data
					ShowWindow(4);
				}
			}
		});
		Signup_Reg.setBounds(141, 217, 65, 23);
		frame.getContentPane().add(Signup_Reg);
		
		JButton Signup_cancle = new JButton("취소");
		Signup_cancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowWindow(5);
			}
		});
		Signup_cancle.setBounds(218, 217, 65, 23);
		frame.getContentPane().add(Signup_cancle);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setEnabled(false);
		passwordField_1.setBounds(155, 104, 116, 21);
		frame.getContentPane().add(passwordField_1);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setEnabled(false);
		passwordField_2.setBounds(155, 129, 116, 21);
		frame.getContentPane().add(passwordField_2);

	}
	
	void ShowWindow(int num){
		switch(num) {
		case 0:		// id overlap check window ( id already used )
			JOptionPane.showMessageDialog(null, "아이디 중복", "다른 아이디를 입력하세요", JOptionPane.DEFAULT_OPTION);
			break;
		case 1:		// id overlap check window ( id doesn't exist )
			JOptionPane.showMessageDialog(null, "아이디 확인", "이 아이디를 사용할 수 있습니다", JOptionPane.DEFAULT_OPTION);
			break;
		case 2:		// password double check ( pw doesn't same )
			JOptionPane.showMessageDialog(null, "같은 비밀번호를 입력하세요", "비밀번호 확인 필요", JOptionPane.DEFAULT_OPTION);
			break;
		
		case 3:		// doesn't checked the id overlapped
			JOptionPane.showMessageDialog(null, "아이디를 확인하세요", "아이디 확인", JOptionPane.DEFAULT_OPTION);
			break;
		case 4:		// Sign up Check
			Object[] options = {"Sign up", "Cancel"};
			int n = JOptionPane.showOptionDialog(null, "회원가입 하시겠습니까?", "회원가입", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if(n == JOptionPane.OK_OPTION) {
				//Sign Up
				String id = idField.getText();
				String pw = String.valueOf(passwordField_1.getPassword());
				String nickname = nicknameField.getText();
				insertCustomer(id, pw, nickname);
				JOptionPane.showMessageDialog(null, "가입되었습니다. 환영합니다", "가입", JOptionPane.DEFAULT_OPTION);
				this.frame.dispose();
			}
			else if(n == JOptionPane.CANCEL_OPTION) {
				// Cancel
			}
			break;
		
		case 5:
			Object[] options2 = {"Exit Sign up", "Cancel"};
			int m = JOptionPane.showOptionDialog(null, "가입을 취소하시겠습니까?", "취소", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, options2[1]);
			if(m == JOptionPane.OK_OPTION) {
				// Exit sign up
				this.frame.dispose();
			}
			else if(m == JOptionPane.CANCEL_OPTION) {
				// Cancel
			}
			break;
			
		case 6:		// password double check ( pw doesn't same )
			JOptionPane.showMessageDialog(null, "Please Enter your nickname", "Enter nickname", JOptionPane.DEFAULT_OPTION);
			break;
		}
	}
	
	boolean CheckOverlap(String checkID) {
		String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";	// default
		String DB_USER = "c##ora_user";
		String DB_PW = "KIM";
		
		Connection conn = null;		// 연결
		Statement stmt = null;		// 상태
		ResultSet rs = null;		// 결과
		
		String query = "select * from clients";
		boolean result = false;
		
		// 드라이버 Connection 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");		// Connect with JDBC Driver(default)
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection( 	// Make Connection
					DB_URL, 
					DB_USER, 
					DB_PW);
			
			stmt = conn.createStatement();
			
			// stmt's query to resultSet
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				String id = rs.getString(1);		// c_id
				if(!id.equals(checkID)) {
					result =  false;		// not Overlapped
				}
				else {
					result =  true;		// Overlapped
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// cut off driver connection
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	void insertCustomer(String id, String pw, String nickname) {
		String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";	// default
		String DB_USER = "c##ora_user";
		String DB_PW = "KIM";
		
		Connection conn = null;		// 연결
		Statement stmt = null;		// 상태
		ResultSet rs = null;		// 결과
		
		String query = "insert into clients(c_id, c_pw, c_nick)" 
			+ "values('" + id + "', '" + pw + "', '" + nickname + "')";
		
		
		// 드라이버 Connection 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");		// Connect with JDBC Driver(default)
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection( 	// Make Connection
					DB_URL, 
					DB_USER, 
					DB_PW);
			
			stmt = conn.createStatement();
			
			// stmt's query to resultSet
			rs = stmt.executeQuery(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// cut off driver connection
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
