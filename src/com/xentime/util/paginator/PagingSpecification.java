package com.xentime.util.paginator;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.lang.Math;

public class PagingSpecification implements PagingSpecificationInterface {

	private List<?> objectList;
	private Integer perPage;
	private Integer orphans;
	private Integer numPages;
	private Integer count; // stores the number of items across all pages
	private Boolean inLeadingRange = false;
	private Boolean inTrailingRange = false;
	private List<Integer> pageNumbers = new ArrayList<Integer>();
	private List<Integer> pageNumbersOutsideLeadingRange = new ArrayList<Integer>();
	private List<Integer> pageNumbersOutsideTrailingRange = new ArrayList<Integer>();

	private final Integer leadingPageRangeDisplayed = 5;
	private final Integer trailingPageRangeDisplayed = 5;
	private final Integer leadingPageRange = 8;
	private final Integer trailingPageRange = 8;
	private final Integer numPagesOutsideRange = 2;
	private final Integer adjacentPages = 4;

	public PagingSpecification(Integer count, Integer perPage, Integer orphans){
		this.count = count;
		this.perPage = perPage;
		this.orphans = orphans;
		setNumPages();
	}

	public Integer validateNumber(Integer number){
		if(number<1){
			throw new IllegalArgumentException("That page number is less than 1");
		}
		if(number>this.numPages && number != 1){
			throw new IllegalArgumentException("That page contains no results");
		}
		return number;
	}

	/*
	 * Returns a Page object for the given 1-based page number.
	 */
	public PageSpecification getPageSpecification(Integer pageNumber){
		pageNumber = validateNumber(pageNumber);

		if(getNumPages() <= (leadingPageRangeDisplayed + numPagesOutsideRange + 1)){
			inLeadingRange = true;
			inTrailingRange = true;

			setPageNumbers(getRange(1, getNumPages(), 1, getNumPages()));
		} else if(pageNumber <= leadingPageRange){
			inLeadingRange = true;

			setPageNumbers(getRange(1, leadingPageRangeDisplayed, 1, getNumPages()));

			ListIterator<Integer> iter = new IntegerRange(0, -numPagesOutsideRange+1, -1).iterator();
			while(iter.hasNext()){
				pageNumbersOutsideLeadingRange.add(iter.next()+getNumPages());
			}
		} else if(pageNumber > (getNumPages() - trailingPageRange)){
			inTrailingRange = true;
			setPageNumbers(getRange(getNumPages()-trailingPageRangeDisplayed+1, getNumPages(), 1, getNumPages()));
			pageNumbersOutsideTrailingRange = getRange(0, numPagesOutsideRange-1, 0, numPagesOutsideRange);
			for(int i=0;i<pageNumbersOutsideTrailingRange.size();i++){
				pageNumbersOutsideTrailingRange.set(i, pageNumbersOutsideTrailingRange.get(i)+1);
			}
		} else {
			setPageNumbers(getRange(pageNumber-adjacentPages, pageNumber+adjacentPages, 1, getNumPages()));

			ListIterator<Integer> iter = new IntegerRange(0, -numPagesOutsideRange+1, -1).iterator();
			while(iter.hasNext()){
				pageNumbersOutsideLeadingRange.add(iter.next()+getNumPages());
			}

			pageNumbersOutsideTrailingRange = getRange(0, numPagesOutsideRange, 0, numPagesOutsideRange);
			for(int i=0;i<pageNumbersOutsideTrailingRange.size();i++){
				pageNumbersOutsideTrailingRange.set(i, pageNumbersOutsideTrailingRange.get(i)+1);
			}

		}

		return new PageSpecification(pageNumber, this);
	}

	public List<?> getObjectList(){
		return this.objectList;
	}

	public void setObjectList(List<?> objectList){
		this.objectList = objectList;
	}

	public Integer getCount(){
		return this.count;
	}

	public void setCount(Integer count){
		this.count = count;
	}

	public Integer getNumPages(){
		return this.numPages;
	}
	
	public Boolean getInLeadingRange(){
		return this.inLeadingRange;
	}

	public Boolean getInTrailingRange(){
		return this.inTrailingRange;
	}
	
	public List<Integer> getPageNumbers(){
		return this.pageNumbers;
	}

	public void setPageNumbers(List<Integer> pageNumbers){
		this.pageNumbers = pageNumbers;
	}

	public List<Integer> getPageNumbersOutsideLeadingRange(){
		return pageNumbersOutsideLeadingRange;
	}

	public List<Integer> getPageNumbersOutsideTrailingRange(){
		return pageNumbersOutsideTrailingRange;
	}

	public void setNumPages(){
		if(getNumPages()==null){
			if(getCount()==0){
				this.numPages = 0;
			} else {
				Integer hits = (getCount() - getOrphans());
				if(hits < 1) hits = 1;
				this.numPages = ((Double)Math.ceil(hits/(float)getPerPage())).intValue();
			}
		}
	}

	public List<Integer> getPageRange(){
		Integer start = 1;
		Integer stop = getNumPages() + 1;
		List<Integer> result = new ArrayList<Integer>();

		for(int i=0;i<stop-start;i++) result.add(i, start+i);

		return result;
	}

	public Integer getPerPage(){
		return perPage;
	}

	public Integer getOrphans(){
		return this.orphans;
	}

	public List<Integer> getRange(Integer start, Integer stop, Integer min, Integer max){
		ListIterator<Integer> iter = new IntegerRange(start, stop).iterator();
		List<Integer> result = new ArrayList<Integer>();
		while(iter.hasNext()){
			Integer i = iter.next();
			if(i<=max&&i>=min) result.add(i);
		}
		return result;
	}
	
}
