package dbQueryExport.josemarsilva.com.br;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SqlParamDialogUI extends JDialog {
	//
	// Table Column Titles ...
	//
	private final String COLUMNTITLE_PARAM = new String("Param");
	private final String COLUMNTITLE_VALUE = new String("Value");
	private final String COLUMNTITLE_OBS = new String("Obs");

	private final JPanel contentPanel = new JPanel();
	private DefaultTableModel tableModelSqlParams;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SqlParamDialogUI dialog = new SqlParamDialogUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SqlParamDialogUI() {
		setTitle("SQL Parameters");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		//
		// initCompleted ...
		//
		initCompleted();
	}
	
	/*
	 * initDialogCompleted
	 */ 
	public void initCompleted() {
		//
		// Columns ...
		//
		tableModelSqlParams = new DefaultTableModel(); 
		tableModelSqlParams.addColumn(COLUMNTITLE_PARAM);
		tableModelSqlParams.addColumn(COLUMNTITLE_VALUE);
		tableModelSqlParams.addColumn(COLUMNTITLE_OBS);
//		tableSqlParams.getColumnModel().getColumn(0).setPreferredWidth(180);
//		tableSqlParams.getColumnModel().getColumn(1).setPreferredWidth(180);
//		tableSqlParams.getColumnModel().getColumn(1).setPreferredWidth(700);

		tableModelSqlParams.addRow(new Object[]{"r1c1", "r1c2","r1c3"});
		tableModelSqlParams.addRow(new Object[]{"r2c1", "r2c2","r2c3"});
		tableModelSqlParams.addRow(new Object[]{"r3c1", "r3c2","r3c3"});
	}
	
	/*
	 * 
	 */
	public void closeDialog(boolean bRet){
		
	}
}
