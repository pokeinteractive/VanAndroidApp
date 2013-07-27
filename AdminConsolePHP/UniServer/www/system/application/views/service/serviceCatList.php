<!-- Middle Side -->		
<div class="main_navigation" style="width:675px;">

	<div class="main_content" style="width:653px;">

          <DIV class="subject-cat-list" style="width:280px;"> 
         	<UL>
				<?
				$i=0;
				$total1 = sizeof($subjectList) / 2;
				$total2 = sizeof($subjectList);
				for ($i=0 ;$i < $total1 ; $i++)
				{  
					$row = $subjectList[$i];
				?> 
             		<div class="subject-list corner hoverable">
             			<a href="/service/serviceList/<?=$row->subject_id?>">
           				<IMG src="/graphics/subject/s<?=$row->subject_id?>.jpg" width="50" height="50">
             			<div class="subject-name"><?= $row->subject?>&nbsp;&nbsp;(<?=$row->service_count?>)<br><p><?=$row->detail?></p></div>
             			</a>
             		</div>
         		<?
         		}
         		?>
          	</UL>
          </DIV>
		<!--column 2-->
          <DIV class="subject-cat-list" style="width:280px;"> 
            	<?

				for (;$i < $total2; $i++)
				{  $row = $subjectList[$i];
				?> 
             		<div class="subject-list corner hoverable">
             			<a href="/service/serviceList/<?=$row->subject_id?>">
           				<IMG src="/graphics/subject/s<?=$row->subject_id?>.jpg" width="50" height="50">
             			<div class="subject-name"><?= $row->subject?>&nbsp;&nbsp;(<?=$row->service_count?>)<br><p><?=$row->detail?></p></div>
             			</a>
             		</div>
         		<?
         		}
         		?>
          	</UL>
          </DIV>
  			
	</div>	<!-- main_content -->			

</div> <!-- main_navigation -->

<!-- end Middle Side -->



