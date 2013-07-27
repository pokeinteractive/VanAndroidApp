// Array.indexOf( value, begin, strict ) - Return index of the first element that matches value
Array.prototype.indexOf = function( v, b, s ) {
	for( var i = +b || 0, l = this.length; i < l; i++ ) {
		if( this[i]===v || s && this[i]==v ){
			return i;
		}
 	}
	return -1;
};

// Array.push() - Add an element to the end of an array, return the new length
if( typeof Array.prototype.push==='undefined' ) {
	Array.prototype.push = function() {
		for( var i = 0, b = this.length, a = arguments, l = a.length; i<l; i++ ) {
			this[b+i] = a[i];
		}
		return this.length;
	};
}

// Array.unique( strict ) - Remove duplicate values
Array.prototype.unique = function( b ) {
	var a = [], i, l = this.length;
	for( i=0; i<l; i++ ) {
		if( a.indexOf( this[i], 0, b ) < 0 ) { a.push( this[i] ); }
	}
	return a;
};

function getNodeContent(xmldoc,key,label) {
	/*
	取得節點內容值
	輸入項:
		xmldoc	:xml文件
		key	:標籤名稱
		label	:子節點屬性名稱,用於取得屬性值
	輸出項:
		Array([a,b])
		a=標籤名稱, 或標籤中指定屬性值
		b=標籤字串值
		例:
		<product>
		<name value="產品">Sony Handy Cam</name>
		<price value="單價">21000</name>
		</product>
		getNodeContent(xml,'product') 回傳值為[name,Sony Handy Cam][price,21000]
		getNodeContent(xml,'product','value') 回傳值為[產品,Sony Handy Cam][單價,21000]

	*/
	var na=[],va=[],l;
	var outstr='';

	key=(typeof key=='undefined')?getNodeName(xmldoc,false):key;

	for (var i=0;i<xmldoc.getElementsByTagName(key).length;i++){
		var xml=xmldoc.getElementsByTagName(key)[i];
		na=getNodeName(xml,false);	//必需是唯一值,因為接下來的動作,會依序讀出

		for (var j=0;j<na.length;j++){
			var xmlk=xml.getElementsByTagName(na[j]);
			for (var k=0;k<xmlk.length;k++){
				l=Math.max(va.length,va.length-1);
				if(typeof label!='undefined'){
					if(typeof xmlk[k].getAttribute(label)!=null && typeof xmlk[k].getAttribute(label)!=''){
						va[l]=[xmlk[k].getAttribute(label),xmlk[k].childNodes[0].nodeValue];
					}else{
						va[l]=[na[j],xmlk[k].childNodes[0].nodeValue];
					}
				}else{
					va[l]=[na[j],xmlk[k].childNodes[0].nodeValue];
				}
				outstr+=va[l]+'\n';
			}
		}
	}

	return va
}

function getNodeName(xmldoc,duplicate){
	/*
	取得子節點標籤名稱
	輸入項:
		xmldoc		:xml文件
		duplicate	:boolean,允許重複項,預設為 true
	輸出項:
		Array()
		以第一層子節點標籤名稱組成的陣列
	*/

	var d;
	var na=[];

	d=(typeof duplicate=='undefined')?true:duplicate;

	for (var i=0;i<xmldoc.childNodes.length;i++){
		if (xmldoc.childNodes[i].hasChildNodes()){
			na=na.concat(xmldoc.childNodes[i].tagName);
		}
	}

	na=(d)?na:na.unique();

	return na
}