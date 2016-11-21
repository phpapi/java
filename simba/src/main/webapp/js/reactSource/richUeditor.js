const {Switch ,Button,message } = antd;
var Ueditor = React.createClass({ 
    componentDidMount(){
        var editor = UE.getEditor(this.props.id, {
             // 工具栏
                toolbars: [[
                    'fullscreen', 'source', '|', 'undo', 'redo', '|',
                    'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch',  
                    '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                    'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                    'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                    'directionalityltr', 'directionalityrtl', 'indent', '|',
                    'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|', 
                    'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                    'simpleupload',  
                    'horizontal', 'date', 'time',  
                ]]
                ,lang:"zh-cn"
                // 字体
                ,'fontfamily':[
                   { label:'',name:'songti',val:'宋体,SimSun'},
                   { label:'',name:'kaiti',val:'楷体,楷体_GB2312, SimKai'},
                   { label:'',name:'yahei',val:'微软雅黑,Microsoft YaHei'},
                   { label:'',name:'heiti',val:'黑体, SimHei'},
                   { label:'',name:'lishu',val:'隶书, SimLi'},
                   { label:'',name:'andaleMono',val:'andale mono'},
                   { label:'',name:'arial',val:'arial, helvetica,sans-serif'},
                   { label:'',name:'arialBlack',val:'arial black,avant garde'},
                   { label:'',name:'comicSansMs',val:'comic sans ms'},
                   { label:'',name:'impact',val:'impact,chicago'},
                   { label:'',name:'timesNewRoman',val:'times new roman'}
                ]
                // 字号
                ,'fontsize':[10, 11, 12, 14, 16, 18, 20, 24, 36]
                , enableAutoSave : false
                , autoHeightEnabled : false
                , initialFrameHeight: this.props.height
                , initialFrameWidth: '100%'
        });
        var me = this;
        editor.ready( function( ueditor ) {
            var value = me.props.value?me.props.value:'<p></p>';
            editor.setContent(value); 
        }); 
    },
    render : function(){
        return (
             <script id={this.props.id} name="content" type="text/plain">
                  
             </script>
        )
    }
})

const DemoEditor = React.createClass({
	
	getValue : function(){
		const {id} = this.props;
		return UE.getEditor(id).getContent();
	},
	
	submit : function(){
		var content = this.getValue();
		const {sumbitFn} = this.props;
		sumbitFn(content);
	},
	
	clear:function(){
		const {id} = this.props;
		UE.getEditor(id).setContent('', false);
		message.info("清空成功");
	},
	
	render(){
		var height = !this.props.height ? 300 :this.props.height ;
		return (
				<div>
				 	<Ueditor value={this.props.defaultValue} id={this.props.id} height={height}/> ,
					<Button type="primary" onClick={this.submit}>{this.props.submitButtonName}</Button>
					<Button type="ghost" onClick={this.clear}>{this.props.cancelButtonName}</Button>
				</div>
		);
	}
	
});
const submitFn = (content)=>{
	message.success("提交内容：" + content);
}
ReactDOM.render(
  <DemoEditor  id="editor" submitButtonName="提交" cancelButtonName="清空" defaultValue="<h1>我是你大哥o!!!</h1><br/>第二行了" sumbitFn={submitFn}/>,
  document.getElementById('example')
);