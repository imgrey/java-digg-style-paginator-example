package com.xentime.controller;

import com.xentime.entities.ExampleEntity;
import com.xentime.util.paginator.IntegerRange;
import com.xentime.util.paginator.PageSpecification;
import com.xentime.util.paginator.PagingSpecification;
import com.xentime.util.paginator.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/")
public class ItemController {
	
	private static final Logger logger = Logger.getLogger(ItemController.class);

	private final Integer itemsPerPage = 10;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public PageSpecification getItems(
			@RequestParam(value = "page", required = false) Integer pageNumber,
			ModelMap model) {

		if(pageNumber==null) pageNumber=1;

		// How many objects will be created
		Integer itemsToGenerate = itemsPerPage*100;

		PagingSpecification pagingSpec = new PagingSpecification(itemsToGenerate, itemsPerPage, itemsPerPage/5);
		PageSpecification pageSpec;
		try{
			pageSpec = pagingSpec.getPageSpecification(pageNumber);
		} catch (IllegalArgumentException e){
			if(logger.isDebugEnabled()){
				logger.debug(String.format("getItems: %s", e.toString()));
			}
			throw new ResourceNotFoundException();
		}

		Integer offset = pageSpec.getOffset();
		Integer limit = pageSpec.getLimit();

		ListIterator<Integer> iter = new IntegerRange(offset, offset+limit, 1).iterator(); // DAO can be here
		List<ExampleEntity> objectList = new ArrayList<ExampleEntity>(); // the list of objects to assign to page spec
		while(iter.hasNext()){
			ExampleEntity entity = new ExampleEntity();
			entity.setId(iter.next());
			objectList.add(entity);
		}

		pagingSpec.setObjectList(objectList);

		return pageSpec;
	}
}
