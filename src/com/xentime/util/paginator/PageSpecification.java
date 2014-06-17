package com.xentime.util.paginator;

public class PageSpecification implements PageSpecificationInterface {

	private final Integer pageNumber;
	private final PagingSpecification pagingSpecification;
	@SuppressWarnings("unused")
	private Boolean hasNext;
	@SuppressWarnings("unused")
	private Boolean hasPrevious;
	@SuppressWarnings("unused")
	private Integer previousPageNumber;
	@SuppressWarnings("unused")
	private Integer nextPageNumber;

	public PageSpecification(Integer pageNumber, PagingSpecification pagingSpecification) {
		this.pageNumber = pageNumber;
		this.pagingSpecification = pagingSpecification;
		this.hasNext = getHasNext();
		this.hasPrevious = getHasPrevious();
		this.previousPageNumber = getPreviousPageNumber();
		this.nextPageNumber = getNextPageNumber();
	}

	public Object getItem(Integer index){
		return getPagingSpecification().getObjectList().get(index);
	}

	public Integer length(){
		return getPagingSpecification().getCount();
	}

	public Boolean getHasNext(){
		return (this.pageNumber<this.pagingSpecification.getNumPages()); 
	}
	
	public Boolean getHasPrevious(){
		return (this.pageNumber > 1);
	}
	
	public Boolean hasOtherPages(){
		return (getHasPrevious()||getHasNext());
	}

	public Integer getNextPageNumber(){
		return this.pageNumber + 1;
	}

	public Integer getPreviousPageNumber(){
		return this.pageNumber - 1;
	}
	
	/*
	 * Returns the 1-based index of the first object on this page,
	 * relative to total objects in the paginator.
	*/
	public Integer getStartIndex(){
		if(this.pagingSpecification.getCount() == 0) return 0;
		return (getPagingSpecification().getPerPage() * (getPageNumber() - 1)) + 1;		
	}

	/*
	 * Returns the 1-based index of the last object on this page,
	 * relative to total objects found (hits).
	 */
	public Integer getEndIndex(){
		if(getPageNumber() == getPagingSpecification().getNumPages()) return getPagingSpecification().getCount();
		return (getPageNumber() + getPagingSpecification().getPerPage());
	}

	public PagingSpecification getPagingSpecification(){
		return this.pagingSpecification;
	}

	public Integer getPageNumber(){
		return this.pageNumber;
	}

	public Integer getOffset(){
		return (getPageNumber() - 1) * getPagingSpecification().getPerPage();
	}
	
	public Integer getLimit(){
		PagingSpecification pagingSpec = getPagingSpecification();
		Integer top = getOffset() + pagingSpec.getPerPage();
		if((top + pagingSpec.getOrphans()) >= pagingSpec.getCount()){
			top = pagingSpec.getCount();
		}

		return getPagingSpecification().getPerPage();
	}

	@Override
	public int hashCode(){
		return this.pageNumber.hashCode();
	}
	
    @Override
    public boolean equals(Object o){
            if(o instanceof PageSpecification){
                    return pageNumber.equals(((PageSpecification) o).pageNumber);
            }
            return false;
    }

}