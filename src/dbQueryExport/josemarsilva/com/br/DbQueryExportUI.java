package dbQueryExport.josemarsilva.com.br;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Canvas;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.ScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

public class DbQueryExportUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtJdbcDriver;
	private JTextField txtDatabaseUrl;
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private JTextField txtSqlfilename;
	private JTable tableSqlParams;
	private JTextField textFieldLogMessage;
	private JTextField txtExportfile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DbQueryExportUI frame = new DbQueryExportUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DbQueryExportUI() {
		setTitle("DbQueryExport v1.00.a");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel_1_Connection = new JPanel();
		panel_1_Connection.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JPanel panel_2_SqlQueryFilename = new JPanel();
		panel_2_SqlQueryFilename.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JPanel panel_3_SqlQuery = new JPanel();
		panel_3_SqlQuery.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JPanel panel_4_SqlParams = new JPanel();
		panel_4_SqlParams.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JPanel panel_5_Log = new JPanel();
		panel_5_Log.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JPanel panel_6_ExportFilename = new JPanel();
		panel_6_ExportFilename.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JPanel panel_7_Commands = new JPanel();
		panel_7_Commands.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1_Connection, GroupLayout.PREFERRED_SIZE, 576, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_2_SqlQueryFilename, GroupLayout.PREFERRED_SIZE, 576, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_3_SqlQuery, GroupLayout.PREFERRED_SIZE, 577, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_4_SqlParams, GroupLayout.PREFERRED_SIZE, 579, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_6_ExportFilename, GroupLayout.PREFERRED_SIZE, 578, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_5_Log, GroupLayout.PREFERRED_SIZE, 579, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_7_Commands, GroupLayout.PREFERRED_SIZE, 577, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel_1_Connection, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2_SqlQueryFilename, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3_SqlQuery, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_4_SqlParams, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_5_Log, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_6_ExportFilename, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_7_Commands, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(284, Short.MAX_VALUE))
		);
		panel_7_Commands.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("50dlu"),
				ColumnSpec.decode("50dlu"),
				ColumnSpec.decode("50dlu"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JButton btnExport = new JButton("Export ...");
		panel_7_Commands.add(btnExport, "1, 1");
		
		JButton btnCopyLog = new JButton("Copy Log ...");
		panel_7_Commands.add(btnCopyLog, "2, 1");
		panel_6_ExportFilename.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("center:75dlu"),
				ColumnSpec.decode("left:250dlu:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JButton btnExportFile = new JButton("Export File ...");
		panel_6_ExportFilename.add(btnExportFile, "2, 1");
		
		txtExportfile = new JTextField();
		txtExportfile.setEditable(false);
		txtExportfile.setText("ExportFile");
		panel_6_ExportFilename.add(txtExportfile, "3, 1, fill, default");
		txtExportfile.setColumns(10);
		
		JProgressBar progressBarLog = new JProgressBar();
		
		textFieldLogMessage = new JTextField();
		textFieldLogMessage.setEditable(false);
		textFieldLogMessage.setColumns(10);
		GroupLayout gl_panel_5_Log = new GroupLayout(panel_5_Log);
		gl_panel_5_Log.setHorizontalGroup(
			gl_panel_5_Log.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_5_Log.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_5_Log.createParallelGroup(Alignment.TRAILING)
						.addComponent(textFieldLogMessage, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
						.addComponent(progressBarLog, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_5_Log.setVerticalGroup(
			gl_panel_5_Log.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5_Log.createSequentialGroup()
					.addContainerGap()
					.addComponent(progressBarLog, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldLogMessage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(48, Short.MAX_VALUE))
		);
		panel_5_Log.setLayout(gl_panel_5_Log);
		panel_4_SqlParams.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("center:75dlu"),
				ColumnSpec.decode("left:275dlu:grow"),},
			new RowSpec[] {
				RowSpec.decode("top:50dlu:grow"),}));
		
		Canvas canvas = new Canvas();
		panel_4_SqlParams.add(canvas, "1, 1");
		
		JLabel lblSqlParams = new JLabel("SQL Param(s)");
		panel_4_SqlParams.add(lblSqlParams, "2, 1");
		
		tableSqlParams = new JTable();
		panel_4_SqlParams.add(tableSqlParams, "3, 1, fill, fill");
		panel_3_SqlQuery.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("center:75dlu"),
				ColumnSpec.decode("left:250dlu:grow"),},
			new RowSpec[] {
				RowSpec.decode("top:50dlu"),}));
		
		Canvas canvas_SqlQuery = new Canvas();
		panel_3_SqlQuery.add(canvas_SqlQuery, "1, 1");
		
		JLabel lblSqlQuery = new JLabel("SQL Query");
		panel_3_SqlQuery.add(lblSqlQuery, "2, 1");
		
		JTextArea textAreaSqlQuery = new JTextArea();
		textAreaSqlQuery.setFont(new Font("Monospaced", Font.PLAIN, 9));
		textAreaSqlQuery.setText("SqlQuery");
		panel_3_SqlQuery.add(textAreaSqlQuery, "3, 1, fill, fill");
		panel_2_SqlQueryFilename.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("center:75dlu"),
				ColumnSpec.decode("left:200dlu:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JButton btnSqlfilename = new JButton("Open SQL Filename");
		panel_2_SqlQueryFilename.add(btnSqlfilename, "2, 1");
		
		txtSqlfilename = new JTextField();
		txtSqlfilename.setEditable(false);
		txtSqlfilename.setText("SqlFilename");
		panel_2_SqlQueryFilename.add(txtSqlfilename, "3, 1, fill, default");
		txtSqlfilename.setColumns(10);
		panel_1_Connection.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("center:75dlu"),
				ColumnSpec.decode("left:200dlu:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		Canvas canvas_JdbcDriver = new Canvas();
		panel_1_Connection.add(canvas_JdbcDriver, "1, 1");
		
		JButton btnJdbcDriver = new JButton("Jdbc Driver ...");
		panel_1_Connection.add(btnJdbcDriver, "2, 1, center, default");
		
		txtJdbcDriver = new JTextField();
		txtJdbcDriver.setEditable(false);
		txtJdbcDriver.setToolTipText("Jdbc Driver");
		txtJdbcDriver.setText("Jdbc Driver");
		panel_1_Connection.add(txtJdbcDriver, "3, 1, fill, default");
		txtJdbcDriver.setColumns(10);
		
		Canvas canvas_DatabaseUrl = new Canvas();
		panel_1_Connection.add(canvas_DatabaseUrl, "1, 2");
		
		JLabel lblDatabaseUrl = new JLabel("Database Url :");
		panel_1_Connection.add(lblDatabaseUrl, "2, 2, center, default");
		
		txtDatabaseUrl = new JTextField();
		txtDatabaseUrl.setEditable(false);
		txtDatabaseUrl.setToolTipText("Database Url");
		txtDatabaseUrl.setText("Database Url");
		panel_1_Connection.add(txtDatabaseUrl, "3, 2, fill, default");
		txtDatabaseUrl.setColumns(10);
		
		Canvas canvas_Username = new Canvas();
		panel_1_Connection.add(canvas_Username, "1, 3");
		
		JLabel lblUsername = new JLabel("${Username} :");
		panel_1_Connection.add(lblUsername, "2, 3, center, default");
		
		txtUsername = new JTextField();
		txtUsername.setEditable(false);
		txtUsername.setToolTipText("Username");
		txtUsername.setText("Username");
		panel_1_Connection.add(txtUsername, "3, 3, fill, default");
		txtUsername.setColumns(10);
		
		Canvas canvas_Password = new Canvas();
		panel_1_Connection.add(canvas_Password, "1, 4");
		
		JLabel lblPassword = new JLabel("${Password} :");
		panel_1_Connection.add(lblPassword, "2, 4, center, default");
		
		pwdPassword = new JPasswordField();
		pwdPassword.setEditable(false);
		pwdPassword.setToolTipText("Password");
		pwdPassword.setText("Password");
		panel_1_Connection.add(pwdPassword, "3, 4, fill, default");
		contentPane.setLayout(gl_contentPane);
	}
}
