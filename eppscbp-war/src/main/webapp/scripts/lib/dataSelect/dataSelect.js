define(function(){
	var Vcity = {};
	/* *
	 * 静态方法集
	 * @name _m
	 * */
	Vcity._m = {
		/* 选择元素 */
		$:function (arg, context) {
			var tagAll, n, eles = [], i, sub = arg.substring(1);
			context = context || document;
			if (typeof arg == 'string') {
				switch (arg.charAt(0)) {
					case '#':
						return document.getElementById(sub);
						break;
					case '.':
						if (context.getElementsByClassName) return context.getElementsByClassName(sub);
						tagAll = Vcity._m.$('*', context);
						n = tagAll.length;
						for (i = 0; i < n; i++) {
							if (tagAll[i].className.indexOf(sub) > -1) eles.push(tagAll[i]);
						}
						return eles;
						break;
					default:
						return context.getElementsByTagName(arg);
						break;
				}
			}
		},
	
		/* 绑定事件 */
		on:function (node, type, handler) {
			node.addEventListener ? node.addEventListener(type, handler, false) : node.attachEvent('on' + type, handler);
		},

		/* 移除事件 */
		remove:function(node, type, handler) {
			node.removeEventListener ? node.removeEventListener(type, handler, false) : node.detachEvent('on'+type,handler); 
			return this;
		},
	
		/* 获取事件 */
		getEvent:function(event){
			return event || window.event;
		},
	
		/* 获取事件目标 */
		getTarget:function(event){
			return event.target || event.srcElement;
		},
	
		/* 获取元素位置 */
		getPos:function (node) {
			var scrollx = document.documentElement.scrollLeft || document.body.scrollLeft,
				scrollt = document.documentElement.scrollTop || document.body.scrollTop;
			var pos = node.getBoundingClientRect();
			return {top:pos.top + scrollt, right:pos.right + scrollx, bottom:pos.bottom + scrollt, left:pos.left + scrollx }
		},
	
		/* 添加样式名 */
		addClass:function (c, node) {
			if(!node)return;
			node.className = Vcity._m.hasClass(c,node) ? node.className : node.className + ' ' + c ;
		},
	
		/* 移除样式名 */
		removeClass:function (c, node) {
			var reg = new RegExp("(^|\\s+)" + c + "(\\s+|$)", "g");
			if(!Vcity._m.hasClass(c,node))return;
			node.className = reg.test(node.className) ? node.className.replace(reg, '') : node.className;
		},
	
		/* 是否含有CLASS */
		hasClass:function (c, node) {
			if(!node || !node.className)return false;
			return node.className.indexOf(c)>-1;
		},
	
		/* 阻止冒泡 */
		stopPropagation:function (event) {
			event = event || window.event;
			event.stopPropagation ? event.stopPropagation() : event.cancelBubble = true;
		},
		/* 去除两端空格 */
		trim:function (str) {
			return str.replace(/^\s+|\s+$/g,'');
		}
	};
	
	/* 所有城市数据,可以按照格式自行添加（北京|beijing|bj），前16条为热门城市 */
	Vcity.allCity = ['中国银行|01|zg_104100000004_01',
	'工商银行|02|gs_102100099996_02',
	'建设银行|03|js_105100000017_03',
	'交通银行|04|jt_301290000007_04',
	'华夏银行|05|hx_304100040000_05',
	'招商银行|06|sz_308584000013_06',
	'光大银行|07|gd_303100000006_07',
	'农业银行|08|ny_103100000026_08',
	'民生银行|09|ms_305100000013_09',
	'兴业银行|10|xy_309391000011_10',
	'中信银行|11|zx_302100011000_11',
	'北京银行|12|bj_313100000013_12',
	'浦发银行|13|pf_310290000013_13',
	'上海银行|14|sh_313290000017_14',
	'平安银行|15|pa_307584007998_15',
	'邮政储蓄银行|16|yz_403100000004_16',
	'恒丰银行|17|hf_315456000105_17',
	'花旗银行|18|hq_000000000000_18',
	'浙商银行|19|zj_316331000018_19',
	'渤海银行股份有限公司|20|bh_318110000014_20',
	'邢台银行股份有限公司|21|xt_313131000016_21',
	'张家口市商业银行股份有限公司|22|zj_313138000019_22',
	'承德银行股份有限公司|23|cd_313141052422_23',
	'沧州银行|24|cz_313143005157_24',
	'晋商银行股份有限公司|25|js_313161000017_25',
	'晋城市商业银行|26|jc_313168000003_26',
	'内蒙古银行|27|nm_313191000011_27',
	'包商银行股份有限公司|28|bs_313192000013_28',
	'鄂尔多斯银行股份有限公司|29|er_313205057830_29',
	'大连银行|30|dl_313222080002_30',
	'鞍山市商业银行|31|as_313223007007_31',
	'锦州银行|32|jz_313227000012_32',
	'葫芦岛银行股份有限公司|33|hl_313227600018_33',
	'营口银行股份有限公司资金清算中心|34|yk_313228000276_34',
	'阜新银行结算中心|35|fx_313229000008_35',
	'吉林银行|36|jl_313241066661_36',
	'哈尔滨银行结算中心|37|hr_313261000018_37',
	'龙江银行股份有限公司|38|lj_313261099913_38',
	'南京银行股份有限公司|39|nj_313301008887_39',
	'江苏银行股份有限公司|40|js_313301099999_40',
	'杭州银行股份有限公司|41|hz_313331000014_41',
	'宁波银行股份有限公司|42|nb_313332082914_42',
	'温州银行股份有限公司|43|wz_313333007331_43',
	'湖州银行股份有限公司|44|hz_313336071575_44',
	'绍兴银行股份有限公司营业部|45|sx_313337009004_45',
	'浙江稠州商业银行|46|zj_313338707013_46',
	'台州银行股份有限公司|47|tz_313345001665_47',
	'浙江泰隆商业银行|48|zj_313345010019_48',
	'浙江民泰商业银行|49|zj_313345400010_49',
	'福建海峡银行股份有限公司|50|fj_313391080007_50',
	'厦门银行股份有限公司|51|xm_313393080005_51',
	'南昌银行|52|nc_313421087506_52',
	'赣州银行股份有限公司|53|gz_313428076517_53',
	'上饶银行|54|sr_313433076801_54',
	'青岛银行|55|qd_313452060150_55',
	'齐商银行|56|qs_313453001017_56',
	'东营市商业银行|57|dy_313455000018_57',
	'烟台银行股份有限公司|58|yt_313456000108_58',
	'潍坊银行|59|wf_313458000013_59',
	'济宁银行股份有限公司|60|jn_313461000012_60',
	'泰安市商业银行|61|ta_313463000993_61',
	'莱商银行|62|ls_313463400019_62',
	'威海市商业银行|63|wh_313465000010_63',
	'德州银行股份有限公司|64|dz_313468000015_64',
	'临商银行股份有限公司|65|ls_313473070018_65',
	'日照银行股份有限公司|66|rz_313473200011_66',
	'郑州银行|67|zz_313491000232_67',
	'开封市商业银行|68|kf_313492070005_68',
	'洛阳银行|69|ly_313493080539_69',
	'漯河市商业银行|70|lh_313504000010_70',
	'商丘市商业银行股份有限公司|71|sq_313506082510_71',
	'南阳市商业银行|72|ny_313513080408_72',
	'汉口银行资金清算中心|73|hk_313521000011_73',
	'长沙银行股份有限公司|74|cs_313551088886_74',
	'广州银行|75|gz_313581003284_75',
	'深发展银行|76|zf_307584007998_76',
	'东莞银行股份有限公司|77|dw_313602088017_77',
	'广西北部湾银行|78|gx_313611001018_78',
	'柳州银行股份有限公司清算中心|79|lz_313614000012_79',
	'重庆银行|80|cq_313653000013_80',
	'攀枝花市商业银行|81|pz_313656000019_81',
	'德阳银行股份有限公司|82|dy_313658000014_82',
	'绵阳市商业银行|83|my_313659000016_83',
	'贵阳市商业银行|84|gy_313701098010_84',
	'富滇银行股份有限公司运营管理部|85|fz_313731010015_85',
	'兰州银行股份有限公司|86|lz_313821001016_86',
	'青海银行股份有限公司营业部|87|nh_313851000018_87',
	'宁夏银行总行清算中心|88|nx_313871000007_88',
	'乌鲁木齐市商业银行清算中心|89|wl_313881000002_89',
	'昆仑银行股份有限公司|90|kl_313882000012_90',
	'苏州银行股份有限公司|91|sz_313305066661_91',
	'昆山农村商业银行|92|ks_314305206650_92',
	'吴江农村商业银行清算中心|93|wj_314305400015_93',
	'江苏常熟农村商业银行股份有限公司清算中心|94|js_314305506621_94',
	'张家港农村商业银行|95|zj_314305670002_95',
	'广州农村商业银行股份有限公司|96|gz_314581000011_96',
	'佛山顺德农村商业银行股份有限公司|97|fs_314588000016_97',
	'重庆农村商业银行股份有限公司|98|cq_14653000011_98',
	'其他银行|99|qt_000000000000_99',
	'广东发展银行股份有限公司运营作业部|A1|gd_306581000003_A1',
	'天津农村合作银行|A2|tj_317110010019_A2',
	'天津银行股份有限公司|A3|tj_313110000017_A3',
	'徽商银行股份有限公司|A4|hs_319361000013_A4',
	'上海农村商业银行|A5|sh_322290000011_A5',
	'北京农村商业银行股份有限公司|A6|bj_402100000018_A6',
	'江苏省农村信用社联合社信息结算中心|A7|js_402301099998_A7',
	'宁波鄞州农村合作银行|A8|nb_402332010004_A8',
	'安徽省农村信用联社资金清算中心|A9|ah_402361018886_A9',
	'福建省农村信用社联合社|B1|fj_402391000068_B1',
	'湖北省农村信用社联合社结算中心|B2|hb_402521000032_B2',
	'深圳农村商业银行|B3|sz_402584009991_B3',
	'东莞农村商业银行股份有限公司|B4|dg_402602000018_B4',
	'广西壮族自治区农村信用社联合社|B5|gx_402611099974_B5',
	'海南省农村信用社联合社资金清算中心|B6|nh_402641000014_B6',
	'云南省农村信用社联合社|B7|yn_402731057238_B7',
	'宁夏黄河农村商业银行股份有限公司|B8|nx_402871099996_B8',
	'外换银行中国有限公司|B9|wh_591110000016_B9',
	'新韩银行中国有限公司|C1|xhb_595100000007_C1',
	'企业银行中国有限公司|C2|qy_596110000013_C2',
	'韩亚银行中国有限公司|C3|hy_597100000014_C3',
	'河北银行股份有限公司|C4|hb_313121006888_C4',
	'邯郸市商业银行股份有限公司|C5|hd_313127000013_C5',
	'珠海华润银行股份有限公司清算中心|C6|zh_313585000990_C6',
	'自贡市商业银行清算中心|C7|zg_313655091983_C7',
	'广东南粤银行股份有限公司|C8|gd_313591001001_C8',
	'桂林银行股份有限公司|C9|gl_313617000018_C9',
	'吉林省农村信用社联合社不办理转汇业务|D1|jl_402241000015_D1',
	'嘉兴银行股份有限公司清算中心不对外办理业务|D2|jx_313335081005_D2',
	'廊坊银行|D3|lf_313146000019_D3',
	'山东省农村信用社联合社不对外办理业务|D4|sd_402451000010_D4',
	'友利银行中国有限公司|D5|yl_593100000020_D5',
	'长安银行股份有限公司|D6|ca_313791030003_D6',
	'浙江省农村信用社联合社|D7|zj_402331000007_D7',
	'平顶山银行股份有限公司|D8|pd_313495081900_D8',
	'北京顺义银座村镇银行股份有限公司|D9|bj_320100010011_D9',
	'广东华兴银行股份有限公司|E1|gd_313586000006_E1',
	'江西赣州银座村镇银行股份有限公司|E2|jx_320428090311_E2',
	'齐鲁银行|E3|ql_313451000019_E3',
	'深圳福田银座村镇银行股份有限公司|E4|sz_320584002002_E4',
	'浙江景宁银座村镇银行股份有限公司|E5|zj_320343800019_E5',
	'浙江三门银座村镇银行股份有限公司|E6|zj_320345790018_E6',
	'重庆黔江银座村镇银行股份有限公司|E7|cq_320687000016_E7',
	'重庆渝北银座村镇银行股份有限公司|E8|cq_320653000104_E8',
	'乌鲁木齐市商业银行|E9|wl_313881000002_E9',
	'南充市商业银行股份有限公司|F1|NC_313673093259_F1',
	'成都银行股份有限公司|F2|cd_313651000001_F2',
	'盛京银行|F3|sj_313221030008_F3'
];
	/* 正则表达式 筛选中文城市名、拼音、首字母 */
	
	Vcity.regEx = /^([\u4E00-\u9FA5\uf900-\ufa2d]+)\|(\w+)\|(\w)\w*$/i;
	Vcity.regExChiese = /([\u4E00-\u9FA5\uf900-\ufa2d]+)/;
	
	/* *
	 * 格式化城市数组为对象oCity，按照a-h,i-p,q-z,hot热门城市分组：
	 * {HOT:{hot:[]},ABCDEFGH:{a:[1,2,3],b:[1,2,3]},IJKLMNOP:{i:[1.2.3],j:[1,2,3]},QRSTUVWXYZ:{}}
	 * */
	
	function dataClean() {
		var citys = Vcity.allCity, match, letter,
			regEx = Vcity.regEx,
			reg2 = /^[a-c]$/i, reg3 = /^[d-g]$/i, reg4 = /^[h-k]$/i, reg5 = /^[l-o]$/i,
			reg6 = /^[p-s]$/i, reg7 = /^[t-x]$/i, reg8 = /^[y-z]$/i;
		// if (!Vcity.oCity) {
			Vcity.oCity = {
				// ABC:{},DEFG:{},HIJK:{}, LMNO:{}, PQRS:{},TUVWX:{},YZ:{}
				hot:{}
			};
			// console.log(citys);
			// console.log(citys.length);
			for (var i = 0, n = citys.length; i < n; i++) {
				match = regEx.exec(citys[i]);
				// console.log(match)
				// console.log(match[3]);
				letter = match[3].toUpperCase();
				// console.log(letter)
				// if (reg2.test(letter)) {
				// 	if (!Vcity.oCity.ABC[letter]) Vcity.oCity.ABC[letter] = [];
				// 	Vcity.oCity.ABC[letter].push(match[1]+"_"+citys[i].split("_")[1]+"_"+citys[i].split("_")[2]);
				// } else if (reg3.test(letter)) {
				// 	if (!Vcity.oCity.DEFG[letter]) Vcity.oCity.DEFG[letter] = [];
				// 	Vcity.oCity.DEFG[letter].push(match[1]+"_"+citys[i].split("_")[1]+"_"+citys[i].split("_")[2]);
				// } else if (reg4.test(letter)) {
				// 	if (!Vcity.oCity.HIJK[letter]) Vcity.oCity.HIJK[letter] = [];
				// 	Vcity.oCity.HIJK[letter].push(match[1]+"_"+citys[i].split("_")[1]+"_"+citys[i].split("_")[2]);
				// }else if (reg5.test(letter)) {
				// 	if (!Vcity.oCity.LMNO[letter]) Vcity.oCity.LMNO[letter] = [];
				// 	Vcity.oCity.LMNO[letter].push(match[1]+"_"+citys[i].split("_")[1]+"_"+citys[i].split("_")[2]);
				// }else if (reg6.test(letter)) {
				// 	if (!Vcity.oCity.PQRS[letter]) Vcity.oCity.PQRS[letter] = [];
				// 	Vcity.oCity.PQRS[letter].push(match[1]+"_"+citys[i].split("_")[1]+"_"+citys[i].split("_")[2]);
				// }else if (reg7.test(letter)) {
				// 	if (!Vcity.oCity.TUVWX[letter]) Vcity.oCity.TUVWX[letter] = [];
				// 	Vcity.oCity.TUVWX[letter].push(match[1]+"_"+citys[i].split("_")[1]+"_"+citys[i].split("_")[2]);
				// }else if (reg8.test(letter)) {
				// 	if (!Vcity.oCity.YZ[letter]) Vcity.oCity.YZ[letter] = [];
				// 	Vcity.oCity.YZ[letter].push(match[1]+"_"+citys[i].split("_")[1]+"_"+citys[i].split("_")[2]);
				// }
				/* 热门城市 前19条 Veryhuo.COM */
				// if(i<19){
					if(!Vcity.oCity.hot['hot']) Vcity.oCity.hot['hot'] = [];
					Vcity.oCity.hot['hot'].push(match[1]+"_"+citys[i].split("_")[1]+"_"+citys[i].split("_")[2]);
				// }
			}
		// }
	};
	
	/* 城市HTML模板 */
	Vcity._template = [
		// '<p class="tip">热门城市(支持汉字/拼音)</p>',
		'<ul>',
		'<li class="on">省份</li>',
		// '<li>ABC</li>',
		// '<li>DEFG</li>',
		// '<li>HIJK</li>',
		// '<li>LMNO</li>',
		// '<li>PQRS</li>',
		// '<li>TUVWX</li>',
		// '<li>YZ</li>',
		'</ul>'
	];
	
	/* *
	 * 城市控件构造函数
	 * @DataSelector
	 * */
	
	function DataSelector () {
		this.initialize.apply(this, arguments);
	};
	
	DataSelector.prototype = {
	
		constructor:DataSelector,

		
		needcallback:false,
	
		/* 初始化 */
	
		initialize :function (options) {
			var input = options.input;
			if(options.callback != undefined){
				needcallback = options.callback;
			}
			this.input = Vcity._m.$('#'+ input);
			this.callBack = options.callBack;
			
			Vcity.allCity = options.allData || Vcity.allCity;
			// 整理数据
			dataClean();	
			this.clazz = options.clazz;

			if(this.clazz === 'citySelector') {
				Vcity._template = [
					'<ul>',
					'<li class="on">城市</li>',
					'</ul>'
					];
			}

			this.autoCityComplete();
			this.inputEvent();
		},
	
		/*
		* 重整数据
		*/
		// restartDate:function(data) {
		// 	Vcity.allCity = data;
		// 	dataClean();
		// 	this.setData();
		// },

		/* *
		 * @createWarp
		 * 创建城市BOX HTML 框架
		 * */
	
		createWarp:function(){
			//var inputPos = Vcity._m.getPos(this.input);
			var inputPos = $(this.input).position();
			var div = this.rootDiv = document.createElement('div');
			var that = this;

			// 设置DIV阻止冒泡
			Vcity._m.on(this.rootDiv,'click',function(event){
				Vcity._m.stopPropagation(event);
			});
	
			// 设置点击文档隐藏弹出的城市选择框
			Vcity._m.on(document, 'click', function (event) {
				event = Vcity._m.getEvent(event);
				var target = Vcity._m.getTarget(event);
				if(target == that.input || target == that.downArrow){ return false;}
				if (that.cityBox){Vcity._m.addClass('hide', that.cityBox);}
				if (that.cityBox){$(".city-select-box").css({borderColor:"#bbd4f2"});}
				if (that.ul){Vcity._m.addClass('hide', that.ul);}
				if(that.myIframe){Vcity._m.addClass('hide',that.myIframe);}
			});
			div.className = 'bankSelector';
			div.style.position = 'absolute';
			div.style.top = inputPos.top+$(this.input).outerHeight() + 'px';
			div.style.zIndex = 999999;
	
			// 判断是否IE6，如果是IE6需要添加iframe才能遮住SELECT框
			var isIe = (document.all) ? true : false;
			var isIE6 = this.isIE6 = isIe && !window.XMLHttpRequest;
			if(isIE6){
				var myIframe = this.myIframe =  document.createElement('iframe');
				myIframe.frameborder = '0';
				myIframe.src = 'about:blank';
				myIframe.style.position = 'absolute';
				myIframe.style.zIndex = '-1';
				this.rootDiv.appendChild(this.myIframe);
			}
	
			var childdiv = this.cityBox = document.createElement('div');
			childdiv.className = 'bankBox';
			// 增加识别样式
			$(this.rootDiv).addClass(this.clazz);
			childdiv.id = 'bankBox';
			childdiv.innerHTML = Vcity._template.join('');
			var hotCity = this.hotCity =  document.createElement('div');
			hotCity.className = 'hotCity';
			childdiv.appendChild(hotCity);
			div.appendChild(childdiv);
			this.createHotCity();
		},
	
		/* *
		 * @createHotCity
		 * TAB下面DIV：hot,a-h,i-p,q-z 分类HTML生成，DOM操作
		 * {HOT:{hot:[]},ABCDEFGH:{a:[1,2,3],b:[1,2,3]},IJKLMNOP:{},QRSTUVWXYZ:{}}
		 **/
	
		createHotCity:function(){
			this.setData();
			/* IE6 */
			this.changeIframe();
	
			// this.tabChange();
			this.linkEvent(this.callBack);
		},

		setData:function() {
			var odiv,odl,odt,odd,odda=[],str,key,ckey,sortKey,regEx = Vcity.regEx,
				oCity = Vcity.oCity;
			for(key in oCity){
				odiv = this[key] = document.createElement('div');
				// 先设置全部隐藏hide
				odiv.className = key + ' ' + 'bankTab1';
				sortKey=[];
				for(ckey in oCity[key]){
					sortKey.push(ckey);
					// ckey按照ABCDEDG顺序排序
					sortKey.sort();
				}
				
				for(var j=0,k = sortKey.length;j<k;j++){	
					odl = document.createElement('dl');
					odt = document.createElement('dt');
					odd = document.createElement('dd');
					odt.innerHTML = sortKey[j] == 'hot'?'&nbsp;':sortKey[j];
					odda = [];
					for(var i=0,n=oCity[key][sortKey[j]].length;i<n;i++){
						var pc = oCity[key][sortKey[j]][i].split("_")[2];
						if(pc == 'undefined'){
							str = '<a href="javascript:" cc='+ oCity[key][sortKey[j]][i].split("_")[1] +' pc='+ '' +'>' + oCity[key][sortKey[j]][i].split("_")[0] + '</a>';
						}else{
							str = '<a href="javascript:" cc='+ oCity[key][sortKey[j]][i].split("_")[1] +' pc='+ oCity[key][sortKey[j]][i].split("_")[2] +'>' + oCity[key][sortKey[j]][i].split("_")[0] + '</a>';
						}
						odda.push(str);
					}
					odd.innerHTML = odda.join('');
					odl.appendChild(odt);
					odl.appendChild(odd);
					odiv.appendChild(odl);
				}
				// 移除热门城市的隐藏CSS
				// Vcity._m.removeClass('hide',this.ABC);
				this.hotCity.appendChild(odiv);
			}
			$(this.input).parent().find('.'+this.clazz).remove();
			$(this.input).parent().append($(this.rootDiv));
		},
	
		/* *
		 *  tab按字母顺序切换
		 *  @ tabChange
		 * */
	
		tabChange:function(){
			var lis = Vcity._m.$('li',this.cityBox);
			var divs = Vcity._m.$('div',this.hotCity);
			var that = this;
			for(var i=0,n=lis.length;i<n;i++){
				lis[i].index = i;
				lis[i].onclick = function(){
					for(var j=0;j<n;j++){
						Vcity._m.removeClass('on',lis[j]);
						Vcity._m.addClass('hide',divs[j]);
					}
					Vcity._m.addClass('on',this);
					Vcity._m.removeClass('hide',divs[this.index]);
					/* IE6 改变TAB的时候 改变Iframe 大小*/
					that.changeIframe();
				};
			}
		},
	
		/* *
		 * 城市LINK事件.点击触发事件
		 *  @linkEvent
		 * */
	
		linkEvent:function(callback){
			var links = Vcity._m.$('a',this.hotCity);
			var that = this;
			for(var i=0,n=links.length;i<n;i++){
				links[i].onclick = function(){
					that.input.value = this.innerHTML;
					// $('#cityCode').val($(this).attr("cc"));
					$('#bankCodeId').val($(this).attr("pc"));
					$(".city-select-box").css({borderColor:"#bbd4f2"});
					Vcity._m.addClass('hide',that.cityBox);
					Vcity._m.addClass('hide',that.myIframe);
					$(that.input).blur();	

					// 触发回调函数
					callback();
				}
			}
		},
		/* *
		 * INPUT城市输入框事件
		 * @inputEvent
		 * */
	
		inputEvent:function(){
			var that = this;
			this.downArrow = document.getElementById('downArrow');
			//console.log([this.input]);
			function inputClick(event){
				event = event || window.event;
				//Vcity._m.stopPropagation(event);
				if(!that.cityBox){
					that.createWarp();
				}else if(!!that.cityBox || Vcity._m.hasClass('hide',that.cityBox)){
					// slideul 不存在或者 slideul存在但是是隐藏的时候 两者不能共存
					if(!that.ul || (that.ul && Vcity._m.hasClass('hide',that.ul))){
						Vcity._m.removeClass('hide',that.cityBox);
						/* IE6 移除iframe 的hide 样式 */
						Vcity._m.removeClass('hide',that.myIframe);
						that.changeIframe();
					}
				}else { 
					Vcity._m.addClass('hide',that.cityBox);       
				}

				// 特殊处理 隐藏下拉的支行
				$('#subBankPullDown').hide();
				$('#subBankPullDown').parent().css('height','85px');
			}
			if(this.downArrow){
				Vcity._m.on(this.downArrow, 'click', function(event){
					if(!that.cityBox || Vcity._m.hasClass('hide',that.cityBox)){
						that.input.select();
						inputClick(event);
					}
					else{
						if (that.cityBox){Vcity._m.addClass('hide', that.cityBox);}
						if (that.cityBox){$(".city-select-box").css({borderColor:"#bbd4f2"});}
						if (that.ul){Vcity._m.addClass('hide', that.ul);}
						if(that.myIframe){Vcity._m.addClass('hide',that.myIframe);}
					}
				});
			}
			Vcity._m.remove(this.input, 'click', inputClick);
			Vcity._m.on(this.input, 'click', inputClick);
			
			Vcity._m.on(this.input,'focus',function(){
				that.input.select();
				$(".city-select-box").css({borderColor:"#ff9500"});
				if(that.input.value == '银行名称') that.input.value = '';
			});
			Vcity._m.on(this.input,'blur',function(){
				//if(that.input.value == '') that.input.value = '南京';
				if(that.input.value == '') {that.autoCityComplete();}
			});
			
		
			Vcity._m.on(this.input,'keyup',function(event){
				event = event || window.event;
				var keycode = event.keyCode;
				Vcity._m.addClass('hide',that.cityBox);
				// that.createUl();
	
				/* 移除iframe 的hide 样式 */
				Vcity._m.removeClass('hide',that.myIframe);
	
				// 下拉菜单显示的时候捕捉按键事件
				if(that.ul && !Vcity._m.hasClass('hide',that.ul) && !that.isEmpty){
					that.KeyboardEvent(event,keycode);
				}
			});
		},
	
		/* *
		 * 生成下拉选择列表
		 * @ createUl
		 * */
	
		// createUl:function () {
		// 	//console.log('createUL');
		// 	var str;
		// 	var value = Vcity._m.trim(this.input.value);
		// 	// 当value不等于空的时候执行
		// 	if (value !== '') {
		// 		var reg = new RegExp("^" + value + "|\\|" + value, 'gi');
		// 		var searchResult = [];
		// 		for (var i = 0, n = Vcity.allCity.length; i < n; i++) {
		// 			if (reg.test(Vcity.allCity[i])) {
		// 				var match = Vcity.regEx.exec(Vcity.allCity[i]);
		// 				if (searchResult.length !== 0) {
		// 					str = '<li cc = ' + Vcity.allCity[i].split("_")[1] +' pc  = '+ Vcity.allCity[i].split("_")[2] +'><b class="cityname">' + match[1] + '</b><b class="cityspell">' + match[2] + '</b></li>';
		// 				} else {
		// 					str = '<li class="on" cc = ' + Vcity.allCity[i].split("_")[1] +' pc  = '+ Vcity.allCity[i].split("_")[2] +'><b class="cityname">' + match[1] + '</b><b class="cityspell">' + match[2] + '</b></li>';
		// 				}
		// 				searchResult.push(str);
		// 			}
		// 		}
		// 		this.isEmpty = false;
		// 		// 如果搜索数据为空
		// 		if (searchResult.length == 0) {
		// 			this.isEmpty = true;
		// 			str = '<li class="empty">对不起，没有找到数据 "<em>' + value + '</em>"</li>';
		// 			searchResult.push(str);
		// 		}
		// 		// 如果slideul不存在则添加ul
		// 		if (!this.ul) {
		// 			var ul = this.ul = document.createElement('ul');
		// 			ul.className = 'cityslide';
		// 			this.rootDiv && this.rootDiv.appendChild(ul);
		// 			// 记录按键次数，方向键
		// 			this.count = 0;
		// 		} else if (this.ul && Vcity._m.hasClass('hide', this.ul)) {
		// 			this.count = 0;
		// 			Vcity._m.removeClass('hide', this.ul);
		// 		}
		// 		this.ul.innerHTML = searchResult.join('');
	
		// 		/* IE6 */
		// 		this.changeIframe();
	
		// 		// 绑定Li事件
		// 		this.liEvent();
		// 	}else{
		// 		Vcity._m.addClass('hide',this.ul);
		// 		Vcity._m.removeClass('hide',this.cityBox);
	
		// 		Vcity._m.removeClass('hide',this.myIframe);
	
		// 		this.changeIframe();
		// 	}
		// },
	
		/* IE6的改变遮罩SELECT 的 IFRAME尺寸大小 */
		changeIframe:function(){
			if(!this.isIE6)return;
			this.myIframe.style.width = this.rootDiv.offsetWidth + 'px';
			this.myIframe.style.height = this.rootDiv.offsetHeight + 'px';
		},
	
		/* *
		 * 特定键盘事件，上、下、Enter键
		 * @ KeyboardEvent
		 * */
	
		KeyboardEvent:function(event,keycode){
			var lis = Vcity._m.$('li',this.ul);
			var len = lis.length;
			switch(keycode){
				case 40: //向下箭头↓
					this.count++;
					if(this.count > len-1) this.count = 0;
					for(var i=0;i<len;i++){
						Vcity._m.removeClass('on',lis[i]);
					}
					Vcity._m.addClass('on',lis[this.count]);
					break;
				case 38: //向上箭头↑
					this.count--;
					if(this.count<0) this.count = len-1;
					for(i=0;i<len;i++){
						Vcity._m.removeClass('on',lis[i]);
					}
					Vcity._m.addClass('on',lis[this.count]);
					break;
				case 13: // enter键
					this.input.value = Vcity.regExChiese.exec(lis[this.count].innerHTML)[0];
					Vcity._m.addClass('hide',this.ul);
					Vcity._m.addClass('hide',this.ul);
					/* IE6 */
					Vcity._m.addClass('hide',this.myIframe);
					$(".city-select-box").css({borderColor:"#bbd4f2"});
					break;
				default:
					break;
			}
		},
	
		/* *
		 * 下拉列表的li事件
		 * @ liEvent
		 * */
	
		liEvent:function(){
			var that = this;
			var lis = Vcity._m.$('li',this.ul);
			for(var i = 0,n = lis.length;i < n;i++){
				Vcity._m.on(lis[i],'click',function(event){
					event = Vcity._m.getEvent(event);
					var target = Vcity._m.getTarget(event);
					that.input.value = Vcity.regExChiese.exec(target.innerHTML)[0];
					$('#cityCode').val($(this).attr("cc"));
					$('#provinceCode').val($(this).attr("pc"));
					//alert($(target).attr("pc"));
					Vcity._m.addClass('hide',that.ul);
					/* IE6 下拉菜单点击事件 */
					Vcity._m.addClass('hide',that.myIframe);
					$(".city-select-box").css({borderColor:"#bbd4f2"});
				});
				Vcity._m.on(lis[i],'mouseover',function(event){
					event = Vcity._m.getEvent(event);
					var target = Vcity._m.getTarget(event);
					Vcity._m.addClass('on',target);
				});
				Vcity._m.on(lis[i],'mouseout',function(event){
					event = Vcity._m.getEvent(event);
					var target = Vcity._m.getTarget(event);
					Vcity._m.removeClass('on',target);
				})
			}
		},
	
		/*自定义获取城市*/
		autoCityComplete : function(){
			var that = this;
			// $.getScript("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js", function(){
			// 	var city = "";
			// 	var flag = false;
			// 	if(typeof remote_ip_info!="undefined"){
			// 		city = remote_ip_info.city;
			// 		if (city != "" && city !=null && city !="undefined"){
			// 				that.input.value = city;
			// 				codeInfo = that.getCityCode(city);
			// 				$("#cityCode").val(codeInfo.split("_")[1]);
			// 				$("#provinceCode").val(codeInfo.split("_")[2]);
			// 				flag = true;
			// 				return false;
			// 			}
			// 		}
			// 	if (!flag){
					//sina API无返回或城市不在列表中:默认南京
					//$("#cityCode").val("320100");
					//$("#provinceCode").val("320000");
					// that.input.value = "中国银行"
					// var placeInput = that.input.placeholder,
					var valueInput = that.input.value;
					that.input.value = (valueInput == '')? '' : valueInput;
					return false;
				// }
			// })
		},
		//车险大厅城市数据验证
		validateCarCity: function (txtCarCity)
		{
			rt = false;
			carCitys = Vcity.allCity;
			for(var i=0;i<carCitys.length;i++)
			{
				if(txtCarCity.val() == Vcity.allCity[i].split("|")[0])
				{
					rt = true;
					break;
				}
			}
			txtCarCity.focus();
			return rt;
		},
	
		//车险大厅城市代码获取
	   getCityCode: function (txtCityName) 
		{
			carCitys = Vcity.allCity;
			var codeInfo = "";
			for(var i=0;i<carCitys.length;i++)
			{
				if(txtCityName == Vcity.allCity[i].split("|")[0])
				{
					codeInfo = Vcity.allCity[i].split("|")[2];
					break;
				}
			}
			return codeInfo;
		}   
		
	};
    return DataSelector;
});