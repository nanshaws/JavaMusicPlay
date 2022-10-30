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
	int n=0;//避免点多次下一首会放多的
	int y=0;//同理
	LrcParser lp=new LrcParser();//这个是对象是歌词的
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
	JButton stop=new JButton("暂停");
	JButton New=new JButton("新增");
	JButton Remove=new JButton("移除");
	JButton allRemove=new JButton("全部移除");
	JButton Setup=new JButton("设置");
	JButton volume=new JButton("音量");
	JButton upone=new JButton("上一首");
	JButton andone=new JButton("下一首");
	JButton xiangxi=new JButton("详细");
	JMenuBar menubar=new JMenuBar();
	JMenu menu=new JMenu("文件");
	JMenuItem menuitem=new JMenuItem("打开");
	public static JTextArea lyrics=new JTextArea(9,30);	
	
		//2.使用多线程控制播放音乐,因为该类播放音乐时会阻塞，所以应该使用多线程来控制
	 void buju() {	
		 lyrics.setLineWrap(true);
		 lyrics.setWrapStyleWord(true);
		 JScrollPane jsp = new JScrollPane(lyrics);
		 f.setBounds(100, 100, 500, 600);
		 f.add(panel);
		 menubar.add(menu);
		 menu.add(menuitem);
		 f.setTitle("音乐播放器");
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
	        			System.out.println("sb");//不知道为什么没有用;
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
	                // 双击时处理
	            	if(e.getClickCount()==1) {
	            		filename = list.getSelectedItem();
	            		  hs=list.getItemCount()+1;
	            	}	            	
	                if (e.getClickCount() == 2) {	                	
	                    // 播放选中的文件
	                    //filename = list.getSelectedItem();	          	                    
						try {							
							player = new Player(new FileInputStream(new File(filepath + filename)));
							new Thread(()-> {
								try {
									//3.播放音乐
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
	            //labelfilepath.setText(" 播放目录： " + filepath);	 
	            // 显示文件列表
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
	System.out.println("录入成功");
	
}
}
