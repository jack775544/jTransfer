import React, {Component} from 'react';
import $ from 'jquery'
import './App.css';
import common from './common.js'
import fileIcon from './icons/document.png'
import folderIcon from './icons/folder.png'

const api = "http://localhost:8080";
let pwd = "/home/jack/";

class ItemTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {items: []};
    }

    //return $.get(common.buildUrl("http://localhost:8080/ls", {path: encodeURI("/home/jack")}), function (r) { console.log(r) });

    componentDidMount() {
        $.get(common.buildUrl(api + "/ls", {path: encodeURI("/home/jack")}), function (r) {
            let items = r.items.map(function (x) {
                return {
                    fileName: x.filename[0],
                    createdDate: x.filename[2],
                    modifiedDate: x.filename[1],
                    size: x.filename[3],
                    type: x.filename[4]
                }
            });
            this.setState({items: items})
        }.bind(this));
    }

    render() {
        return (
            <ul className="items">
                {this.state.items.map(function (x) {
                    return (
                        <li key={pwd + x.fileName}>
                            <Item filename={x.fileName} createdDate={x.createdDate} modifiedDate={x.modifiedDate}
                                  size={x.size} type={x.type}/>
                        </li>
                    );
                })}
            </ul>
        );
    }
}

class Item extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            fileName: props.filename,
            createdDate: props.createdDate,
            modifiedDate: props.modifiedDate,
            size: props.size,
            type: props.type
        }
    }

    render() {
        let fileClass;
        let imgPath;
        if (this.state.type == 2) {
            fileClass = "folder";
            imgPath = folderIcon;
        } else {
            fileClass = "file";
            imgPath = fileIcon;
        }
        return (
            <div className="item">
                <a>
                    <img className={fileClass} src={imgPath}/>
                    <span className="fileName">{this.state.fileName}</span>
                </a>
            </div>
        );
    }
}

class App extends Component {
    handleUpdate() {
        this.forceUpdate();
    }

    // <img src={logo} className="App-logo" alt="logo"/>
    render() {
        return (
            <div className="App">
                <div className="App-header">
                    <h2>jTransfer</h2>
                </div>
                <ItemTable/>
            </div>
        );
    }
}

export default App;
