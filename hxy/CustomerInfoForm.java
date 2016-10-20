package hxy;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class CustomerInfoForm extends JDialog {
	private JTable enterpriseInfoTable=null;
	private JTable linkManInfoTable=null;
	private int enterpriseRecoder=0;
	private int linkManRecoder=0;
	public CustomerInfoForm() {
		setVisible(true);
		setTitle("�ͻ���Ϣά��");
		setBounds(100, 100, 879, 416);
		getContentPane().setLayout(null);
		init();
		allInfoLoad();
	}

	void init(){
		JLabel label = new JLabel("\u4F01\u4E1A\u4FE1\u606F");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(186, 0, 70, 25);
		getContentPane().add(label);
		
		JPanel enterprisePanel = new JPanel();
		enterprisePanel.setBounds(10, 26, 484, 341);
		getContentPane().add(enterprisePanel);
		
		JPanel linkManPanel = new JPanel();
		linkManPanel.setBounds(515, 26, 338, 172);
		getContentPane().add(linkManPanel);
		linkManPanel.setLayout(null);
		
	    /////////////��ҵ��Ϣ
		Object enterpriseHead[]={"ID","����","���","����","��ַ","�ʱ�","��������","�˻�"};
		Object enterpriseInfo[][]=new Object[20][8];
		enterprisePanel.setLayout(null);
		enterpriseInfoTable=new JTable(enterpriseInfo, enterpriseHead);
		JScrollPane enterpriseInfojsp=new JScrollPane(enterpriseInfoTable);
		enterpriseInfojsp.setBounds(0, 0, 0, 0);
		enterprisePanel.add(enterpriseInfojsp);
		enterpriseInfojsp.setBounds(0, 0,  484, 341);
		
		/////////��ϵ����Ϣ
		Object linkManHead[]={"��˾","����","�ֻ�","����"};
		Object linkManInfo[][]=new Object[10][4];
		linkManInfoTable=new JTable(linkManInfo, linkManHead);
		JScrollPane linkManInfojsp=new JScrollPane(linkManInfoTable);
		linkManPanel.add(linkManInfojsp);
		linkManInfojsp.setBounds(0, 0, 338, 172);
		
		
		JLabel label_1 = new JLabel("\u8054\u7CFB\u4EBA\u4FE1\u606F ");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(630, 5, 79, 15);
		getContentPane().add(label_1);
		
		JButton deEnButton = new JButton("\u5220\u9664\u4F01\u4E1A\u4FE1\u606F");
		deEnButton.addActionListener(new ActionListener() {      /////////��ҵ��Ϣ��ɾ��
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				if((enterpriseInfoTable.getSelectedRow()>=0)&&(enterpriseInfoTable.getSelectedRow()<enterpriseRecoder)){
					String cmd="select name from Linkman where company_name='"+enterpriseInfoTable.getValueAt(enterpriseInfoTable.getSelectedRow(), 1).toString().trim()+"';";
					ConnectDatabase conn=new ConnectDatabase();
					try {
						ResultSet re=conn.state.executeQuery(cmd);
						if(re.next()){    /////////�жϸ���ҵ��ϵ�˵���Ϣ�Ƿ�ɾ����
							re.close();
							new JOptionPane().showMessageDialog(deEnButton, "����ɾ������ҵ����ϵ�ˣ�����");
						}
						else{
							re.close();
							cmd="delete from Enterprise where id='"+enterpriseInfoTable.getValueAt(enterpriseInfoTable.getSelectedRow(), 0).toString().trim()+"';";
							conn.state.executeUpdate(cmd);
							conn.state.close();
							new JOptionPane().showMessageDialog(deEnButton, "��ҵ��Ϣɾ���ɹ�������");
							enterpriseRecoder--;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}					
				}
				else{
					System.out.println(enterpriseRecoder+"     "+enterpriseInfoTable.getSelectedRow());
					new JOptionPane().showMessageDialog(deEnButton, "��ѡ����Ҫɾ������ҵ������");
				}
			}
		});
		deEnButton.setBounds(515, 264, 118, 23);
		getContentPane().add(deEnButton);
		
		JButton addEnButton = new JButton("\u6DFB\u52A0\u4F01\u4E1A\u4FE1\u606F");
		addEnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AddEnterpriseDialog();
			}
		});
		addEnButton.setBounds(515, 220, 118, 23);
		getContentPane().add(addEnButton);
		
		JButton deLinkManButton = new JButton("\u5220\u9664\u8054\u7CFB\u4EBA\u4FE1\u606F");
		deLinkManButton.addActionListener(new ActionListener() {      ////////ɾ����ϵ�˼�����
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				if((linkManInfoTable.getSelectedRow()>=0)&&(linkManInfoTable.getSelectedRow()<linkManRecoder)){
					//System.out.println(linkManRecoder+"    "+linkManInfoTable.getSelectedRow());
					{ ////////////////ִ��ɾ�����
						String cmd="delete from Linkman where name='"+linkManInfoTable.getValueAt(linkManInfoTable.getSelectedRow(), 1)+"';";
						System.out.println(cmd);
						ConnectDatabase conn=new ConnectDatabase();
						try {
							conn.state.executeUpdate(cmd);
							conn.state.close();
							new JOptionPane().showMessageDialog(deLinkManButton, "ɾ����ϵ�˳ɹ�������");
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					if(linkManInfoTable.getSelectedRow()==linkManRecoder-1){
						for(int i=0;i<linkManInfoTable.getColumnCount();i++){
							linkManInfoTable.setValueAt(null, linkManRecoder-1, i);							
						}
						linkManRecoder--;
					}
					else{
						for(int i=linkManInfoTable.getSelectedRow();i<linkManRecoder-1;i++){
							for(int j=0;j<linkManInfoTable.getColumnCount();j++){
								linkManInfoTable.setValueAt(linkManInfoTable.getValueAt(i+1, j), i, j);								
							}
						}
						for(int k=0;k<linkManInfoTable.getColumnCount();k++){
							linkManInfoTable.setValueAt(null, linkManRecoder-1, k);
						}
						linkManRecoder--;
					}			
					
				}
				else{
					new JOptionPane().showMessageDialog(deLinkManButton, "��ѡ����Ҫɾ������ϵ�ˣ�����");
				}
			}
		});
		deLinkManButton.setBounds(677, 264, 134, 23);
		getContentPane().add(deLinkManButton);
		
		JButton addLinkManButton = new JButton("\u589E\u52A0\u8054\u7CFB\u4EBA\u4FE1\u606F");
		addLinkManButton.addActionListener(new ActionListener() { /////////������ϵ�˰�ť������
			public void actionPerformed(ActionEvent arg0) {
				new AddLinkManDialog();
			}
		});
		addLinkManButton.setBounds(677, 220, 134, 23);
		getContentPane().add(addLinkManButton);
		
		JButton updateEnButton = new JButton("\u4FEE\u6539\u4F01\u4E1A\u4FE1\u606F");
		updateEnButton.addActionListener(new ActionListener() {       ///////��ҵ��Ϣ���¼�����
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				if(enterpriseInfoTable.getSelectedRow()>=0&&enterpriseInfoTable.getSelectedRow()<enterpriseRecoder){
					new UpdateEnterPriseDialog(enterpriseInfoTable.getValueAt(enterpriseInfoTable.getSelectedRow(), 0).toString());
				}
				else{
					new JOptionPane().showMessageDialog(updateEnButton, "��ѡ����Ҫ�޸���Ϣ����ҵ������");
				}
			}
		});
		updateEnButton.setBounds(516, 308, 117, 23);
		getContentPane().add(updateEnButton);
		
		JButton button = new JButton("\u4FEE\u6539\u8054\u7CFB\u4EBA\u4FE1\u606F");
		button.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				if(linkManInfoTable.getSelectedRow()>=0&&linkManInfoTable.getSelectedRow()<linkManRecoder){
					new UpdateLinkManDialog(linkManInfoTable.getValueAt(linkManInfoTable.getSelectedRow(), 0).toString(),linkManInfoTable.getValueAt(linkManInfoTable.getSelectedRow(), 1).toString().trim());
				}
				else{
					new JOptionPane().showMessageDialog(updateEnButton, "��ѡ����Ҫ�޸���Ϣ����ϵ�ˣ�����");
				}
			}
		});
		button.setBounds(677, 308, 134, 23);
		getContentPane().add(button);
		
		JButton freshButton = new JButton("\u5237\u65B0");
		freshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				allInfoLoad();
			}
		});
		freshButton.setBounds(616, 344, 93, 23);
		getContentPane().add(freshButton);
	}
	
	void allInfoLoad(){
		String cmd="select id,company_name,short_name,owner,address,postcode,bank,account from Enterprise;";
		enterpriseRecoder=infoLoad(enterpriseInfoTable,enterpriseRecoder,8,cmd);
		cmd="select company_name,name,phone,email from Linkman;";
		linkManRecoder=infoLoad(linkManInfoTable,linkManRecoder,4,cmd);
	}
	
	void clearTable(JTable table,int recoder){           //////////������ÿ�
		for(int i=0;i<recoder;i++){
			for(int j=0;j<table.getColumnCount();j++){
				table.setValueAt(null, i, j);				
			}
		}
	}
	
	int infoLoad(JTable table,int recoder,int clumn,String cmd){
		try {
			ConnectDatabase conn=new ConnectDatabase();
			ResultSet re=conn.state.executeQuery(cmd);        ///////////////��ѯ��Ʒ��Ϣ������д�ɴ洢����
			recoder=0;
			while(re.next()){
				for(int i=0;i<clumn;i++){
					table.setValueAt(re.getObject(i+1), recoder, i);
				}
				recoder++;
			}
			re.close();
			conn.state.close();
			System.out.println(recoder);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return recoder;
	}
}
