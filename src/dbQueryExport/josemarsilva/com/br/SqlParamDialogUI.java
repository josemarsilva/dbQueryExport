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
	private JScrollPane scrollPane;
	private DefaultTableModel tableModelSqlParams;
	private JTable tableSqlParams;

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
		{
			scrollPane = new JScrollPane();
		}
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)
		);
		{
			tableModelSqlParams = new DefaultTableModel() {
				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 0 || column == 2)
						return false;
					else 
						return true;
				}
			}; 
			tableModelSqlParams.addColumn(COLUMNTITLE_PARAM);
			tableModelSqlParams.addColumn(COLUMNTITLE_VALUE);
			tableModelSqlParams.addColumn(COLUMNTITLE_OBS);
		}
		{
			tableSqlParams = new JTable(tableModelSqlParams);
			scrollPane.setViewportView(tableSqlParams);
		}
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						closeDialog(true);
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
						closeDialog(false);
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
		// Set Preferred Width ...
		//
		tableSqlParams.getColumnModel().getColumn(0).setPreferredWidth(300);
		tableSqlParams.getColumnModel().getColumn(1).setPreferredWidth(600);
		tableSqlParams.getColumnModel().getColumn(2).setPreferredWidth(1200);
		//
		// Add Rows ...
		//
		tableModelSqlParams.addRow(new Object[]{"r01c1", "r01c2 name","r01c3: It is a really very big description for this column"});
		tableModelSqlParams.addRow(new Object[]{"r02c1", "r02c2 name","r02c3: It is a really very big description for this column"});
		tableModelSqlParams.addRow(new Object[]{"r03c1", "r03c2 name","r03c3: It is a really very big description for this column"});
		tableModelSqlParams.addRow(new Object[]{"r04c1", "r04c2 name","r04c3: It is a really very big description for this column"});
		tableModelSqlParams.addRow(new Object[]{"r05c1", "r05c2 name","r05c3: It is a really very big description for this column"});
		tableModelSqlParams.addRow(new Object[]{"r06c1", "r06c2 name","r06c3: It is a really very big description for this column"});
		tableModelSqlParams.addRow(new Object[]{"r07c1", "r07c2 name","r07c3: It is a really very big description for this column"});
		tableModelSqlParams.addRow(new Object[]{"r08c1", "r08c2 name","r08c3: It is a really very big description for this column"});
		tableModelSqlParams.addRow(new Object[]{"r09c1", "r09c2 name","r09c3: It is a really very big description for this column"});
		tableModelSqlParams.addRow(new Object[]{"r11c1", "r01c2 name","r11c3: It is a really very big description for this column"});
		tableModelSqlParams.addRow(new Object[]{"r12c1", "r02c2 name","r12c3: It is a really very big description for this column"});
		tableModelSqlParams.addRow(new Object[]{"r13c1", "r03c2 name","r13c3: It is a really very big description for this column"});
		tableModelSqlParams.addRow(new Object[]{"r14c1", "r04c2 name","r14c3: It is a really very big description for this column"});
		tableModelSqlParams.addRow(new Object[]{"r15c1", "r05c2 name","r15c3: It is a really very big description for this column"});
		tableModelSqlParams.addRow(new Object[]{"r16c1", "r06c2 name","r16c3: It is a really very big description for this column"});
		tableModelSqlParams.addRow(new Object[]{"r17c1", "r07c2 name","r17c3: It is a really very big description for this column"});
		tableModelSqlParams.addRow(new Object[]{"r18c1", "r08c2 name","r18c3: It is a really very big description for this column"});
		tableModelSqlParams.addRow(new Object[]{"r19c1", "r09c2 name","r19c3: It is a really very big description for this column"});
	}
	
	/*
	 * 
	 */
	public void closeDialog(boolean bRet){
		
	}
}
