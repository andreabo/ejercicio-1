import React, { Component } from 'react';
import { Link, Redirect } from 'react-router-dom';
import axios from 'axios';

import LoginContext from '../contexts/LoginContext'

import { api } from '../api'

class ListItem extends Component {
  constructor(props) {
    super(props);
    this.state = {category: '', data: []};
  }

  componentDidMount() {
    const that=this;
    console.log(this.props);
    this.setState({category: this.props.match.params.list});
    axios.get(`${api.url}${this.props.match.params.list}`, {
    })
      .then(function (response) {
        console.log(response);
        that.setState({ data: response.data });
      })
      .catch(function (error) {
        console.log(error);
      })
  }

  goToEdit = (id) => {
    this.props.history.push(`/edit/${this.props.match.params.list}/${id}`)
  };

  confirmDelete = (id) => {
    if (window.confirm('Are you sure you wish to delete this item?')){
      axios.delete(`${api.url}${this.props.match.params.list}/${id}`, {
      })
        .then(function (response) {
          console.log(response);
          window.location.reload();
        })
        .catch(function (error) {
          console.log(error);
        })
    }
  };

  exportPDF = () => {
    axios.get(`${api.url}${this.props.match.params.list}/report`, {
    })
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      })
  };

  render() {
    const listItems = this.state.data.map((item) =>
      <li className='item-list__item' key={item.id.toString()}>
        {item.name}
        {this.state.category === 'inventory' && <div>Stock: {item.stock}</div>}
        <div>
          <button className='item-list__item__edit-button' onClick={(id) => this.goToEdit(item.id)}>
            Edit
          </button>
          <button className='item-list__item__delete-button' onClick={(id) => this.confirmDelete(item.id)}>
            Delete
          </button>
        </div>
      </li>
    );

    return (
      <LoginContext.Consumer>
        {value => value.loggedIn ?
          (
            <div className='page-container'>
              <h1>
                LIST
              </h1>
              <div className='buttons-grp'>
                <div className='button'>
                  <Link to={'/'}>Back Home</Link>
                </div>
                <div className='button'>
                  <Link to={`/new/${this.props.match.params.list}`}>Add {this.props.match.params.list}</Link>
                </div>
              </div>
              <ul className='item-list'>
                {listItems}
              </ul>
              <button onClick={this.exportPDF}>
                Export PDF
              </button>
            </div>
          ) :
          <Redirect to='/login' />
        }
      </LoginContext.Consumer>
    );
  }
}

export default ListItem;