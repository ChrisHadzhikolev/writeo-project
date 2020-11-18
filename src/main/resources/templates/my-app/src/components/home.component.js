import React, { Component } from "react";

import BuyerService from "../services/buyer.service";

export default class Home extends Component {
  constructor(props) {
    super(props);

    this.state = {
      content: "",
      buyers: []
    };
  }
  componentDidMount() {
    BuyerService.getAll()
      .then((response) => {
        this.setState({
          buyers: response.data,
        });
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  }

  render() {
    return (
      <div className="container">
        <header className="jumbotron">
          <h3>{this.state.content}</h3>
        </header>
      </div>
    );
  }
}
