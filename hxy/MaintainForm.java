package hxy;

import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MaintainForm extends JFrame {
	private static final long serialVersionUID = 1L;
	
	Object head[]={"ID","��Ʒ����", "��װ��ʽ", "������λ", "��Ʒ����", "������", "��������", "�ۼ�"};
	Object goods[][]=new Object[30][8];
	JTable goodsInfoTable=null;
	JPanel goodsInfoPanel=null;
	
	JLabel label=null;
	JButton freshButton=null;
	JButton addGoodsButton =null;
	JButton deleteGoodsButton =null;
	JButton updateGoodsButton =null;
	int recoder=0;
	public MaintainForm() {
		setVisible(true);
		setTitle("��Ʒά��");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 743, 486);
		getContentPane().setLayout(null);
		load();
		init();
	}
	
	void load(){		
		label = new JLabel("\u5546\u54C1\u4FE1\u606F");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("����", Font.PLAIN, 16));
		label.setBounds(219, 10, 79, 15);
		getContentPane().add(label);
		
		goodsInfoPanel = new JPanel();
		goodsInfoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		goodsInfoPanel.setLayout(null);
		getContentPane().add(goodsInfoPanel);
		goodsInfoPanel.setBounds(10, 35, 530, 400);
		   /////////��Ʒ��Ϣ������      ��ͷ��Ϣ������                   ��Ʒ��Ϣ����ʾ
		ConnectDatabase conn=new ConnectDatabase();
		String cmd="select id,name,packstyle,unite,productarea,keeptime,description,price from Infor_Goods;";
		try {
			@SuppressWarnings("unused")
			int counter=0;     ////////////������
			ResultSet re=conn.state.executeQuery(cmd);
			while(re.next()){
				counter++;
			}
			re.beforeFirst();
			if(re.isBeforeFirst()){
				while(re.next()){
					for(int i=0;i<8;i++){
						goods[recoder][i]=re.getString(i+1);
					}
					recoder++;
				}
			}
			re.close();
			conn.conn.close();
			goodsInfoTable=new JTable(goods,head);
			JScrollPane goodsInfojsp=new JScrollPane(goodsInfoTable);
			goodsInfoPanel.add(goodsInfojsp);
			goodsInfojsp.setBounds(0, 0, 530, 400);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	void init(){
		freshButton = new JButton("\u5237\u65B0");		   /////////////ˢ�°�ť
		freshButton.setBounds(582, 35, 93, 23);
		getContentPane().add(freshButton);
		freshButton.addActionListener(new FreshButtonListener());
			
		addGoodsButton = new JButton("\u589E\u52A0\u5546\u54C1");		  /////////�����Ʒ��Ϣ��ť
		addGoodsButton.setBounds(582, 79, 93, 23);
		getContentPane().add(addGoodsButton);
		addGoodsButton.addActionListener(new AddGoodsButtonListener());
			
		deleteGoodsButton = new JButton("\u5220\u9664\u5546\u54C1");           ///////////////��Ʒ��Ϣɾ����ť		
		deleteGoodsButton.setBounds(582, 127, 93, 23);
		getContentPane().add(deleteGoodsButton);
		deleteGoodsButton.addActionListener(new DeleteButtonListener());
		
		updateGoodsButton = new JButton("\u4FEE\u6539\u5546\u54C1");		   //////////////��Ʒ��Ϣ���İ�ť
		updateGoodsButton.setBounds(582, 173, 93, 23);
		getContentPane().add(updateGoodsButton);	
		updateGoodsButton.addActionListener(new UpdateButtonListener());
	}
	
	class UpdateButtonListener implements ActionListener{
		@SuppressWarnings("static-access")
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(goodsInfoTable.getSelectedRow()<recoder&&goodsInfoTable.getSelectedRow()>=0){				
				new UpdateGoods(((String)goodsInfoTable.getValueAt(goodsInfoTable.getSelectedRow(), 0))); //////////////��ȡѡ�е���Ʒid							
			}
			else{
				new JOptionPane().showMessageDialog(updateGoodsButton,"��ѡ����Ҫ���µ���Ʒ������");
			}
		}
		
	}
	
	
	class DeleteButtonListener implements ActionListener{
		@SuppressWarnings("static-access")
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(goodsInfoTable.getSelectedRow()<recoder&&goodsInfoTable.getSelectedRow()>=0){
				ConnectDatabase conn=new ConnectDatabase();
			String cmd="delete from Infor_Goods where id='"+goodsInfoTable.getValueAt(goodsInfoTable.getSelectedRow(), 0)+"'";
			try {
				conn.state.executeUpdate(cmd);
				System.out.println("ɾ����Ʒ�ɹ�������");
				if(goodsInfoTable.getSelectedRow()==recoder-1){ //////////��Ϊ���һ����¼
					for(int i=0;i<goodsInfoTable.getColumnCount();i++){
						goodsInfoTable.setValueAt("", recoder-1, i);						
					}
					recoder--;
				}
				else{
					for(int i=goodsInfoTable.getSelectedRow();i<recoder-1;i++){
						for(int j=0;j<goodsInfoTable.getColumnCount();j++){
							goodsInfoTable.setValueAt(goodsInfoTable.getValueAt(i+1, j), i, j);
						}
					}
					for(int k=0;k<goodsInfoTable.getColumnCount();k++){
						goodsInfoTable.setValueAt("", recoder-1, k);
					}
					recoder--;
				}
				new JOptionPane().showMessageDialog(deleteGoodsButton,"��Ʒɾ���ɹ�������");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
			else{
				System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh");
				new JOptionPane().showMessageDialog(deleteGoodsButton,"��ѡ����Ҫɾ������Ʒ������");
			}
		}
		
	}

	
	class AddGoodsButtonListener implements ActionListener{          /////////////////��Ӱ�ť������
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new AddGoods();
		}		
	}
	
	
	class FreshButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			ConnectDatabase conn=new ConnectDatabase();
			String cmd="select id,name,packstyle,unite,productarea,keeptime,description,price from Infor_Goods;";						
			try {
				recoder=0;     ////////////������
				ResultSet re=conn.state.executeQuery(cmd);
				if(re.isBeforeFirst()){
					while(re.next()){
						for(int i=0;i<8;i++){
							goodsInfoTable.setValueAt(re.getString(i+1), recoder, i);
						}
						recoder++;
					}
				}
				re.close();
				conn.conn.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
		
	}
}