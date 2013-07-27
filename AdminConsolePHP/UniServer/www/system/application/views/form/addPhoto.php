<div id="content" class="clear">

	<div id="content-left" class="class">
	
		<div class="a-col">
		
			<h2>Add Photo</h2>
			    
			<form action="/openform/submitAddPhoto" method="POST" enctype="multipart/form-data">
			<input type="hidden" name="photo_type" value="<?=$photo_type?>"/>
			<input type="hidden" name="key_id" value="<?=$key_id?>"/>
			
			<!-- for add photo -->
			<div class="content-box">
			  <div class="first-child"><div></div></div>
			  <div class="content-inner">
			      <dl class="class-description">
				      <dt>Photo upload</dt>
				      <dd>
						<input name="photo" type="file" size="40"/>
				      </dd>
				      <dt>Description</dt>
				      <dd>
				      	<input type="text" name="photo_subject" value="" maxlength="30" size="40"/>
				      </dd>
				      <dt><dt>
				      <dd>
						<li class="btn-input"><span><input type="submit" name="addPhoto" value="Add" /></span></li>
				      </dd>	      
			      </dl>
			  </div><!-- end inner content -->
			  <div class="last-child"><div></div></div>
			</div>
			</form>
			
			
			<? if (isset($photoList)): ?>
			
			<h2>Remove Photo</h2>
			
			<div class="content-box">
			  <div class="first-child"><div></div></div>
			  <div class="content-inner">
			      <dl class="class-description">
			      <lu>
			      <? 
					foreach ($photoList as $row)
					{ 
				  ?>	
				      <li><p><img src="/upload/<?=$row->photo?>" title="<?=$row->photo_subject?>" alt="" class="thumb-med" width="48" height="48"> 
						 	 &nbsp;&nbsp;&nbsp;&nbsp;<a href="/openform/removePhoto/<?=$photo_type?>/<?=$key_id?>/<?=$row->photo_id?>"><span>Click to Remove</span></a>
						 </p>
					  </li>	 
				      
				  <?    
				    }
				  ?> 
			      </lu>				  
			      </dl>
			  </div><!-- end inner content -->
			  <div class="last-child"><div></div></div>
			</div>			
			<? endif; ?>
		<form action="/<?=$photo_type?>/<?=$photo_type?>Info/<?=$key_id?>" method="post">
			<li class="btn-input"><span><input type="submit" name="returnButton" value="Return" /></span></li>
		</form>
	    </div>
	    

	   

	<div class="b-col"><div class="col-head"><div></div></div>


	</div><!-- end a/btest -->

</div>
</div> <!-- #container -->
<!-- #wrapper -->

    
