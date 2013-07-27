if(typeof Prototype=="undefined"){alert("CalendarDateSelect Error: Prototype could not be found. Please make sure that your application's layout includes prototype.js (.g. <%= javascript_include_tag :defaults %>) *before* it includes calendar_date_select.js (.g. <%= calendar_date_select_includes %>).")}if(Prototype.Version<"1.6"){alert("Prototype 1.6.0 is required.  If using earlier version of prototype, please use calendar_date_select version 1.8.3")}Element.addMethods({purgeChildren:function(A){$A(A.childNodes).each(function(B){$(B).remove()})},build:function(A,D,B,C){var E=Element.build(D,B,C);A.appendChild(E);return E}});Element.build=function(type,options,style){var e=$(document.createElement(type));$H(options).each(function(pair){eval("e."+pair.key+" = pair.value")});if(style){$H(style).each(function(pair){eval("e.style."+pair.key+" = pair.value")})}return e};nil=null;Date.one_day=24*60*60*1000;Date.weekdays=$w("S M T W T F S");Date.first_day_of_week=0;Date.months=$w("January February March April May June July August September October November December");Date.padded2=function(A){var B=parseInt(A,10);if(A<10){B="0"+B}return B};Date.prototype.getPaddedMinutes=function(){return Date.padded2(this.getMinutes())};Date.prototype.getAMPMHour=function(){var A=this.getHours();return(A==0)?12:(A>12?A-12:A)};Date.prototype.getAMPM=function(){return(this.getHours()<12)?"AM":"PM"};Date.prototype.stripTime=function(){return new Date(this.getFullYear(),this.getMonth(),this.getDate())};Date.prototype.daysDistance=function(A){return Math.round((A-this)/Date.one_day)};Date.prototype.toFormattedString=function(B){var A,C;C=Date.months[this.getMonth()]+" "+this.getDate()+", "+this.getFullYear();if(B){A=this.getHours();C+=" "+this.getAMPMHour()+":"+this.getPaddedMinutes()+" "+this.getAMPM()}return C};Date.parseFormattedString=function(A){return new Date(A)};Math.floor_to_interval=function(B,A){return Math.floor(B/A)*A};window.f_height=function(){return([window.innerHeight?window.innerHeight:null,document.documentElement?document.documentElement.clientHeight:null,document.body?document.body.clientHeight:null].select(function(A){return A>0}).first()||0)};window.f_scrollTop=function(){return([window.pageYOffset?window.pageYOffset:null,document.documentElement?document.documentElement.scrollTop:null,document.body?document.body.scrollTop:null].select(function(A){return A>0}).first()||0)};_translations={OK:"OK",Now:"Now",Today:"Today"};SelectBox=Class.create();SelectBox.prototype={initialize:function(D,B,A,C){this.element=$(D).build("select",A,C);this.populate(B)},populate:function(A){this.element.purgeChildren();var B=this;$A(A).each(function(C){if(typeof (C)!="object"){C=[C,C]}B.element.build("option",{value:C[1],innerHTML:C[0]})})},setValue:function(B){var C=this.element;var A=false;$R(0,C.options.length-1).each(function(D){if(C.options[D].value==B.toString()){C.selectedIndex=D;A=true}});return A},getValue:function(){return $F(this.element)}};CalendarDateSelect=Class.create();CalendarDateSelect.prototype={initialize:function(A,B){this.target_element=$(A);if(!this.target_element){alert("Target element "+A+" not found!");return false}if(down=this.target_element.down("INPUT")){this.target_element=down}this.target_element.calendar_date_select=this;this.last_click_at=0;this.options=$H({embedded:false,popup:nil,time:false,buttons:true,year_range:10,calendar_div:nil,close_on_click:nil,minute_interval:5,popup_by:this.target_element,month_year:"dropdowns",onchange:this.target_element.onchange,valid_date_check:nil}).merge(B||{});this.selection_made=$F(this.target_element).strip()!=="";this.use_time=this.options.get("time");this.callback("before_show");this.calendar_div=$(this.options.get("calendar_div"));this.parseDate();if(this.calendar_div==nil){this.calendar_div=$(this.options.get("embedded")?this.target_element.parentNode:document.body).build("div")}if(!this.options.get("embedded")){this.calendar_div.setStyle({position:"absolute",visibility:"hidden",left:0,top:0})}this.calendar_div.addClassName("calendar_date_select");if(this.options.get("embedded")){this.options.set("close_on_click",false)}if(this.options.get("close_on_click")===nil){if(this.options.get("time")){this.options.set("close_on_click",false)}else{this.options.set("close_on_click",true)}}if(!this.options.get("embedded")){Event.observe(document,"mousedown",this.closeIfClickedOut_handler=this.closeIfClickedOut.bindAsEventListener(this));Event.observe(document,"keypress",this.keyPress_handler=this.keyPress.bindAsEventListener(this))}this.init();if(!this.options.get("embedded")){this.positionCalendarDiv()}this.callback("after_show")},positionCalendarDiv:function(){var M=false;var F=this.calendar_div.cumulativeOffset(),H=F[0],G=F[1],C=this.calendar_div.getDimensions(),P=C.height,B=C.width;var L=window.f_scrollTop(),K=window.f_height();var O=$(this.options.get("popup_by")).cumulativeOffset(),D=O[1],N=O[0],E=$(this.options.get("popup_by")).getDimensions().height,I=D+E;if(((I+P)>(L+K))&&(I-P>L)){M=true}var A=N.toString()+"px",J=(M?(D-P):(D+E)).toString()+"px";this.calendar_div.style.left=A;this.calendar_div.style.top=J;this.calendar_div.setStyle({visibility:""});if(navigator.appName=="Microsoft Internet Explorer"){this.iframe=$(document.body).build("iframe",{className:"ie6_blocker"},{left:A,top:J,height:P.toString()+"px",width:B.toString()+"px",border:"0px"})}},init:function(){var that=this;$w("top header body buttons footer bottom").each(function(name){eval("var "+name+"_div = that."+name+"_div = that.calendar_div.build('div', { className: 'cds_"+name+"' }, { clear: 'left'} ); ")});this.initHeaderDiv();this.initButtonsDiv();this.initCalendarGrid();this.updateFooter("&nbsp;");this.refresh();this.setUseTime(this.use_time)},initHeaderDiv:function(){var A=this.header_div;this.close_button=A.build("a",{innerHTML:"x",href:"#",onclick:function(){this.close();return false}.bindAsEventListener(this),className:"close"});this.next_month_button=A.build("a",{innerHTML:"&gt;",href:"#",onclick:function(){this.navMonth(this.date.getMonth()+1);return false}.bindAsEventListener(this),className:"next"});this.prev_month_button=A.build("a",{innerHTML:"&lt;",href:"#",onclick:function(){this.navMonth(this.date.getMonth()-1);return false}.bindAsEventListener(this),className:"prev"});if(this.options.get("month_year")=="dropdowns"){this.month_select=new SelectBox(A,$R(0,11).map(function(B){return[Date.months[B],B]}),{className:"month",onchange:function(){this.navMonth(this.month_select.getValue())}.bindAsEventListener(this)});this.year_select=new SelectBox(A,[],{className:"year",onchange:function(){this.navYear(this.year_select.getValue())}.bindAsEventListener(this)});this.populateYearRange()}else{this.month_year_label=A.build("span")}},initCalendarGrid:function(){var B=this.body_div;this.calendar_day_grid=[];var G=B.build("table",{cellPadding:"0px",cellSpacing:"0px",width:"100%"});var C=G.build("thead").build("tr");Date.weekdays.each(function(H){C.build("th",{innerHTML:H})});var E=G.build("tbody");var A=0,D;for(var F=0;F<42;F++){D=(F+Date.first_day_of_week)%7;if(F%7==0){days_row=E.build("tr",{className:"row_"+A++})}(this.calendar_day_grid[F]=days_row.build("td",{calendar_date_select:this,onmouseover:function(){this.calendar_date_select.dayHover(this)},onmouseout:function(){this.calendar_date_select.dayHoverOut(this)},onclick:function(){this.calendar_date_select.updateSelectedDate(this,true)},className:(D==0)||(D==6)?" weekend":""},{cursor:"pointer"})).build("div");this.calendar_day_grid[F]}},initButtonsDiv:function(){var A=this.buttons_div;if(this.options.get("time")){var B=$A(this.options.get("time")=="mixed"?[[" - ",""]]:[]);A.build("span",{innerHTML:"@",className:"at_sign"});var C=new Date();this.hour_select=new SelectBox(A,B.concat($R(0,23).map(function(E){C.setHours(E);return $A([C.getAMPMHour()+" "+C.getAMPM(),E])})),{calendar_date_select:this,onchange:function(){this.calendar_date_select.updateSelectedDate({hour:this.value})},className:"hour"});A.build("span",{innerHTML:":",className:"seperator"});var D=this;this.minute_select=new SelectBox(A,B.concat($R(0,59).select(function(E){return(E%D.options.get("minute_interval")==0)}).map(function(E){return $A([Date.padded2(E),E])})),{calendar_date_select:this,onchange:function(){this.calendar_date_select.updateSelectedDate({minute:this.value})},className:"minute"})}else{if(!this.options.get("buttons")){A.remove()}}if(this.options.get("buttons")){A.build("span",{innerHTML:"&nbsp;"});if(this.options.get("time")=="mixed"||!this.options.get("time")){b=A.build("a",{innerHTML:_translations.Today,href:"#",onclick:function(){this.today(false);return false}.bindAsEventListener(this)})}if(this.options.get("time")=="mixed"){A.build("span",{innerHTML:" | ",className:"button_seperator"})}if(this.options.get("time")){b=A.build("a",{innerHTML:_translations.Now,href:"#",onclick:function(){this.today(true);return false}.bindAsEventListener(this)})}if(!this.options.get("embedded")){A.build("span",{innerHTML:"&nbsp;"});A.build("a",{innerHTML:_translations.OK,href:"#",onclick:function(){this.close();return false}.bindAsEventListener(this)})}}},refresh:function(){this.refreshMonthYear();this.refreshCalendarGrid();this.setSelectedClass();this.updateFooter()},refreshCalendarGrid:function(){this.beginning_date=new Date(this.date).stripTime();this.beginning_date.setDate(1);this.beginning_date.setHours(12);var D=this.beginning_date.getDay();if(D<3){D+=7}this.beginning_date.setDate(1-D+Date.first_day_of_week);var C=new Date(this.beginning_date);var A=new Date().stripTime();var B=this.date.getMonth();vdc=this.options.get("valid_date_check");for(var E=0;E<42;E++){day=C.getDate();month=C.getMonth();cell=this.calendar_day_grid[E];Element.remove(cell.childNodes[0]);div=cell.build("div",{innerHTML:day});if(month!=B){div.className="other"}cell.day=day;cell.month=month;cell.year=C.getFullYear();if(vdc){if(vdc(C.stripTime())){cell.removeClassName("disabled")}else{cell.addClassName("disabled")}}C.setDate(day+1)}if(this.today_cell){this.today_cell.removeClassName("today")}if($R(0,42).include(days_until=this.beginning_date.daysDistance(A))){this.today_cell=this.calendar_day_grid[days_until];this.today_cell.addClassName("today")}},refreshMonthYear:function(){var A=this.date.getMonth();var C=this.date.getFullYear();if(this.options.get("month_year")=="dropdowns"){this.month_select.setValue(A,false);var B=this.year_select.element;if(this.flexibleYearRange()&&(!(this.year_select.setValue(C,false))||B.selectedIndex<=1||B.selectedIndex>=B.options.length-2)){this.populateYearRange()}this.year_select.setValue(C)}else{this.month_year_label.update(Date.months[A]+" "+C.toString())}},populateYearRange:function(){this.year_select.populate(this.yearRange().toArray())},yearRange:function(){if(!this.flexibleYearRange()){return $R(this.options.get("year_range")[0],this.options.get("year_range")[1])}var A=this.date.getFullYear();return $R(A-this.options.get("year_range"),A+this.options.get("year_range"))},flexibleYearRange:function(){return(typeof (this.options.get("year_range"))=="number")},validYear:function(A){if(this.flexibleYearRange()){return true}else{return this.yearRange().include(A)}},dayHover:function(A){var B=new Date(this.selected_date);B.setYear(A.year);B.setMonth(A.month);B.setDate(A.day);this.updateFooter(B.toFormattedString(this.use_time))},dayHoverOut:function(A){this.updateFooter()},setSelectedClass:function(){if(!this.selection_made){return }if(this.selected_cell){this.selected_cell.removeClassName("selected")}if($R(0,42).include(days_until=this.beginning_date.stripTime().daysDistance(this.selected_date.stripTime()))){this.selected_cell=this.calendar_day_grid[days_until];this.selected_cell.addClassName("selected")}},reparse:function(){this.parseDate();this.refresh()},dateString:function(){return(this.selection_made)?this.selected_date.toFormattedString(this.use_time):"&nbsp;"},parseDate:function(){var A=$F(this.target_element).strip();this.date=A==""?NaN:Date.parseFormattedString(this.options.get("date")||A);if(isNaN(this.date)){this.date=new Date()}if(!this.validYear(this.date.getFullYear())){this.date.setYear((this.date.getFullYear()<this.yearRange().start)?this.yearRange().start:this.yearRange().end)}this.selected_date=new Date(this.date);this.use_time=/[0-9]:[0-9]{2}/.exec(A)?true:false;this.date.setDate(1)},updateFooter:function(A){if(!A){A=this.dateString()}this.footer_div.purgeChildren();this.footer_div.build("span",{innerHTML:A})},updateSelectedDate:function(E,D){var F=$H(E);if((this.target_element.disabled||this.target_element.readOnly)&&this.options.get("popup")!="force"){return false}if(F.get("day")){var C=this.selected_date,B=this.options.get("valid_date_check");for(var A=0;A<=3;A++){C.setDate(F.get("day"))}C.setYear(F.get("year"));C.setMonth(F.get("month"));if(B&&!B(C.stripTime())){return false}this.selected_date=C;this.selection_made=true}if(!isNaN(F.get("hour"))){this.selected_date.setHours(F.get("hour"))}if(!isNaN(F.get("minute"))){this.selected_date.setMinutes(Math.floor_to_interval(F.get("minute"),this.options.get("minute_interval")))}if(F.get("hour")===""||F.get("minute")===""){this.setUseTime(false)}else{if(!isNaN(F.get("hour"))||!isNaN(F.get("minute"))){this.setUseTime(true)}}this.updateFooter();this.setSelectedClass();if(this.selection_made){this.updateValue()}if(this.options.get("close_on_click")){this.close()}if(D&&!this.options.get("embedded")){if((new Date()-this.last_click_at)<333){this.close()}this.last_click_at=new Date()}},navMonth:function(A){(target_date=new Date(this.date)).setMonth(A);return(this.navTo(target_date))},navYear:function(A){(target_date=new Date(this.date)).setYear(A);return(this.navTo(target_date))},navTo:function(A){if(!this.validYear(A.getFullYear())){return false}this.date=A;this.date.setDate(1);this.refresh();this.callback("after_navigate",this.date);return true},setUseTime:function(B){this.use_time=this.options.get("time")&&(this.options.get("time")=="mixed"?B:true);if(this.use_time&&this.selected_date){var C=Math.floor_to_interval(this.selected_date.getMinutes(),this.options.get("minute_interval"));var A=this.selected_date.getHours();this.hour_select.setValue(A);this.minute_select.setValue(C)}else{if(this.options.get("time")=="mixed"){this.hour_select.setValue("");this.minute_select.setValue("")}}},updateValue:function(){var A=this.target_element.value;this.target_element.value=this.dateString();if(A!=this.target_element.value){this.callback("onchange")}},today:function(A){var C=new Date();this.date=new Date();var B=$H({day:C.getDate(),month:C.getMonth(),year:C.getFullYear(),hour:C.getHours(),minute:C.getMinutes()});if(!A){B=B.merge({hour:"",minute:""})}this.updateSelectedDate(B,true);this.refresh()},close:function(){if(this.closed){return false}this.callback("before_close");this.target_element.calendar_date_select=nil;Event.stopObserving(document,"mousedown",this.closeIfClickedOut_handler);Event.stopObserving(document,"keypress",this.keyPress_handler);this.calendar_div.remove();this.closed=true;if(this.iframe){this.iframe.remove()}if(this.target_element.type!="hidden"){this.target_element.focus()}this.callback("after_close")},closeIfClickedOut:function(A){if(!$(Event.element(A)).descendantOf(this.calendar_div)){this.close()}},keyPress:function(A){if(A.keyCode==Event.KEY_ESC){this.close()}},callback:function(A,B){if(this.options.get(A)){this.options.get(A).bind(this.target_element)(B)}}};