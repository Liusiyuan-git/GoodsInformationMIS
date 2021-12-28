package hxy;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JComboBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BuyerForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField tfbuyer;
	private JTextField tfBuyTime;
    private	JTable needGoodsTable;
	private int recoder=0;      /////////////////��¼�������������Ʒ�����¼��
	private int counter=0;       //////////��¼�ɹ���Ʒ��Ϣ
	@SuppressWarnings("deprecation")
	public BuyerForm() {
		setVisible(true);
		setTitle("�ɹ�Աά��");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 756, 531);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 71, 330, 377);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("\u9700\u6C42\u4FE1\u606F");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("����", Font.PLAIN, 16));
		label.setBounds(118, 10, 92, 27);
		getContentPane().add(label);
		
		JLabel lblNewLabel = new JLabel("\u8BA2\u5355\r\n\u65F6\u95F4");
		lblNewLabel.setBounds(51, 50, 54, 15);
		getContentPane().add(lblNewLabel);
		
		tfbuyer = new JTextField();
		tfbuyer.setBounds(456, 43, 92, 21);
		getContentPane().add(tfbuyer);
		tfbuyer.setColumns(10);
		
		JLabel label_1 = new JLabel("\u91C7\u8D2D\u5355\u4FE1\u606F");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("����", Font.PLAIN, 16));
		label_1.setBounds(519, 13, 92, 20);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u91C7\u8D2D\u65F6\u95F4");
		label_2.setBounds(577, 50, 54, 15);
		getContentPane().add(label_2);
		
		tfBuyTime = new JTextField();
		tfBuyTime.setBounds(635, 43, 85, 21);
		getContentPane().add(tfBuyTime);
		tfBuyTime.setColumns(10);
		{          //////////////Ԥ��ɹ�ʱ��
			Date now=new Date();
			tfBuyTime.setText((now.getYear()+1900)+"-"+(now.getMonth()+1)+"-"+now.getDate());
		}
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(400, 70, 320, 377);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		Object []headBuy={"��ƷID","��Ʒ����","�ɹ�����","����","���"};       ////////////////��ʾ�ɹ���Ϣ��
		Object [][]buyGoods=new Object[25][5];
		JTable buyGoodsTable=new JTable(buyGoods,headBuy);
		JScrollPane buyGoodsjsp=new JScrollPane(buyGoodsTable);
		panel_1.add(buyGoodsjsp);
		buyGoodsjsp.setBounds(0, 0, 320, 377);
		
		
		JButton button = new JButton("\u91C7\u8D2D\u8BA2\u5355\u6253\u5370");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button.setBounds(605, 457, 115, 23);
		getContentPane().add(button);
		
		Object []head={"��ƷID","��Ʒ����","��������","����","���"};       ////////////��ʾ������Ϣ��
		Object [][]needGoods=new Object[25][5];
		needGoodsTable=new JTable(needGoods,head);
		JScrollPane needGoodsjsp=new JScrollPane(needGoodsTable);
		panel.add(needGoodsjsp);
		needGoodsjsp.setBounds(0,0,330, 377);
		
		JButton purchaseTableButton = new JButton("\u91C7\u8D2D\u5355\u751F\u6210");
		purchaseTableButton.addActionListener(new ActionListener() {
			@SuppressWarnings({ "static-access" })
			public void actionPerformed(ActionEvent arg0) {
				if(counter>0){   /////////////�ɹ�����������
					Date now=new Date();
					String purDate=(now.getYear()+1900)+""+(now.getMonth()+1)+""+now.getDate()+"";//////////////��ȡϵͳ��ǰʱ�����ɵ������ɹ���Ϣ
					String cmd="insert into buyGoodsInfo values('"+purDate+"',";
					int count=0; ///////////////��¼�ɹ�����¼�������				
					while(count<counter){ /////////////////�жϲɹ����еļ�¼�ĵ�һ���Ƿ�Ϊ��
						cmd+="'"+buyGoodsTable.getValueAt(count, 0)+"','"+buyGoodsTable.getValueAt(count, 1)+"',"+Float.parseFloat(buyGoodsTable.getValueAt(count, 2).toString())+","
					         +Float.parseFloat(buyGoodsTable.getValueAt(count, 3).toString())+","
								+Float.parseFloat(buyGoodsTable.getValueAt(count, 2).toString())*Float.parseFloat(buyGoodsTable.getValueAt(count, 3).toString())+")";
						System.out.println(cmd);
						try {
							ConnectDatabase conn=new ConnectDatabase();
							conn.state.executeUpdate(cmd);
							conn.state.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						count++;
						cmd="insert into buyGoodsInfo values('"+purDate+"',";
					}
					new JOptionPane().showMessageDialog(purchaseTableButton, "�ɹ���������ɣ�����");
				}
				else{
					new JOptionPane().showMessageDialog(purchaseTableButton, "������ɹ���Ϣ������");
				}
				
			}
		});
		purchaseTableButton.setBounds(398, 455, 103, 27);
		getContentPane().add(purchaseTableButton);
		
		JButton buyerAddButton = new JButton(">>");
		buyerAddButton.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				if(needGoodsTable.getSelectedRow()>=0&&needGoodsTable.getSelectedRow()<recoder){
					for(int i=0;i<needGoodsTable.getColumnCount();i++){
						buyGoodsTable.setValueAt(needGoodsTable.getValueAt(needGoodsTable.getSelectedRow(), i), counter, i);
					}
					counter++;
				}
				else{
					new JOptionPane().showMessageDialog(buyerAddButton, "��ѡ���Ѳɹ�������Ʒ������");
				}
			}
		});
		buyerAddButton.setBounds(345, 206, 54, 23);
		getContentPane().add(buyerAddButton);
		
		JButton buyerDeleteButton = new JButton("<<");
		buyerDeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(buyGoodsTable.getSelectedRow()>=0&&buyGoodsTable.getSelectedRow()<counter){
					if(buyGoodsTable.getSelectedRow()==counter-1){
						for(int i=0;i<buyGoodsTable.getColumnCount();i++){
							buyGoodsTable.setValueAt(null, buyGoodsTable.getSelectedRow(), i);
						}
						counter--;
					}
					else{
						for(int i=buyGoodsTable.getSelectedRow();i<counter-1;i++){
							for(int j=0;j<buyGoodsTable.getColumnCount();j++){
								buyGoodsTable.setValueAt(buyGoodsTable.getValueAt(i+1, j), i, j);
							}
						}
						for(int k=0;k<buyGoodsTable.getColumnCount();k++){
							buyGoodsTable.setValueAt(null, counter-1, k);
						}
						counter--;
					}
					
				}
				else{  ///////////////δѡ��Ҫɾ���Ĳɹ���Ʒ
					
				}
			}
		});
		buyerDeleteButton.setBounds(345, 260, 54, 23);
		getContentPane().add(buyerDeleteButton);
		
		JComboBox<Integer> yearComboBox = new JComboBox<Integer>();
		yearComboBox.addFocusListener(new FocusAdapter() {   ////////////////////���ѡ�������
			@Override
			public void focusLost(FocusEvent arg0) {
				String year=yearComboBox.getSelectedItem().toString();
				System.out.println(year+"hhhhhhhhhhhhhhhh");
			}
		});
		yearComboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		yearComboBox.addItem(2015);
		yearComboBox.addItem(2016);
		yearComboBox.addItem(2017);
		yearComboBox.setBounds(107, 47, 71, 21);
		getContentPane().add(yearComboBox);
		
		JLabel label_3 = new JLabel("\u5E74");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(177, 50, 22, 15);
		getContentPane().add(label_3);

		JComboBox<Integer> monthComboBox = new JComboBox<Integer>();
		for(int i=1;i<13;i++){
			monthComboBox.addItem(i);
		}
		monthComboBox.setBounds(200, 47, 54, 21);
		getContentPane().add(monthComboBox);
		
		JComboBox<Integer> dayComboBox = new JComboBox<Integer>();
		dayComboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				Date now=new Date();
			    if((now.getYear()+1900)<Integer.parseInt(yearComboBox.getSelectedItem().toString())){ ////////////////��ݳ�����ǰ���
			    	System.out.println("��ѡ�����ݳ����˵�ǰ����ݣ�����");
			    }
			    else if((now.getYear()+1900)==Integer.parseInt(yearComboBox.getSelectedItem().toString())){  ////////////��ݺ͵�ǰ�����ͬ
			    	if((now.getMonth()+1)<Integer.parseInt(monthComboBox.getSelectedItem().toString())){ ///////////�·ݳ�����ǰ�·�
			    		System.out.println("��ѡ����·ݳ����˵�ǰ���·ݣ�����");
			    	}
			    	else if(now.getMonth()+1==Integer.parseInt(monthComboBox.getSelectedItem().toString())){///////////�·ݺ͵�ǰ�·���ͬ
			    		if(now.getDate()<Integer.parseInt(dayComboBox.getSelectedItem().toString())){ //////////����������ǰ����
			    			System.out.println("��ѡ������������˵�ǰ������������");
			    		}
			    		else{   ///////////������ͬ�������ڵ�ǰ����֮ǰ���߾��ǵ������ڣ�Ȼ����������ڶ�����Ϣ��Ͷ�����Ʒ��ϸ��Ϣ���н��в�ѯ�ͻ���
					    	String orderDate=yearComboBox.getSelectedItem().toString().trim()+"-"+monthComboBox.getSelectedItem().toString().trim()
					    			+"-"+dayComboBox.getSelectedItem().toString().trim();
					    	orderAdd(orderDate);
					    	//tfBuyTime.setText();
			    		}
			    	}
			    	else{////////////�����ͬ�·��ڵ�ǰ�·�֮ǰ
			    		String orderDate=yearComboBox.getSelectedItem().toString().trim()+"-"+monthComboBox.getSelectedItem().toString().trim()
				    			+"-"+dayComboBox.getSelectedItem().toString().trim();
				    	orderAdd(orderDate);
			    	}
			    }
			    else{ ////////////����ڵ�ǰ����֮ǰ
			    	String orderDate=yearComboBox.getSelectedItem().toString().trim()+"-"+monthComboBox.getSelectedItem().toString().trim()
			    			+"-"+dayComboBox.getSelectedItem().toString().trim();
			    	orderAdd(orderDate);
			    }
			}
		});
		dayComboBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		dayComboBox.setBounds(275, 47, 55, 21);
		getContentPane().add(dayComboBox);
		
		
		monthComboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {          /////////////////�·������б��ʧȥ���㣬��ѡ������
				dayComboBox.removeAllItems();
				if("1".equals(monthComboBox.getSelectedItem().toString())|"3".equals(monthComboBox.getSelectedItem().toString())
						|"5".equals(monthComboBox.getSelectedItem().toString())|"7".equals(monthComboBox.getSelectedItem().toString())
						|"8".equals(monthComboBox.getSelectedItem().toString())|"10".equals(monthComboBox.getSelectedItem().toString())
						|"12".equals(monthComboBox.getSelectedItem().toString())){
					for(int i=1;i<32;i++){
						dayComboBox.addItem(i);
					}					
				}
				else if("2".equals(monthComboBox.getSelectedItem().toString())){
					if(Integer.parseInt(yearComboBox.getSelectedItem().toString())%4==0){
						for(int i=1;i<30;i++){
							dayComboBox.addItem(i);
						}
					}else{
						for(int i=1;i<29;i++){
							dayComboBox.addItem(i);
						}
					}
				}
				else{
					for(int i=1;i<31;i++){
						dayComboBox.addItem(i);
					}
				}
			}
		});
		
		JLabel label_4 = new JLabel("\u6708");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(255, 50, 22, 15);
		getContentPane().add(label_4);
		
		
		
		JLabel label_5 = new JLabel("\u65E5");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(326, 50, 22, 15);
		getContentPane().add(label_5);
		
		JLabel label_6 = new JLabel("\u91C7\u8D2D\u5458");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(403, 46, 54, 15);
		getContentPane().add(label_6);
	}
	
	@SuppressWarnings("static-access")
	void orderAdd(String orderDate){         ///////////////������Ϣ�ۺ�
		//////////�Ƚ����ÿգ�����ʾ��ѯ�õ��Ķ�������
				if(recoder>0){
					for(int k=0;k<recoder;k++){
						for(int j=0;j<needGoodsTable.getColumnCount();j++){
							needGoodsTable.setValueAt(null, k, j);
						}
					}
					recoder=0;
				}
				else{
					recoder=0;					
				}
		/////////////���ɳ�ʼ���󶩵�,�Բɹ���������ƷID���б������ҳ���ͬ����Ʒ������������
		for(int i=1;i<10;i++){
			String id="00"+i;     //////////��ƷID�������ڲ���
			String cmd="select orderGoodsInfo.goodsID,orderGoodsInfo.goodsName,orderGoodsInfo.goodsCount,orderGoodsInfo.price from orderGoodsInfo,orderInfo where orderGoodsInfo.orderID=orderInfo.orderID and goodsID='"+id+"' and orderDate='"+orderDate+"';";
			ConnectDatabase conn=new ConnectDatabase();
			try {
				ResultSet re=conn.state.executeQuery(cmd);
				float count=0;      /////////�����Ʒ������
				
				while(re.next()){
					count+=re.getFloat(3);
				}
				if(count>0){            ///////////////�ж���Ʒ��������0�����ж�������Ʒ
					re.first();
					needGoodsTable.setValueAt(re.getString(1), recoder, 0); /////��ƷID
					needGoodsTable.setValueAt(re.getString(2), recoder, 1);  ///////��Ʒ����
					needGoodsTable.setValueAt(count, recoder, 2);            ////////��Ʒ�����ܺ�
					needGoodsTable.setValueAt(re.getFloat(4), recoder, 3);  //////////��Ʒ����
					needGoodsTable.setValueAt(count*re.getFloat(4), recoder, 4);  /////////�ܶ�
					recoder++;
				}
				conn.state.close();
				re.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(recoder<=0){
			new JOptionPane().showMessageDialog(needGoodsTable, "����ѡ���ڵ���û�ж���������");
		}
		else{
			new JOptionPane().showMessageDialog(needGoodsTable, "������Ϣ�ۺϳɹ�������");
		}
	}
}
