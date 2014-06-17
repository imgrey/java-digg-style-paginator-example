package com.xentime.util.paginator;

public interface PageSpecificationInterface {

	Object getItem(Integer index);
	Integer length();
	Boolean getHasNext();
	Boolean getHasPrevious();
	Boolean hasOtherPages();
	Integer getNextPageNumber();
	Integer getPreviousPageNumber();
	Integer getStartIndex();
	Integer getEndIndex();
	Integer getPageNumber();
	Integer getOffset();
	Integer getLimit();
	PagingSpecification getPagingSpecification();

}