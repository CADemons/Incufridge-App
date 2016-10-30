package swing;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import common.TCPClient;
import common.TextFileReader;


@SuppressWarnings("serial")
public class DataDisplayPanel extends JPanel {
	private JLabel tempLabel;
	private JButton updateButton;
	private JButton createLogButton;
	private JTextPane logPanel;
	private JScrollPane scroll;
	
	public DataDisplayPanel() {
		tempLabel = new JLabel("Temperature: ");
		updateButton = new JButton("Update");
		createLogButton = new JButton("Get log");
		logPanel = new JTextPane();
		logPanel.setPreferredSize(new Dimension(400, 300));
		scroll = new JScrollPane(logPanel);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		AL AL = new AL();
		updateButton.addActionListener(AL);
		createLogButton.addActionListener(AL);
		
		this.add(tempLabel);
		this.add(updateButton);
		this.add(createLogButton);
		this.add(scroll);
	}
	
	private class AL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == updateButton) {
//				if (Communicator.isConnected()) {
//					tempLabel.setText("Temperature: " + Communicator.getTemperature());
//				} else {
//					JOptionPane.showMessageDialog(null, "No connection");
//				}
				double temp = -1;
				try {
					temp = TCPClient.getTemp();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tempLabel.setText("Temperature: " + temp);
			} else if (e.getSource() == createLogButton) {
				try {
					TCPClient.getLog();
					logPanel.setText(TextFileReader.readEntireFile("log.txt"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
