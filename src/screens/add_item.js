import React, { Component } from 'react';
import { Link, Redirect } from 'react-router-dom';
import axios from 'axios';

import LoginContext from '../contexts/LoginContext'

import { api } from '../api'

class AddItem extends Component {
  constructor(props) {
    super(props);
    this.state = {value: '', item: '', stock: ''};
  }

  componentDidMount () {
    console.log(this.props.match.params.item);
    this.setState({item: this.props.match.params.item});
  }

  handleNameChange = (event) => {
    this.setState({value: event.target.value});
  };

  handleStockChange = (event) => {
    this.setState({stock: event.target.value});
  };

  handleSubmit = (event) => {
    const that=this;
    const params = this.state.item === 'inventory' ? {name: this.state.value, stock: this.state.stock} : {name: this.state.value};
    axios.post(`${api.url}${this.props.match.params.item}`, params)
      .then(function (response) {
        console.log(response);
        that.props.history.goBack();
      })
      .catch(function (error) {
        console.log(error);
      });
    event.preventDefault();
  };

  render() {
    return (
      <LoginContext.Consumer>
        {value => value.loggedIn ?
          (
            <div className='page-container'>
              <h1>
                ADD
              </h1>
              <div>
                <div className='buttons-grp'>
                  <div className='button'>
                    <Link to={`/list/${this.props.match.params.item}`}>Back to {this.props.match.params.item}</Link>
                  </div>
                </div>
                <form className='add-item-form' onSubmit={this.handleSubmit}>
                  <input type="text" value={this.state.value} placeholder={'NAME'} onChange={this.handleNameChange} />
                  {this.state.item === 'inventory' && <input type="number" value={this.state.stock} placeholder={'STOCK'} pattern="[0-9]*" onChange={this.handleStockChange} />}
                  <input type="submit" value="Submit" />
                </form>
              </div>
            </div>
          ) :
          <Redirect to='/login' />
        }
      </LoginContext.Consumer>
    );
  }
}

export default AddItem;