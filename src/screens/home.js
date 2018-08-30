import React, { Component } from 'react';
import { Link, Redirect } from 'react-router-dom';

import LoginContext from '../contexts/LoginContext'

import './styles.css'

class Home extends Component {
  render() {
    return (
      <LoginContext.Consumer>
        {value => value.loggedIn ?
          (
            <div className='page-container'>
              <h1>
                HOME
              </h1>
              <br/>
              <ul className='category-list'>
                <li className='category-list__item'>
                  <Link to={`/list/users`}>See users</Link>
                </li>
                <li className='category-list__item'>
                  <Link to={`/list/inventory`}>See inventory</Link>
                </li>
              </ul>
            </div>
          ) :
          <Redirect to='/login' />
        }
      </LoginContext.Consumer>
    );
  }
}

export default Home;