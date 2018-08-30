import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import axios from 'axios';

import LoginContext from '../contexts/LoginContext'

import { api } from '../api'

import './styles.css'

class Login extends Component {
  constructor() {
    super();
    this.state = {
      username: '',
      password: '',
      error: '',
    };
  }

  dismissError = () => {
    this.setState({ error: '' });
  };

  handleSubmit = (evt, context) =>  {
    evt.preventDefault();

    if (!this.state.username) {
      return this.setState({ error: 'Username is required' });
    }

    if (!this.state.password) {
      return this.setState({ error: 'Password is required' });
    }

    this.setState({ error: '' });
    console.log(`${api.url}admins?name=${this.state.username}&password=${this.state.password}`);
    const that=this;
    axios.get(`${api.url}admins?name=${this.state.username}&password=${this.state.password}`)
      .then(function (response) {
        // handle success
        console.log(response);
        if (response.data.length > 0) {
          // USER/PASS MATCH
          context.login(1);
        } else {
          that.setState({ error: 'User Name/Password combination not valid' });
        }
      })
      .catch(function (error) {
        // handle error
        console.log(error);
      })
      .then(function () {
        // always executed
      });
  };

  handleUserChange = (evt) =>  {
    this.setState({
      username: evt.target.value,
    });
  };

  handlePassChange = (evt) =>  {
    this.setState({
      password: evt.target.value,
    });
  };

  render() {
    return (
      <LoginContext.Consumer>
        {value => !value.loggedIn ?
          (
            <div className='page-container'>
              <form onSubmit={(e) => this.handleSubmit(e, value)}>
                {
                  this.state.error &&
                  <h3 onClick={this.dismissError}>
                    <button onClick={this.dismissError}>✖</button>
                    <br/>
                    {this.state.error}
                  </h3>
                }
                <label>User Name</label>
                <input type="text" data-test="username" value={this.state.username} onChange={this.handleUserChange} />

                <label>Password</label>
                <input type="password" data-test="password" value={this.state.password} onChange={this.handlePassChange} />

                <input type="submit" value="Log In" data-test="submit" />
              </form>
            </div>
          ) :
          <Redirect to='/' />
        }
      </LoginContext.Consumer>
    );
  }
}

export default Login;