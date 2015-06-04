package dbQueryExport.josemarsilva.com.br;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.swing.ImageIcon;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DbQueryExportUI extends JFrame {
	//
	// Version and build ...
	//
	private final String APP_VERSION = new String("v1.03.20150604");
	//
	// Args parameters ...
	//
	private final String ARGS_COMMAND_LINE_CLASSNAME = new String("-c");
	private final String ARGS_COMMAND_LINE_PARAM_DATABASEURL = new String("-d");
	private final String ARGS_COMMAND_LINE_PARAM_USERNAME = new String("-u");
	private final String ARGS_COMMAND_LINE_PARAM_PASSWORD = new String("-p");
	private final String ARGS_COMMAND_LINE_PARAM_SQLFILENAME = new String("-f");
	private final String ARGS_COMMAND_LINE_PARAM_EXPORTFILENAME = new String("-o");
	
	//
	// Messages ...
	//
	private final String MSG_INFO_COMMAND_LINE_HELP = new String("Database Query Export allows you export to a file the results of a query\n\n"
		+ "Usage: dbQueryExport [options]\n"
		+ "    " + ARGS_COMMAND_LINE_CLASSNAME            + "     Class name for invocation" + "\n"
		+ "    " + ARGS_COMMAND_LINE_PARAM_DATABASEURL    + "     Database Url location for Jdbc Driver" + "\n"
		+ "    " + ARGS_COMMAND_LINE_PARAM_USERNAME       + "     Username Jdbc connection" + "\n"
		+ "    " + ARGS_COMMAND_LINE_PARAM_PASSWORD       + "     Password Jdbc connection" + "\n"
		+ "    " + ARGS_COMMAND_LINE_PARAM_SQLFILENAME    + "     SQL Query Filename complete path" + "\n"
		+ "    " + ARGS_COMMAND_LINE_PARAM_EXPORTFILENAME + "     Export Filename complete path" + "\n"
		+ "\n"
		+ "SQL Query ad hoc parameters convention is ${ParamName|ParamDescription}\n"
		+ "\n\n"
		+ "Examples:\n"
		+ "    a) Command line arguments\n"
		+ "       dbQueryExport.jar -c oracle.jdbc.driver.OracleDriver -d jdbc:oracle:thin:@localhost:1521:dbname -u username -p password -f \"C:\\TEMP\\sqlquery.sql\" -o \"C:\\TEMP\\sqlquery.xls\" \n"
		+ "       dbQueryExport.jar -c org.postgresql.Driver -d jdbc:postgresql://localhost/dbname -u username -p password -f \"C:\\TEMP\\sqlquery.sql\" -o \"C:\\TEMP\\sqlquery.xls\" \n"
		+ "       dbQueryExport.jar -c com.mysql.jdbc.Driver -d jdbc:mysql://localhost:3306/dbname -u username -p password -f \"C:\\TEMP\\sqlquery.sql\" -o \"C:\\TEMP\\sqlquery.xls\" \n"
		+ "    b) SQL Query adhoc parameters to bind on execution:\n"
		+ "        SELECT owner, table_name FROM user_tables WHERE owner = UPPER('${pOwner|Enter Owner of the tables})'\n"
		+ "        SELECT hostname, user from user WHERE hostname LIKE UPPER('${pHostname|Enter HostName to uses})'\n"
		+ "\n\n"
		+ "See also:\n"
		+ "    http://github.com/josemarsilva/dbQueryExport"
		);
	// Title
	private final String MSG_TITLE_INFO_WARNING = new String("Informação");
	private final String MSG_TITLE_WARN_WARNING = new String("Atenção");
	private final String MSG_TITLE_CRITICAL_WARNING = new String("Erro Crítico");
	private final String MSG_TITLE_QUESTION_WARNING = new String("Pergunta a ser respondida");
	private final String MSG_INFO_COPYTOCLIPBOARD_SUCCESS = new String("Cópia das mensagens para área de Clipboard concluída com sucesso!");
	private final String MSG_INFO_EXECUTION_SUCCESS = new String("Execução concluída com sucesso!");	
	private final String MSG_INFO_SQLPARAM_SUCCESS = new String("Extração dos parâmetros 'SQL Query' concluída com sucesso!");	
	private final String MSG_INFO_QUESTION_OPENFILE = new String("Você quer abrir o arquivo '%s' agora?");
	private final String MSG_INFO_QUESTION_EXITAPP = new String("Tem certeza que deseja sair da aplicação?");
	private final String MSG_WARN_EXECUTION_FAILED = new String("Execução concluída com falha!\n Exceção %s");
	private final String MSG_WARN_EXCEL_LIMIT_EXCEEDED = new String("Resultado de sua SQL Query é superior ao limite de %s linhas do Excel. Resultado será truncado neste limite!");
	private final String MSG_WARN_NOTFOUND = new String("Arquivo '%s' não existe ou não pode ser aberto para leitura!");
	private final String MSG_CHECK_CLASSNAME_MISSING = new String("Campo 'Jdbc Class Name' não foi preenchido!"); 
	private final String MSG_CHECK_DATABASEURL_MISSING = new String("Campo 'Database Url' não foi preenchido!"); 
	private final String MSG_CHECK_USERNAME_MISSING = new String("Campo 'Username' não foi preenchido!"); 
	private final String MSG_CHECK_PASSWORD_MISSING = new String("Campo 'Password' não foi preenchido!"); 
	private final String MSG_CHECK_SQLQUERY_MISSING = new String("Campo 'SQL Query' não foi preenchido!"); 
	private final String MSG_CHECK_EXPORTFILE_MISSING = new String("Campo 'Export File' não foi preenchido!"); 

	
	//
	// Table Column Titles ...
	//
	private final String COLUMNTITLE_PARAM = new String("Param");
	private final String COLUMNTITLE_VALUE = new String("Value");
	private final String COLUMNTITLE_DESCRIPTION = new String("Description");
	private final String COLUMNTITLE_HIDDEN = new String("Hidden");
	
	
	//
	// Filename and extensions ...
	//
	private final String FILENAMEEXTENSIONFILTER_SQL = new String("sql");
	private final String FILENAMEEXTENSIONFILTER_SQL_TITLE = new String("SQL Query");
	private final String FILENAMEEXTENSIONFILTER_XLS = new String("xls");
	private final String FILENAMEEXTENSIONFILTER_XLS_TITLE = new String("Excel 2007");
	
	//
	// XLS
	//
	private final String WORKSHEET_DEFAULT_NAME = new String("dbQueryExport");
	private final String EXCEL_EXTENSION = new String(".xls");
	private final int WORKSHEET_MAX_ROW = 65536;
	
	//
	// SQL Parameter ${param|description} ...
	//
	private final String SQL_PARAM_BEGIN = new String("${");
	private final String SQL_PARAM_END = new String("}");
	private final String SQL_PARAM_SEPARATOR = new String("\\|");
	
	
	
	//
	// Class Properties ...
	//
	private static Map<String, String> mapArgs = new HashMap<String, String>();

	//
	// UI fields ...
	//
	private JPanel contentPane;
	private JTextField txtClassname;
	private JTextField txtDatabaseUrl;
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private JTextField txtSqlFilename;
	private DefaultTableModel tableModelSqlParams;
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
	private JScrollPane scrollPane;
	private JLabel lblClassName;
	private JLabel lblDatabaseUrl;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblSqlQuery;

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
		setTitle("DbQueryExport - "+ APP_VERSION);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 569);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel_1_Connection = new JPanel();
		panel_1_Connection.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JPanel panel_2_SqlQueryFilename = new JPanel();
		panel_2_SqlQueryFilename.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JPanel panel_6_ExportFilename = new JPanel();
		panel_6_ExportFilename.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JPanel panel_7_Commands = new JPanel();
		panel_7_Commands.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JScrollPane scrollPane_4_SqlParams = new JScrollPane();
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
		btnExport.setToolTipText("Export results from SQL Query to file ...");
		btnExport.setIcon(new ImageIcon(DbQueryExportUI.class.getResource("/icon-services-16x16.png")));
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkUserEntries()) {
					actionListenerExportExecute();
				}
			}

		});
		btnExport.setHorizontalAlignment(SwingConstants.LEFT);
		panel_7_Commands.add(btnExport, "1, 1");
		
		btnCopyStatusMessage = new JButton("Copy msg ...");
		btnCopyStatusMessage.setToolTipText("Copy log message to clipboard ...");
		btnCopyStatusMessage.setIcon(new ImageIcon(DbQueryExportUI.class.getResource("/icon-copy-16x16.png")));
		btnCopyStatusMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionListenerStatusMessageCopy();
			}
		});
		btnCopyStatusMessage.setHorizontalAlignment(SwingConstants.LEFT);
		panel_7_Commands.add(btnCopyStatusMessage, "2, 1");
		
		btnAbout = new JButton("About ...");
		btnAbout.setToolTipText("About and command line parameters for DbQueryExport ...");
		btnAbout.setIcon(new ImageIcon(DbQueryExportUI.class.getResource("/icon-sign-info-16x16.png")));
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionListenerAboutShow();
			}
		});
		btnAbout.setHorizontalAlignment(SwingConstants.LEFT);
		panel_7_Commands.add(btnAbout, "3, 1");
		
		JButton btnExit = new JButton("Exit");
		btnExit.setToolTipText("Exit application ...");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionListenerExitApp();
			}
		});
		btnExit.setIcon(new ImageIcon(DbQueryExportUI.class.getResource("/icon-exit-1-16x16.png")));
		panel_7_Commands.add(btnExit, "4, 1");
		panel_6_ExportFilename.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("center:75dlu"),
				ColumnSpec.decode("left:250dlu:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		btnExportFile = new JButton("Export File ...");
		btnExportFile.setToolTipText("Define export file ...");
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
		
		panel_2_SqlQueryFilename.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("center:75dlu"),
				ColumnSpec.decode("left:200dlu:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		btnSqlFilename = new JButton("SQL Query Filename");
		btnSqlFilename.setToolTipText("Open SQL Query file ...");
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
		
		lblClassName = new JLabel("Jdbc Class Name");
		panel_1_Connection.add(lblClassName, "2, 1, center, default");
		
		txtClassname = new JTextField();
		txtClassname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				refreshEditChange("txtClassname");
			}
		});
		panel_1_Connection.add(txtClassname, "3, 1, fill, default");
		txtClassname.setColumns(10);
		
		Canvas canvas_DatabaseUrl = new Canvas();
		panel_1_Connection.add(canvas_DatabaseUrl, "1, 2");
		
		lblDatabaseUrl = new JLabel("Database Url");
		panel_1_Connection.add(lblDatabaseUrl, "2, 2, center, default");
		
		txtDatabaseUrl = new JTextField();
		txtDatabaseUrl.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				refreshEditChange("txtDatabaseUrl");
			}
		});
		txtDatabaseUrl.setToolTipText("Database Url");
		panel_1_Connection.add(txtDatabaseUrl, "3, 2, fill, default");
		txtDatabaseUrl.setColumns(10);
		
		Canvas canvas_Username = new Canvas();
		panel_1_Connection.add(canvas_Username, "1, 3");
		
		lblUsername = new JLabel("Username");
		panel_1_Connection.add(lblUsername, "2, 3, center, default");
		
		txtUsername = new JTextField();
		txtUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				refreshEditChange("txtUsername");
			}
		});
		txtUsername.setToolTipText("Username");
		panel_1_Connection.add(txtUsername, "3, 3, fill, default");
		txtUsername.setColumns(10);
		
		Canvas canvas_Password = new Canvas();
		panel_1_Connection.add(canvas_Password, "1, 4");
		
		lblPassword = new JLabel("Password");
		panel_1_Connection.add(lblPassword, "2, 4, center, default");
		
		pwdPassword = new JPasswordField();
		pwdPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				refreshEditChange("pwdPassword");
			}
		});
		pwdPassword.setToolTipText("Password");
		panel_1_Connection.add(pwdPassword, "3, 4, fill, default");
		
		JScrollPane scrollPane_3_SqlQuery = new JScrollPane();
		scrollPane_3_SqlQuery.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		scrollPane = new JScrollPane();
		
		btnSqlParams = new JButton("SQL Params");
		scrollPane_4_SqlParams.setRowHeaderView(btnSqlParams);
		btnSqlParams.setToolTipText("Extract SQL Params from SQL Query ...");
		btnSqlParams.setIcon(new ImageIcon(DbQueryExportUI.class.getResource("/icon-table-arrow-16x16.png")));
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
			tableModelSqlParams.addColumn(COLUMNTITLE_DESCRIPTION);
			tableModelSqlParams.addColumn(COLUMNTITLE_HIDDEN);
		}
		{			
			tableSqlParams = new JTable(tableModelSqlParams);
			scrollPane_4_SqlParams.setViewportView(tableSqlParams);
		}
		btnSqlParams.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionListenerSqlParamsExtract();
			}
		});
		
		lblSqlQuery = new JLabel("SQL Query ");
		scrollPane_3_SqlQuery.setRowHeaderView(lblSqlQuery);
		
		textAreaSqlQuery = new JTextArea();
		textAreaSqlQuery.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				refreshEditChange("textAreaSqlQuery");
			}
		});
		scrollPane_3_SqlQuery.setViewportView(textAreaSqlQuery);
		textAreaSqlQuery.setFont(new Font("Monospaced", Font.PLAIN, 9));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(13)
							.addComponent(panel_1_Connection, GroupLayout.PREFERRED_SIZE, 566, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addComponent(panel_2_SqlQueryFilename, GroupLayout.PREFERRED_SIZE, 569, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addComponent(scrollPane_3_SqlQuery, GroupLayout.PREFERRED_SIZE, 561, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(11)
							.addComponent(scrollPane_4_SqlParams, GroupLayout.PREFERRED_SIZE, 568, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(11)
							.addComponent(panel_6_ExportFilename, GroupLayout.PREFERRED_SIZE, 568, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(12)
							.addComponent(panel_7_Commands, GroupLayout.PREFERRED_SIZE, 577, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 569, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel_1_Connection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(panel_2_SqlQueryFilename, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(scrollPane_3_SqlQuery, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(scrollPane_4_SqlParams, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_6_ExportFilename, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(panel_7_Commands, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(51))
		);
		
		progressBarLog = new JProgressBar();
		scrollPane.setColumnHeaderView(progressBarLog);
		
		textAreaStatusMessage = new JTextArea();
		scrollPane.setViewportView(textAreaStatusMessage);
		textAreaStatusMessage.setEditable(false);
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
				MSG_INFO_COMMAND_LINE_HELP, MSG_TITLE_INFO_WARNING,
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
				MSG_INFO_COPYTOCLIPBOARD_SUCCESS, MSG_TITLE_INFO_WARNING,
				JOptionPane.INFORMATION_MESSAGE);
		
	}

	protected void actionListenerExportFileSelect() {
		//
		// Refresh Edit Change ...
		//
		refreshEditChange("btnExportFile");
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
			String sExportFile = jFileChooser.getSelectedFile().getPath();
			if (sExportFile.length() > 0) {
				if (sExportFile.length() > String.valueOf(EXCEL_EXTENSION).length()) {
					if (!sExportFile.toLowerCase().endsWith(EXCEL_EXTENSION.toLowerCase())) {
						sExportFile = sExportFile + EXCEL_EXTENSION;					
					}
				} else {
					sExportFile = sExportFile + EXCEL_EXTENSION;					
				}
			}
			txtExportFile.setText(sExportFile);
			refreshEditChange("btnExportFile");
		}
		
	}
	
	/*
	 * 
	 */
	protected void getSqlParamsFromSqlQuery(String sSql) {
		
		//
		// Remove all rows from table ...
		//
		for (int i = tableModelSqlParams.getRowCount() - 1; i >= 0; i--) {
			tableModelSqlParams.removeRow(i);
		}
		
		//
		// sSql is not empty ...
		//
		if (sSql.length() > 0) {
			//
			// Loop while exists SQL_PARAM_BEGIN
			//
			int i=0;
			int j=0;
			while ( sSql.substring(i, sSql.length()-1).contains(SQL_PARAM_BEGIN) ){
				//
				// i = sSql.indexOf( next( SQL_PARAM_BEGIN ) )
				//
				i = sSql.substring(i, sSql.length()-1).indexOf(SQL_PARAM_BEGIN) + j;
				
				//
				// Stop if no more next( SQL_PARAM_BEGIN )
				//
				if (i < 0){
					break;
				}
				
				//
				// j = sSql.indexOf( next( SQL_PARAM_END ) )
				//
				j = sSql.substring(i+SQL_PARAM_BEGIN.length(), sSql.length()-1).indexOf(SQL_PARAM_END) + i + SQL_PARAM_BEGIN.length() + SQL_PARAM_END.length();
				
				//
				// Stop if no more next( SQL_PARAM_END )
				//
				if (j < 0){
					break;
				}
				
				//
				// add table row of each param ...
				//
				String sSqlParam = sSql.substring(i, j);
				String[] sqlParamParts = sSql.substring(i, j).replace(SQL_PARAM_BEGIN, "").replace(SQL_PARAM_END, "").split(SQL_PARAM_SEPARATOR);
				String sSqlParamName = new String("");
				String sSqlParamDescription = new String("");
				if (sqlParamParts.length > 0 ) sSqlParamName = sqlParamParts[0];
				if (sqlParamParts.length > 1 ) sSqlParamDescription = sqlParamParts[1];
				tableModelSqlParams.addRow(new Object[]{sSqlParamName, "", sSqlParamDescription,sSqlParam});

				//
				// Next( SQL_PARAM_BEGIN )
				//
				i = j;
				
			}
			
		}

		
	}

	protected void actionListenerSqlParamsExtract() {
		//
		// Extract parameters from SQL ...
		//
		getSqlParamsFromSqlQuery(textAreaSqlQuery.getText());
		
		//
		// Message Dialog Box ...
		//
		JOptionPane.showMessageDialog(getParent(),
				MSG_INFO_SQLPARAM_SUCCESS, MSG_TITLE_INFO_WARNING,
				JOptionPane.INFORMATION_MESSAGE);


	}

	protected void actionListenerExitApp() {
		//
		// ExitApp ...
		//
		int dialogButton = JOptionPane.YES_NO_OPTION;
		dialogButton = JOptionPane.showConfirmDialog(getParent(),
				MSG_INFO_QUESTION_EXITAPP, MSG_TITLE_QUESTION_WARNING,
				dialogButton);
        if(dialogButton == JOptionPane.YES_OPTION) {
            System.exit(0);
          }
		
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
	
	/*
	 * 
	 */
	public String getFinalSqlQuery() {
		
		//
		// Get original SqlQuery ...
		//
		String sFinalSqlQuery = textAreaSqlQuery.getText();
		
		//
		// Replace SqlParams for literal values ...
		//
		String sSqlParam = new String("");
		String sSqlParamValue = new String("");
		for (int i = 0; i < tableModelSqlParams.getRowCount(); i++) {
			sSqlParam = (String)tableModelSqlParams.getValueAt(i, 3);
			sSqlParamValue = (String)tableModelSqlParams.getValueAt(i, 1);
			if (!sSqlParam.equals("")) {
				sFinalSqlQuery = sFinalSqlQuery.replace(sSqlParam, sSqlParamValue);
			}
		}
		
		//
		// Return ...
		//
		return sFinalSqlQuery;
	}
	
	@SuppressWarnings("deprecation")
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
		String fieldNameFailure = new String("");
		String sFinalSqlQuery = new String("");
		boolean bMessageSqlQuery = false;
		
		try {
			
			//
			// Step #2: Open new .xls file  ...
			//
			statusMessageAddMsg("Step #2: Open new .xls file ...");
			fieldNameFailure = new String("txtExportFile");
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
			fieldNameFailure = new String("txtClassname");
			Class.forName(txtClassname.getText());
			statusMessageAddMsg("  - DriverManager.getConnection(" + txtDatabaseUrl.getText() + "," + txtUsername.getText() + ", ********" + ")" );
			fieldNameFailure = new String("txtDatabaseUrl,txtUsername,pwdPassword");
			conn = DriverManager.getConnection(txtDatabaseUrl.getText(),txtUsername.getText(),pwdPassword.getText());
			int rowCount = 0;

			//
			// Step #4: Prepare and execute Query Statement ...
			//
			statusMessageAddMsg("Step #4: Prepare and execute Query Statement ..." );
			statusMessageAddMsg("  - conn.prepareStatement()" );
			fieldNameFailure = new String("textAreaSqlQuery");
			sFinalSqlQuery = getFinalSqlQuery();
			bMessageSqlQuery = true;
			prepStmt = conn.prepareStatement(sFinalSqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			statusMessageAddMsg("  - prepStmt.executeQuery()" );
			ResultSet rs = prepStmt.executeQuery();
			bMessageSqlQuery = false;
			statusMessageAddMsg("  - rs.getMetaData()" );
			ResultSetMetaData rsmd = rs.getMetaData();
			fieldNameFailure = new String("textAreaSqlQuery");
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
			boolean bAbortResultSet = false;
			while (rs.next() && !bAbortResultSet) {
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
				statusSetProgress(rowCurrent/rowCount*100);
				
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
				} else {
					//
					// Excel limit exceeded, so abort result set
					//
					bAbortResultSet = true;
					statusMessageAddMsg(MSG_WARN_EXCEL_LIMIT_EXCEEDED.replace("%s", String.valueOf(WORKSHEET_MAX_ROW)));
					JOptionPane.showMessageDialog(getParent(),
							MSG_WARN_EXCEL_LIMIT_EXCEEDED.replace("%s", String.valueOf(WORKSHEET_MAX_ROW)), MSG_TITLE_WARN_WARNING,
							JOptionPane.ERROR_MESSAGE);
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
					MSG_INFO_EXECUTION_SUCCESS, MSG_TITLE_INFO_WARNING,
					JOptionPane.INFORMATION_MESSAGE);
			
			//
			// Step #9: Open file?
			//
			int dialogButton = JOptionPane.YES_NO_OPTION;
			dialogButton = JOptionPane.showConfirmDialog(getParent(),
					MSG_INFO_QUESTION_OPENFILE.replaceAll("%s", txtExportFile.getText()), MSG_TITLE_QUESTION_WARNING,
					dialogButton);
	        if(dialogButton == JOptionPane.YES_OPTION) {
	        	Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + txtExportFile.getText());
	          }
			

		} catch (Exception e) {
			//
			// Message Dialog Box ...
			//
			JOptionPane.showMessageDialog(getParent(),
					MSG_WARN_EXECUTION_FAILED.replace("%s", e.toString()), MSG_TITLE_CRITICAL_WARNING,
					JOptionPane.ERROR_MESSAGE);
			//
			// Log error message ...
			//
			e.printStackTrace();
			statusMessageAddMsg(e.toString());
			if (bMessageSqlQuery) {
				statusMessageAddMsg(sFinalSqlQuery);
			}
			
			//
			// Color red fieldName on Failure ...
			//
			switch (fieldNameFailure) {
			case "btnExportFile":
				btnExportFile.setForeground(Color.red);
				break;
			case "txtClassname":
				lblClassName.setForeground(Color.red);
				break;
			case "txtDatabaseUrl,txtUsername,pwdPassword":
				lblDatabaseUrl.setForeground(Color.red);
				lblUsername.setForeground(Color.red);
				lblPassword.setForeground(Color.red);
				break;
			case "textAreaSqlQuery":
				lblSqlQuery.setForeground(Color.red);
		}
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
					MSG_WARN_NOTFOUND.replace("%s", filename), MSG_TITLE_WARN_WARNING,
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
		}
		if ( !(mapArgs.get(ARGS_COMMAND_LINE_PARAM_PASSWORD)==null) ) {
			pwdPassword.setText(mapArgs.get(ARGS_COMMAND_LINE_PARAM_PASSWORD));
		}
		if ( !(mapArgs.get(ARGS_COMMAND_LINE_PARAM_SQLFILENAME)==null) ) {
			txtSqlFilename.setText(mapArgs.get(ARGS_COMMAND_LINE_PARAM_SQLFILENAME));
			textAreaSqlQuery.setText(getFileContents(txtSqlFilename.getText()));
			getSqlParamsFromSqlQuery(textAreaSqlQuery.getText());
		}
		if ( !(mapArgs.get(ARGS_COMMAND_LINE_PARAM_EXPORTFILENAME)==null) ) {
			txtExportFile.setText(mapArgs.get(ARGS_COMMAND_LINE_PARAM_EXPORTFILENAME));
		}
		
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
		if (n >= 0 && n <=100 ) {
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
			case "txtClassname":
				lblClassName.setForeground(Color.BLACK);
				break;
			case "txtDatabaseUrl":
				lblDatabaseUrl.setForeground(Color.BLACK);
				lblUsername.setForeground(Color.BLACK);
				lblPassword.setForeground(Color.BLACK);
				break;
			case "txtUsername":
				lblUsername.setForeground(Color.BLACK);
				break;
			case "pwdPassword":
				lblPassword.setForeground(Color.BLACK);
				break;
			case "textAreaSqlQuery":
				lblSqlQuery.setForeground(Color.BLACK);
			case "btnExportFile":
				btnExportFile.setForeground(Color.BLACK);
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
		// Initialize table columns ...
		//
		tableSqlParams.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableSqlParams.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableSqlParams.getColumnModel().getColumn(2).setPreferredWidth(150);
		tableSqlParams.getColumnModel().getColumn(3).setMinWidth(0);
		tableSqlParams.getColumnModel().getColumn(3).setMaxWidth(0);
		tableSqlParams.getColumnModel().getColumn(3).setWidth(0);
		tableSqlParams.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		
		//
		// Initialize UI fields ...
		//
		initUIFieldsFromArgs();
		
	}
	
	/*
	 * checkUserEntries()
	 */
	private boolean checkUserEntries() {
		//
		// Check JdbcClass, DatabaseUrl, Username and Password 
		//
		if (txtClassname.getText().equals("")) {
			JOptionPane.showMessageDialog(getParent(),
					MSG_CHECK_CLASSNAME_MISSING, MSG_TITLE_CRITICAL_WARNING,
					JOptionPane.ERROR_MESSAGE);
			lblClassName.setForeground(Color.red);
			return false;
		}
		if (txtDatabaseUrl.getText().equals("")) {
			JOptionPane.showMessageDialog(getParent(),
					MSG_CHECK_DATABASEURL_MISSING, MSG_TITLE_CRITICAL_WARNING,
					JOptionPane.ERROR_MESSAGE);
			lblDatabaseUrl.setForeground(Color.red);
			return false;
		}
		if (txtUsername.getText().equals("")) {
			JOptionPane.showMessageDialog(getParent(),
					MSG_CHECK_USERNAME_MISSING, MSG_TITLE_CRITICAL_WARNING,
					JOptionPane.ERROR_MESSAGE);
			lblUsername.setForeground(Color.red);
			return false;
		}
		if (pwdPassword.getText().equals("")) {
			JOptionPane.showMessageDialog(getParent(),
					MSG_CHECK_PASSWORD_MISSING, MSG_TITLE_CRITICAL_WARNING,
					JOptionPane.ERROR_MESSAGE);
			lblPassword.setForeground(Color.red);
			return false;
		}
		if (textAreaSqlQuery.getText().equals("")) {
			JOptionPane.showMessageDialog(getParent(),
					MSG_CHECK_SQLQUERY_MISSING, MSG_TITLE_CRITICAL_WARNING,
					JOptionPane.ERROR_MESSAGE);
			lblSqlQuery.setForeground(Color.red);
			return false;
		}
		if (txtExportFile.getText().equals("")) {
			JOptionPane.showMessageDialog(getParent(),
					MSG_CHECK_EXPORTFILE_MISSING, MSG_TITLE_CRITICAL_WARNING,
					JOptionPane.ERROR_MESSAGE);
			btnExportFile.setForeground(Color.red);
			return false;
		}
		
		//
		// Return required ...
		//
		return true;
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
		
		//
		// Cursor waiting ...
		//
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
	}
	
	/**
	 * unlockUserEntries()
	 */
	private void unlockUserEntries()  {
		//
		// Disable all user entries during processing ...
		//
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
		
		//
		// Cursor waiting ...
		//
		this.setCursor(Cursor.getDefaultCursor());
		
	}
}
