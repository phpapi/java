const {Editor, EditorState, RichUtils} = Draft;

class ReactRichEditor extends React.Component {
	  constructor(props) {
	    super(props);
	    this.state = {editorState: EditorState.createEmpty()};

	    this.focus = () => this.refs.editor.focus();
	    this.onChange = (editorState) => this.setState({editorState});

	    this.handleKeyCommand = (command) => this._handleKeyCommand(command);
	    this.toggleBlockType = (type) => this._toggleBlockType(type);
	    this.toggleInlineStyle = (style) => this._toggleInlineStyle(style);
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
	      React.createElement("div", {className: "RichEditor-root"}, 
	        React.createElement(BlockStyleControls, {
	          editorState: editorState, 
	          onToggle: this.toggleBlockType}
	        ), 
	        React.createElement(InlineStyleControls, {
	          editorState: editorState, 
	          onToggle: this.toggleInlineStyle}
	        ), 
	        React.createElement("div", {className: className, onClick: this.focus}, 
	          React.createElement(Editor, {
	            blockStyleFn: getBlockStyle, 
	            customStyleMap: styleMap, 
	            editorState: editorState, 
	            handleKeyCommand: this.handleKeyCommand, 
	            onChange: this.onChange, 
	            placeholder: this.props.placeholder, 
	            ref: this.props.id, 
	            spellCheck: true}
	          )
	        )
	      )
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
   React.createElement("span", {className: className, onMouseDown: this.onToggle}, 
     this.props.label
   )
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
 React.createElement("div", {className: "RichEditor-controls"}, 
   BLOCK_TYPES.map((type) =>
     React.createElement(StyleButton, {
       active: type.style === blockType, 
       label: type.label, 
       onToggle: props.onToggle, 
       style: type.style}
     )
   )
 )
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
 React.createElement("div", {className: "RichEditor-controls"}, 
   INLINE_STYLES.map(type =>
     React.createElement(StyleButton, {
       active: currentStyle.has(type.style), 
       label: type.label, 
       onToggle: props.onToggle, 
       style: type.style}
     )
   )
 )
);
};

const {Switch ,Button,message } = antd;

const DemoEditor = React.createClass({displayName: "DemoEditor",
	submit : function(){
		message.success("点击提交");
	},
	clear:function(){
		message.info("点击清空");
	},
	render(){
		return (
				React.createElement("div", null, 
					React.createElement(ReactRichEditor, {placeholder: this.props.placeholder, id: this.props.id}), 
					React.createElement(Button, {type: "primary", onClick: this.submit}, this.props.submitButtonName), 
					React.createElement(Button, {type: "ghost", onClick: this.clear}, this.props.cancelButtonName)
				)
		);
	}
});
ReactDOM.render(
  React.createElement(DemoEditor, {placeholder: "请输入文本", id: "richEditor", submitButtonName: "提交", cancelButtonName: "清空"}),
  document.getElementById('example')
);
