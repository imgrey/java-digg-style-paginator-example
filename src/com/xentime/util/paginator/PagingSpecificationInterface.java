package com.xentime.util.paginator;

public interface PagingSpecificationInterface {

	PageSpecification getPageSpecification(Integer pageNumber);
	Integer getPerPage();
	Integer getOrphans();
	void setNumPages();

}
