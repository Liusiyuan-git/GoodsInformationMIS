package hxy;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LogIn {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	ConnectDatabase conn=null;
	Statement state=null;
	ResultSet re=null;
	public LogIn() {
		initialize();
	}
	private void initialize() {
		frame = new JFrame();	
		frame.setVisible(true);
		//frame.getContentPane().setBackground();
		frame.getContentPane().setLayout(null);
		frame.setBounds(100, 100, 529, 347);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblHang = new JLabel("Hang&650");
		lblHang.setHorizontalAlignment(SwingConstants.CENTER);
		lblHang.setFont(new Font("����", Font.PLAIN, 18));
		lblHang.setBounds(223, 30, 106, 38);
		frame.getContentPane().add(lblHang);
		
		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setFont(new Font("����", Font.PLAIN, 16));
		lblUsername.setBounds(72, 75, 86, 27);
		frame.getContentPane().add(lblUsername);
		
		textField = new JTextField();
		textField.setBounds(193, 76, 183, 27);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("PassWord");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("����", Font.PLAIN, 16));
		lblPassword.setBounds(73, 130, 85, 27);
		frame.getContentPane().add(lblPassword);
		
		JButton btnLogin = new JButton("Login");   //////////////////��¼��ť������
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("����", Font.PLAIN, 16));
		passwordField.setEchoChar('*');
		passwordField.setBounds(193, 130, 183, 27);
		frame.getContentPane().add(passwordField);
		
		passwordField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@SuppressWarnings("deprecation")
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==(char)10){
					if((!"".equals(textField.getText().trim()))&&(!"".equals(passwordField.getText().trim()))){/////�ж������ֵ�Ƿ�Ϊ��
						conn=new ConnectDatabase();  /////////���������ݿ����ʵ����׼���û���������֤
						String cmd=null;
						cmd="select password,role from staff_Infor where username='"+textField.getText().trim()+"';";
						try {
							re=conn.state.executeQuery(cmd);
							if(re.next()){
								if(passwordField.getText().equals(re.getString(1).trim())){ ///////////������֤�ɹ�
									System.out.println("Log in succeed!!!");
									frame.dispose(); /////////��¼�����˳�
									if(re.getString(2).trim().equals("Administrator".trim())){    ////////�ж�Ȩ�޽��벻ͬ�Ľ��棬����Ա
										new OwnerForm();
									}
									else if(re.getString(2).trim().equals("Buyer".trim())){//////////�ɹ�Ա
										new BuyerForm();
									}
									else if(re.getString(2).trim().equals("Service".trim())){                  /////////////�ͷ�									
										new ServiceForm(textField.getText().trim());
									}
									else{
										JOptionPane.showMessageDialog(frame, "�û�Ȩ�޲����ڣ�����\n�����µ�¼");
									}
								}
								else{                                  ///////////////////������֤ʧ��
									JOptionPane.showMessageDialog(frame, "������󣡣���\n����������");
								}
							}
							else{
								JOptionPane.showMessageDialog(frame, "�û��������ڣ�����\n����������");
							}
							conn.conn.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(frame, e1);
						}
					}
				
				}
			}
		});
		

		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				if((!"".equals(textField.getText().trim()))&&(!"".equals(passwordField.getText().trim()))){/////�ж������ֵ�Ƿ�Ϊ��
					try {
						conn=new ConnectDatabase();  /////////���������ݿ����ʵ����׼���û���������֤
						String cmd=null;
						cmd="select password,role from staff_Infor where username='"+textField.getText().trim()+"';";						
						re=conn.state.executeQuery(cmd);
						if(re.next()){
							if(passwordField.getText().equals(re.getString(1).trim())){ ///////////������֤�ɹ�
								System.out.println("Log in succeed!!!");
								frame.dispose(); /////////��¼�����˳�
								if(re.getString(2).trim().equals("Administrator".trim())){    ////////�ж�Ȩ�޽��벻ͬ�Ľ��棬����Ա
									new OwnerForm();
								}
								else if(re.getString(2).trim().equals("Buyer".trim())){//////////�ɹ�Ա
									new BuyerForm();
								}
								else if(re.getString(2).trim().equals("Service".trim())){                  /////////////�ͷ�									
									new ServiceForm(textField.getText().trim());
								}
								else{
									JOptionPane.showMessageDialog(frame, "�û�Ȩ�޲����ڣ�����\n�����µ�¼");
								}
							}
							else{                                  ///////////////////������֤ʧ��
								JOptionPane.showMessageDialog(frame, "������󣡣���\n����������");
							}
						}
						else{
							JOptionPane.showMessageDialog(frame, "�û��������ڣ�����\n����������");
						}
						conn.conn.close();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frame, "Database connection Error!!!"+e);
						e.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(frame, "�������û���������!!!");
				}
			}
		});
		btnLogin.setBounds(193, 189, 68, 23);
		frame.getContentPane().add(btnLogin);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(btnRegister, "���Թ���Ա��ݽ��������Ϣ������");
			}
		});
		btnRegister.setBounds(279, 189, 92, 23);
		frame.getContentPane().add(btnRegister);
	}
}
