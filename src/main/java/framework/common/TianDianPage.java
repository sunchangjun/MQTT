package framework.common;

public class TianDianPage {
	
	//记录总条数
	private  Long  total;
	
	//总页数
	private  Integer  pageCount;
	
	//当前页
	private Integer currentPage;
	
	//内容 
	private Object content;





	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}


	
	

}
