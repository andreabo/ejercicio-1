import React, { Component } from 'react';
import { Link, Redirect } from 'react-router-dom';
import axios from 'axios';

import LoginContext from '../contexts/LoginContext'

import { api } from '../api'

class EditItem extends Component {
  constructor(props) {
    super(props);
    this.state = {list: '', name: '', stock: '', data: []};
  }

  componentDidMount() {
    const that=this;
    this.setState({list: this.props.match.params.list});
    axios.get(`${api.url}${this.props.match.params.list}?id=${this.props.match.params.item}`, {
    })
      .then(function (response) {
        console.log(response);
        that.setState({ data: response.data, name: response.data[0].name, stock: response.data[0].stock });
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  handleNameChange = (event) => {
    this.setState({name: event.target.value});
  };

  handleStockChange = (event) => {
    this.setState({stock: event.target.value});
  };

  handleSubmit = (event) => {
    const that=this;
    const params = this.state.list === 'inventory' ? {name: this.state.name, stock: this.state.stock} : {name: this.state.name};
    axios.put(`${api.url}${this.props.match.params.list}/${this.props.match.params.item}`, params)
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
    const itemName = this.state.name;
    const itemStock = this.state.stock;
    return (
      <LoginContext.Consumer>
        {value => value.loggedIn ?
          (
            <div className='page-container'>
              <h1>
                EDIT
              </h1>
              <div>
                <div className='buttons-grp'>
                  <div className='button'>
                    <Link to={`/list/${this.props.match.params.list}`}>Back to {this.props.match.params.list}</Link>
                  </div>
                </div>
                <form className='add-item-form' onSubmit={this.handleSubmit}>
                  <input type="text" value={this.state.name} onChange={this.handleNameChange} />
                  {this.state.list === 'inventory' && <input type="number" value={this.state.stock} pattern="[0-9]*" onChange={this.handleStockChange} />}
                  <input type="submit" value="Update" />
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

export default EditItem;