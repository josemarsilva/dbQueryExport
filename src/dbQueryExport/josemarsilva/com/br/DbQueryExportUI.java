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
import javax.swing.JFileChooser;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import javax.swing.ImageIcon;

public class DbQueryExportUI extends JFrame {
	//
	// Args parameters ...
	//
	private final String ARGS_COMMAND_LINE_CLASSNAME = new String("-c");
	private final String ARGS_COMMAND_LINE_PARAM_DATABASEURL = new String("-r");
	private final String ARGS_COMMAND_LINE_PARAM_USERNAME = new String("-u");
	private final String ARGS_COMMAND_LINE_PARAM_PASSWORD = new String("-p");
	private final String ARGS_COMMAND_LINE_PARAM_SQLFILENAME = new String("-f");
	private final String ARGS_COMMAND_LINE_PARAM_EXPORTFILENAME = new String("-o");
	
	//
	// Messages ...
	//
	private final String MSG_INFO_COMMAND_LINE_HELP = new String("Database Query Export allows you export to a file the results of a query\n\n"
		+ "Usage: dbQueryExport [options]\n"
		+ "  " + ARGS_COMMAND_LINE_CLASSNAME            + "     Class name for invocation" + "\n"
		+ "  " + ARGS_COMMAND_LINE_PARAM_DATABASEURL    + "     Database Url location for Jdbc Driver (*)" + "\n"
		+ "  " + ARGS_COMMAND_LINE_PARAM_USERNAME       + "     Username Jdbc connection" + "\n"
		+ "  " + ARGS_COMMAND_LINE_PARAM_PASSWORD       + "     Password Jdbc connection" + "\n"
		+ "  " + ARGS_COMMAND_LINE_PARAM_SQLFILENAME    + "     SQL Query Filename complete path" + "\n"
		+ "  " + ARGS_COMMAND_LINE_PARAM_EXPORTFILENAME + "     Export Filename complete path" + "\n"
		+ "\n(*) Use <Username> and <Password> macro names to expand these command line parameters\n\n"
		+ "See also:\n"
		+ "  http://github.com/josemarsilva/dbQueryExport\n"
		+ "  http://josemarfuregattideabreusilva.blogspot.com.br\n"
		);
	private final String MSG_INFO_TITLE_WARNING = new String("Informação");
	private final String MSG_WARN_TITLE_WARNING = new String("Atenção");
	private final String MSG_CRITICAL_TITLE_WARNING = new String("Erro Crítico");
	private final String MSG_INFO_COPYTOCLIPBOARD_SUCCESS = new String("Cópia das mensagens para área de Clipboard concluída com sucesso!");
	private final String MSG_INFO_EXECUTION_SUCCESS = new String("Execução concluída com sucesso!");	
	private final String MSG_WARN_EXECUTION_FAILED = new String("Execução concluída com falha!\n Exceção %s");
	private final String MSG_WARN_NOTFOUND = new String("Arquivo '%s' não existe ou não pode ser aberto para leitura!");
	private final String MSG_WARN_OUTPUTFILE_MISSING = new String("Arquivo de exportação do resultado da Query não foi definido!"); 

	
	//
	// Table Column Titles ...
	//
	private final String COLUMNTITLE_PARAM = new String("Param");
	private final String COLUMNTITLE_VALUE = new String("Value");
	private final String COLUMNTITLE_OBS = new String("Obs");
	
	
	//
	// Filename and extensions ...
	//
	private final String FILENAMEEXTENSIONFILTER_JAR = new String("jar");
	private final String FILENAMEEXTENSIONFILTER_JAR_TITLE = new String("JDBC Driver");
	private final String FILENAMEEXTENSIONFILTER_SQL = new String("sql");
	private final String FILENAMEEXTENSIONFILTER_SQL_TITLE = new String("SQL Query");
	private final String FILENAMEEXTENSIONFILTER_XLS = new String("xls");
	private final String FILENAMEEXTENSIONFILTER_XLS_TITLE = new String("Excel 2007");
	
	//
	// XLS
	//
	private final String WORKSHEET_DEFAULT_NAME = new String("dbQueryExport");
	private final int WORKSHEET_MAX_ROW = 65536;
	
	//
	// Class Properties ...
	//
	private static Map<String, String> mapArgs = new HashMap<String, String>();

	//
	// UI fields ...
	//
	private JPanel contentPane;
	private JTextField txtDatabaseUrl;
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private JTextField txtSqlFilename;
	private JTable tableSqlParams;
	private JTextField txtExportFile;
	private JButton btnSqlFilename;
	private JButton btnSqlParams;
	private JProgressBar progressBarLog;
	private JButton btnExportFile;
	private JButton btnExport;
	private JButton btnCopyStatusMessage;
	private JButton btnAbout;
	private JTextArea textAreaStatusMessage;
	private JTextArea textAreaSqlQuery;
	private JTextField txtClassname;

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
		setBounds(100, 100, 600, 600);
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
						.addComponent(panel_2_SqlQueryFilename, GroupLayout.PREFERRED_SIZE, 576, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_4_SqlParams, GroupLayout.PREFERRED_SIZE, 579, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_5_StatusMessage, GroupLayout.PREFERRED_SIZE, 579, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_6_ExportFilename, GroupLayout.PREFERRED_SIZE, 578, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_7_Commands, GroupLayout.PREFERRED_SIZE, 577, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_3_SqlQuery, GroupLayout.PREFERRED_SIZE, 577, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_1_Connection, GroupLayout.PREFERRED_SIZE, 576, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel_1_Connection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(panel_2_SqlQueryFilename, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3_SqlQuery, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_4_SqlParams, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_5_StatusMessage, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_6_ExportFilename, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_7_Commands, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(32, Short.MAX_VALUE))
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
		btnExport.setIcon(new ImageIcon(DbQueryExportUI.class.getResource("/icon-services-16x16.png")));
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionListenerExportExecute();
			}

		});
		btnExport.setHorizontalAlignment(SwingConstants.LEFT);
		panel_7_Commands.add(btnExport, "1, 1");
		
		btnCopyStatusMessage = new JButton("Copy Msg ...");
		btnCopyStatusMessage.setIcon(new ImageIcon(DbQueryExportUI.class.getResource("/icon-copy-16x16.png")));
		btnCopyStatusMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionListenerStatusMessageCopy();
			}
		});
		btnCopyStatusMessage.setHorizontalAlignment(SwingConstants.LEFT);
		panel_7_Commands.add(btnCopyStatusMessage, "2, 1");
		
		btnAbout = new JButton("About ...");
		btnAbout.setIcon(new ImageIcon(DbQueryExportUI.class.getResource("/icon-sign-info-16x16.png")));
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionListenerAboutShow();
			}
		});
		btnAbout.setHorizontalAlignment(SwingConstants.LEFT);
		panel_7_Commands.add(btnAbout, "3, 1");
		
		JButton btnExit = new JButton("Exit");
		btnExit.setIcon(new ImageIcon(DbQueryExportUI.class.getResource("/icon-exit-1-16x16.png")));
		panel_7_Commands.add(btnExit, "4, 1");
		panel_6_ExportFilename.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("center:75dlu"),
				ColumnSpec.decode("left:250dlu:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		btnExportFile = new JButton("Export File ...");
		btnExportFile.setIcon(new ImageIcon(DbQueryExportUI.class.getResource("/icon-save-16x16.png")));
		btnExportFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionListenerExportFileSelect();
			}
		});
		panel_6_ExportFilename.add(btnExportFile, "2, 1");
		
		txtExportFile = new JTextField();
		txtExportFile.setFont(new Font("Tahoma", Font.PLAIN, 9));
		txtExportFile.setEditable(false);
		panel_6_ExportFilename.add(txtExportFile, "3, 1, fill, default");
		txtExportFile.setColumns(10);
		
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
		textAreaStatusMessage.setEditable(false);
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
		btnSqlParams.setIcon(new ImageIcon(DbQueryExportUI.class.getResource("/icon-table-arrow-16x16.png")));
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
				RowSpec.decode("default:grow"),}));
		
		Canvas canvas_SqlQuery = new Canvas();
		panel_3_SqlQuery.add(canvas_SqlQuery, "1, 1");
		
		JLabel lblSqlQuery = new JLabel("SQL Query");
		panel_3_SqlQuery.add(lblSqlQuery, "2, 1");
		
		JScrollPane scrollPane_SqlQuery = new JScrollPane();
		panel_3_SqlQuery.add(scrollPane_SqlQuery, "3, 1, fill, fill");
		
		textAreaSqlQuery = new JTextArea();
		scrollPane_SqlQuery.setViewportView(textAreaSqlQuery);
		textAreaSqlQuery.setFont(new Font("Monospaced", Font.PLAIN, 9));
		panel_2_SqlQueryFilename.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("center:75dlu"),
				ColumnSpec.decode("left:200dlu:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		btnSqlFilename = new JButton("SQL Query Filename");
		btnSqlFilename.setIcon(new ImageIcon(DbQueryExportUI.class.getResource("/icon-open-folder-16x16.png")));
		btnSqlFilename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionListenerSqlFilenameSelect();
			}
		});
		panel_2_SqlQueryFilename.add(btnSqlFilename, "2, 1");
		
		txtSqlFilename = new JTextField();
		txtSqlFilename.setFont(new Font("Tahoma", Font.PLAIN, 9));
		txtSqlFilename.setEditable(false);
		panel_2_SqlQueryFilename.add(txtSqlFilename, "3, 1, fill, default");
		txtSqlFilename.setColumns(10);
		panel_1_Connection.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("center:75dlu"),
				ColumnSpec.decode("left:200dlu:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		Canvas canvas_JdbcDriver = new Canvas();
		panel_1_Connection.add(canvas_JdbcDriver, "1, 1");
		
		JLabel lblClassName = new JLabel("Jdbc Class Name");
		panel_1_Connection.add(lblClassName, "2, 1, center, default");
		
		txtClassname = new JTextField();
		txtClassname.setEditable(false);
		panel_1_Connection.add(txtClassname, "3, 1, fill, default");
		txtClassname.setColumns(10);
		
		Canvas canvas_DatabaseUrl = new Canvas();
		panel_1_Connection.add(canvas_DatabaseUrl, "1, 2");
		
		JLabel lblDatabaseUrl = new JLabel("Database Url");
		panel_1_Connection.add(lblDatabaseUrl, "2, 2, center, default");
		
		txtDatabaseUrl = new JTextField();
		txtDatabaseUrl.setEditable(false);
		txtDatabaseUrl.setToolTipText("Database Url");
		panel_1_Connection.add(txtDatabaseUrl, "3, 2, fill, default");
		txtDatabaseUrl.setColumns(10);
		
		Canvas canvas_Username = new Canvas();
		panel_1_Connection.add(canvas_Username, "1, 3");
		
		JLabel lblUsername = new JLabel("Username");
		panel_1_Connection.add(lblUsername, "2, 3, center, default");
		
		txtUsername = new JTextField();
		txtUsername.setEditable(false);
		txtUsername.setToolTipText("Username");
		panel_1_Connection.add(txtUsername, "3, 3, fill, default");
		txtUsername.setColumns(10);
		
		Canvas canvas_Password = new Canvas();
		panel_1_Connection.add(canvas_Password, "1, 4");
		
		JLabel lblPassword = new JLabel("Password");
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
	
	/*
	 * ************************************************************************
	 * actionListener<UI Field> ...
	 * ************************************************************************
	 */

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
		//
		// Select Export file ...
		//
		final JFileChooser jFileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				FILENAMEEXTENSIONFILTER_XLS_TITLE + " (." + FILENAMEEXTENSIONFILTER_XLS + ")", FILENAMEEXTENSIONFILTER_XLS);
		jFileChooser.setFileFilter(filter);
		int returnVal = jFileChooser.showOpenDialog(getParent());
		
		//
		// File Choosen ?
		//
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			//
			// Set file choosen ...
			//
			txtExportFile.setText(jFileChooser.getSelectedFile().getPath());
			refreshEditChange("btnExportFile");
		}
		
	}

	protected void actionListenerSqlParamsExtract() {
		// TODO Auto-generated method stub
		
	}

	protected void actionListenerSqlFilenameSelect() {
		//
		// Select Sql Filename  ..
		//
		final JFileChooser jFileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				FILENAMEEXTENSIONFILTER_SQL_TITLE + " (." + FILENAMEEXTENSIONFILTER_SQL + ")", FILENAMEEXTENSIONFILTER_SQL);
		jFileChooser.setFileFilter(filter);
		int returnVal = jFileChooser.showOpenDialog(getParent());
		
		//
		// File Choosen ?
		//
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			//
			// Set file choosen ...
			//
			txtSqlFilename.setText(jFileChooser.getSelectedFile().getPath());
			textAreaSqlQuery.setText(getFileContents(txtSqlFilename.getText()));
			refreshEditChange("btnSqlFilename");
		}
		
	}
	
	protected void actionListenerExportExecute() {
		//
		// Step #1: Lock fields for user entries ...
		//
		statusMessageReset();
		statusMessageAddMsg("Step #1: Lock fields for user entries");
		lockUserEntries();
		
		Connection conn;
		PreparedStatement prepStmt;
		FileOutputStream fileOutputStream;
		Workbook workbook ;
		
		try {
			
			//
			// Step #2: Open new .xls file  ...
			//
			statusMessageAddMsg("Step #2: Open new .xls file ...");
			statusMessageAddMsg("  - FileOutputStream("+txtExportFile.getText()+")");
			fileOutputStream = new FileOutputStream(new File(txtExportFile.getText()));
			statusMessageAddMsg("  - new HSSFWorkbook()");
			workbook = new HSSFWorkbook();
			statusMessageAddMsg("  - workbook.createSheet("+WORKSHEET_DEFAULT_NAME+")" );
		    Sheet sheet = workbook.createSheet(WORKSHEET_DEFAULT_NAME);
		    Row row = null;
		    Cell cell = null;
			
			//
			// Step #3: Open new JDBC connection  ...
			//
			statusMessageAddMsg("Step #3: Open new JDBC connection ...");
			statusMessageAddMsg("  - Class.forName(" + txtClassname.getText() + ")" );
			Class.forName(txtClassname.getText());
			statusMessageAddMsg("  - DriverManager.getConnection(" + txtDatabaseUrl.getText() + ")" );
			conn = DriverManager.getConnection(txtDatabaseUrl.getText());
			int rowCount = 0;

			//
			// Step #4: Prepare and execute Query Statement ...
			//
			statusMessageAddMsg("Step #4: Prepare and execute Query Statement ..." );
			statusMessageAddMsg("  - conn.prepareStatement()" );
			prepStmt = conn.prepareStatement(textAreaSqlQuery.getText(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			statusMessageAddMsg("  - prepStmt.executeQuery()" );
			ResultSet rs = prepStmt.executeQuery();
			statusMessageAddMsg("  - rs.getMetaData()" );
			ResultSetMetaData rsmd = rs.getMetaData();
			statusMessageAddMsg("  - sheet.createRow()" );
			row = sheet.createRow(0);
			for (int i=1; i <= rsmd.getColumnCount(); i++) {
				String columnLable =  rsmd.getColumnLabel(i);
				cell = row.createCell(i-1);
				cell.setCellValue(columnLable);
			}
			statusMessageAddMsg("  - rs.last()" );
			rs.last();
			statusMessageAddMsg("  - rs.getRow()" );
			rowCount = rs.getRow();
			statusMessageAddMsg("  - rs.beforeFirst()" );
	        rs.beforeFirst();
	        
			//
			// Step #5: Loop on ResultSet ...
			//
			statusMessageAddMsg("Step #5: Loop on ResultSet ..." );
			int rowCurrent = 0;
			while (rs.next()) {
				//
				// Status message row() ...
				//
				if (rowCurrent <= 10 ) {
					statusMessageAddMsg("  - row(" + rowCurrent +"/"+rowCount+")" );
				} else if (rowCurrent <=100 & rowCurrent%10 == 0) {
					statusMessageAddMsg("  - row(" + rowCurrent +"/"+rowCount+")" );
				} else if (rowCurrent <=1000 & rowCurrent%100 == 0) {
					statusMessageAddMsg("  - row(" + rowCurrent +"/"+rowCount+")" );
				} else if (rowCurrent <=10000 & rowCurrent%1000 == 0) {
					statusMessageAddMsg("  - row(" + rowCurrent +"/"+rowCount+")" );
				} else if (rowCurrent%1000 == 0){
					statusMessageAddMsg("  - row(" + rowCurrent +"/"+rowCount+")" );
				}
				
				//
				// Status set progress() ...
				//
				statusSetProgress(rowCurrent/rowCount);
				
				//
				// If row < 65636 Then ...
				//
				if (rowCurrent < WORKSHEET_MAX_ROW-2 ) {
										//
					// Add current row for sheet ...
					//
					row = sheet.createRow(rowCurrent+1);
					
					//
					// Get ResultSetMetaData for current query-row and Put into sheet-row  ...
					//
					rsmd = rs.getMetaData();
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						cell = row.createCell(i-1);
						int type = rsmd.getColumnType(i);
						if ( type == Types.DATE ) {
							Date dateValue = rs.getDate(i);
							cell.setCellValue(dateValue);						
						} else if ( type == Types.DOUBLE || type == Types.NUMERIC || type == Types.REAL || type == Types.SMALLINT ) {
							Double doubleValue = rs.getDouble(i);
							cell.setCellValue(doubleValue);
						} else {
							String value = rs.getString(i);
							cell.setCellValue(value);						
						}
					}
				}
					
				
				//
				// next ...
				//
				rowCurrent++;
			}
			statusMessageAddMsg("  - row(" + rowCurrent +"/"+rowCount+")" );

			//
			// Step #6: Close .xls file ...
			//
			statusMessageAddMsg("Step #6: Close .xls file ...");
			statusMessageAddMsg("  - workbook.write()");
		    workbook.write(fileOutputStream);
			statusMessageAddMsg("  - fileOutputStream.close()");
			fileOutputStream.close();

			//
			// Step #7: Close Jdbc connection ...
			//
			statusMessageAddMsg("Step #7: Close Jdbc connection ..." );
			statusMessageAddMsg("  - rs.close()" );
			rs.close();
			statusMessageAddMsg("  - prepStmt.close()" );
			prepStmt.close();
			statusMessageAddMsg("  - conn.close()" );
			conn.close();

			//
			// Step #8: Show Message ...
			//
			JOptionPane.showMessageDialog(getParent(),
					MSG_INFO_EXECUTION_SUCCESS, MSG_INFO_TITLE_WARNING,
					JOptionPane.INFORMATION_MESSAGE);
			

		} catch (Exception e) {
			JOptionPane.showMessageDialog(getParent(),
					MSG_WARN_EXECUTION_FAILED.replace("%s", e.toString()), MSG_CRITICAL_TITLE_WARNING,
					JOptionPane.ERROR_MESSAGE);
			statusMessageAddMsg(e.toString());
			e.printStackTrace();
		} finally {
			
		}
						
		//
		// Step #99: Unlock fields for user entries ...
		//
		statusMessageAddMsg("Step #99: Unlock fields for user entries ...");
		unlockUserEntries();
						
	}
	
	protected String getFileContents(String filename){
		String content = new String("");
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				content = content + line + "\n";
			}
			bufferedReader.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(getParent(),
					MSG_WARN_NOTFOUND.replace("%s", filename), MSG_WARN_TITLE_WARNING,
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return content;
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
	public void initUIFieldsFromArgs(){
		//
		// Initialize UIFields based on command line args ...
		//
		if ( !(mapArgs.get(ARGS_COMMAND_LINE_CLASSNAME)==null)) {
			txtClassname.setText(mapArgs.get(ARGS_COMMAND_LINE_CLASSNAME));
		}
		if ( !(mapArgs.get(ARGS_COMMAND_LINE_PARAM_DATABASEURL)==null) ) {
			txtDatabaseUrl.setText(mapArgs.get(ARGS_COMMAND_LINE_PARAM_DATABASEURL));
		}
		if ( !(mapArgs.get(ARGS_COMMAND_LINE_PARAM_USERNAME)==null) ) {
			txtUsername.setText(mapArgs.get(ARGS_COMMAND_LINE_PARAM_USERNAME));
			txtDatabaseUrl.setText( txtDatabaseUrl.getText().replace("<Username>", txtUsername.getText()) );
		}
		if ( !(mapArgs.get(ARGS_COMMAND_LINE_PARAM_PASSWORD)==null) ) {
			pwdPassword.setText(mapArgs.get(ARGS_COMMAND_LINE_PARAM_PASSWORD));
			txtDatabaseUrl.setText( txtDatabaseUrl.getText().replace("<Password>", pwdPassword.getText()) );
		}
		if ( !(mapArgs.get(ARGS_COMMAND_LINE_PARAM_SQLFILENAME)==null) ) {
			txtSqlFilename.setText(mapArgs.get(ARGS_COMMAND_LINE_PARAM_SQLFILENAME));
			textAreaSqlQuery.setText(getFileContents(txtSqlFilename.getText()));
		}
		if ( !(mapArgs.get(ARGS_COMMAND_LINE_PARAM_EXPORTFILENAME)==null) ) {
			txtExportFile.setText(mapArgs.get(ARGS_COMMAND_LINE_PARAM_EXPORTFILENAME));
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
		statusSetProgress(0);
	}
	
	/**
	 * statusMessageAddMsg()
	 */
	public void statusMessageAddMsg(String msg) {
		textAreaStatusMessage.setText( textAreaStatusMessage.getText() + msg + "\n"); 
	}
	
	/*
	 * statusSetProgress() 
	 */
	public void statusSetProgress(int n) {
		if (n >= 0 & n <=100 ) {
			progressBarLog.setValue(n);
			progressBarLog.updateUI();
		}
	}

	/**
	 * refreshEditChange()
	 */
	public void refreshEditChange(String fieldName) {
		//
		// Refresh edit changes depending on fieldName
		//
		switch (fieldName) {
		case "btnJdbcDriver":
			txtDatabaseUrl.setEditable(true);
			txtUsername.setEditable(true);
			pwdPassword.setEditable(true);
			break;
		}
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
		initUIFieldsFromArgs();
		
	}

	/**
	 * lockUserEntries()
	 */
	private void lockUserEntries()  {
		//
		// Disable all user entries during processing ...
		//
		txtClassname.setEnabled(false);
		txtDatabaseUrl.setEnabled(false);
		txtUsername.setEnabled(false);
		pwdPassword.setEnabled(false);
		txtSqlFilename.setEnabled(false);;
		tableSqlParams.setEnabled(false);
		txtExportFile.setEnabled(false);;
		btnSqlFilename.setEnabled(false);
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
//		txtJdbcDriver.setEnabled(true);
		txtClassname.setEnabled(true);
		txtDatabaseUrl.setEnabled(true);
		txtUsername.setEnabled(true);
		pwdPassword.setEnabled(true);
		txtSqlFilename.setEnabled(true);;
		tableSqlParams.setEnabled(true);
		txtExportFile.setEnabled(true);;
		btnSqlFilename.setEnabled(true);
		btnSqlParams.setEnabled(true);
		btnExportFile.setEnabled(true);
		btnExport.setEnabled(true);
		btnCopyStatusMessage.setEnabled(true);
		btnAbout.setEnabled(true);
		
	}
}
