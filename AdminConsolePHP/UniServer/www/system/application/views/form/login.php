<!-- Middle Side -->		
<div class="main_navigation" style="width:966px;">

	<div class="main_content" style="width:944px;">
		<h2>請登入, 以享受我們為您提供的服務</h2>
		<p><?=$message?></p>			    
		<form action="/openform/submitLogin" method="POST">
		     <dl class="service-description">
				 <dt>電郵地址</dt>
				 <dd>
					<input name="email" type="text" size="50" tabindex="1" class="text" />
				 </dd>
				 <dt>密碼</dt>
				 <dd>
			      	<input type="password" name="password" value="" size="50" tabindex="2" class="text" />
		         </dd>
				 <dt></dt>
				 <dd>
				     <p>
					   <span class="btn-input"><span><input name="commit" tabindex="3" value="登入" type="submit"></span></span>
					 </p>
  				 </dd>	      
			</dl>
		</form>
	</div>
</div>