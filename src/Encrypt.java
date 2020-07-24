import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Encrypt extends JFrame {

	private JPanel contentPane;
	JPasswordField pw;
	JTextField t1,t2;
	JButton b1,b2,b3,ok;
	File textfile,imagefile;
	static final int N=50;
	RSA rsa=new RSA();
	JLabel l3;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Encrypt frame = new Encrypt();
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
	public Encrypt() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 634, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Select the text File");
		lblNewLabel.setBounds(40, 57, 109, 24);
		contentPane.add(lblNewLabel);
		
		JLabel lblSelectAnImage = new JLabel("Select an Image");
		lblSelectAnImage.setBounds(40, 132, 109, 24);
		contentPane.add(lblSelectAnImage);
		
		t1 = new JTextField();
		t1.setBounds(202, 59, 204, 24);
		contentPane.add(t1);
		t1.setColumns(10);
		
		t2 = new JTextField();
		t2.setColumns(10);
		t2.setBounds(202, 134, 204, 24);
		contentPane.add(t2);
		
		JButton b1 = new JButton("Choose");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
					JFileChooser file_chooser=new JFileChooser();
					int v=file_chooser.showOpenDialog(Encrypt.this);
		FileNameExtensionFilter filter=new FileNameExtensionFilter("Text File","txt");
					file_chooser.setFileFilter(filter);
					if(v==JFileChooser.APPROVE_OPTION){
						textfile=file_chooser.getSelectedFile();
						String abspath=textfile.getAbsolutePath();
						t1.setText(abspath);
					}	
					
				
			
			}
		});
		b1.setBounds(444, 58, 89, 23);
		contentPane.add(b1);
		
		JButton b2 = new JButton("Choose");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
					JFileChooser file_chooser=new JFileChooser();
					int v=file_chooser.showSaveDialog(Encrypt.this);
					FileNameExtensionFilter filter=new FileNameExtensionFilter("JPG & GIF","jpg","gif","png");
					file_chooser.setFileFilter(filter);
					if(v==JFileChooser.APPROVE_OPTION){
					imagefile=file_chooser.getSelectedFile();
					String abspath=imagefile.getAbsolutePath();
					t2.setText(abspath);
						}
					
			}
		});
		b2.setBounds(444, 135, 89, 23);
		contentPane.add(b2);
		
		JButton b3 = new JButton("Encrypt");
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
					l3.setVisible(true);
					ok.setVisible(true);
					pw.setVisible(true);
					b3.setEnabled(false);
					ok.setEnabled(true);
				
			}
		});
		b3.setBounds(202, 257, 89, 23);
		contentPane.add(b3);
		
		l3 = new JLabel("Enter Password");
		l3.setBounds(40, 211, 109, 23);
		contentPane.add(l3);
		l3.setVisible(false);
		
		pw = new JPasswordField();
		pw.setBounds(202, 212, 204, 20);
		contentPane.add(pw);
		pw.setVisible(false);
		
		ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				try{
					
					String text="";
					FileInputStream fis=new FileInputStream(textfile);
					int i=0;
					while((i=fis.read())!=-1){
							text=text+(char)i;
					}
					System.out.println("Actual Message: "+text);

					/*--- dividing the text into N/8 i.e 6 bytes each and encrypting each chunk -----*/
					String enc_msg="";
					int max=N/8;
					for(i=0;i<text.length();i=i+max){
							if(text.length()-i>max){
								int j=i+max;
						String sub=text.substring(i,j);
						byte b[]=sub.getBytes();
						BigInteger bin=new BigInteger(b);
						BigInteger enc=rsa.encrypt(bin);
						String enc_s=enc.toString();
						enc_msg=enc_msg+enc_s+"#";
								
								System.out.println(sub+"->"+enc_s);
							}
							else{
								String sub=text.substring(i);
								byte b[]=sub.getBytes();
								BigInteger bin=new BigInteger(b);
								BigInteger enc=rsa.encrypt(bin);
								String enc_s=enc.toString();
								enc_msg=enc_msg+enc_s+"#";
								
								System.out.println(sub+"->"+enc_s);
							}
					}
					System.out.println("Encrypted Message: "+enc_msg);
					

	String pwd=pw.getText();
	if(pwd.length()>=4 && pwd.length()<=6){
	byte bb[]=pwd.getBytes();
	BigInteger pass=new BigInteger(bb);
	BigInteger encpwd=rsa.encrypt(pass);
	enc_msg=encpwd+"#"+enc_msg+rsa.getModulus()+"#"+rsa.getPrivateKey();
	System.out.println("Encrypted Message with password and key: "+enc_msg);
		
	enc_msg=" "+enc_msg;
										
	FileOutputStream out=new FileOutputStream(imagefile,true);
	byte ascii[]=enc_msg.getBytes();
	out.write(ascii);
						
	JOptionPane.showMessageDialog(null,"Message has been encrypted to "+imagefile.getName());
						
						out.close();	
						fis.close();
		
						ok.setEnabled(false);
					//	b4.setEnabled(true);
					}
					else
						JOptionPane.showMessageDialog(null,"Password must contain 4 to 6 chars");
				}
				catch(Exception ex){
					ex.printStackTrace();
				}

				

				
			}

			
		});
		ok.setBounds(444, 211, 89, 23);
		contentPane.add(ok);
		ok.setVisible(false);
	}
}
