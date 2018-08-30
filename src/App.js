import React, { Component } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom'

import Home from './screens/home';
import AddItem from './screens/add_item';
import ListItem from './screens/list_items';
import EditItem from './screens/edit_item';
import Login from './screens/login';
import LoginContext from './contexts/LoginContext'

import './App.css';

class App extends Component {
  state = {
    loggedIn: parseInt(window.sessionStorage.getItem('userLoggedIn'), 10),
  };

  logIn = (value) => {
    this.setState({loggedIn: value});
    window.sessionStorage.setItem('userLoggedIn', value.toString());
  };

  render() {
    return (
      <div>
        <button onClick={() => this.logIn(0)}>Log Out</button>
        <LoginContext.Provider value={{loggedIn: this.state.loggedIn, login: this.logIn}}>
          <BrowserRouter>
            <div>
              <Switch>
                <Route path="/list/:list" component={ListItem} />
                <Route path="/new/:item" component={AddItem} />
                <Route path="/edit/:list/:item" component={EditItem} />
                <Route path="/login" component={Login} />
                <Route path="/" component={Home} />
              </Switch>
            </div>
          </BrowserRouter>
        </LoginContext.Provider>
      </div>
    );
  }
}

export default App;
