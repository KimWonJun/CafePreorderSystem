package miniProject;

import java.awt.EventQueue;
import java.awt.Window;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//import miniProject.SignupPage;
//import update.After_Login_P;
//import miniProject.After_Login_Cor;
//import miniProject.After_Login_Per;

public class FirstPage {
	
//	SignupPage SUP;

	private JFrame frame;
	public JTextField ID;
	private JPasswordField PW;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FirstPage window = new FirstPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	boolean searchIDPW(String checkID, String checkPW, boolean customer) {
		System.out.println("searchIDPW �Լ� ȣ��");
		
		String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";	// default
		String DB_USER = "c##ora_user";
		String DB_PW = "KIM";
		
		Connection conn = null;		// ����
		Statement stmt = null;		// ����
		ResultSet rs = null;		// ���
		String query  = "";
		
		if(customer == true)
			query = "select * from customer";
		else
			query = "select * from store";
		boolean result = false;
		
		// ����̹� Connection 
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
			System.out.println(checkID + "-------" + checkPW);
			while(rs.next()) {
				String id = rs.getString(3);		// c_id
				if(customer == true) {
					String pw = rs.getString(4); // c_pw
					System.out.println(id + " ------- " + pw);

					if (id.equals(checkID)) {
						if (pw.equals(checkPW)) {
							result = true;
							break;
						} else {
							result = false;
						}
					}
				} else if (customer == false) {
					if (id.equals(checkID)) {
						result = true;
						break;
					} else
						result = false;
				} else {
					result = false; // Overlapped
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

	/**
	 * Create the application.
	 */
	public FirstPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(300, 300, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Corporation_Member
		JRadioButton Cor_Member = new JRadioButton("기업 회원");
		//Peronal_Member
		JRadioButton Per_Member = new JRadioButton("개인 회원");		
		//�׷�ȭ
		ButtonGroup  login_radio_group = new ButtonGroup();
		login_radio_group.add(Cor_Member);
		login_radio_group.add(Per_Member);
		
		Cor_Member.setBounds(121, 64, 105, 23);
		frame.getContentPane().add(Cor_Member);
		
		Per_Member.setBounds(230, 64, 95, 23);
		frame.getContentPane().add(Per_Member);
		
		
		JLabel Input_ID = new JLabel("아이디 : ");
		Input_ID.setBounds(133, 110, 57, 15);
		frame.getContentPane().add(Input_ID);
		
		JLabel Input_PW = new JLabel("패스워드 : ");
		Input_PW.setBounds(133, 138, 57, 15);
		frame.getContentPane().add(Input_PW);
		
		ID = new JTextField();
		ID.setBounds(187, 107, 116, 21);
		frame.getContentPane().add(ID);
		ID.setColumns(10);
		
		JButton Sign_up = new JButton("회원가입");
//		SUP = new SignupPage();
		Sign_up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ȸ������ ������ ����
//				SUP.frame.setVisible(true);
			}
		});
		Sign_up.setBounds(89, 197, 97, 23);
		frame.getContentPane().add(Sign_up);
		
		JButton Sign_in_chk = new JButton("로그인");
		Sign_in_chk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (Cor_Member.isSelected()) {
					boolean login = searchIDPW(ID.getText(), String.valueOf(PW.getPassword()), false);
					if (login == true) { // ���̵�, ��й�ȣ ��ġ Ȯ��
						System.out.println("��ȸ���� �½��ϴ�");
//						After_Login_Cor ALC = new After_Login_Cor();
						frame.dispose();
//						ALC.setVisible(true);
					} else if (ID.getText().equals("") || String.copyValueOf(PW.getPassword()).equals("")) {
						JOptionPane.showMessageDialog(null, "���̵� �Ǵ� ��й�ȣ�� �Է��ϼ���",
								"�Է°� ����", JOptionPane.DEFAULT_OPTION);
					}
					else { // ���̵�, ��й�ȣ ����ġ
						JOptionPane.showMessageDialog(null, "���̵� �Ǵ� �н����带 Ȯ���ϼ���",
								"���̵�/��й�ȣ ����", JOptionPane.DEFAULT_OPTION);
						PW.setText("");
					}
				} else if (Per_Member.isSelected()) {
					boolean login = searchIDPW(ID.getText(), String.valueOf(PW.getPassword()), true);
					if (login == true) { // ���̵�, ��й�ȣ ��ġ Ȯ��
						System.out.println("���ȸ���� �½��ϴ�");
//						After_Login_Per ALP = new After_Login_Per();
						frame.dispose();
//						ALP.setVisible(true);
					} else if (ID.getText().equals("") || String.copyValueOf(PW.getPassword()).equals("")) {
						JOptionPane.showMessageDialog(null, "���̵� �Ǵ� ��й�ȣ�� �Է��ϼ���", "Enter Id or Password",
								JOptionPane.DEFAULT_OPTION);
					} else { // ���̵�, ��й�ȣ ����ġ
						JOptionPane.showMessageDialog(null, "���̵� �Ǵ� �н����带 Ȯ���ϼ���",
								"���̵�/��й�ȣ ����", JOptionPane.DEFAULT_OPTION);
					}
				} else {
					JOptionPane.showMessageDialog(null, "�Ҽ��� ������ �ּ���");
				}

			}
		});
		Sign_in_chk.setBounds(196, 197, 74, 23);
		frame.getContentPane().add(Sign_in_chk);
		
		JButton Sign_in_close = new JButton("닫기");
		Sign_in_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dialogButton =  JOptionPane.showConfirmDialog(null, "���� �����Ͻðڽ��ϱ�?","����",JOptionPane.YES_NO_OPTION);
				if(dialogButton == JOptionPane.YES_OPTION)
					frame.dispose();
			}
		});
		Sign_in_close.setBounds(282, 197, 74, 23);
		frame.getContentPane().add(Sign_in_close);
		
		PW = new JPasswordField();
		PW.setBounds(187, 135, 116, 21);
		frame.getContentPane().add(PW);

	}
}
