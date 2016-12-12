package edu.njit.cs631citylib;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Reserve extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tableDocReserveResult;
	
	public static void main(String[] args) {
		try {
			DBManager m = DBManager.getInstance();
			m.connect();
			Reserve dialog = new Reserve("1");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Create the dialog.
	 */
	public Reserve(String cardNumber) {
		
		DBManager m = DBManager.getInstance();
		
		String[] columnNames = {"ReserveNo", "ReaderId", "DocId", "CopyNo", "LibId", "DayTime"};
		ArrayList<ArrayList<Object>> reserveResult = new ArrayList<ArrayList<Object>>();
		
		reserveResult = m.execQuery("SELECT `RESNUMBER`, `READERID`, `DOCID`, `COPYNO`, `LIBID`, `DTIME` FROM `RESERVES` WHERE `READERID` = " + cardNumber + ";");

		Object[][] array = new Object[reserveResult.size()][];
		for (int i = 0; i < reserveResult.size(); i++) {
			ArrayList<Object> row = reserveResult.get(i);
			array[i] = row.toArray();
		}

		if (reserveResult == null || reserveResult.size() <= 0){
			JOptionPane.showMessageDialog(null, "No Reserved Documents");
		}

		DefaultTableModel tm = new DefaultTableModel(array, columnNames);
		
		setBounds(100, 100, 1053, 610);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 1047, 582);
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
	
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 83, 1008, 404);
		contentPanel.add(scrollPane);
	
		tableDocReserveResult = new JTable();
		scrollPane.setViewportView(tableDocReserveResult);
		tableDocReserveResult.setModel(tm);
	
	}
}
