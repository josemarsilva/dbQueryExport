package dbQueryExport.josemarsilva.com.br;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ProgressJDialogUI extends JDialog {
	
	//
	// Class atributes ...
	//
	private boolean bCancel = false;

	private final JPanel contentPanel = new JPanel();
	private JProgressBar progressBar;
	private JLabel lblProgressTitle;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ProgressJDialogUI dialog = new ProgressJDialogUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ProgressJDialogUI() {
		setTitle("Progress");
		setBounds(100, 100, 300, 124);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		progressBar = new JProgressBar();
		lblProgressTitle = new JLabel(" ");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblProgressTitle, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblProgressTitle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(110, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
		
	/*
	 * 
	 */
	public boolean getCancel(){
		return bCancel;
	}
	
	/*
	 * 
	 */
	public void resetProgress() {
		setCancel(false);
		setProgressBar(0);
		setProgressTitle("");
	}
	
	/*
	 * 
	 */
	private void setCancel(boolean pCancel) {
		bCancel = pCancel;
	}
	
	/*
	 * 
	 */
	public void setProgressBar(int n) {
		if (n >= 0 && n <=100 ) {
			progressBar.setValue(n);
			progressBar.updateUI();
		}
	}
	
	/*
	 * 
	 */
	public void setProgressTitle(String pTitle) {
		lblProgressTitle.setText(pTitle);
	}

}
