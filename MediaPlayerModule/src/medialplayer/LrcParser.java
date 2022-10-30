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
		 String title;//�洢��ʵı���
		public String getTitle(){return title;}
		public void setTitle(String t){title=t;}
		 String singer;//�洢��������
		public String getSinger(){return singer;}
		public void setSinger(String s){singer=s;}
		 String album;//�洢
		public String getAlbum(){return album;}
		public void setAlbum(String a){album=a;}
		 String by;//���ߣ�ָ�༭LRC��ʵ��ˣ�
		public String getBy(){return by;}
		public void setBy(String b){by=b;}
		 Map<Long, String> infos;//ӳ�䣬ʵ�ָ�����һһ��Ӧ�Ĺ�ϵ
		public Map<Long, String> getInfos(){return infos;}
		public void setInfos(Map<Long, String> i){infos=i;}
	}
	private LrcInfo lrcinfo = new LrcInfo();
	private long currentTime = 0;//�����ʱʱ��
	 String currentContent = null;//�����ʱ���
	private Map<Long, String> maps = new HashMap<Long, String>();//�û��������еĸ�ʺ�ʱ�����Ϣ���ӳ���ϵ��Map
	InputStream readLrcFile(String path) throws FileNotFoundException {
		File f = new File(path);//��ȡ�ļ�Դ
		InputStream ins = new FileInputStream(f);//��������
		return ins;
        }
	public LrcInfo parser(String path) throws Exception {
		InputStream in = readLrcFile(path);//������һ������������ȡ�ļ�Դ
		lrcinfo = parser(in);
		return lrcinfo;
	}
	public LrcInfo parser(InputStream inputStream) throws IOException {
		InputStreamReader inr = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(inr);//ÿ��ÿ�ж�ȡ
		String line = null;
		while ((line = reader.readLine()) != null)//�ж϶�ȡ���ǲ���Ϊnull
			parserLine(line);
		lrcinfo.setInfos(maps);
		return lrcinfo;
	}
	private void parserLine(String str) {
		if (str.startsWith("[ti:")) {//��ȡIrc�ļ��ı���//��ΪIrc�ļ���������ti��ͷ
			String title = str.substring(4, str.length() - 1);
			System.out.println("title--->" + title);//����
			lrcinfo.setTitle(title);//���÷�����ñ���
		}
		else if (str.startsWith("[ar:")) {//��ȡIrc�ĸ��֣���ΪIrc�ļ���������ar��ͷ
			String singer = str.substring(4, str.length() - 1);
			System.out.println("singer--->" + singer);//
			lrcinfo.setSinger(singer);//���÷�����ø���
		}
		else if (str.startsWith("[al:")) {//���[al:ר����
			String album = str.substring(4, str.length() - 1);
			System.out.println("album--->" + album);
			lrcinfo.setAlbum(album);//���÷������ר����
		}
		else if (str.startsWith("[by:")) {//��ñ��ߣ�ָ�༭LRC��ʵ��ˣ�
			String by = str.substring(4, str.length() - 1);
			System.out.println("by--->" + by);
			lrcinfo.setBy(by);//���÷�����ñ���
		}
		else {
			String reg = "\\[(\\d{2}:\\d{2}\\.\\d{2})\\]";//java�����ʾ
			//ƥ�䡾00��00��00�����ָ��ʱ���ʽ��������ʽ
			Pattern pattern = Pattern.compile(reg);//�ж�reg�ַ����Ƿ�ƥ���������ʽ��
			Matcher matcher = pattern.matcher(str);
			while (matcher.find())/**���Բ������ģʽƥ����������еĵ���һ�������С�����Ծͼ̳� **/{
				String msg = matcher.group();//��msg�洢ƥ��ɹ����ص���
				int start = matcher.start();//��start�洢������ǰƥ�����ʼλ�õ�������
				int end = matcher.end();//��end�洢�������ƥ���ַ���������һ.
				int groupCount = matcher.groupCount();
				for (int i = 0; i <= groupCount; i++) {
					String timeStr = matcher.group(i);
					if (i == 1) {
						currentTime = strToLong(timeStr);//���ú����洢ʱ��
					}
				}
				String[] content = pattern.split(str);
				for (int i = 0; i < content.length; i++) {
					if (i == content.length - 1) {
						currentContent = content[i];//�洢���
					}
				}
				maps.put(currentTime, currentContent);
				buju fd=new buju ();
				fd.lyrics.append("put---currentTime--->" + currentTime
						+ "----currentContent---->" + currentContent);
				//������ڲ���������ʾ��ʵ�
				System.out.println("put---currentTime--->" + currentTime
				+ "----currentContent---->" + currentContent);
				//�������eclipse�Ŀ���̨��չʾ���
			}
		}
	}
	private long strToLong(String timeStr) {//������������Ϊ�˻��ʱ��
		String[] s = timeStr.split(":");//�����գ�����ָ�洢
		int min = Integer.parseInt(s[0]);//���ַ���s�������з��ŵ�int��������
		String[] ss = s[1].split("\\.");//ƥ��\\.������ָ��洢
		int sec = Integer.parseInt(ss[0]);
		int mill= Integer.parseInt(ss[1]);
		return min * 60 * 1000 + sec * 1000 + mill * 10;//ȥ��С���㲢����ʮ
	}
}
	