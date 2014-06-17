[#-- Inserts digg-style paginator --]
[#macro paginator pageSpec prefix ]
[#if pageSpec?has_content ]
[#if pageSpec.pagingSpecification.numPages > 1 ]
<div class="paginator">
  [#if pageSpec.hasPrevious ]
  <span class="prev"><a id="${prefix}page_${pageSpec.previousPageNumber}" href="#">&laquo; Previous</a></span>
  [#else]
  <span class="prev-na">&laquo; Previous</span>
  [/#if]

  [#if ! pageSpec.pagingSpecification.inLeadingRange ]
  [#list pageSpec.pagingSpecification.pageNumbersOutsideTrailingRange as num ]
    <span class="page"><a href="#" id="${prefix}page_${num}">${num}</a></span>
  [/#list]
  ...
  [/#if]

  [#list pageSpec.pagingSpecification.pageNumbers as num ]
    [#if num = pageSpec.pageNumber ]
      <span class="curr">${num}</span>
    [#else]
      <span class="page"><a href="#" id="${prefix}page_${num}">${num}</a></span>
    [/#if]
  [/#list]

  [#if ! pageSpec.pagingSpecification.inTrailingRange ]
    ...
    [#list pageSpec.pagingSpecification.pageNumbersOutsideLeadingRange?reverse as num ]
     <span class="page"><a href="#" id="${prefix}page_${num}">${num}</a></span>
    [/#list]
  [/#if]

  [#if pageSpec.hasNext ]
    <span class="next"><a href="#" title="Next Page" id="${prefix}page_${pageSpec.nextPageNumber}">Next &raquo;</a></span>
  [#else]
    <span class="next-na">Next &raquo;</span>
  [/#if]

</div>
[/#if]
[/#if]

[/#macro]