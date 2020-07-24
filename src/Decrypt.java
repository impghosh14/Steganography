import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.RandomAccess;
import java.awt.event.ActionEvent;

public class Decrypt extends JFrame {

	private JPanel contentPane;
	private JTextField t1;
	private JTextField t2;
	private JPasswordField pw;
	private JButton b1,b2,b3;
	File textfile,imagefile;
	static final int N=50;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Decrypt frame = new Decrypt();
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
	public Decrypt() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 639, 374);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Select the image file");
		lblNewLabel.setBounds(49, 56, 141, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Select a text file");
		lblNewLabel_1.setBounds(49, 121, 147, 17);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setBounds(49, 184, 154, 14);
		contentPane.add(lblNewLabel_2);
		
		t1 = new JTextField();
		t1.setBounds(225, 54, 197, 23);
		contentPane.add(t1);
		t1.setColumns(10);
		
		t2 = new JTextField();
		t2.setColumns(10);
		t2.setBounds(225, 118, 197, 20);
		contentPane.add(t2);
		
		pw = new JPasswordField();
		pw.setBounds(228, 181, 194, 23);
		contentPane.add(pw);
		
		 b3 = new JButton("Decrypt");
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object ob=e.getSource();
				if(ob==b3)
				{
				try
				{
				int index=0,cnt=0;
				RandomAccessFile file=new RandomAccessFile(imagefile, "r");
				int i=0;
				while((i=file.read())!=-1)
				{
					cnt++;
					if(i==32)
						index=cnt;
					
				}
				file.seek(index);
				
				String str="";
				while((i=file.read())!=-1)
				{
					str=str+(char)i;
					
				}
				System.out.println(str);
				str=str.trim();
				String msg[]=str.split("#");
				String en=msg[0];
				String modulus=msg[msg.length-2];
				String pk=msg[msg.length-1];
				System.out.println("Enc Password"+en);
				System.out.println("Modulus"+modulus);
				System.out.println("Private Key"+pk);
				BigInteger p=new BigInteger(en);
				BigInteger m=new BigInteger(modulus);
				BigInteger pk2=new BigInteger(pk);
				BigInteger pwd=p.modPow(pk2, m);
				byte b[]=pwd.toByteArray();
				String pass=new String(b);
				String passwd=pw.getText();
				if(pass.equals(pass))
				{
					String original="";
					for(i=1;i<=msg.length-3;i++)
					{
						BigInteger em=new BigInteger(msg[i]);
						BigInteger dm=em.modPow(pk2, m);
						byte b1[]=dm.toByteArray();
						String om=new String(b1);
						original=original+om;
						
					}
					//System.out.println("Message Decrypted");
					FileOutputStream out=new FileOutputStream(textfile);
					out.write(original.getBytes());
					JOptionPane.showMessageDialog(null, "Message decrypted");
					out.close();
				}else
				{
			JOptionPane.showMessageDialog(null, "Invalid password");
					file.close();	
				}
				
				
				}catch (Exception e1) {
				e1.printStackTrace();
				}
				}
			}
		});
		b3.setBounds(203, 245, 89, 23);
		contentPane.add(b3);
		
		 b1 = new JButton("Choose");
		 b1.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		Object ob=e.getSource();
				if(ob==b1)
				{
				JFileChooser file_chooser=new JFileChooser();
				int v=file_chooser.showOpenDialog(Decrypt.this);
				FileNameExtensionFilter filter=new FileNameExtensionFilter("Image File","jpg","png");
				file_chooser.setFileFilter(filter);
				if(v==JFileChooser.APPROVE_OPTION)
				{
				imagefile=file_chooser.getSelectedFile();
				String abspath=imagefile.getAbsolutePath();
				t1.setText(abspath);
				}
				}
		 	}
		 });
		b1.setBounds(457, 52, 89, 23);
		contentPane.add(b1);
		
		 b2 = new JButton("Choose");
		 b2.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		Object ob=e.getSource();
				if(ob==b2)
				{
				JFileChooser file_chooser=new JFileChooser();
				int v=file_chooser.showOpenDialog(Decrypt.this);
				FileNameExtensionFilter filter=new FileNameExtensionFilter("Image File","txt");
				file_chooser.setFileFilter(filter);
				if(v==JFileChooser.APPROVE_OPTION)
				{
				textfile=file_chooser.getSelectedFile();
				String abspath=textfile.getAbsolutePath();
				t2.setText(abspath);
				}
				}
		 	}
		 });
		b2.setBounds(457, 118, 89, 23);
		contentPane.add(b2);
	}
}
