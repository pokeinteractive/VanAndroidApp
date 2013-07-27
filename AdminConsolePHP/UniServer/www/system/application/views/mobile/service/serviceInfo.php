<div data-role="content">
		<h4><?=$service->service_name?></h4>
		<dl class="business-info">
	    	<? if ($service->website): ?>
		    <dt>網址:</dt>
		    <dd><p><a href="http://<?=$service->website?>"><?=$service->website?></a></p></dd>
			<? endif; ?>
			<? if ($service->phone): ?>
		    <dt>電話:</dt><dd><p><?=$service->phone?> <?=$service->contact_person?></p></dd>
			<? endif; ?>
			<? if ($service->address): ?>
		    <dt>地址:</dt><dd><p><?=$service->address?></p></dd>
			<? endif; ?>
			<dt>更新:</dt>
	      	<dd><?= date('d/m/Y',strtotime($service->created))?></dd> 	
		</dl>
			<? if ($service->main_point): ?>
			<div class="main_content">	
				 <h2>必看要點</h2>
				 <div class="service-description">
						<?= $service->main_point?>	
				 </div>
			</div>
			<? endif; ?>
	
	<? if ($service->description || $service->price_desc || $service->promo_desc): ?>
	<h4>服務簡介</h4>
		 
	   <dl class="service-description">
		  <? if ($service->description): ?>
	      <dt>簡介</dt>
	      <dd>
	          <p><?=nl2br($service->description)?>&nbsp;
	      </dd>
	      <? endif; ?>
		  <? if ($service->price_desc): ?>
	      <dt>特別之處</dt>
	      <dd>
	          <?=nl2br($service->price_desc)?>&nbsp;
	      </dd>
	      <? endif; ?>
	      <? if ($service->promo_desc): ?>
	      <dt>推廣及細節</dt>
	      <dd>
	          <?=nl2br($service->promo_desc)?>&nbsp;
	      </dd>
	      <? endif; ?>
      	   </dl>
	<? endif; ?>
	
	<a href="/service/commentList/<?=$service->service_id?>" data-role="button" data-iconpos="right" data-icon="arrow-r">用家評論</a>
    
    <? if (sizeof($photoList) > 0): ?>  
	<h4>相片</h4>
	
	<? $count=0; 
		foreach ($photoList as $row)
		{ 
	?>
		<div style="padding:0px 0px 1px 1px; display:inline">
		 <a target="new" href="/upload/gallery/<?=$row->photo?>"><img src="/upload/gallery/<?=$row->photo?>" title="" alt="" class="thumb-med"  width="128" height="96" /></a>
		</div>
	<?
		}
	?>
	<? endif; ?>
	  
	</div><!-- /content -->




