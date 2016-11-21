const {Editor, EditorState,ContentState, RichUtils} = Draft;
const {Switch ,Button,message } = antd;


class ReactRichEditor extends React.Component {
	  constructor(props) {
	    super(props);
	    var editorId = props.id;
	    if(!editorId){
	    	editorId = "editor";
	    }
	    var defaultValue = props.defaultValue;
	    var editorState = null;
	    if(!defaultValue){
	    	editorState = EditorState.createEmpty();
	    }else{
	    	editorState = EditorState.createWithContent(ContentState.createFromText(defaultValue));
	    }
	    this.state = {"editorState": editorState};
	    this.focus = () => this.refs[editorId].focus();
	    this.onChange = (editorState) => this.setState({editorState});

	    this.handleKeyCommand = (command) => this._handleKeyCommand(command);
	    this.toggleBlockType = (type) => this._toggleBlockType(type);
	    this.toggleInlineStyle = (style) => this._toggleInlineStyle(style);
	    this.getValue = () =>  this._getValue();
	    this.getPlainText = () => this._getPlainText();
	    this.clear = () => this._clear();
	  }
	  
	  _getPlainText(){
		  const {editorState} = this.state;
		  var contentState = editorState.getCurrentContent();
		  return contentState.getPlainText();
	  }
	  
	  _getValue(){
		  const {editorState} = this.state;
		  var contentState = editorState.getCurrentContent();
		  return this._getPlainText(contentState);
	  }

	  _clear(){
		  const editorState = EditorState.createEmpty();
		  this.setState({editorState});
	  }

	  _handleKeyCommand(command) {
	    const {editorState} = this.state;
	    const newState = RichUtils.handleKeyCommand(editorState, command);
	    if (newState) {
	      this.onChange(newState);
	      return true;
	    }
	    return false;
	  }

	  _toggleBlockType(blockType) {
	    this.onChange(
	      RichUtils.toggleBlockType(
	        this.state.editorState,
	        blockType
	      )
	    );
	  }

	  _toggleInlineStyle(inlineStyle) {
	    this.onChange(
	      RichUtils.toggleInlineStyle(
	        this.state.editorState,
	        inlineStyle
	      )
	    );
	  }

	  render() {
	    const {editorState} = this.state;

	    // If the user changes block type before entering any text, we can
	    // either style the placeholder or hide it. Let's just hide it now.
	    let className = 'RichEditor-editor';
	    var contentState = editorState.getCurrentContent();
	    if (!contentState.hasText()) {
	      if (contentState.getBlockMap().first().getType() !== 'unstyled') {
	        className += ' RichEditor-hidePlaceholder';
	      }
	    }

	    return (
	      <div className="RichEditor-root">
	        <BlockStyleControls
	          editorState={editorState}
	          onToggle={this.toggleBlockType}
	        />
	        <InlineStyleControls
	          editorState={editorState}
	          onToggle={this.toggleInlineStyle}
	        />
	        <div className={className} onClick={this.focus}>
	          <Editor
	            blockStyleFn={getBlockStyle}
	            customStyleMap={styleMap}
	            editorState={editorState}
	            handleKeyCommand={this.handleKeyCommand}
	            onChange={this.onChange}
	            placeholder={this.props.placeholder}
	            ref={this.props.id}
	            spellCheck={true}
	          />
	        </div>
	      </div>
	    );
	  }
	}

// Custom overrides for "code" style.
const styleMap = {
CODE: {
 backgroundColor: 'rgba(0, 0, 0, 0.05)',
 fontFamily: '"Inconsolata", "Menlo", "Consolas", monospace',
 fontSize: 16,
 padding: 2,
},
};

function getBlockStyle(block) {
switch (block.getType()) {
 case 'blockquote': return 'RichEditor-blockquote';
 default: return null;
}
}

class StyleButton extends React.Component {
constructor() {
 super();
 this.onToggle = (e) => {
   e.preventDefault();
   this.props.onToggle(this.props.style);
 };
}

render() {
 let className = 'RichEditor-styleButton';
 if (this.props.active) {
   className += ' RichEditor-activeButton';
 }

 return (
   <span className={className} onMouseDown={this.onToggle}>
     {this.props.label}
   </span>
 );
}
}

const BLOCK_TYPES = [
{label: '标题1', style: 'header-one'},
{label: '标题2', style: 'header-two'},
{label: '引用', style: 'blockquote'},
{label: '无序号排序', style: 'unordered-list-item'},
{label: '有序号排序', style: 'ordered-list-item'},
{label: '代码块', style: 'code-block'},
];

const BlockStyleControls = (props) => {
const {editorState} = props;
const selection = editorState.getSelection();
const blockType = editorState
 .getCurrentContent()
 .getBlockForKey(selection.getStartKey())
 .getType();

return (
 <div className="RichEditor-controls">
   {BLOCK_TYPES.map((type) =>
     <StyleButton
       active={type.style === blockType}
       label={type.label}
       onToggle={props.onToggle}
       style={type.style}
     />
   )}
 </div>
);
};

var INLINE_STYLES = [
{label: '粗体', style: 'BOLD'},
{label: '斜体', style: 'ITALIC'},
{label: '下划线', style: 'UNDERLINE'},
{label: '等宽字体', style: 'CODE'},
];

const InlineStyleControls = (props) => {
var currentStyle = props.editorState.getCurrentInlineStyle();
return (
 <div className="RichEditor-controls">
   {INLINE_STYLES.map(type =>
     <StyleButton
       active={currentStyle.has(type.style)}
       label={type.label}
       onToggle={props.onToggle}
       style={type.style}
     />
   )}
 </div>
);
};



const DemoEditor = React.createClass({
	
	getRichId : function(){
		const {id} = this.props;
		var richEditor = id + "_rich";
		return richEditor;
	},
	
	getValue : function(){
		const {getValue} = this.refs[this.getRichId()];
		var content = getValue();
		return content;
	},
	
	submit : function(){
		var content = this.getValue();
		const {sumbitFn} = this.props;
		sumbitFn(content);
	},
	
	clear:function(){
		const {clear} = this.refs[this.getRichId()];
		clear();
		message.info("清空成功");
	},
	
	render(){
		return (
				<div>
					<ReactRichEditor placeholder={this.props.placeholder} id={this.props.id} defaultValue={this.props.defaultValue} ref={this.getRichId()}/>
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
  <DemoEditor placeholder="请输入文本" id="editor" submitButtonName="提交" cancelButtonName="清空" defaultValue="<h1>我是你大哥o!!!</h1><br/>第二行了" sumbitFn={submitFn}/>,
  document.getElementById('example')
);
