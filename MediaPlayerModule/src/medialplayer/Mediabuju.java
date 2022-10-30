package medialplayer;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.List;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import medialplayer.LrcParser;
import javax.swing.*;
class buju extends JFrame implements ActionListener{ 
	int n=0;//���������һ�׻�Ŷ��
	int y=0;//ͬ��
	LrcParser lp=new LrcParser();//����Ƕ����Ǹ�ʵ�
public static	int  hs;
	Player player;
public static	String filename;
public static	String filepath;
	Label labelfilepath;
List list = new List(10);
List geci=new List(10);
	FlowLayout out=new  FlowLayout(0,50,10);
	JFrame f=new JFrame();
	JPanel panel=new JPanel();
	JButton stop=new JButton("��ͣ");
	JButton New=new JButton("����");
	JButton Remove=new JButton("�Ƴ�");
	JButton allRemove=new JButton("ȫ���Ƴ�");
	JButton Setup=new JButton("����");
	JButton volume=new JButton("����");
	JButton upone=new JButton("��һ��");
	JButton andone=new JButton("��һ��");
	JButton xiangxi=new JButton("��ϸ");
	JMenuBar menubar=new JMenuBar();
	JMenu menu=new JMenu("�ļ�");
	JMenuItem menuitem=new JMenuItem("��");
	public static JTextArea lyrics=new JTextArea(9,30);	
	
		//2.ʹ�ö��߳̿��Ʋ�������,��Ϊ���ಥ������ʱ������������Ӧ��ʹ�ö��߳�������
	 void buju() {	
		 lyrics.setLineWrap(true);
		 lyrics.setWrapStyleWord(true);
		 JScrollPane jsp = new JScrollPane(lyrics);
		 f.setBounds(100, 100, 500, 600);
		 f.add(panel);
		 menubar.add(menu);
		 menu.add(menuitem);
		 f.setTitle("���ֲ�����");
		 f.setJMenuBar(menubar);
		 f.setLayout(out);
		 stop.addActionListener(this);
		 Remove.addActionListener(this);
		 menuitem.addActionListener(this);
		 allRemove.addActionListener(this);
		 andone.addActionListener(this);
		 upone.addActionListener(this);
		 f.add(jsp);
		 f.add(stop);
		 f.add(andone);
		 f.add(upone);
		 f.add(Remove);
		 f.add(allRemove);
		 f.add(geci,"left");
		 f.add(list, "Center");
		 f.setVisible(true);
		 f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	 }
	  void open() {
	        FileDialog dialog = new FileDialog(this, "Open", 0);
	        dialog.setVisible(true);	        
	        filepath  = dialog.getDirectory();
	        menuitem.addMouseListener(new MouseAdapter() {
	        	public void mouseClicked(MouseEvent e) {
	        		if(e.getSource()==menuitem) {
	        			System.out.println("sb");//��֪��Ϊʲôû����;
	        	        dialog.setVisible(true);	        
	        	        //filepath  = dialog.getDirectory();
	        		}
	        	}
	        });
	       geci.addMouseListener(new  MouseAdapter() {
	    	   public void mouseClicked(MouseEvent e) {
	    		   if(e.getClickCount()==2) {
	            		filename = geci.getSelectedItem();	            		
	            		try {
	            			lp.readLrcFile(filepath+filename);
							lp.parser(filepath+filename);
							
							//lyrics.append(lp.currentContent);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
	    		   }
	    	   }
	       });
	       upone.addMouseListener(new MouseAdapter() {
	    	   public void mouseClicked(MouseEvent e) {
	    		   if(e.getClickCount()==1) {
	    			   hs=list.getSelectedIndex();
	    			   filename=list.getItem(hs-1);
	    		   }
	    		   if(n!=1) {	    			   
	    		   if(e.getClickCount()==2) {
	    			   n=1;
	    			   try {							
							player = new Player(new FileInputStream(new File(filepath + filename)));
							new Thread(()-> {
								try {
									Thread.sleep(100);
									player.play();				
								} catch (JavaLayerException h) {
									h.printStackTrace();
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}).start();
						} catch (FileNotFoundException | JavaLayerException e1) {
							
							e1.printStackTrace();
						}  
	    		   }
	    		   }
	    	   }
	       });	       
	       andone.addMouseListener(new MouseAdapter() {
	    	   public void mouseClicked(MouseEvent e) {
	    		   if(e.getClickCount()==1) {
	    			   hs=list.getSelectedIndex();
	    			   filename=list.getItem(hs+1);
	    		   }
	    		   if(y!=1) {
	    		   if(e.getClickCount()==2) {
	    			   y=1;
	    			   try {							
							player = new Player(new FileInputStream(new File(filepath + filename)));
							new Thread(()-> {
								try {
									Thread.sleep(100);									
									player.play();				
								} catch (Exception h) {
									h.printStackTrace();
								}
							}).start();
						} catch (FileNotFoundException | JavaLayerException e1) {
							
							e1.printStackTrace();
						}  
	    		   }
	    	   }
	    	   }
	       });
	        list.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {	            	
	                // ˫��ʱ����
	            	if(e.getClickCount()==1) {
	            		filename = list.getSelectedItem();
	            		  hs=list.getItemCount()+1;
	            	}	            	
	                if (e.getClickCount() == 2) {	                	
	                    // ����ѡ�е��ļ�
	                    //filename = list.getSelectedItem();	          	                    
						try {							
							player = new Player(new FileInputStream(new File(filepath + filename)));
							new Thread(()-> {
								try {
									//3.��������
									player.play();
								} catch (JavaLayerException h) {
									h.printStackTrace();
								} 
							}).start();
						} catch (FileNotFoundException | JavaLayerException e1) {
							
							e1.printStackTrace();
						} 
	                }
	            }
	        });
	        if (filepath != null) {
	            //labelfilepath.setText(" ����Ŀ¼�� " + filepath);	 
	            // ��ʾ�ļ��б�
	            list.removeAll();
	            geci.removeAll();
	            File filedir = new File(filepath);
	            File[] filelist = filedir.listFiles();	     
	            for (File file : filelist) {
	            	String filename = file.getName().toLowerCase();
	                if (filename.endsWith(".mp3") || filename.endsWith(".wav")) {
	                    list.add(filename);		                 
	                }
	               if(filename.endsWith(".lrc")) {
	            	   geci.add(filename);
	               }
	            }
	        }
	    }
	public void actionPerformed(ActionEvent e) {
		        if(e.getSource()==Remove) {
		        	list.remove(filename);
		        	
		        }		   
		        if(e.getSource()==allRemove) {
		        	geci.removeAll();
		        	list.removeAll();
		        }
		         if(e.getSource()==stop) {
		        try {
		        	n=0;
		        	y=0;
					Thread.sleep(100);
					player.close();					
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
		         }
	}	 
} 
public class Mediabuju {
public static void main(String args[]) throws Exception {
	buju b=new buju();
	b.buju();
	b.open();
	System.out.println("¼��ɹ�");
	
}
}
