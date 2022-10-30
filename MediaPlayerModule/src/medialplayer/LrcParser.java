package medialplayer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class LrcParser {
	class LrcInfo {
		 String title;//存储歌词的标题
		public String getTitle(){return title;}
		public void setTitle(String t){title=t;}
		 String singer;//存储歌手名称
		public String getSinger(){return singer;}
		public void setSinger(String s){singer=s;}
		 String album;//存储
		public String getAlbum(){return album;}
		public void setAlbum(String a){album=a;}
		 String by;//编者（指编辑LRC歌词的人）
		public String getBy(){return by;}
		public void setBy(String b){by=b;}
		 Map<Long, String> infos;//映射，实现歌与歌词一一对应的关系
		public Map<Long, String> getInfos(){return infos;}
		public void setInfos(Map<Long, String> i){infos=i;}
	}
	private LrcInfo lrcinfo = new LrcInfo();
	private long currentTime = 0;//存放临时时间
	 String currentContent = null;//存放临时歌词
	private Map<Long, String> maps = new HashMap<Long, String>();//用户保存所有的歌词和时间点信息间的映射关系的Map
	InputStream readLrcFile(String path) throws FileNotFoundException {
		File f = new File(path);//读取文件源
		InputStream ins = new FileInputStream(f);//建立连接
		return ins;
        }
	public LrcInfo parser(String path) throws Exception {
		InputStream in = readLrcFile(path);//调用上一个函数，来读取文件源
		lrcinfo = parser(in);
		return lrcinfo;
	}
	public LrcInfo parser(InputStream inputStream) throws IOException {
		InputStreamReader inr = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(inr);//每行每行读取
		String line = null;
		while ((line = reader.readLine()) != null)//判断读取的是不是为null
			parserLine(line);
		lrcinfo.setInfos(maps);
		return lrcinfo;
	}
	private void parserLine(String str) {
		if (str.startsWith("[ti:")) {//提取Irc文件的标题//因为Irc文件标题是以ti开头
			String title = str.substring(4, str.length() - 1);
			System.out.println("title--->" + title);//标题
			lrcinfo.setTitle(title);//调用方法获得标题
		}
		else if (str.startsWith("[ar:")) {//读取Irc的歌手，因为Irc文件歌手是以ar开头
			String singer = str.substring(4, str.length() - 1);
			System.out.println("singer--->" + singer);//
			lrcinfo.setSinger(singer);//调用方法获得歌手
		}
		else if (str.startsWith("[al:")) {//获得[al:专辑名
			String album = str.substring(4, str.length() - 1);
			System.out.println("album--->" + album);
			lrcinfo.setAlbum(album);//调用方法获得专辑名
		}
		else if (str.startsWith("[by:")) {//获得编者（指编辑LRC歌词的人）
			String by = str.substring(4, str.length() - 1);
			System.out.println("by--->" + by);
			lrcinfo.setBy(by);//调用方法获得编者
		}
		else {
			String reg = "\\[(\\d{2}:\\d{2}\\.\\d{2})\\]";//java正则表示
			//匹配【00：00：00】这种歌词时间格式的正则表达式
			Pattern pattern = Pattern.compile(reg);//判断reg字符串是否匹配此正则表达式。
			Matcher matcher = pattern.matcher(str);
			while (matcher.find())/**尝试查找与该模式匹配的输入序列的的下一个子序列。如果对就继承 **/{
				String msg = matcher.group();//用msg存储匹配成功返回的组
				int start = matcher.start();//用start存储返回先前匹配的起始位置的索引。
				int end = matcher.end();//用end存储返回最后匹配字符的索引加一.
				int groupCount = matcher.groupCount();
				for (int i = 0; i <= groupCount; i++) {
					String timeStr = matcher.group(i);
					if (i == 1) {
						currentTime = strToLong(timeStr);//调用函数存储时间
					}
				}
				String[] content = pattern.split(str);
				for (int i = 0; i < content.length; i++) {
					if (i == content.length - 1) {
						currentContent = content[i];//存储歌词
					}
				}
				maps.put(currentTime, currentContent);
				buju fd=new buju ();
				fd.lyrics.append("put---currentTime--->" + currentTime
						+ "----currentContent---->" + currentContent);
				//这个是在布局里面显示歌词的
				System.out.println("put---currentTime--->" + currentTime
				+ "----currentContent---->" + currentContent);
				//这个是在eclipse的控制台里展示歌词
			}
		}
	}
	private long strToLong(String timeStr) {//整个函数都是为了获得时间
		String[] s = timeStr.split(":");//将按照：这个分割存储
		int min = Integer.parseInt(s[0]);//把字符串s解析成有符号的int基本类型
		String[] ss = s[1].split("\\.");//匹配\\.将这个分隔存储
		int sec = Integer.parseInt(ss[0]);
		int mill= Integer.parseInt(ss[1]);
		return min * 60 * 1000 + sec * 1000 + mill * 10;//去掉小数点并乘以十
	}
}
	