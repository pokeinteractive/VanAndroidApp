/*
Function : autoComplete v0.3
Author : Audi
http://audi.tw
Date:March 2008
歡迎應用於無償用途散播，並請勿移除本版權宣告
*/

autoComplete=function(fID,url){

	//代表文字欄位
	this.id=document.getElementById(fID);
	this.url=url;

	if (!this.id) return false;
	if (!this.url) return false;

	this.id.focus();

	//存放清單用的圖層
	this.div		='_sg';

	//套用到div的樣式名稱
	this.className		='_sgClass';
	//代表清單物件
	this.suggest		='_qg';

	//清單寬度
	this.divWidth		=this.id.offsetWidth+'px';
	//清單顯示列數
	this.selectHeight	=11;

	//Suggest樣式
	this.overBGColor	=overBGColor;
	this.overTextColor	=overTextColor;
	this.outBGColor		=outBGColor;
	this.outTextColor	=outTextColor;

	//XMLHttpRequest物件
	this.xmlhttp		=this.createXHR();

	//Interval物件
	this.interval=null;

	//偵測中文輸入用
	this.lastValue		=null;

	//保存 this 不變化,以免和其他物件中的 this 對沖
	var pointer = this;

	this.id.onkeyup = function(e){
		document.onmousedown = function(e){pointer.clearDiv();};
		return pointer.onKeyUp(e);
	}

	this.id.onkeydown = function(e){
		document.onmousedown = function(e){pointer.clearDiv();};
		return pointer.onKeyDown(e);
	}
}

autoComplete.prototype.onKeyUp=function(e){
	var pointer=this;
	var key = (!e) ? event.keyCode : e.keyCode;
	var ckey = (!e) ? event.ctrlKey : e.ctrlKey;
	switch(key){
		case 18:	//alt
			break;
		case 27:	//esc
			this.clearDiv();
			break;
		case 13:
			break;
		case 38:	//up
			if (this.selectOBJ!=null){pointer.selectList('up');}
			break;
		case 40:	//down
			if (this.selectOBJ!=null){pointer.selectList('down');}
			break;
		default:
			if (key>=112 && key <=123){  //112-123:F1-F12
				break;
			}else if (ckey){
				break;
			}else{
				this.sendRequest();
				break;
			}
			break;
	}
}

autoComplete.prototype.onKeyDown=function(e){
	var pointer=this;
	var key = (!e) ? event.keyCode : e.keyCode;
	var ckey = (!e) ? event.ctrlKey : e.ctrlKey;

	switch(key){
		case 197:	//For Opera
			if (pointer.interval==null){
				pointer.interval=setInterval(function(){pointer.IMEDetected();},500);
			}
			break;
		case 229:
			if (pointer.interval==null){
				pointer.interval=setInterval(function(){pointer.IMEDetected();},500);
			}
			break;
		default:
			if (pointer.interval!=null){
				window.clearInterval(pointer.interval);
				pointer.interval=null;
				break;
			}
			break;
	}
}

autoComplete.prototype.selectList=function(direct){

	var _id=this.selectOBJ.getAttribute('id');
	var sib=(direct=='down')?'nextSibling':'previousSibling';

	if (_id=='_current_pointer'){
		//取消識別記號
		this.selectOBJ.setAttribute('id',null);
		//設定非選取中樣式
		this.selectOBJ.style.backgroundColor=this.outBGColor;
		this.selectOBJ.style.color=this.outTextColor;
		//移動至指定項次
		if (this.selectOBJ[sib]){
			this.selectOBJ=this.selectOBJ[sib];
		}
	}
	if (this.selectOBJ.nodeName.toLowerCase()=='li'){
		//設定選取中樣式
		this.selectOBJ.style.backgroundColor=this.overBGColor;
		this.selectOBJ.style.color=this.overTextColor;
		//將值填到欄位中
		this.id.value=this.selectOBJ.firstChild.nodeValue;
		//加上識別記號
		this.selectOBJ.setAttribute('id','_current_pointer');
	}
}

autoComplete.prototype.IMEDetected=function(){
	var pointer=this;
	var currValue = this.id.value;
    	var lastValue = this.lastValue;
	window.status='IME Detected! '+currValue;
    	if(currValue != lastValue) {
    		this.lastValue=currValue;
        	this.sendRequest();
    	}
}

autoComplete.prototype.createLayer=function(){

	var divName=this.div;
	var sName=this.suggest;
	var obj=this.id;
	var oLeft,oDiv;

	//create layer
	if (document.getElementById(divName)==null){
		var div=document.createElement('div');
		div.setAttribute('id',divName);
		div.style.position='absolute';
		div.style.width=obj.offsetWidth+'px';
		//div.className=this.className;
		div.style.visibility='hidden';
		obj.parentNode.insertBefore(div,obj);

		//先建立好後再調整位置
		oDiv=obj;
		oLeft=oDiv.offsetLeft;
		while (oDiv.offsetParent){
			oDiv=oDiv.offsetParent;
			oLeft+=oDiv.offsetLeft;
		}
		div.style.top=(div.offsetTop+obj.offsetHeight)+'px';
		div.style.left=oLeft+'px';
		div.style.visibility='visible';
		this.suggestDiv=div;
	}
}

autoComplete.prototype.showSuggest=function(va){
	var obj=document.getElementById(this.div);
	var ul=document.createElement('ul');
	var pointer=this;

	for (var i=0;i<va.length;i++){
		//建立li標籤
		var li=document.createElement('li');

		//建立li內容
		var sText=document.createTextNode(va[i][1]);
		li.appendChild(sText);

		//調整清單項次樣式
		li.style.textAlign='left';
		li.style.cursor='default';
		li.onmouseover=function(){
			this.style.backgroundColor=pointer.overBGColor;
			this.style.color=pointer.overTextColor;
			document.onmousedown=null;
			};
		li.onmouseout=function(){
			this.style.backgroundColor=pointer.outBGColor;
			this.style.color=pointer.outTextColor;
			document.onmousedown=null;
			};
		li.onclick=function(){
			pointer.id.value=this.firstChild.nodeValue;
			pointer.clearDiv();
			//這裡可以作成直接送出submit;
			};
		ul.appendChild(li);
	}

	//調整清單樣式
	ul.setAttribute('id',this.suggest);
	ul.style.margin=0;
	ul.style.padding=0;
	ul.style.listStyle='none';
	ul.className=this.className;

	//判斷是否已經有建議名單
	if (this.id.value!=''){
		if (document.getElementById(this.suggest)){
			obj.replaceChild(ul,document.getElementById(this.suggest));
		}else{
			obj.appendChild(ul);
		}

		//以第一個li作為物件
		this.selectOBJ=ul.firstChild;
	}
}

autoComplete.prototype.clearDiv=function(){
	if (document.getElementById(this.div)!=null){
		var obj=this.id;
		obj.parentNode.removeChild(this.suggestDiv);
		//document.onmousedown=null;
	}
}

autoComplete.prototype.createXHR=function(){
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	}else if (window.ActiveXObject) {
		xmlhttp = new ActiveXObject('Microsoft.XMLHTTP');
	}

	if (!xmlhttp) {
		alert('您使用的瀏覽器不支援 XMLHTTP 物件');
		return false;
	}else{
		return xmlhttp;
	}
}

autoComplete.prototype.sendRequest=function(){
	var pointer=this;
	var url=pointer.url+'?searchStr='+escape(pointer.id.value)+'&ts='+new Date().getTime();

	var form='searchStr='+pointer.id.value+'&ts='+new Date().getTime();
	pointer.xmlhttp.open('GET',url,true);
	pointer.xmlhttp.onreadystatechange=function(){pointer.catchXML()};
	//pointer.xmlhttp.setRequestHeader('Content-Type','application/x-www-form-urlencoded');

	pointer.xmlhttp.send('');
}

autoComplete.prototype.catchXML=function(){
	if (this.xmlhttp.readyState==4){
		xml=this.xmlhttp.responseXML;
		if (this.xmlhttp.status == 200) {
			var va=getNodeContent(xml);
			if (va.length!=0){
				this.createLayer();
    				this.showSuggest(va);
    			}else{
    				this.clearDiv();
    			}
		}else{
			alert(this.xmlhttp.statusText);
		}
	}
}


