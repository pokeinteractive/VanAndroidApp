<!-- Middle Side -->		
<div class="main_navigation">

	<div class="main_content">
	<? 

	if (isset($articleList["result"]))
	foreach ($articleList["result"] as $row)
	{  

	?> 
			
		<div class="Post">
		
		<div class="PostHead">
		 <h2 class="title"><a href="/article/editArticle/<?=$row->article_id?>"><?=$row->subject?></a></h2>
		 <p class="PostDate">
		   <strong class="day"><?= date('d',strtotime($row->updatedate))?></strong>
		   <strong class="month"><?= date('M',strtotime($row->updatedate))?></strong>
		 </p>
		  <div class="PostInfo"><?=$row->updatedate?> ; <a href="/article/listArticle/<?=$row->ar_cat_id?>" title="<?=$row->ar_cat?>"><?=$row->ar_cat?></a></div>
			
		</div>
		
		<div class="PostContent">
		
		 
		<p><?=$row->content?></p>
		<blockquote><p>Requirements: jQuery Framework<br />
		Demo: <a title="Demo" rel="nofollow" href="http://www.sohtanaka.com/web-design/examples/image-zoom/" target="_blank">http://www.sohtanaka.com/web-design/examples/image-zoom/</a><br />
		License: License Free</p></blockquote>
		
		 
		  <ul class="PostDetails">
		   <li class="PostCom"><a href="http://www.webappers.com/2009/04/24/create-fancy-thumbnail-hover-effect-with-jquery/#respond" title="Comment on Create Fancy Thumbnail Hover Effect with jQuery"><span><strong>0</strong> Comments</span></a></li>
		     </ul>
		
		  
		 </div>  
	</div>
 <? } ?>  
 

<? 
/*
<div class="wp-pagenavi">
<span class="pages">Page 1 of 74</span><span class="current">1</span>
<a href="http://www.webappers.com/page/2/" title="2">2</a>
<a href="http://www.webappers.com/page/3/" title="3">3</a>
<a href="http://www.webappers.com/page/4/" title="4">4</a><a href="http://www.webappers.com/page/5/" title="5">5</a><a href="http://www.webappers.com/page/2/">&raquo;</a><span class="extend">...</span><a href="http://www.webappers.com/page/74/" title="Last &raquo;">Last &raquo;</a>
</div>
*/
?>

  <?
  
  showPagation("/article/listArticle/" . $ar_cat_id, $currentPage, $articleList["count"]);
  
  ?>
</div>  

</div>  	