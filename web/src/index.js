import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import './index.css';
import {Router, Route, Link, browserHistory} from 'react-router'

ReactDOM.render(
    <Router history={browserHistory}>
        <Route path='/' component={App}/>
    </Router>,
    document.getElementById('root')
);

/*
 ReactDOM.render(
 <App />,
 document.getElementById('root')
 );
 */