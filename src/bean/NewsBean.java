package bean;

import java.util.ArrayList;

public class NewsBean {
	public Result result;

	public class Result {
		public ArrayList<NewsData> data;
	}

	public class NewsData {
		public String author_name;
		public String date;
		public String thumbnail_pic_s;
		public String title;
		public String url;
		public String uniquekey;
		@Override
		public String toString() {
			return "NewsData [author_name=" + author_name + ", date=" + date
					+ ", thumbnail_pic_s=" + thumbnail_pic_s + ", title="
					+ title + ", url=" + url + "]";
		}
		
	}
}
