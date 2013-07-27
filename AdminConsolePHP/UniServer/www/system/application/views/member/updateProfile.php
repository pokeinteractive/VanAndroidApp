<!-- Middle Side -->		
<div class="main_navigation" style="width:726px;margin-left:0px;">

	<div class="main_content" style="width:706px;">
	
		<h2>修改的個人資料</h2>


		 <form id="contactForm" action="/member/previewProfileChange/" method="post" enctype="multipart/form-data">
			
	      <div class="member-contact">  
		      <div class="member-description">
		     	<table>
				<tbody><tr>  
				  <th><label for="registration_first_name">聯絡人姓氏</label></th>
				  <td>
				    <input class="text" id="registration_first_name" maxlength="30" name="first_name" value="<?= $member->first_name?>" size="10" tabindex="1" type="text">
				  </td>
				  <th>
				    <label for="registration_last_name">聯絡人名稱</label>
				  </th>
				  <td>
				      <input class="text" id="registration_last_name" maxlength="40" name="last_name" value="<?= $member->last_name?>" size="20" tabindex="2" type="text">
				  </td>
				</tr>
				<tr>
				  <th>
				      <label for="registration_email">電郵地址</label>
				  </th>
				  <td colspan="3">
				      <input class="text text-long" id="registration_email" name="email" value="<?=$member->email?>" size="30" tabindex="3" type="text" />
				  </td>
				</tr>
				<tr id="claim-check">
				</tr>
				<tr>  
				   <th>
				    <label for="registration_password">密碼</label>
				  </th>
				  <td>
				    <input class="text" id="registration_password" name="password" size="30" tabindex="4" value="" type="password">
				  </td>
				  <th>
				    <label for="registration_password_confirmation">確認密碼 </label>
				  </th>
				  <td>
				    <input class="text" id="registration_password_confirmation" name="password_confirmation" value="" size="30" tabindex="5" type="password">
				  </td>
				</tr>
				<? /*
				<tr>  
				  <th>
				    <label for="registration_screen_name">顯示名稱</label>
				  </th>
				  <td colspan="3">
				    <input class="text text-long" id="registration_screen_name" name="screen_name" value="<?= $member->member_name?>" size="30" tabindex="6" type="text">
				  </td>
				</tr>
				*/ ?>
				<? if ($member->memberType == 'C'): ?>
				<tr>  
				  <th>
				    <label for="registration_first_name">公司中文名稱</label>
				  </th>
				  <td>
				    <input class="text" id="registration_company_name_chi" maxlength="40" name="company_name_chi" value="<?= $member->company_name_chi?>" size="20" tabindex="7" type="text">
				  </td>
				  <th>
				    <label for="registration_last_name">公司英文名稱</label>
				  </th>
				  <td>
				      <input class="text" id="registration_company_name_eng" maxlength="40" name="company_name_eng" value="<?= $member->company_name_eng?>" size="20" tabindex="8" type="text">
				  </td>
				</tr>		
				<tr>  
				  <th>
				    <label for="registration_screen_name">公司地址</label>
				  </th>
				  <td colspan="3">
				    <input class="text text-long" id="registration_address" name="address" value="<?= $member->address?>" size="60" tabindex="9" type="text">
				  </td>
				</tr>
				<? endif; ?>
				<tr>  
				  <th>
				    <label for="registration_screen_name">電話</label>
				  </th>
				  <td>
				    <input class="text" id="registration_phone" name="phone" value="<?= $member->phone?>" size="30" tabindex="10" type="text">
				  </td>
				  <th>
				    <label for="registration_fax">傳真</label>
				  </th>
				  <td>
				    <input class="text" id="registration_fax" name="fax" value="<?= $member->fax?>" size="30" tabindex="11" type="text">
				  </td>
				</tr>
				<tr>  
				  <th>
				    <label for="registration_website">網址</label>
				  </th>
				  <td colspan="3">
				    http://<input class="text text-long" id="registration_website" name="website" value="<?= $member->website?>" size="60" tabindex="12" type="text">
				  </td>
				</tr>
				<tr>  
				  <th>
				    <label for="registration_photo">頭像圖片</label>
				  </th>
				  <td colspan="3">
				    <input type="file" class="text text-long" id="registration_mphoto" name="mphoto" value="" size="40" tabindex="13" />
				  </td>
				</tr>		
				</tbody>
				</table>
				
		      </div>
	      </div>		      
		      
		      <div class="member-description">
				  <h2>關於我們</h2>
				  <br>
				  <textarea class="text" cols="80" id="about_me_about_me" name="about_me" rows="20" tabindex="14"><?=$member->description?></textarea>

  		      <ul class="head-actions" style="margin-top: 0px;">
		            <li class="btn-input"><a href="#" tabindex="15" onclick="$('#contactForm').submit();">預覽儲存資料</a></li>
   		            <li class="btn-input"><a href="/member/profile/<?=$member->member_id?>" tabindex="16">取消</a></li>
		      </ul> 
				  
			  </div>

		   </form>
	</div>	<!-- end main_content -->
	
	</form>
	
</div> <!-- article_middle -->

<!-- end Middle Side -->
