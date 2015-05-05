package dbQueryExport.josemarsilva.com.br;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.Canvas;
import java.awt.Button;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.ScrollPane;

import javax.swing.JTextArea;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class DbQueryExportUI extends JFrame {
	//
	// Args parameters ...
	//
	private final String ARGS_COMMAND_LINE_JDBCDRIVER = new String("-d");
	private final String ARGS_COMMAND_LINE_PARAM_DATABASEURL = new String("-r");
	private final String ARGS_COMMAND_LINE_PARAM_USERNAME = new String("-u");
	private final String ARGS_COMMAND_LINE_PARAM_PASSWORD = new String("-p");
	private final String ARGS_COMMAND_LINE_PARAM_FILENAME = new String("-f");
	
	//
	// Messages ...
	//
	private final String MSG_INFO_COMMAND_LINE_HELP = new String("Database Query Export allows you to query a SQL Statement, Extract and export data to local file\n\n"
		+ "Usage: dbQueryExport [options]\n"
		+ "  " + ARGS_COMMAND_LINE_JDBCDRIVER        + "     Jdbc Driver" + "\n"
		+ "  " + ARGS_COMMAND_LINE_PARAM_DATABASEURL + "     Database Url location for Jdbc Driver (*)" + "\n"
		+ "  " + ARGS_COMMAND_LINE_PARAM_USERNAME    + "     Username Jdbc connection" + "\n"
		+ "  " + ARGS_COMMAND_LINE_PARAM_PASSWORD    + "     Password Jdbc connection" + "\n"
		+ "  " + ARGS_COMMAND_LINE_PARAM_FILENAME    + "     SQL Query Filename complete path" + "\n"
		+ "\n(*) Use ${Username} and ${Password} macro names to expand these command line parameters"
		);
	private final String MSG_INFO_TITLE_WARNING = new String("Informação");
	private final String MSG_WARN_TITLE_WARNING = new String("Atenção");
	private final String MSG_CRITICAL_TITLE_WARNING = new String("Erro Crítico");
	private final String MSG_INFO_COPYTOCLIPBOARD_SUCCESS = new String("Cópia das mensagens para área de Clipboard concluída com sucesso!");
	private final String MSG_WARN_EXECUTION_FAILED = new String("Execução concluída com falha!");
	private final String MSG_WARN_INPUTFILE_MISSING = new String("Arquivo de ENTRADA do tipo '%s' não foi definido!");
	private final String MSG_WARN_INPUTFILE_NOTFOUND = new String("Arquivo '%s' de ENTRADA não existe!");
	private final String MSG_WARN_OUTPUTFILE_MISSING = new String("Arquivo de SAÍDA não foi definido!"); 
	private final String MSG_INFO_EXECUTION_SUCCESS = new String("Execução concluída com sucesso!");

	

	
	
	//
	// Table Column Titles ...
	//
	private final String COLUMNTITLE_PARAM = new String("Param");
	private final String COLUMNTITLE_VALUE = new String("Value");
	private final String COLUMNTITLE_OBS = new String("Obs");
	
	//
	// Class Properties ...
	//
	private static Map<String, String> mapArgs = new HashMap<String, String>();

	//
	// UI fields ...
	//
	private JPanel contentPane;
	private JTextField txtJdbcDriver;
	private JTextField txtDatabaseUrl;
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private JTextField txtSqlfilename;
	private JTable tableSqlParams;
	private JTextField txtExportfile;
	private JButton btnJdbcDriver;
	private JButton btnSqlfilename;
	private JButton btnSqlParams;
	private JProgressBar progressBarLog;
	private JButton btnExportFile;
	private JButton btnExport;
	private JButton btnCopyStatusMessage;
	private JButton btnAbout;
	private JTextArea textAreaStatusMessage;

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
		//
		// build mapArgs ...
		//
		buildMapArgs(args);
	}

	/**
	 * Create the frame.
	 */
	public DbQueryExportUI() {
		setTitle("DbQueryExport v1.00.a");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 550);
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
		
		JPanel panel_5_StatusMessage = new JPanel();
		panel_5_StatusMessage.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
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
						.addComponent(panel_5_StatusMessage, GroupLayout.PREFERRED_SIZE, 579, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_6_ExportFilename, GroupLayout.PREFERRED_SIZE, 578, GroupLayout.PREFERRED_SIZE)
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
					.addComponent(panel_5_StatusMessage, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_6_ExportFilename, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_7_Commands, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_7_Commands.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("left:50dlu"),
				ColumnSpec.decode("left:50dlu"),
				ColumnSpec.decode("left:50dlu"),
				ColumnSpec.decode("left:50dlu"),
				ColumnSpec.decode("left:50dlu"),
				ColumnSpec.decode("left:50dlu"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		btnExport = new JButton("Export ...");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionListenerExportExecute();
			}

			private void actionListenerExportExecute() {
				// TODO Auto-generated method stub
				
			}
		});
		btnExport.setHorizontalAlignment(SwingConstants.LEFT);
		panel_7_Commands.add(btnExport, "1, 1");
		
		btnCopyStatusMessage = new JButton("Copy Status Message ...");
		btnCopyStatusMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionListenerStatusMessageCopy();
			}
		});
		btnCopyStatusMessage.setHorizontalAlignment(SwingConstants.LEFT);
		panel_7_Commands.add(btnCopyStatusMessage, "2, 1");
		
		btnAbout = new JButton("About ...");
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionListenerAboutShow();
			}
		});
		btnAbout.setHorizontalAlignment(SwingConstants.LEFT);
		panel_7_Commands.add(btnAbout, "6, 1");
		panel_6_ExportFilename.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("center:75dlu"),
				ColumnSpec.decode("left:250dlu:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		btnExportFile = new JButton("Export File ...");
		btnExportFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionListenerExportFileSelect();
			}
		});
		panel_6_ExportFilename.add(btnExportFile, "2, 1");
		
		txtExportfile = new JTextField();
		txtExportfile.setEditable(false);
		panel_6_ExportFilename.add(txtExportfile, "3, 1, fill, default");
		txtExportfile.setColumns(10);
		
		progressBarLog = new JProgressBar();
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel_5_StatusMessage = new GroupLayout(panel_5_StatusMessage);
		gl_panel_5_StatusMessage.setHorizontalGroup(
			gl_panel_5_StatusMessage.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5_StatusMessage.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel_5_StatusMessage.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 555, GroupLayout.PREFERRED_SIZE)
						.addComponent(progressBarLog, GroupLayout.PREFERRED_SIZE, 555, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel_5_StatusMessage.setVerticalGroup(
			gl_panel_5_StatusMessage.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5_StatusMessage.createSequentialGroup()
					.addGap(11)
					.addComponent(progressBarLog, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		textAreaStatusMessage = new JTextArea();
		scrollPane.setViewportView(textAreaStatusMessage);
		panel_5_StatusMessage.setLayout(gl_panel_5_StatusMessage);
		panel_4_SqlParams.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("center:75dlu"),
				ColumnSpec.decode("left:275dlu:grow"),},
			new RowSpec[] {
				RowSpec.decode("top:50dlu:grow"),}));
		
		Canvas canvas = new Canvas();
		panel_4_SqlParams.add(canvas, "1, 1");
		
		btnSqlParams = new JButton("SQL Params");
		btnSqlParams.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionListenerSqlParamsExtract();
			}
		});
		panel_4_SqlParams.add(btnSqlParams, "2, 1");
		
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
		panel_3_SqlQuery.add(textAreaSqlQuery, "3, 1, fill, fill");
		panel_2_SqlQueryFilename.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("center:75dlu"),
				ColumnSpec.decode("left:200dlu:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		btnSqlfilename = new JButton("SQL Query Filename");
		btnSqlfilename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionListenerSqlFilenameSelect();
			}
		});
		panel_2_SqlQueryFilename.add(btnSqlfilename, "2, 1");
		
		txtSqlfilename = new JTextField();
		txtSqlfilename.setEditable(false);
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
		
		btnJdbcDriver = new JButton("Jdbc Driver ...");
		btnJdbcDriver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionListenerJdbcDriverSelect();
			}
		});
		panel_1_Connection.add(btnJdbcDriver, "2, 1, center, default");
		
		txtJdbcDriver = new JTextField();
		txtJdbcDriver.setEditable(false);
		txtJdbcDriver.setToolTipText("Jdbc Driver");
		panel_1_Connection.add(txtJdbcDriver, "3, 1, fill, default");
		txtJdbcDriver.setColumns(10);
		
		Canvas canvas_DatabaseUrl = new Canvas();
		panel_1_Connection.add(canvas_DatabaseUrl, "1, 2");
		
		JLabel lblDatabaseUrl = new JLabel("Database Url :");
		panel_1_Connection.add(lblDatabaseUrl, "2, 2, center, default");
		
		txtDatabaseUrl = new JTextField();
		txtDatabaseUrl.setEditable(false);
		txtDatabaseUrl.setToolTipText("Database Url");
		panel_1_Connection.add(txtDatabaseUrl, "3, 2, fill, default");
		txtDatabaseUrl.setColumns(10);
		
		Canvas canvas_Username = new Canvas();
		panel_1_Connection.add(canvas_Username, "1, 3");
		
		JLabel lblUsername = new JLabel("${Username} :");
		panel_1_Connection.add(lblUsername, "2, 3, center, default");
		
		txtUsername = new JTextField();
		txtUsername.setEditable(false);
		txtUsername.setToolTipText("Username");
		panel_1_Connection.add(txtUsername, "3, 3, fill, default");
		txtUsername.setColumns(10);
		
		Canvas canvas_Password = new Canvas();
		panel_1_Connection.add(canvas_Password, "1, 4");
		
		JLabel lblPassword = new JLabel("${Password} :");
		panel_1_Connection.add(lblPassword, "2, 4, center, default");
		
		pwdPassword = new JPasswordField();
		pwdPassword.setEditable(false);
		pwdPassword.setToolTipText("Password");
		panel_1_Connection.add(pwdPassword, "3, 4, fill, default");
		contentPane.setLayout(gl_contentPane);
		
		//
		// initAppCompleted()
		//
		initAppCompleted();

	}

	protected void actionListenerAboutShow() {
		//
		// Show about dialog box
		//
		JOptionPane.showMessageDialog(getParent(),
				MSG_INFO_COMMAND_LINE_HELP, MSG_INFO_TITLE_WARNING,
				JOptionPane.INFORMATION_MESSAGE);
		
	}

	protected void actionListenerStatusMessageCopy() {
		//
		// Copy status message to clipboard ...
		//
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection clipboardString = new StringSelection(
				textAreaStatusMessage.getText());
		clipboard.setContents( clipboardString, null);
		JOptionPane.showMessageDialog(getParent(),
				MSG_INFO_COPYTOCLIPBOARD_SUCCESS, MSG_INFO_TITLE_WARNING,
				JOptionPane.INFORMATION_MESSAGE);
		
	}

	protected void actionListenerExportFileSelect() {
		// TODO Auto-generated method stub
		
	}

	protected void actionListenerSqlParamsExtract() {
		// TODO Auto-generated method stub
		
	}

	protected void actionListenerSqlFilenameSelect() {
		// TODO Auto-generated method stub
		
	}

	protected void actionListenerJdbcDriverSelect() {
		// TODO Auto-generated method stub
		
	}

	/*
	 * buildMapArgs
	 */
	public static void buildMapArgs(String[] args){
		//
		// Build MapArgs ...
		//
		mapArgs.clear();
		for(int i = 0; i < args.length; i++) {
			if (i + 1 < args.length) {
				mapArgs.put(args[i], args[i+1]);
				i++;
			}
        }
	}
	
	/*
	 * 
	 */
	public void initUIFields(){
		//
		// Initialize UIFields based on command line args ...
		//
		String x = mapArgs.get(ARGS_COMMAND_LINE_JDBCDRIVER);
		if ( !(mapArgs.get(ARGS_COMMAND_LINE_JDBCDRIVER)==null)) {
			txtJdbcDriver.setText(mapArgs.get(ARGS_COMMAND_LINE_JDBCDRIVER));
		}
		if ( !(mapArgs.get(ARGS_COMMAND_LINE_PARAM_DATABASEURL)==null) ) {
			txtDatabaseUrl.setText(mapArgs.get(ARGS_COMMAND_LINE_PARAM_DATABASEURL));
		}
		if ( !(mapArgs.get(ARGS_COMMAND_LINE_PARAM_USERNAME)==null) ) {
			txtUsername.setText(mapArgs.get(ARGS_COMMAND_LINE_PARAM_USERNAME));
		}
		if ( !(mapArgs.get(ARGS_COMMAND_LINE_PARAM_PASSWORD)==null) ) {
			pwdPassword.setText(mapArgs.get(ARGS_COMMAND_LINE_PARAM_PASSWORD));
		}
		if ( !(mapArgs.get(ARGS_COMMAND_LINE_PARAM_FILENAME)==null) ) {
			txtSqlfilename.setText(mapArgs.get(ARGS_COMMAND_LINE_PARAM_FILENAME));
		}
		//
		// JTable initialization ...
		//
		tableSqlParams.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//		tableSqlParams.getColumnModel().getColumn(0).setPreferredWidth(180);
//		tableSqlParams.getColumnModel().getColumn(1).setPreferredWidth(180);
//		tableSqlParams.getColumnModel().getColumn(1).setPreferredWidth(700);
		
	}
	
	/**
	 * statusMessageReset()
	 */
	public void statusMessageReset() {
		textAreaStatusMessage.setText("");
	}
	
	/**
	 * statusMessageAddMsg()
	 */
	public void statusMessageAddMsg(String msg) {
		textAreaStatusMessage.setText( textAreaStatusMessage.getText() + msg + "\n"); 
	}

	/**
	 * refreshEditChange()
	 */
	public void refreshEditChange(String fieldName) {
		//
		// To-do:
		//
	}

	/**
	 * initAppCompleted()
	 */
	public void initAppCompleted() {
		//
		// Reset status message ...
		//
		statusMessageReset();
		
		//
		// Initialize UI fields ...
		//
		initUIFields();
		
	}

	/**
	 * lockUserEntries()
	 */
	private void lockUserEntries()  {
		//
		// Disable all user entries during processing ...
		//
		txtJdbcDriver.setEnabled(false);
		txtDatabaseUrl.setEnabled(false);
		txtUsername.setEnabled(false);
		pwdPassword.setEnabled(false);
		txtSqlfilename.setEnabled(false);;
		tableSqlParams.setEnabled(false);
		txtExportfile.setEnabled(false);;
		btnJdbcDriver.setEnabled(false);
		btnSqlfilename.setEnabled(false);
		btnSqlParams.setEnabled(false);
		btnExportFile.setEnabled(false);
		btnExport.setEnabled(false);
		btnCopyStatusMessage.setEnabled(false);
		btnAbout.setEnabled(false);
		
	}
	
	/**
	 * unlockUserEntries()
	 */
	private void unlockUserEntries()  {
		//
		// Disable all user entries during processing ...
		//
		txtJdbcDriver.setEnabled(true);
		txtDatabaseUrl.setEnabled(true);
		txtUsername.setEnabled(true);
		pwdPassword.setEnabled(true);
		txtSqlfilename.setEnabled(true);;
		tableSqlParams.setEnabled(true);
		txtExportfile.setEnabled(true);;
		btnJdbcDriver.setEnabled(true);
		btnSqlfilename.setEnabled(true);
		btnSqlParams.setEnabled(true);
		btnExportFile.setEnabled(true);
		btnExport.setEnabled(true);
		btnCopyStatusMessage.setEnabled(true);
		btnAbout.setEnabled(true);
		
	}


}
