<?

function showSelected($variable, $value) {
	if ($variable == $value) {
		echo "selected";
	}
}

function showRating($rating) {
	
	if ($rating == 1)
	    echo "<img src=\"/graphics/heart1.gif\" width=\"32\" height=\"32\">";
    else if ($rating == 0)
		echo "<img src=\"/graphics/heart2.gif\" width=\"32\" height=\"32\">";
	else if ($rating == -1)
		echo "<img src=\"/graphics/heart3.gif\" width=\"32\" height=\"32\">";
}

function printDataRow($rowLabelId, $compareList, $planIdList, $isCenter=true) {
	
	for ($i=0; $i < sizeof($planIdList); $i++) {
		foreach ($compareList as $row)
		{  
				if ($planIdList[$i] == $row->plan_id) {
					if ($isCenter)
						echo "<td width=\"10%\" valign=\"top\"><center>".nl2br($row->$rowLabelId)."</center></td>";
					else						
						echo "<td width=\"10%\" valign=\"top\">".nl2br($row->$rowLabelId)."</td>";						
				}
		}
	}
}

function printDataItemRow($rowLabelId, $compareList, $planIdList, $is_qty) {

	for ($i=0; $i < sizeof($planIdList); $i++) {
		$isOut = false;
		foreach ($compareList as $row)
		{   

			if ($rowLabelId == $row->item_id) {
	
				if ($planIdList[$i] == $row->plan_id) {
					if ($is_qty == 'Y' && $row->qty <> "")
						echo "<td width=\"10%\" valign=\"center\"><center>".($row->qty)."</center></td>";
					else if ($is_qty == 'Y'  && $row->qty == "")
						echo "<td width=\"10%\" valign=\"center\"><center>N/A</center></td>";						
					else if ($is_qty <> 'Y' && $row->qty > 0)
						echo "<td width=\"10%\" valign=\"center\"><center><img src=\"/graphics/have.jpg\"></center></td>";
		        	else if ($is_qty <> 'Y' && $row->qty == 0)
						echo "<td width=\"10%\" valign=\"center\"><center><img src=\"/graphics/nothave.jpg\"></center></center></td>";			        	

					$isOut = true;
				}
			}
	
		}
		if (!$isOut) {
			echo "<td width=\"10%\" valign=\"center\"><center><img src=\"/graphics/nothave.jpg\"></center></td>";
		}
	}


}

function pageForwarding($url) {
	$host = $_SERVER['HTTP_HOST'];
	header("Location: http://$host/$url");
}


function showPostMethodPagation($link, $currentPage, $pageCount, $parameter) {
	
	if ($pageCount > 1) {
		?>
		  <div class="wp-pagenavi">

			<? 
			 if ($currentPage != 1) {
			   echo "<a href='javascript:onclickSubmit(\"$link/1\");' title=\"First &raquo;\">|<</a>";
			 }
//			 if ($currentPage > 1) {
//				echo "<span class=\"extend\">...</span><a href='javascript:onclickSubmit(\"$link/".($currentPage - 1 )."\");'><<</a>"; 
//			 }	
			 for ($i=-8 ; $i <= 8 ; $i++) {  
				$page = $currentPage + $i;
				if ($page >= 1 && $page <= $pageCount && $page <> $currentPage)
					echo "<a href='javascript:onclickSubmit(\"$link/$page\");' title=\"$page\">$page</a>";
				else if ($page == $currentPage)
					echo "<span class=\"current\">$page</span>";
			 }
//			 if ($currentPage < $pageCount)
//				echo "<a href='javascript:onclickSubmit(\"$link/".($currentPage + 1 )."\");'>>></a><span class=\"extend\">...</span>"; 
//			 
			 if ($currentPage <> $pageCount) {
				 echo "<a href='javascript:onclickSubmit(\"$link/$pageCount\")'; title=\"Last &raquo;\">>|</a>";
		     }	 
			?>
			<span class="wp-page-num"><?=$currentPage?> / <?=$pageCount?></span>
		  </div>
		  
		<?
		
	}
	
	/*
	if ($pageCount > 1) {
		?>
		  <div class="section">
			<div class="pagination">
			<ul>
			<? 
			 if ($currentPage > 1)
				echo "<li><a href='javascript:onclickSubmit(\"$link/".($currentPage - 1 )."\"'>pre</a></li>"; 
			 for ($i=-2 ; $i <= 2 ; $i++) {  
				$page = $currentPage + $i;
				if ($page >= 1 && $page <= $pageCount && $page <> $currentPage)
					echo "<li><a href='javascript:onclickSubmit(\"$link/$page\")'>$page</a></li>";
				else if ($page == $currentPage)
					echo "<li>$page</li>";
			 }
			 if ($currentPage < $pageCount)
				echo "<li><a href='javascript:onclickSubmit(\"$link/".($currentPage + 1 )."\"'>next</a></li>"; 
			?>
			
			</ul>
			</div>
		  </div>
		<?
		
	}
	*/
}


/*
<div class="wp-pagenavi">
<span class="pages">Page 1 of 74</span><span class="current">1</span><a href="http://www.webappers.com/page/2/" title="2">2</a><a href="http://www.webappers.com/page/3/" title="3">3</a><a href="http://www.webappers.com/page/4/" title="4">4</a><a href="http://www.webappers.com/page/5/" title="5">5</a><a href="http://www.webappers.com/page/2/">&raquo;</a><span class="extend">...</span><a href="http://www.webappers.com/page/74/" title="Last &raquo;">Last &raquo;</a>
</div>
*/
function showPagation($link, $currentPage, $pageCount, $para1="") {
	if ($pageCount > 1) {
	
		if ($para1)
			$para1 = "/" . $para1;

		?>
		  <div class="wp-pagenavi">

			<? 
			 if ($currentPage != 1) {
			   echo "<a href=\"$link/1$para1\" title=\"First &raquo;\">|<</a>";
			 }
//			 if ($currentPage > 1) {
//				echo "<span class=\"extend\">...</span><a href='$link/".($currentPage - 1 )."$para1'><<</a>"; 
//			 }	
			 for ($i=-8 ; $i <= 8 ; $i++) {  
				$page = $currentPage + $i;
				if ($page >= 1 && $page <= $pageCount && $page <> $currentPage)
					echo "<a href=\"$link/$page$para1\" title=\"$page\">$page</a>";
				else if ($page == $currentPage)
					echo "<span class=\"current\">$page</span>";
			 }
//			 if ($currentPage < $pageCount)
//				echo "<a href='$link/".($currentPage + 1 )."$para1'>>></a><span class=\"extend\">...</span>"; 
			 
			 if ($currentPage <> $pageCount) {
				 echo "<a href=\"$link/$pageCount$para1\" title=\"Last &raquo;\">>|</a>";
		     }	 
			?>
			<span class="wp-page-num"><?=$currentPage?> / <?=$pageCount?></span>
		  </div>
		  
		<?
		
	}
}

function lookupLocationId($location_id) {


	if ($location_id=='11')
		return "東區";
	else if ($location_id=='12')
		return "灣仔";
	else if ($location_id=='13')
		return "中上環";
	else if ($location_id=='14')
		return "南區";		
	else if ($location_id=='211')
		return "黃大仙";
	else if ($location_id=='212')
		return "觀塘";
	else if ($location_id=='221')
		return "油尖";
	else if ($location_id=='222')
		return "旺角";
	else if ($location_id=='223')
		return "深水埗";						
	else if ($location_id=='224')
		return "九龍城";
	else if ($location_id=='311')
		return "荃灣";
	else if ($location_id=='312')
		return "葵青";
	else if ($location_id=='321')
		return "西貢";
	else if ($location_id=='322')
		return "大埔";
	else if ($location_id=='323')
		return "沙田";
	else if ($location_id=='331')
		return "屯門";
	else if ($location_id=='341')
		return "上水";
	else if ($location_id=='342')
		return "元朗";									
	else if ($location_id=='4')
		return "大嶼山";
	return "";
}

/*


function showPagation($link, $currentPage, $pageCount) {
	if ($pageCount > 1) {
		?>
		  <div class="section">
			<div class="pagination">
			<ul>
			<? 
			 if ($currentPage > 1)
				echo "<li><a href='$link/".($currentPage - 1 )."'>pre</a></li>"; 
			 for ($i=-2 ; $i <= 2 ; $i++) {  
				$page = $currentPage + $i;
				if ($page >= 1 && $page <= $pageCount && $page <> $currentPage)
					echo "<li><a href='$link/$page'>$page</a></li>";
				else if ($page == $currentPage)
					echo "<li>$page</li>";
			 }
			 if ($currentPage < $pageCount)
				echo "<li><a href='$link/".($currentPage + 1 )."'>next</a></li>"; 
			?>
			
			</ul>
			</div>
		  </div>
		<?
		
	}
}
*/

?>