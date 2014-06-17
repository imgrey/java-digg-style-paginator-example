function(data, prefix) {
			if (data.pagingSpecification.numPages > 1) {
				var paginator = '<div class="paginator">'
				if (data.hasPrevious) {
					paginator += '<span class="prev"><a id="'
							+ prefix
							+ 'page_'
							+ data.previousPageNumber
							+ '" href="#">&laquo; Previous</a></span>';
				} else {
					paginator += '<span class="prev-na">&laquo; Previous</span>';
				}
				if (!data.pagingSpecification.inLeadingRange) {
					for ( var i = 0; i != data.pagingSpecification.pageNumbersOutsideTrailingRange.length; i++) {
						paginator += '<span class="page"><a id="' + prefix
								+ 'page_'
								+ data.pagingSpecification.pageNumbersOutsideTrailingRange[i]
								+ '" href="#">'
								+ data.pagingSpecification.pageNumbersOutsideTrailingRange[i]
								+ '</a></span>';
					}
					paginator += '...';
				}
				for ( var i = 0; i != data.pagingSpecification.pageNumbers.length; i++) {
					if (data.pagingSpecification.pageNumbers[i] == data.pageNumber) {
						paginator += '<span class="curr">'
								+ data.pagingSpecification.pageNumbers[i] + '</span>';
					} else {
						paginator += '<span class="page"><a id="' + prefix
								+ 'page_' + data.pagingSpecification.pageNumbers[i]
								+ '" href="#">' + data.pagingSpecification.pageNumbers[i]
								+ '</a></span>';
					}
				}
				if (!data.pagingSpecification.inTrailingRange) {
					paginator += '...';
					for ( var i = data.pagingSpecification.pageNumbersOutsideLeadingRange.length - 1; i != -1; i--) {
						paginator += '<span class="page"><a id="' + prefix
								+ 'page_' + data.pagingSpecification.pageNumbersOutsideLeadingRange[i]
								+ '" href="#" >'
								+ data.pagingSpecification.pageNumbersOutsideLeadingRange[i]
								+ '</a></span>';
					}
				}
				if (data.hasNext) {
					paginator += '<span class="next"><a id="' + prefix
							+ 'page_' + data.nextPageNumber
							+ '" href="#">Next &raquo;</a></span>';
				} else {
					paginator += '<span class="next-na">Next &raquo;</span>';
				}
				paginator += '</div>';
				return paginator;
			}
		}
