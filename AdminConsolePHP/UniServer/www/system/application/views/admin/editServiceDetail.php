<!-- Middle Side -->		
<div class="main_navigation" style="width:966px;">

	<div class="main_content" style="width:944px;">
		
		<form action="/admin/saveServiceDetail" method="post">
		<input type="hidden" name="detail[service_id]" value="<?=$service_id?>"/>
		<br><textarea id="main_point_content" cols="70" rows="10" name="detail[main_point]"><?=$detail->main_point?></textarea>
		<br>pass-Key:<input class="text" type="password" name="passkey" value=""/>
		<Br><br>
		<li class="btn-input"><span><input name="publish_button" value="提交" type="submit"></span></li>
		</form>
	</div> <!-- END main_content -->
</div> <!-- article_middle -->

<!-- end Middle Side -->

<script src="/js/nicEdit.js" type="text/javascript"></script>
<script>
var area1;

bkLib.onDomLoaded(function() {
	new nicEditor({fullPanel : true,iconsPath : '/graphics/nicEditorIcons.gif'}).panelInstance('main_point_content');
});
</script>