package hxy;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ServiceForm {
    private JFrame frmHuang;
    private JTable goodsInfoTable;
    private JLabel lblInformationOfGoods;
    private JLabel lblNewLabel;
    private JPanel panel;
    private JPanel enInfoPanel;
    private JLabel enName;
    private JTextField tfEnName;
    private JLabel enLinkMan;
    private JTextField tfShortName;
    private JLabel enShortName;
    private JLabel enAdress;
    private JTextField tfAdress;
    private JLabel enPostCode;
    private JTextField tfPostCode;
    private JButton addGoodsButton;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem maintainMenuItem;
    private JMenu menu_1;
    private JMenuItem searchMenuItem;
    private JTextField tfPhone;
    private JTextField tfEmail;
    private JTextField tfLinkMan;
    private int recoder=0;
	private int counter=0;                        ////////////��Ʒ��¼������
    
    private JLabel label;
    private JTextField tfOrderDate;
    private JLabel label_1;
    private JTextField tfSendDate;
    private JLabel label_2;
    private JTextField tfSendAddress;
    private JLabel label_3;
    private JTextField tfSpecial;
    private JLabel label_4;
    private JLabel label_5;
    private JLabel label_6;
    private JMenu menu_2;
    private JMenuItem menuItem;
	public ServiceForm(String uername) {
		initialize();
	}
	private void initialize() {
		frmHuang = new JFrame();  ////////////���������ڵ�����
		frmHuang.setVisible(true);
		frmHuang.setTitle("Huang&650");
		frmHuang.setBounds(0, 0, 985, 674);
		frmHuang.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHuang.getContentPane().setLayout(null);
		
		//////////��Ʒ��Ϣ����ʾ
				////////////��ͷ��Ϣ������
		Object head[]={"ID","��Ʒ����", "��װ��ʽ", "������λ", "��Ʒ����", "������", "��������", "�ۼ�"};
		Object goods[][]=new Object[35][8];
		ConnectDatabase conn=new ConnectDatabase();
		//ConnectMySQL conn = new ConnectMySQL();
		String cmd="select id,name,packstyle,unite,productarea,keeptime,description,price from Infor_Goods;";
		try {
			ResultSet re=conn.state.executeQuery(cmd);
//			while(re.next()){
//				recoder++;
//			}
//		    //goods=new Object[40][8];
//			re.beforeFirst();
//			int count=0;
			if(re.isBeforeFirst()){
				while(re.next()){
					for(int i=0;i<7;i++){
						goods[counter][i]=re.getString(i+1);
					}
					goods[counter++][7]=re.getFloat(8);
				}
			}
			re.close();
			conn.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		goodsInfoTable = new JTable(goods,head);			
		JScrollPane jsp=new JScrollPane(goodsInfoTable);
	    frmHuang.getContentPane().add(jsp);
	    jsp.setBounds(10, 54, 531, 545);
		
		lblInformationOfGoods = new JLabel("Information Of Goods");   /////////////��Ʒ��Ϣ��ǩ
		lblInformationOfGoods.setFont(new Font("����", Font.PLAIN, 16));
		lblInformationOfGoods.setHorizontalAlignment(SwingConstants.CENTER);
		frmHuang.getContentPane().add(lblInformationOfGoods);
		lblInformationOfGoods.setBounds(125, 25, 233, 30);
		
		
		lblNewLabel = new JLabel("\u5BA2\u6237\u9009\u8D2D");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 16));
		lblNewLabel.setBounds(694, 15, 115, 15);
		frmHuang.getContentPane().add(lblNewLabel);
		
		panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frmHuang.getContentPane().add(panel);
		panel.setBounds(562, 40, 382, 585);
		panel.setLayout(null);
		
		enInfoPanel = new JPanel();
		panel.add(enInfoPanel);
		enInfoPanel.setBounds(10, 10, 362, 208);
		enInfoPanel.setLayout(null);
		
		enName = new JLabel("\u4F01\u4E1A\u540D\u79F0");
		enName.setBounds(10, 10, 54, 15);
		enInfoPanel.add(enName);
		

		tfLinkMan = new JTextField();
		tfLinkMan.setBounds(74, 72, 96, 21);
		enInfoPanel.add(tfLinkMan);
		tfLinkMan.setColumns(10);
		
		tfEnName = new JTextField();
		tfEnName.addFocusListener(new FocusAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void focusLost(FocusEvent arg0) {       ////////////////////��ҵ�����ı���ʧȥ���������������ѯ��ҵ��Ϣ����ϵ����Ϣ������Ӧ�Ŀؼ���
				String enName=tfEnName.getText().trim();
				if(!"".equals(enName)){ ///////////////////��ѯ��ҵ��Ϣ
					ConnectDatabase conn=new ConnectDatabase();     /////////////////�������ݿ����Ӷ���׼���������ݿ�
					String cmd="select Enterprise.id,Enterprise.company_name,Enterprise.short_name,Enterprise.address,Enterprise.postcode,Linkman.name,Linkman.telephone,Linkman.phone,Linkman.email,Enterprise.address from Linkman,Enterprise where Enterprise.company_name=Linkman.company_name and Enterprise.company_name='"+enName+"'";
				    try {
						ResultSet re=conn.state.executeQuery(cmd);
						if(re.next()){
							re.beforeFirst();
							int counter=0;
							Object []linkman=null;
							String [][]linkManInfo=null;
							while(re.next()){        ///////////////���������ж���ҵ��ϵ�˵ĸ���
								counter++;
							}
							re.beforeFirst();
//							if(counter==0){           //////////////////��˾û����ϵ����Ϣ
//								System.out.println("cuocucocuciocucocic");
//							}
							linkman=new Object[counter];
							linkManInfo=new String[counter][3];
							counter=0;
							while(re.next()){        //////////////����ϵ�˴浽������
								linkman[counter]=re.getString(6);
								linkManInfo[counter][0]=re.getString(6);   //////////��ϵ������
								linkManInfo[counter][1]=re.getString(8);   //////////�绰
								linkManInfo[counter++][2]=re.getString(9);  //////////��������
							}
							
							for(int k=0;k<linkManInfo.length;k++){
								System.out.println(linkManInfo[k][0]+"   "+linkManInfo[k][1]+"   "+linkManInfo[k][2]);
							}
							
							re.first();     //////////////�����ҵ��Ϣ����Ӧ�Ŀؼ�
							{
								tfShortName.setText(re.getString(3));
								tfAdress.setText(re.getString(4));
								tfPostCode.setText(re.getString(5));  /////////////���ɵ���ʽѡ��Ի����е�ѡ�������
								Object selectValve=JOptionPane.showInputDialog(null, "ChooseLinkMan","LinkMan",JOptionPane.INFORMATION_MESSAGE,null,linkman,linkman[0]);
								
								for(int i=0;i<linkManInfo.length;i++){
									if(selectValve.toString().equals(linkManInfo[i][0].trim())){   /////////////�ҵ�ѡ�е���ϵ�˲�����Ϣ���뵽��Ӧ�Ŀؼ���
										tfLinkMan.setText(selectValve.toString());
										tfPhone.setText(linkManInfo[i][1]);
										tfEmail.setText(linkManInfo[i][2]);
										break;
									}
								}
							}
							Date today=new Date();
							tfOrderDate.setText((today.getYear()+1900)+"-"+(today.getMonth()+1)+"-"+today.getDate());
							tfSendDate.setText((today.getYear()+1900)+"-"+(today.getMonth()+1)+"-"+(today.getDate()+1));
							tfSendAddress.setText(re.getString(10));
							re.close();
							conn.conn.close();
						}
						else{
							JOptionPane.showMessageDialog(frmHuang, "�ù�˾�����ڣ�������������\n����»������ע�ᣡ����");
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		tfEnName.setBounds(74, 7, 96, 21);
		enInfoPanel.add(tfEnName);
		tfEnName.setColumns(10);
		
		enLinkMan = new JLabel("\u4F01\u4E1A\u8054\u7CFB\u4EBA");
		enLinkMan.setBounds(10, 75, 66, 15);
		enInfoPanel.add(enLinkMan);
		
		tfShortName = new JTextField();
		tfShortName.setBounds(243, 7, 109, 21);
		enInfoPanel.add(tfShortName);
		tfShortName.setColumns(10);
		
		enShortName = new JLabel("\u4F01\u4E1A\u7B80\u79F0");
		enShortName.setBounds(180, 10, 54, 15);
		enInfoPanel.add(enShortName);
		
		enAdress = new JLabel("\u4F01\u4E1A\u5730\u5740");
		enAdress.setBounds(10, 41, 54, 15);
		enInfoPanel.add(enAdress);
		
		tfAdress = new JTextField();
		tfAdress.setBounds(74, 38, 96, 21);
		enInfoPanel.add(tfAdress);
		tfAdress.setColumns(10);
		
		enPostCode = new JLabel("\u90AE\u7F16");
		enPostCode.setBounds(190, 41, 54, 15);
		enInfoPanel.add(enPostCode);
		
		tfPostCode = new JTextField();
		tfPostCode.setBounds(243, 38, 109, 21);
		enInfoPanel.add(tfPostCode);
		tfPostCode.setColumns(10);
		
		JLabel laPhone = new JLabel("\u8054\u7CFB\u7535\u8BDD");
		laPhone.setBounds(180, 72, 54, 15);
		enInfoPanel.add(laPhone);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(74, 103, 145, 21);
		enInfoPanel.add(tfEmail);
		tfEmail.setColumns(10);
		
		JLabel laEmail = new JLabel("\u7535\u5B50\u4FE1\u7BB1");
		laEmail.setBounds(10, 106, 54, 15);
		enInfoPanel.add(laEmail);
		
		tfPhone = new JTextField();
		tfPhone.setBounds(243, 69, 109, 21);
		enInfoPanel.add(tfPhone);
		tfPhone.setColumns(10);
		
		label = new JLabel("\u8BA2\u5355\u65E5\u671F");
		label.setBounds(10, 134, 54, 15);
		enInfoPanel.add(label);
		
		tfOrderDate = new JTextField();
		tfOrderDate.setBounds(74, 128, 96, 21);
		enInfoPanel.add(tfOrderDate);
		tfOrderDate.setColumns(10);
		
		label_1 = new JLabel("\u9001\u8D27\u65E5\u671F");
		label_1.setBounds(180, 134, 54, 15);
		enInfoPanel.add(label_1);
		
		tfSendDate = new JTextField();
		tfSendDate.setBounds(243, 131, 109, 21);
		enInfoPanel.add(tfSendDate);
		tfSendDate.setColumns(10);
		
		label_2 = new JLabel("\u9001\u8D27\u5730\u5740");
		label_2.setBounds(10, 164, 54, 15);
		enInfoPanel.add(label_2);
		
		tfSendAddress = new JTextField();
		tfSendAddress.setBounds(74, 159, 278, 21);
		enInfoPanel.add(tfSendAddress);
		tfSendAddress.setColumns(10);
		
		label_3 = new JLabel("\u7279\u6B8A\u8BF4\u660E");
		label_3.setBounds(10, 189, 54, 15);
		enInfoPanel.add(label_3);
		
		tfSpecial = new JTextField();
		tfSpecial.setBounds(74, 186, 278, 21);
		enInfoPanel.add(tfSpecial);
		tfSpecial.setColumns(10);
		
		label_4 = new JLabel("\u5E74");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(253, 109, 25, 15);
		enInfoPanel.add(label_4);
		
		label_5 = new JLabel("\u6708");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(276, 109, 25, 15);
		enInfoPanel.add(label_5);
		
		label_6 = new JLabel("\u65E5");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(298, 109, 25, 15);
		enInfoPanel.add(label_6);
		
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_2);
		panel_2.setLayout(null);
		panel_2.setBounds(10, 253, 362, 295);
		
		Object []headGoods={"ID","��Ʒ����","������λ","����","����"};
		Object [][]goodsData=new Object[20][5];
		JTable goodsTable=new JTable(goodsData,headGoods);
		
		JScrollPane scrollPane = new JScrollPane(goodsTable);
		scrollPane.setBounds(0, 0, 362, 285);
		panel_2.add(scrollPane);
		
		
		JLabel enOrderForm = new JLabel("\u8BA2\u5355\u8BE6\u60C5");
		enOrderForm.setHorizontalAlignment(SwingConstants.CENTER);
		enOrderForm.setBounds(10, 228, 54, 15);
		panel.add(enOrderForm);
		
		
		addGoodsButton = new JButton("\u6DFB\u52A0\u5546\u54C1");
		addGoodsButton.addActionListener(new ActionListener() {    ////////////////////������Ʒ��Ӱ�ť������
			public void actionPerformed(ActionEvent arg0) {
//				System.out.println(goodsInfoTable.getSelectedRow()+"");
//				System.out.println(goodsInfoTable.getValueAt(goodsInfoTable.getSelectedRow(), 0)+"  jjjj");
				if(goodsInfoTable.getSelectedRow()>=0&&goodsInfoTable.getSelectedRow()<counter){      /////////////ѡ�е���Ʒ����
					if(recoder<=goodsTable.getRowCount()){
						goodsTable.setValueAt(goodsInfoTable.getValueAt(goodsInfoTable.getSelectedRow(), 0), recoder, 0);   /////////////ѡ����Ϣ������Ʒ��ӵ�������
						goodsTable.setValueAt(goodsInfoTable.getValueAt(goodsInfoTable.getSelectedRow(), 1), recoder, 1);
						goodsTable.setValueAt(goodsInfoTable.getValueAt(goodsInfoTable.getSelectedRow(), 3), recoder, 2);
						goodsTable.setValueAt(goodsInfoTable.getValueAt(goodsInfoTable.getSelectedRow(), 7), recoder++, 3);
					}
					else{           /////////////����¼�����ڱ�������ʱ
						
					}
				}	
				else{
					JOptionPane.showMessageDialog(frmHuang, "��ѡ����Ʒ������");
				}
			}
		});
		addGoodsButton.setBounds(177, 228, 93, 23);
		panel.add(addGoodsButton);
		
		JButton submitButton = new JButton("\u63D0\u4EA4\u8BA2\u5355");
		submitButton.addActionListener(new ActionListener() {                     ///////////�ύ������ť������
			public void actionPerformed(ActionEvent arg0) {
				if(recoder>0){					
					Date orderTime=new Date();	
					@SuppressWarnings("deprecation")
					String orderID="650"+tfPhone.getText().trim()+orderTime.getYear()+(orderTime.getMonth()>10?orderTime.getMonth()+1:"0"+(orderTime.getMonth()+1))+(orderTime.getDate()>10?orderTime.getDate()+1:"0"+(orderTime.getDate()+1));  /////////���ɶ�����
					try {
						ConnectDatabase conn=new ConnectDatabase();
						String validCmd="select bookMan from orderInfo where orderID='"+orderID+"'";
						ResultSet re=conn.state.executeQuery(validCmd);       ////////////////////�ж��Ƿ��Ѿ��ύ��һ�ζ���
						if(re.next()){
							JOptionPane.showMessageDialog(frmHuang, "�Բ���������Ѿ��ύ�˶���������\n������Ϊ��"+orderID);
							conn.conn.close();
							re.close();
						}
						else{
							String cmd="insert into orderInfo values('";        //////////��Ӷ�����Ϣ���������Զ����ɣ�����Ϊʱ��Ӷ������ֻ��ţ�					
						    cmd=cmd+orderID+"','"+tfOrderDate.getText().trim()+"','"+tfSendDate.getText().trim()+"','"+tfSendAddress.getText().trim()+"','"+tfSpecial.getText().trim()+"','"+tfLinkMan.getText().trim()+"')";
							conn.state.executeUpdate(cmd);
							System.out.println("Order has been created!!!");
							//conn.state.close();
							
							///////////////�������������
							try{
								//ConnectSQLServer conn=new ConnectSQLServer();
								cmd="insert into orderGoodsInfo values('"+orderID+"','";
								int count=0;
								while(goodsTable.getValueAt(count, 0)!=null){     //////////�жϱ���е�����������
									cmd=cmd+goodsTable.getValueAt(count, 0)+"','"+goodsTable.getValueAt(count, 1)+"',"+Float.parseFloat(goodsTable.getValueAt(count,4).toString().trim())+","+Float.parseFloat(goodsTable.getValueAt(count, 3).toString().trim())+","+Float.parseFloat(goodsTable.getValueAt(count, 3).toString().trim())*Float.parseFloat(goodsTable.getValueAt(count, 4).toString().trim())+")";
								    conn.state.executeUpdate(cmd);
								    cmd="insert into orderGoodsInfo values('"+orderID+"','";
								    count++;
								}
								conn.state.close();
								new JOptionPane();
								JOptionPane.showMessageDialog(frmHuang, "�����ύ�ɹ�������\n������Ϊ��"+orderID);
							}catch (SQLException e) {
								e.printStackTrace();
							}
						}						
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					
				}
				else{
					JOptionPane.showMessageDialog(frmHuang, "��ѡ����Ʒ�����ύ����������");
				}
			}
		});
		submitButton.setBounds(279, 552, 93, 23);
		panel.add(submitButton);
		
		JButton reduceGoodsButton = new JButton("\u5220\u9664\u5546\u54C1");
		reduceGoodsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {                  //////////////////ѡ����Ʒɾ����ť������
				if(goodsTable.getSelectedRow()>=0&&goodsTable.getSelectedRow()<recoder){
					if(!"".equals(goodsTable.getValueAt(goodsTable.getSelectedRow(), 0).toString().trim())){
						if(goodsTable.getSelectedRow()==(recoder-1)){    ////////////�ж�ɾ������Ϣ�Ƿ�Ϊ���һ��
							for(int i=0;i<goodsTable.getColumnCount();i++){
								goodsTable.setValueAt("", goodsTable.getSelectedRow(), i);
							}
							recoder--;
						}
						else{         //////////////�������һ��
							for(int i=goodsTable.getSelectedRow();i<recoder-1;i++){        ///////////////������ǰ�ƶ�һ��
								for(int j=0;j<goodsTable.getColumnCount();j++){
									goodsTable.setValueAt(goodsTable.getValueAt(i+1, j), i, j);								
								}
							}
							for(int k=0;k<goodsTable.getColumnCount();k++){          /////////////���һ���ÿ�
								goodsTable.setValueAt("", recoder-1, k);
							}
							recoder--;
						}
					}
				}	
				else{
					JOptionPane.showMessageDialog(frmHuang, "��ѡ����Ҫɾ������Ʒ������");
				}
			}
		});
		reduceGoodsButton.setBounds(280, 228, 93, 23);
		panel.add(reduceGoodsButton);
		
		
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 115, 30);
		frmHuang.getContentPane().add(menuBar);
		
		menu = new JMenu("\u7EF4\u62A4");
		menuBar.add(menu);
		
		maintainMenuItem = new JMenuItem("\u7EF4\u62A4\u5546\u54C1"); ////////ά����Ʒ��Ϣ�˵���ѡ��
		maintainMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new MaintainForm();
			}
		});
		menu.add(maintainMenuItem);
		
		menu_1 = new JMenu("\u67E5\u8BE2");
		menuBar.add(menu_1);
		
		searchMenuItem = new JMenuItem("\u67E5\u8BE2\u5BA2\u6237\u4FE1\u606F");
		searchMenuItem.addMouseListener(new MouseAdapter() {                          /////////////////��ѯ�ͻ���Ϣ�˵�������
			@Override
			public void mousePressed(MouseEvent arg0) {                     ////////////////����һ��������ʾ�ͻ�������Ϣ��customer��
				//System.out.println("hhhhhhhhhhhhhhhhhhhhhhhh");
				new CustomerOrderForm();
			} 
		});
		menu_1.add(searchMenuItem);
		
		menu_2 = new JMenu("\u5237\u65B0");
		menuBar.add(menu_2);
		
		menuItem = new JMenuItem("\u4FE1\u606F\u5237\u65B0");
		menuItem.addMouseListener(new MouseAdapter() {                  //////////////////////ҳ��ˢ�²˵����������е�������գ����¶�ȡ���ݿ���ȡ��Ʒ��Ϣ
			@Override
			public void mousePressed(MouseEvent arg0) {
				System.out.println(counter);
				int count=0;
				System.out.println(goodsInfoTable.getValueAt(count, 0).toString().trim());
				while(count<counter){/////////////////�жϱ���������,���������
					for(int i=0;i<goodsInfoTable.getColumnCount();i++){
						goodsInfoTable.setValueAt("", count, i);          //////////////������еĵ�Ԫ���ÿ�
					}
					count++;
				}
				String cmd="select id,name,packstyle,unite,productarea,keeptime,description,price from Infor_Goods;";
				System.out.println(cmd);
				ConnectDatabase conn=new ConnectDatabase();
				try {
					ResultSet re=conn.state.executeQuery(cmd);
					counter=0;
					while(re.next()){
						for(int i=0;i<goodsInfoTable.getColumnCount();i++){
							goodsInfoTable.setValueAt(re.getString(i+1), counter, i);							
						}
						counter++;
					}
					re.close();
					conn.state.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		menu_2.add(menuItem);
	}
}
