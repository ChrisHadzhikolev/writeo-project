import React, { Component } from "react";

export default class ForbiddenComponent extends Component {
  render() {
    return (
      <div>
        <br />
        <br />
        <br />
        <hr />
        <center>
          <h1>HTTP Code 403</h1>
          <h1>Access Denied</h1>
        </center>
        <hr />
      </div>
    );
  }
}
